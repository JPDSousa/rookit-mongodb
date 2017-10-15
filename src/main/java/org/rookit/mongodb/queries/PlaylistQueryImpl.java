package org.rookit.mongodb.queries;

import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.smof.collection.ParentQuery;

class PlaylistQueryImpl extends AbstractPlayableQuery<Playlist, PlaylistQuery> implements PlaylistQuery {
	
	PlaylistQueryImpl(ParentQuery<Playlist> query) {
		super(query);
	}

	@Override
	public PlaylistQuery withName(String name) {
		query.withFieldEquals(Playlist.NAME, name);
		return this;
	}

	@Override
	public PlaylistQuery withTrack(Track track) {
		query.withFieldEquals(Playlist.TRACKS, track);
		return this;
	}

}
