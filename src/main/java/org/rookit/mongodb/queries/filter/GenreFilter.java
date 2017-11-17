package org.rookit.mongodb.queries.filter;

import java.util.regex.Pattern;

@SuppressWarnings("javadoc")
public interface GenreFilter<Q extends GenreFilter<Q>> extends PlayableFilter<Q> {
	
	Q withName(String genreName);
	Q withName(Pattern regex);

	Q withDescription(String description);
	Q withDescription(Pattern regex);

}
