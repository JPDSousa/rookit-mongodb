package org.rookit.mongodb.update;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.PlaylistQuery;

class PlaylistUpdateFilterQueryImpl extends AbstractPlayableUpdateFilterQuery<Playlist, PlaylistQuery, PlaylistUpdateFilterQuery>
		implements PlaylistUpdateFilterQuery {

	protected PlaylistUpdateFilterQueryImpl(PlaylistQuery filter, UpdateOperations<Playlist> updateQuery) {
		super(filter, updateQuery);
	}

	@Override
	public PlaylistUpdateFilterQuery withName(String name) {
		filter.withName(name);
		return this;
	}

	@Override
	public PlaylistUpdateFilterQuery withTrack(Track track) {
		filter.withTrack(track);
		return this;
	}

}
