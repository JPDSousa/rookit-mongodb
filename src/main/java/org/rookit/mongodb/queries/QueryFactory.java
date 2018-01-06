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
package org.rookit.mongodb.queries;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public class QueryFactory {
	
	public static final QueryFactory create(Datastore datastore) {
		return new QueryFactory(datastore);
	}
	
	private final Datastore datastore;
	
	private QueryFactory(Datastore datastore){
		this.datastore = datastore;
	}
	
	public AlbumQuery createAlbumQuery(Query<Album> smofQuery) {
		return new MorphiaAlbumQuery(datastore, smofQuery.disableValidation());
	}
	
	public ArtistQuery createArtistQuery(Query<Artist> smofQuery) {
		return new MorphiaArtistQuery(datastore, smofQuery.disableValidation());
	}
	
	public GenreQuery createGenreQuery(Query<Genre> smofQuery) {
		return new MorphiaGenreQuery(datastore, smofQuery.disableValidation());
	}
	
	public TrackQuery createTrackQuery(Query<Track> smofQuery) {
		return new MorphiaTrackQuery(datastore, smofQuery.disableValidation());
	}

	public PlaylistQuery createPlaylistQuery(Query<Playlist> smofQuery) {
		return new MorphiaPlaylistQuery(datastore, smofQuery.disableValidation());
	}

}
