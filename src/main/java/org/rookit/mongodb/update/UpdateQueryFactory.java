package org.rookit.mongodb.update;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.AlbumQuery;
import org.rookit.mongodb.queries.ArtistQuery;
import org.rookit.mongodb.queries.GenreQuery;
import org.rookit.mongodb.queries.PlaylistQuery;
import org.rookit.mongodb.queries.TrackQuery;

@SuppressWarnings("javadoc")
public class UpdateQueryFactory {
	
	private static UpdateQueryFactory singleton;
	
	public static UpdateQueryFactory getDefault() {
		if(singleton == null) {
			singleton = new UpdateQueryFactory();
		}
		return singleton;
	}
	
	private UpdateQueryFactory() {}
	
	public TrackUpdateQuery newTrackUpdateQuery(UpdateOperations<Track> update, TrackQuery filter) {
		return new TrackUpdateQueryImpl(update, filter);
	}
	
	public AlbumUpdateQuery newAlbumUpdateQuery(UpdateOperations<Album> update, AlbumQuery filter) {
		return new AlbumUpdateQueryImpl(update, filter);
	}
	
	public ArtistUpdateQuery newArtistUpdateQuery(UpdateOperations<Artist> update, ArtistQuery filter) {
		return new ArtistUpdateQueryImpl(update, filter);
	}
	
	public GenreUpdateQuery newGenreUpdateQuery(UpdateOperations<Genre> update, GenreQuery filter) {
		return new GenreUpdateQueryImpl(update, filter);
	}
	
	public PlaylistUpdateQuery newPlaylistUpdateQuery(UpdateOperations<Playlist> update, PlaylistQuery filter) {
		return new PlaylistUpdateQueryImpl(update, filter);
	}

}
