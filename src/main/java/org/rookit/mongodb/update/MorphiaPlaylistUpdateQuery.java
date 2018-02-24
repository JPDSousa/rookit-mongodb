package org.rookit.mongodb.update;

import static org.rookit.api.dm.play.PlaylistFields.*;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.queries.PlaylistQuery;
import org.rookit.api.storage.update.PlaylistUpdateFilterQuery;
import org.rookit.api.storage.update.PlaylistUpdateQuery;

class MorphiaPlaylistUpdateQuery extends AbstractPlayableUpdateQuery<Playlist, PlaylistQuery, PlaylistUpdateQuery, PlaylistUpdateFilterQuery> implements PlaylistUpdateQuery {

	MorphiaPlaylistUpdateQuery(UpdateOperations<Playlist> query, PlaylistQuery filter) {
		super(query, filter);
	}

	@Override
	public PlaylistUpdateFilterQuery where() {
		return new MorphiaPlaylistUpdateFilterQuery(filter, query);
	}

	@Override
	public PlaylistUpdateQuery add(Track track) {
		query.addToSet(TRACKS, track);
		return this;
	}

	@Override
	public PlaylistUpdateQuery setImage(byte[] image) {
		LOGGER.invalidOperation("Not supported yet");
		return this;
	}

}
