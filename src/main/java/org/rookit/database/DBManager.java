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
package org.rookit.database;

import java.io.Closeable;

import org.rookit.database.queries.AlbumQuery;
import org.rookit.database.queries.ArtistQuery;
import org.rookit.database.queries.GenreQuery;
import org.rookit.database.queries.TrackQuery;
import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface DBManager extends Closeable{
	
	String DB_NAME = "AnalyzeDB";
	String GENRES = "Genres";
	String ARTISTS = "Artists";
	String ALBUMS = "Albums";
	String TRACKS = "Tracks";
	String TRACKPATHS = "Track_paths";
	
	String IGNORED = "Ignored";
	
	String SERVICES = "Services";
	
	
	void reset();
	
	void init();
	void create();
	void clear();

	void addAlbum(Album album);
	void addGenre(Genre genre);
	void addTrack(Track track);
	void addArtist(Artist artist);
	
	void updateAlbum(Album album);
	void updateGenre(Genre genre);
	void updateTrack(Track trak);
	void updateArtist(Artist artist);
	
	ArtistQuery getArtists();
	GenreQuery getGenres();
	TrackQuery getTracks();
	AlbumQuery getAlbums();

	@Override
	boolean equals(Object object);
	
	@Override
	int hashCode();
}
