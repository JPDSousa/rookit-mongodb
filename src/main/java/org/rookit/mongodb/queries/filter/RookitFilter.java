package org.rookit.mongodb.queries.filter;

import org.bson.types.ObjectId;

@SuppressWarnings("javadoc")
public interface RookitFilter<Q extends RookitFilter<Q>> {

	Q withId(ObjectId id);
	
}
