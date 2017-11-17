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
import java.util.stream.Stream;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.smof.collection.ParentQuery;
import org.smof.element.Element;

abstract class AbstractRookitQuery<E extends Element> implements RookitQuery<E> {

	protected static final boolean INCLUDE_BOUND = false;
	
	protected static <T extends Object> Object[] toObjectArray(T[] array) {
		return Arrays.stream(array).map(t -> (Object) t).toArray();
	}
	
	protected final ParentQuery<E> query;

	protected AbstractRookitQuery(ParentQuery<E> query) {
		super();
		this.query = query;
	}
	
	@Override
	public Stream<E> stream() {
		return query.results().stream();
	}
	
	@Override
	public long count() {
		return query.results().count();
	}
	
	
	@Override
	public E first() {
		return query.results().first();
	}
	
	@Override
	public E byElement(E element) {
		return query.byElement(element);
	}

	@Override
	public E byID(ObjectId id) {
		return query.withFieldEquals(Element.ID, id)
				.results().first();
	}

	@Override
	public BsonDocument getBson() {
		return query.asBson();
	}
	
}
