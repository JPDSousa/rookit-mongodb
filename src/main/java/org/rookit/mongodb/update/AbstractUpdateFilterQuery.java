package org.rookit.mongodb.update;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.storage.queries.RookitQuery;
import org.rookit.api.storage.update.RookitUpdateFilterQuery;
import org.rookit.mongodb.queries.AbstractRookitQuery;

abstract class AbstractUpdateFilterQuery<E extends RookitModel, Q extends RookitQuery<Q, E>, U extends RookitUpdateFilterQuery<U>> 
	implements RookitUpdateFilterQuery<U> {

	protected final Q filter;
	private final Datastore datastore; 
	private final UpdateOperations<E> updates;
	
	protected AbstractUpdateFilterQuery(Q filter, UpdateOperations<E> updates) {
		super();
		this.filter = filter;
		this.updates = updates;
		this.datastore = ((AbstractRookitQuery<Q, E>) filter).getDatastore();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public U withId(ObjectId id) {
		filter.withId(id);
		return (U) this;
	}



	@Override
	public void execute() {
		datastore.update(((AbstractRookitQuery<Q, E>) filter).getQuery(), updates);
	}

}
