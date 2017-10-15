package org.rookit.mongodb.queries;

import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.smof.collection.ParentQuery;

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
	
	public AlbumQuery createAlbumQuery(ParentQuery<Album> smofQuery) {
		return new AlbumQueryImpl(smofQuery);
	}
	
	public ArtistQuery createArtistQuery(ParentQuery<Artist> smofQuery) {
		return new ArtistQueryImpl(smofQuery);
	}
	
	public GenreQuery createGenreQuery(ParentQuery<Genre> smofQuery) {
		return new GenreQueryImpl(smofQuery);
	}
	
	public TrackQuery createTrackQuery(ParentQuery<Track> smofQuery) {
		return new TrackQueryImpl(smofQuery);
	}

	public PlaylistQuery createPlaylistQuery(ParentQuery<Playlist> smofQuery) {
		return new PlaylistQueryImpl(smofQuery);
	}

}
