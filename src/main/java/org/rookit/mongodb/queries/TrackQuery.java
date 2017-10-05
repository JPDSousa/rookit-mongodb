package org.rookit.mongodb.queries;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface TrackQuery extends GenreableQuery<Track, TrackQuery> {
	
	TrackQuery withTitle(String title);
	TrackQuery withTitle(Pattern regex);
	
	TrackQuery withMainArtist(Artist artist);
	
	TrackQuery withFeature(Artist artist);
	
	TrackQuery withProducer(Artist artist);
	
	TrackQuery withBPM(int bpm);
	TrackQuery withBPM(int min, int max);
	
	TrackQuery withLyrics(String lyrics);
	TrackQuery withLyrics(Pattern regex);
	
	TrackQuery withExplicitLyrics(boolean explicit);
	
	TrackQuery withOriginal(Track track);
	TrackQuery withOriginal(ObjectId id);

	TrackQuery withHiddenTrack(String hiddenTrack);
	TrackQuery withHiddenTrack(Pattern regex);

	TrackQuery withVersionArtist(Artist artist);
	
	TrackQuery withVersionToken(String token);
	TrackQuery withVersionToken(Pattern regex);
	
}
