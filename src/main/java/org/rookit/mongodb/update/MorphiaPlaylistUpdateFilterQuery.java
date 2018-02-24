package org.rookit.mongodb.update;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.queries.PlaylistQuery;
import org.rookit.api.storage.update.PlaylistUpdateFilterQuery;

class MorphiaPlaylistUpdateFilterQuery extends AbstractPlayableUpdateFilterQuery<Playlist, PlaylistQuery, PlaylistUpdateFilterQuery>
		implements PlaylistUpdateFilterQuery {

	protected MorphiaPlaylistUpdateFilterQuery(PlaylistQuery filter, UpdateOperations<Playlist> updateQuery) {
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
