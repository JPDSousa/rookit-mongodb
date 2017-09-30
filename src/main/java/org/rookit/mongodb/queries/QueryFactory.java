package org.rookit.mongodb.queries;

import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.track.Track;
import org.smof.collection.SmofQuery;

@SuppressWarnings("javadoc")
public class QueryFactory {
	
	private static QueryFactory singleton;
	
	public static final QueryFactory getDefault() {
		if(singleton == null) {
			singleton = new QueryFactory();
		}
		return singleton;
	}
	
	private QueryFactory(){}
	
	public AlbumQuery createAlbumQuery(SmofQuery<Album> smofQuery) {
		return new AlbumQueryImpl(smofQuery);
	}
	
	public ArtistQuery createArtistQuery(SmofQuery<Artist> smofQuery) {
		return new ArtistQueryImpl(smofQuery);
	}
	
	public GenreQuery createGenreQuery(SmofQuery<Genre> smofQuery) {
		return new GenreQueryImpl(smofQuery);
	}
	
	public TrackQuery createTrackQuery(SmofQuery<Track> smofQuery) {
		return new TrackQueryImpl(smofQuery);
	}

}
