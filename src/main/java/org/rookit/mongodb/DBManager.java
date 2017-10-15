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
import java.io.InputStream;
import java.util.stream.Stream;

import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.parser.IgnoreField;
import org.rookit.dm.parser.TrackFormat;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.AlbumQuery;
import org.rookit.mongodb.queries.ArtistQuery;
import org.rookit.mongodb.queries.GenreQuery;
import org.rookit.mongodb.queries.TrackQuery;
import org.smof.gridfs.SmofGridRef;

@SuppressWarnings("javadoc")
public interface DBManager extends Closeable{
	
	String HOST = "localhost";
	int PORT = 27017;
	String DB_NAME = "rookit";
	
	static DBManager open(String host, int port, String dbName) {
		return new DBManagerImpl(host, port, dbName);
	}
	
	static DBManager open() {
		return open(HOST, PORT, DB_NAME);
	}
	
	String GENRES = "Genres";
	String ARTISTS = "Artists";
	String ALBUMS = "Albums";
	String TRACKS = "Tracks";
	String PLAYLISTS = "Playlists";
	
	String IGNORED = "Ignored";
	String TRACK_FORMATS = "TFormats";
	
	void reset();
	
	void init();
	void clear();
	
	void loadBucket(String bucketName);
	byte[] download(SmofGridRef ref);
	InputStream stream(SmofGridRef ref);

	void addAlbum(Album album);
	void addGenre(Genre genre);
	void addTrack(Track track);
	void addArtist(Artist artist);
	
	void updateAlbum(Album album);
	void updateGenre(Genre genre);
	void updateTrack(Track trak);
	void updateArtist(Artist artist);
	void updateIgnored(IgnoreField value);
	void updateTrackFormat(TrackFormat value);
	
	ArtistQuery getArtists();
	GenreQuery getGenres();
	TrackQuery getTracks();
	AlbumQuery getAlbums();

	int getIgnoredOccurrences(String value);
	int getTrackFormatOccurrences(String value);
	Stream<String> streamTrackFormats();
	
	@Override
	boolean equals(Object object);
	
	@Override
	int hashCode();
}
