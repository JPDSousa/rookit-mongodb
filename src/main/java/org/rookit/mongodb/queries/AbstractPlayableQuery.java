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

import static org.rookit.dm.play.able.Playable.*;

import java.time.Duration;
import java.time.LocalDate;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.rookit.dm.play.able.Playable;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayableQuery<E extends Playable, Q extends PlayableQuery<E, Q>> extends AbstractRookitQuery<Q, E> implements PlayableQuery<E, Q> {

	protected AbstractPlayableQuery(Datastore datastore, Query<E> query) {
		super(datastore, query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q playedMoreThan(long plays) {
		query.field(PLAYS).greaterThan(plays);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q playedLessThan(long plays) {
		query.field(PLAYS).lessThan(plays);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q playedBetween(long min, long max) {
		query.and(
				query.criteria(PLAYS).greaterThan(min),
				query.criteria(PLAYS).lessThan(max)
				);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastPlayedBefore(LocalDate date) {
		query.field(LAST_PLAYED).lessThan(date);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastPlayedAfter(LocalDate date) {
		query.field(LAST_PLAYED).greaterThan(date);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skippedMoreThan(long skipped) {
		query.field(SKIPPED).greaterThan(skipped);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skippedLessThan(long skipped) {
		query.field(SKIPPED).lessThan(skipped);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skippedBetween(long min, long max) {
		query.and(
				query.criteria(SKIPPED).greaterThan(min),
				query.criteria(SKIPPED).lessThan(max)
				);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastSkippedBefore(LocalDate date) {
		query.field(LAST_SKIPPED).lessThan(date);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastSkippedAfter(LocalDate date) {
		query.field(LAST_SKIPPED).greaterThan(date);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withDurationGreaterThan(Duration duration) {
		query.field(DURATION).greaterThan(duration);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withDurationSmallerThan(Duration duration) {
		query.field(DURATION).lessThan(duration);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withDurationBetween(Duration min, Duration max) {
		query.and(
				query.criteria(DURATION).greaterThan(min),
				query.criteria(DURATION).lessThan(max)
				);
		return (Q) this;
	}


}
