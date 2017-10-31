package org.rookit.mongodb.update;

import org.rookit.mongodb.queries.filter.GenreableFilter;

@SuppressWarnings("javadoc")
public interface GenreableUpdateFilterQuery<Q extends GenreableUpdateFilterQuery<Q>> 
	extends GenreableFilter<Q>, PlayableUpdateFilterQuery<Q> {

	//
	
}
