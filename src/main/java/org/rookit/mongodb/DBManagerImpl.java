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
import org.rookit.mongodb.update.AlbumUpdateQuery;
import org.rookit.mongodb.update.ArtistUpdateQuery;
import org.rookit.mongodb.update.GenreUpdateQuery;
import org.rookit.mongodb.update.PlaylistUpdateQuery;
import org.rookit.mongodb.update.TrackUpdateQuery;
import org.rookit.mongodb.update.UpdateQueryFactory;
import org.rookit.mongodb.utils.DatabaseValidator;
import org.smof.collection.CollectionOptions;
import org.smof.collection.Smof;
import org.smof.collection.SmofUpdate;
import org.smof.exception.SmofException;
import org.smof.gridfs.SmofGridRef;

class DBManagerImpl implements DBManager{
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();

	private final Smof smof;
	private final QueryFactory queryFactory;
	private final UpdateQueryFactory updateFactory;

	DBManagerImpl(String host, int port, String databaseName) {
		this.smof = Smof.create(host, port, databaseName);
		this.queryFactory = QueryFactory.getDefault();
		this.updateFactory = UpdateQueryFactory.getDefault();
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

	@Override
	public AlbumQuery getAlbums() {
		return queryFactory.createAlbumQuery(smof.find(Album.class));
	}

	private CollectionOptions<Album> getAlbumOptions() {
		final CollectionOptions<Album> options = CollectionOptions.create();
		options.upsert(true);
		options.addPreHook(album -> {
			if(album.getCover() != null) {
				album.getCover().setBucketName(COVER_BUCKET);
			}
		});
		return options;
	}

	private CollectionOptions<Artist> getArtistOptions() {
		final CollectionOptions<Artist> options = CollectionOptions.create();
		options.upsert(true);
		options.addPreHook(artist -> {
			if(artist.getPicture() != null) {
				artist.getPicture().setBucketName(PICTURE_BUCKET);
			}
		});
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
		options.addPreHook(track -> {
			if(track.getPath() != null) {
				track.getPath().setBucketName(AUDIO_BUCKET);
			}
		});
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
		smof.loadBucket(AUDIO_BUCKET);
		smof.loadBucket(COVER_BUCKET);
		smof.loadBucket(PICTURE_BUCKET);
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
	public void replaceAlbum(Album album) {
		smof.replace(Album.class, album);
	}

	@Override
	public void replaceArtist(final Artist artist) {
		smof.replace(Artist.class, artist);
	}

	@Override
	public void replaceGenre(final Genre genre) {
		smof.replace(Genre.class, genre);
	}

	@Override
	public void updateIgnored(IgnoreField value) {
		smof.update(IgnoreField.class)
		.setUpsert(true)
		.increase(IgnoreField.OCCURRENCES, value.getOccurrences())
		.where()
		.fieldEq(IgnoreField.VALUE, value.getValue())
		.execute();
	}

	@Override
	public void replacePlaylist(Playlist playlist) {
		smof.replace(Playlist.class, playlist);
	}

	@Override
	public void replaceTrack(final Track track) {
		smof.replace(Track.class, track);
	}

	@Override
	public void updateTrackFormat(TrackFormat value) {
		smof.update(TrackFormat.class)
		.setUpsert(true)
		.increase(TrackFormat.OCCURRENCES, value.getOccurrences())
		.where()
		.fieldEq(TrackFormat.VALUE, value.getValue())
		.execute();
	}

	@Override
	public AlbumUpdateQuery updateAlbum() {
		final SmofUpdate<Album> update = smof.update(Album.class);
		return updateFactory.newAlbumUpdateQuery(update, getAlbums());
	}

	@Override
	public GenreUpdateQuery updateGenre() {
		final SmofUpdate<Genre> update = smof.update(Genre.class);
		return updateFactory.newGenreUpdateQuery(update, getGenres());
	}

	@Override
	public TrackUpdateQuery updateTrack() {
		final SmofUpdate<Track> update = smof.update(Track.class);
		return updateFactory.newTrackUpdateQuery(update, getTracks());
	}

	@Override
	public ArtistUpdateQuery updateArtist() {
		final SmofUpdate<Artist> update = smof.update(Artist.class);
		return updateFactory.newArtistUpdateQuery(update, getArtists());
	}

	@Override
	public PlaylistUpdateQuery updatePlaylist() {
		final SmofUpdate<Playlist> update = smof.update(Playlist.class);
		return updateFactory.newPlaylistUpdateQuery(update, getPlaylists());
	}
}
