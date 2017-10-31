package org.rookit.mongodb.update;

import org.rookit.mongodb.queries.RookitQuery;
import org.smof.collection.SmofUpdateQuery;
import org.smof.element.Element;

abstract class AbstractUpdateFilterQuery<E extends Element, Q extends RookitQuery<E>> implements RookitUpdateFilterQuery {

	protected final Q filter;
	private final SmofUpdateQuery<E> updateQuery;
	
	protected AbstractUpdateFilterQuery(Q filter, SmofUpdateQuery<E> updateQuery) {
		super();
		this.filter = filter;
		this.updateQuery = updateQuery;
	}

	@Override
	public void execute() {
		updateQuery.withFilter(filter.getBson());
		updateQuery.execute();
	}

}
