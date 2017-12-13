package org.rookit.mongodb.update;

import org.rookit.dm.play.PlaylistSetter;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface PlaylistUpdateQuery extends PlayableUpdateQuery<PlaylistUpdateQuery, PlaylistUpdateFilterQuery>, PlaylistSetter<PlaylistUpdateQuery> {

	PlaylistUpdateQuery add(Track track);
	
}
