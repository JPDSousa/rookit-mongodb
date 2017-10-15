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
import java.io.InputStream;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.GenreFactory;
import org.rookit.dm.parser.IgnoreField;
import org.rookit.dm.parser.TrackFormat;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.play.PlaylistFactory;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.mongodb.queries.AlbumQuery;
import org.rookit.mongodb.queries.ArtistQuery;
import org.rookit.mongodb.queries.GenreQuery;
import org.rookit.mongodb.queries.PlaylistQuery;
import org.rookit.mongodb.queries.QueryFactory;
import org.rookit.mongodb.queries.TrackQuery;
import org.rookit.mongodb.utils.DatabaseValidator;
import org.smof.collection.CollectionOptions;
import org.smof.collection.Smof;
import org.smof.exception.SmofException;
import org.smof.gridfs.SmofGridRef;

class DBManagerImpl implements DBManager{
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();

	private final Smof smof;
	private final QueryFactory queryFactory;

	DBManagerImpl(String host, int port, String databaseName) {
		smof = Smof.create(host, port, databaseName);
		this.queryFactory = QueryFactory.getDefault();
	}

	@Override
	public void addAlbum(final Album album) {
		smof.insert(album);
	}

	@Override
	public void addArtist(final Artist artist) {
		smof.insert(artist);
	}
	
	@Override
	public void addGenre(final Genre genre) {
		smof.insert(genre);
	}

	@Override
	public void addPlaylist(Playlist playlist) {
		smof.insert(playlist);
	}
	
	@Override
	public void addTrack(final Track track) {
		smof.insert(track);
	}

	@Override
	public void clear() {
		smof.dropAllCollections();
		smof.dropAllBuckets();
	}

	@Override
	public void close() throws IOException {
		this.smof.close();
	}

	@Override
	public byte[] download(SmofGridRef ref) {
		try {
			return IOUtils.toByteArray(stream(ref));
		} catch (IOException e) {
			VALIDATOR.handleIOException(e);
			return null;
		}
	}

	private CollectionOptions<Album> getAlbumOptions() {
		final CollectionOptions<Album> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	@Override
	public AlbumQuery getAlbums() {
		return queryFactory.createAlbumQuery(smof.find(Album.class));
	}

	private CollectionOptions<Artist> getArtistOptions() {
		final CollectionOptions<Artist> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	@Override
	public ArtistQuery getArtists() {
		return queryFactory.createArtistQuery(smof.find(Artist.class));
	}


	@Override
	public GenreQuery getGenres() {
		return queryFactory.createGenreQuery(smof.find(Genre.class));
	}

	private CollectionOptions<Genre> getGenresOptions() {
		final CollectionOptions<Genre> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	@Override
	public int getIgnoredOccurrences(String value) {
		final IgnoreField result = smof.find(IgnoreField.class)
				.withFieldEquals(IgnoreField.VALUE, value.toLowerCase())
				.results()
				.first();
		if(result == null) {
			return 0;
		}
		return result.getOccurrences();
	}

	private CollectionOptions<IgnoreField> getIngoredOptions() {
		final CollectionOptions<IgnoreField> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	private CollectionOptions<Playlist> getPlaylistOptions() {
		final CollectionOptions<Playlist> options = CollectionOptions.create();
		return options;
	}

	@Override
	public PlaylistQuery getPlaylists() {
		return queryFactory.createPlaylistQuery(smof.find(Playlist.class));
	}

	private CollectionOptions<TrackFormat> getTFormatOptions() {
		final CollectionOptions<TrackFormat> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	@Override
	public int getTrackFormatOccurrences(String value) {
		final TrackFormat result = smof.find(TrackFormat.class)
				.withFieldEquals(TrackFormat.VALUE, value.toLowerCase())
				.results().first();
		return result == null ? 0 : result.getOccurrences();
	}

	private CollectionOptions<Track> getTrackOptions() {
		final CollectionOptions<Track> options = CollectionOptions.create();
		options.upsert(false);
		return options;
	}

	@Override
	public TrackQuery getTracks() {
		return queryFactory.createTrackQuery(smof.find(Track.class));
	}

	@Override
	public void init() throws SmofException {
		smof.loadCollection(TRACKS, Track.class, TrackFactory.getDefault(), getTrackOptions());
		smof.loadCollection(ALBUMS, Album.class, AlbumFactory.getDefault(), getAlbumOptions());
		smof.loadCollection(ARTISTS, Artist.class, ArtistFactory.getDefault(), getArtistOptions());
		smof.loadCollection(GENRES, Genre.class, GenreFactory.getDefault(), getGenresOptions());
		smof.loadCollection(PLAYLISTS, Playlist.class, PlaylistFactory.getDefault(), getPlaylistOptions());
		smof.loadCollection(IGNORED, IgnoreField.class, getIngoredOptions());
		smof.loadCollection(TRACK_FORMATS, TrackFormat.class, getTFormatOptions());
		smof.loadBucket(Track.AUDIO);
		smof.loadBucket(Album.COVER_BUCKET);
		smof.loadBucket(Artist.PICTURE_BUCKET);
	}

	@Override
	public void loadBucket(String bucketName) {
		smof.loadBucket(bucketName);
	}

	@Override
	public void reset() throws SmofException {
		clear();
		init();
	}

	@Override
	public InputStream stream(SmofGridRef ref) {
		return smof.getGridStreamManager().download(ref);
	}

	@Override
	public Stream<String> streamTrackFormats() {
		return smof.find(TrackFormat.class)
				.results()
				.stream()
				.map(t -> t.getValue());
	}

	@Override
	public void updateAlbum(Album album) {
		smof.update(Album.class)
		.setUpsert(true)
		.fromElement(album);
	}

	@Override
	public void updateArtist(final Artist artist) {
		//		updateGenreCollection(artist.getGenres());
		//		artists.update(artist);
	}

	@Override
	public void updateGenre(final Genre genre) {
		//		genres.update(genre);
	}

	@Override
	public void updateIgnored(IgnoreField value) {
		smof.update(IgnoreField.class)
		.setUpsert(true)
		.increase(value.getOccurrences(), IgnoreField.OCCURRENCES)
		.where()
		.fieldEq(IgnoreField.VALUE, value.getValue())
		.execute();
	}

	@Override
	public void updatePlaylist(Playlist playlist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTrack(final Track track) {
		//		updateGenreCollection(track.getGenres());
		//		updateArtistCollection(track.getMainArtists());
		//		updateArtistCollection(track.getFeatures());
		//		updateArtistCollection(track.getVersionArtists());
		//		updateArtistCollection(track.getProducers());
		//		tracks.update(track);
	}

	@Override
	public void updateTrackFormat(TrackFormat value) {
		smof.update(TrackFormat.class)
		.setUpsert(true)
		.increase(value.getOccurrences(), TrackFormat.OCCURRENCES)
		.where()
		.fieldEq(TrackFormat.VALUE, value.getValue())
		.execute();
	}
}
