/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.mongodb.queries;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.storage.queries.RookitQuery;
import org.rookit.api.storage.utils.FieldOrder;
import org.rookit.api.storage.utils.Order;
import org.rookit.mongodb.utils.OrderImpl;

import com.google.common.collect.Lists;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitQuery<Q extends RookitQuery<Q, E>, E extends RookitModel> implements RookitQuery<Q, E> {

	protected static <T extends Object> Object[] toObjectArray(T[] array) {
		return Arrays.stream(array).map(t -> (Object) t).toArray();
	}
	
	protected final Query<E> query;
	private final Datastore datastore;

	protected AbstractRookitQuery(Datastore datastore, Query<E> query) {
		super();
		this.query = query;
		this.datastore = datastore;
	}
	
	@Override
	public Order newOrder() {
		return new OrderImpl();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q order(Order order) {
		final List<Sort> sorts = Lists.newLinkedList();
		for(final FieldOrder field : order.getFields()) {
			switch(field.getOrder()) {
			case ASC:
				sorts.add(Sort.ascending(field.getField()));
				break;
			case DSC:
				sorts.add(Sort.descending(field.getField()));
				break;
			}
		}
		query.order(sorts.toArray(new Sort[sorts.size()]));
		return (Q) this;
	}

	public Query<E> getQuery() {
		return query;
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
	
	@Override
	public Stream<E> stream() {
		return StreamSupport.stream(query.fetch().spliterator(), false);
	}
	
	@Override
	public long count() {
		return query.count();
	}
	
	
	@Override
	public E first() {
		return query.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withId(ObjectId id) {
		query.field(RookitModel.ID).equal(id);
		return (Q) this;
	}

	@Override
	public String toString() {
		return query.toString();
	}
	
}
