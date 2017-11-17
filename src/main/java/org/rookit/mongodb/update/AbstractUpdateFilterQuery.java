package org.rookit.mongodb.update;

import org.bson.types.ObjectId;
import org.rookit.mongodb.queries.RookitQuery;
import org.smof.collection.SmofUpdateQuery;
import org.smof.element.Element;

abstract class AbstractUpdateFilterQuery<E extends Element, Q extends RookitQuery<Q, E>, U extends RookitUpdateFilterQuery<U>> 
	implements RookitUpdateFilterQuery<U> {

	protected final Q filter;
	private final SmofUpdateQuery<E> updateQuery;
	
	protected AbstractUpdateFilterQuery(Q filter, SmofUpdateQuery<E> updateQuery) {
		super();
		this.filter = filter;
		this.updateQuery = updateQuery;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public U withId(ObjectId id) {
		filter.withId(id);
		return (U) this;
	}



	@Override
	public void execute() {
		updateQuery.withFilter(filter.getBson());
		updateQuery.execute();
	}

}
