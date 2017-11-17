package org.rookit.mongodb.queries.filter;

import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface PlaylistFilter<Q extends PlaylistFilter<Q>> extends PlayableFilter<Q> {

	Q withName(String name);

	Q withTrack(Track track);

}
