package org.rookit.mongodb.queries;

import org.bson.types.ObjectId;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface TrackQuery extends RookitQuery<Track> {
	
	TrackQuery withHiddenTrack(String hiddenTrack);
	
	TrackQuery withTitle(String title);
	
	TrackQuery withVersionToken(String token);
	
	TrackQuery withBPM(int bpm);
	
	TrackQuery withLyrics(String lyrics);
	
	TrackQuery withExplicitLyrics(boolean explicit);
	
	TrackQuery withOriginal(Track track);
	
	TrackQuery withOriginal(ObjectId id);
	
}
