package org.rookit.mongodb.update;

@SuppressWarnings("javadoc")
public interface RookitUpdateQuery<S extends RookitUpdateFilterQuery> {
	
	S where();

}
