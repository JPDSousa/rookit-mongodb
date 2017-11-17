package org.rookit.mongodb.update;

import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.PlaylistQuery;
import org.smof.collection.SmofUpdateQuery;

class PlaylistUpdateFilterQueryImpl extends AbstractPlayableUpdateFilterQuery<Playlist, PlaylistQuery, PlaylistUpdateFilterQuery>
		implements PlaylistUpdateFilterQuery {

	protected PlaylistUpdateFilterQueryImpl(PlaylistQuery filter, SmofUpdateQuery<Playlist> updateQuery) {
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
