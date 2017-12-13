package org.rookit.mongodb.queries.filter;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface TrackFilter<Q extends TrackFilter<Q>> extends GenreableFilter<Q> {
	
	Q withTitle(String title);
	Q withTitle(Pattern regex);
	
	Q withMainArtist(Artist artist);
	
	Q withFeature(Artist artist);
	
	Q withProducer(Artist artist);
	
	Q withLyrics(String lyrics);
	Q withLyrics(Pattern regex);
	
	Q withExplicitLyrics(boolean explicit);
	
	Q withOriginal(Track track);
	Q withOriginal(ObjectId id);

	Q withHiddenTrack(String hiddenTrack);
	Q withHiddenTrack(Pattern regex);

	Q withVersionArtist(Artist artist);
	
	Q withVersionToken(String token);
	Q withVersionToken(Pattern regex);

}
