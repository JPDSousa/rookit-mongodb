/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.mongodb;

import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.Converters;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.MapperOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.parser.IgnoredField;
import org.rookit.api.dm.parser.TrackFormat;
import org.rookit.dm.parser.IgnoredFieldImpl;
import org.rookit.dm.parser.TrackFormatImpl;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.storage.DBManager;
import org.rookit.api.storage.queries.AlbumQuery;
import org.rookit.api.storage.queries.ArtistQuery;
import org.rookit.api.storage.queries.GenreQuery;
import org.rookit.api.storage.queries.PlaylistQuery;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.api.storage.update.AlbumUpdateQuery;
import org.rookit.api.storage.update.ArtistUpdateQuery;
import org.rookit.api.storage.update.GenreUpdateQuery;
import org.rookit.api.storage.update.PlaylistUpdateQuery;
import org.rookit.api.storage.update.TrackUpdateQuery;
import org.rookit.api.storage.utils.Order;
import org.rookit.mongodb.gridfs.Buckets;
import org.rookit.mongodb.gridfs.GridFsBiStreamConverter;
import org.rookit.mongodb.morphia.DurationConverter;
import org.rookit.mongodb.morphia.IndexCache;
import org.rookit.mongodb.queries.QueryFactory;
import org.rookit.mongodb.update.UpdateQueryFactory;
import org.rookit.mongodb.utils.DatabaseValidator;
import org.rookit.mongodb.utils.OrderImpl;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

@SuppressWarnings("javadoc")
public class RookitMorphia implements DBManager {
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();

	// Morphia
	private final Morphia morphia;
	private final Datastore datastore;
	// MongoDB
	private final MongoClient client;
	private final MongoDatabase rawDb;
	
	private final IndexCache indexCache;
	
	private final QueryFactory queryFactory;
	private final UpdateQueryFactory updateFactory;
	
	private final RookitFactories dmFactories;

	@Inject
	RookitMorphia(final RookitFactories dmFactories,
			final MongoClient mongoClient,
			final MongoDatabase database,
			final Buckets bucketCache) {
		indexCache = new IndexCache();
		
		this.client = mongoClient;
		this.rawDb = database;
		
		this.morphia = new Morphia();
		this.datastore = morphia.createDatastore(client, database.getName());
		
		this.queryFactory = QueryFactory.create(datastore);
		this.updateFactory = UpdateQueryFactory.getDefault();
		this.dmFactories = dmFactories;
		
		final Mapper mapper = morphia.getMapper();
		final MapperOptions options = mapper.getOptions();
		final Converters converters = mapper.getConverters();
		options.setMapSubPackages(true);
		morphia.mapPackage(RookitModel.class.getPackage().getName());
		converters.addConverter(GridFsBiStreamConverter.create(bucketCache));
		converters.addConverter(DurationConverter.getDefault());
		
		datastore.ensureIndexes();
	}
	
	@SuppressWarnings("unchecked")
	private void save(RookitModel element) {
		final Mapper mapper = morphia.getMapper();
		final MongoCollection<Document> collection = rawDb.getCollection(
				mapper.getCollectionName(element));
		final Document update = new Document(morphia.toDBObject(element).toMap());
		final Bson query = indexCache.createUniqueQuery(element.getClass(), update);
		final FindOneAndReplaceOptions options = new FindOneAndReplaceOptions();
		options.upsert(true);
		options.returnDocument(ReturnDocument.AFTER);
		update.remove(RookitModel.ID);
		final Document result = collection.findOneAndReplace(query, update, options);
		element.setId(result.getObjectId(RookitModel.ID));
	}

	@Override
	public void addAlbum(final Album album) {
		for(TrackSlot slot : album.getTracks()) {
			addTrack(slot.getTrack());
		}
		for(Artist artist : album.getArtists()) {
			addArtist(artist);
		}
		for(Genre genre : album.getGenres()) {
			addGenre(genre);
		}
		save(album);
	}

	@Override
	public void addArtist(final Artist artist) {
		artist.getGenres().forEach(this::addGenre);
		save(artist);
	}

	@Override
	public void addGenre(final Genre genre) {
		save(genre);
	}

	@Override
	public void addPlaylist(Playlist playlist) {
		if(playlist instanceof StaticPlaylist) {
			((StaticPlaylist) playlist).getTracks().forEach(this::addTrack);
		}
		save(playlist);
	}

	@Override
	public void addTrack(final Track track) {
		track.getGenres().forEach(this::addGenre);
		track.getMainArtists().forEach(this::addArtist);
		track.getFeatures().forEach(this::addArtist);
		track.getProducers().forEach(this::addArtist);
		if(track.isVersionTrack()) {
			final VersionTrack versionTrack = track.getAsVersionTrack();
			addTrack(versionTrack.getOriginal());
			versionTrack.getVersionArtists().forEach(this::addArtist);
		}
		save(track);
	}

	@Override
	public void clear() {
		rawDb.drop();
	}

	@Override
	public void close() throws IOException {
		client.close();
	}

	@Override
	public AlbumQuery getAlbums() {
		return queryFactory.createAlbumQuery(datastore.find(Album.class));
	}

	@Override
	public ArtistQuery getArtists() {
		return queryFactory.createArtistQuery(datastore.find(Artist.class));
	}


	@Override
	public GenreQuery getGenres() {
		return queryFactory.createGenreQuery(datastore.find(Genre.class));
	}

	@Override
	public PlaylistQuery getPlaylists() {
		return queryFactory.createPlaylistQuery(datastore.find(Playlist.class));
	}

	@Override
	public int getIgnoredOccurrences(String value) {
		final IgnoredFieldImpl result = datastore.find(IgnoredFieldImpl.class)
				.field(IgnoredFieldImpl.VALUE).equal(value.toLowerCase())
				.get();
		return result == null ? 0 : result.getOccurrences();
	}

	@Override
	public int getTrackFormatOccurrences(String value) {
		final TrackFormatImpl result = datastore.find(TrackFormatImpl.class)
				.field(TrackFormatImpl.VALUE).equal(value.toLowerCase())
				.get();
		return result == null ? 0 : result.getOccurrences();
	}

	@Override
	public TrackQuery getTracks() {
		return queryFactory.createTrackQuery(datastore.find(Track.class));
	}

	@Override
	public void init() {
		VALIDATOR.info("Initializing database module");
	}

	@Override
	public synchronized void reset() {
		clear();
		init();
	}

	@Override
	public Stream<String> streamTrackFormats() {
		return StreamSupport.stream(datastore.find(TrackFormatImpl.class)
				.fetch()
				.spliterator(), false)
				.map(TrackFormatImpl::getValue);
	}

	@Override
	public void replaceAlbum(Album album) {
		addAlbum(album);
	}

	@Override
	public void replaceArtist(final Artist artist) {
		addArtist(artist);
	}

	@Override
	public void replaceGenre(final Genre genre) {
		addGenre(genre);
	}

	@Override
	public void replacePlaylist(Playlist playlist) {
		addPlaylist(playlist);
	}

	@Override
	public void replaceTrack(final Track track) {
		addTrack(track);
	}

	@Override
	public void updateIgnored(IgnoredField value) {
		final UpdateOperations<IgnoredFieldImpl> update = datastore.createUpdateOperations(IgnoredFieldImpl.class)
				.inc(IgnoredFieldImpl.OCCURRENCES, value.getOccurrences());
		final Query<IgnoredFieldImpl> query = datastore.find(IgnoredFieldImpl.class)
				.field(IgnoredFieldImpl.VALUE).equal(value.getValue());
		datastore.update(query, update);
	}

	@Override
	public void updateTrackFormat(TrackFormat value) {
		final UpdateOperations<TrackFormatImpl> update = datastore.createUpdateOperations(TrackFormatImpl.class)
				.inc(TrackFormatImpl.OCCURRENCES, value.getOccurrences());
		final Query<TrackFormatImpl> query = datastore.find(TrackFormatImpl.class)
				.field(TrackFormatImpl.VALUE).equal(value.getValue());
		datastore.update(query, update);
	}

	@Override
	public AlbumUpdateQuery updateAlbum() {
		final UpdateOperations<Album> update = datastore.createUpdateOperations(Album.class);
		return updateFactory.newAlbumUpdateQuery(update, getAlbums());
	}

	@Override
	public GenreUpdateQuery updateGenre() {
		final UpdateOperations<Genre> update = datastore.createUpdateOperations(Genre.class);
		return updateFactory.newGenreUpdateQuery(update, getGenres());
	}

	@Override
	public TrackUpdateQuery updateTrack() {
		final UpdateOperations<Track> update = datastore.createUpdateOperations(Track.class);
		return updateFactory.newTrackUpdateQuery(update, getTracks());
	}

	@Override
	public ArtistUpdateQuery updateArtist() {
		final UpdateOperations<Artist> update = datastore.createUpdateOperations(Artist.class);
		return updateFactory.newArtistUpdateQuery(update, getArtists());
	}

	@Override
	public PlaylistUpdateQuery updatePlaylist() {
		final UpdateOperations<Playlist> update = datastore.createUpdateOperations(Playlist.class);
		return updateFactory.newPlaylistUpdateQuery(update, getPlaylists());
	}

	@Override
	public RookitFactories getFactories() {
		return dmFactories;
	}

	@Override
	public Order newOrder() {
		return new OrderImpl();
	}

}
