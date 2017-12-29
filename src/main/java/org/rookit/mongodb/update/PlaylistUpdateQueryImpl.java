package org.rookit.mongodb.update;

import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.PlaylistQuery;
import org.rookit.utils.exception.InvalidOperationException;

import static org.rookit.dm.play.DatabaseFields.*;

import org.mongodb.morphia.query.UpdateOperations;

class PlaylistUpdateQueryImpl extends AbstractPlayableUpdateQuery<Playlist, PlaylistQuery, PlaylistUpdateQuery, PlaylistUpdateFilterQuery> implements PlaylistUpdateQuery {

	PlaylistUpdateQueryImpl(UpdateOperations<Playlist> query, PlaylistQuery filter) {
		super(query, filter);
	}

	@Override
	public PlaylistUpdateFilterQuery where() {
		return new PlaylistUpdateFilterQueryImpl(filter, query);
	}

	@Override
	public PlaylistUpdateQuery add(Track track) {
		query.addToSet(TRACKS, track);
		return this;
	}

	@Override
	public PlaylistUpdateQuery setImage(byte[] image) {
		throw new InvalidOperationException("Not supported yet");
	}


}
