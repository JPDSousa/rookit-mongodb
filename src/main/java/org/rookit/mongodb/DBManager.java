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

import java.io.Closeable;
import java.util.stream.Stream;

import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.parser.IgnoreField;
import org.rookit.dm.parser.TrackFormat;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.AlbumQuery;
import org.rookit.mongodb.queries.ArtistQuery;
import org.rookit.mongodb.queries.GenreQuery;
import org.rookit.mongodb.queries.PlaylistQuery;
import org.rookit.mongodb.queries.TrackQuery;
import org.rookit.mongodb.update.AlbumUpdateQuery;
import org.rookit.mongodb.update.ArtistUpdateQuery;
import org.rookit.mongodb.update.GenreUpdateQuery;
import org.rookit.mongodb.update.PlaylistUpdateQuery;
import org.rookit.mongodb.update.TrackUpdateQuery;

@SuppressWarnings("javadoc")
public interface DBManager extends Closeable{
	
	String HOST = "localhost";
	int PORT = 27017;
	String DB_NAME = "rookit";
	
	static DBManager open(String host, int port, String dbName) {
		return new RookitMorphia(host, port, dbName);
	}
	
	static DBManager open() {
		return open(HOST, PORT, DB_NAME);
	}
	
	String AUDIO_BUCKET = "audio";
	String COVER_BUCKET = "album_covers";
	String PICTURE_BUCKET = "artist_pictures";
	
	void reset();
	
	void init();
	void clear();
	
	void addAlbum(Album album);
	void addGenre(Genre genre);
	void addTrack(Track track);
	void addArtist(Artist artist);
	void addPlaylist(Playlist playlist);
	
	void replaceAlbum(Album album);
	AlbumUpdateQuery updateAlbum();
	void replaceGenre(Genre genre);
	GenreUpdateQuery updateGenre();
	void replaceTrack(Track track);
	TrackUpdateQuery updateTrack();
	void replaceArtist(Artist artist);
	ArtistUpdateQuery updateArtist();
	void replacePlaylist(Playlist playlist);
	PlaylistUpdateQuery updatePlaylist();
	void updateIgnored(IgnoreField value);
	void updateTrackFormat(TrackFormat value);
	
	ArtistQuery getArtists();
	GenreQuery getGenres();
	TrackQuery getTracks();
	AlbumQuery getAlbums();
	PlaylistQuery getPlaylists();

	int getIgnoredOccurrences(String value);
	int getTrackFormatOccurrences(String value);
	Stream<String> streamTrackFormats();
	
	@Override
	boolean equals(Object object);
	
	@Override
	int hashCode();
}
