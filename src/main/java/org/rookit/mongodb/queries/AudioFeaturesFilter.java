package org.rookit.mongodb.queries;

import org.rookit.dm.track.audio.TrackKey;
import org.rookit.dm.track.audio.TrackMode;

import com.google.common.collect.Range;

@SuppressWarnings("javadoc")
public interface AudioFeaturesFilter<Q> {

	Q withBPM(short bpm);
	Q withBPM(short min, short max);
	Q withBPM(Range<Short> range);

	Q withTrackKey(TrackKey key);

	Q withTrackMode(TrackMode mode);

	Q withInstrumental(boolean instrumental);
	
	Q withLive(boolean live);
	
	Q withAcoustic(boolean acoustic);
	
	Q withDanceability(double danceability);
	
	Q withEnergy(double energy);
	
	Q withValence(double valence);

}
