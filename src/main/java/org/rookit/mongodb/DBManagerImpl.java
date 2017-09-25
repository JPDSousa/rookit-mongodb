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

import org.apache.commons.io.IOUtils;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.GenreFactory;
import org.rookit.dm.parser.IgnoreField;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.mongodb.queries.AlbumQuery;
import org.rookit.mongodb.queries.ArtistQuery;
import org.rookit.mongodb.queries.GenreQuery;
import org.rookit.mongodb.queries.TrackQuery;
import org.rookit.mongodb.utils.DatabaseValidator;
import org.smof.collection.CollectionOptions;
import org.smof.collection.Smof;
import org.smof.collection.SmofQuery;
import org.smof.exception.SmofException;
import org.smof.gridfs.SmofGridRef;

class DBManagerImpl implements DBManager{
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();

	private final Smof smof;

	DBManagerImpl(String host, int port, String databaseName) {
		smof = Smof.create(host, port, databaseName);
	}

	@Override
	public void reset() throws SmofException {
		clear();
		init();
	}

	@Override
	public void init() throws SmofException {
		smof.loadCollection(TRACKS, Track.class, TrackFactory.getDefault(), getTrackOptions());
		smof.loadCollection(ALBUMS, Album.class, AlbumFactory.getDefault(), getAlbumOptions());
		smof.loadCollection(ARTISTS, Artist.class, ArtistFactory.getDefault(), getArtistOptions());
		smof.loadCollection(GENRES, Genre.class, GenreFactory.getDefault(), getGenresOptions());
		smof.loadCollection(IGNORED, IgnoreField.class, getIngoredOptions());
		smof.loadBucket(Track.AUDIO);
	}
	
	private CollectionOptions<IgnoreField> getIngoredOptions() {
		final CollectionOptions<IgnoreField> options = CollectionOptions.create();
		return options;
	}

	private CollectionOptions<Genre> getGenresOptions() {
		final CollectionOptions<Genre> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	private CollectionOptions<Artist> getArtistOptions() {
		final CollectionOptions<Artist> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	private CollectionOptions<Album> getAlbumOptions() {
		final CollectionOptions<Album> options = CollectionOptions.create();
		options.upsert(true);
		return options;
	}

	private CollectionOptions<Track> getTrackOptions() {
		final CollectionOptions<Track> options = CollectionOptions.create();
		options.upsert(false);
		return options;
	}

	@Override
	public void clear() {
		smof.dropAllCollections();
		smof.dropAllBuckets();
	}

	@Override
	public void loadBucket(String bucketName) {
		smof.loadBucket(bucketName);
	}

	@Override
	public void addAlbum(final Album album) {
		//		for(Genre genre : album.getGenres()) {
		//			addGenre(genre);
		//		}
		//		for(Artist artist : album.getArtists()) {
		//			addArtist(artist);
		//		}
		//
		//		for(Track track : album.getTracks()) {
		//			addTrack(track);
		//		}
		//
		//		albums.insert(album);
		//		tracks.updateAlbumId(album.getId(), album.getTracks());
		smof.insert(album);
	}


	@Override
	public void addGenre(final Genre genre) {
		//		genres.add(genre);
		smof.insert(genre);
	}

	@Override
	public void addTrack(final Track track) {
		smof.insert(track);
	}

	@Override
	public void addArtist(final Artist artist) {
		smof.insert(artist);
	}

	@Override
	public void updateAlbum(Album album) {
		smof.update(Album.class)
		.setUpsert(true)
		.fromElement(album);
	}

	@Override
	public void updateGenre(final Genre genre) {
		//		genres.update(genre);
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
	public void updateArtist(final Artist artist) {
		//		updateGenreCollection(artist.getGenres());
		//		artists.update(artist);
	}

	@Override
	public ArtistQuery getArtists() {
		final SmofQuery<Artist> artistQuery = smof.find(Artist.class);
		return new ArtistQuery(artistQuery);
	}

	@Override
	public GenreQuery getGenres() {
		final SmofQuery<Genre> genreQuery = smof.find(Genre.class);
		return new GenreQuery(genreQuery);
	}

	@Override
	public TrackQuery getTracks() {
		final SmofQuery<Track> trackQuery = smof.find(Track.class);
		return new TrackQuery(trackQuery);
	}

	@Override
	public AlbumQuery getAlbums() {
		final SmofQuery<Album> albumQuery = smof.find(Album.class);
		return new AlbumQuery(albumQuery);
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
	public int getIgnoredOccurrences(String value) {
		final SmofQuery<IgnoreField> query = smof.find(IgnoreField.class);
		query.withField(IgnoreField.VALUE, value.toLowerCase());
		final IgnoreField result = query.results().first();
		if(result == null) {
			return 0;
		}
		return result.getOccurrences();
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

	@Override
	public InputStream stream(SmofGridRef ref) {
		return smof.getGridStreamManager().download(ref);
	}
}