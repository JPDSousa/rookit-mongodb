package org.rookit.mongodb.update;

import org.rookit.dm.track.TrackSetter;
import org.rookit.dm.track.audio.AudioFeatureSetter;

@SuppressWarnings("javadoc")
public interface TrackUpdateQuery extends GenreableUpdateQuery<TrackUpdateQuery, TrackUpdateFilterQuery>, TrackSetter<TrackUpdateQuery>, AudioFeatureSetter<TrackUpdateQuery> {
	
	//
	
}
