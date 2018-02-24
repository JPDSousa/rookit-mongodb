package org.rookit.mongodb.update;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.queries.AlbumQuery;
import org.rookit.api.storage.queries.ArtistQuery;
import org.rookit.api.storage.queries.GenreQuery;
import org.rookit.api.storage.queries.PlaylistQuery;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.api.storage.update.AlbumUpdateQuery;
import org.rookit.api.storage.update.ArtistUpdateQuery;
import org.rookit.api.storage.update.GenreUpdateQuery;
import org.rookit.api.storage.update.PlaylistUpdateQuery;
import org.rookit.api.storage.update.TrackUpdateQuery;

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
		return new MorphiaTrackUpdateQuery(update, filter);
	}
	
	public AlbumUpdateQuery newAlbumUpdateQuery(UpdateOperations<Album> update, AlbumQuery filter) {
		return new MorphiaAlbumUpdateQuery(update, filter);
	}
	
	public ArtistUpdateQuery newArtistUpdateQuery(UpdateOperations<Artist> update, ArtistQuery filter) {
		return new MorphiaArtistUpdateQuery(update, filter);
	}
	
	public GenreUpdateQuery newGenreUpdateQuery(UpdateOperations<Genre> update, GenreQuery filter) {
		return new MorphiaGenreUpdateQuery(update, filter);
	}
	
	public PlaylistUpdateQuery newPlaylistUpdateQuery(UpdateOperations<Playlist> update, PlaylistQuery filter) {
		return new MorphiaPlaylistUpdateQuery(update, filter);
	}

}
