package org.rookit.mongodb.update;

import org.rookit.mongodb.queries.filter.RookitFilter;

@SuppressWarnings("javadoc")
public interface RookitUpdateFilterQuery<Q extends RookitUpdateFilterQuery<Q>> extends RookitFilter<Q> {
	
	void execute();

}
