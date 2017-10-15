package org.rookit.mongodb.queries;

import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface PlaylistQuery extends PlayableQuery<Playlist, PlaylistQuery> {

	PlaylistQuery withName(String name);
	
	PlaylistQuery withTrack(Track track);
	
}
