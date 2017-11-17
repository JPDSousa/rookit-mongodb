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

import java.time.LocalDate;

import org.rookit.dm.play.Playable;
import org.smof.collection.ParentQuery;

import static org.rookit.dm.play.Playable.*;

abstract class AbstractPlayableQuery<E extends Playable, Q extends PlayableQuery<E, Q>> extends AbstractRookitQuery<Q, E> implements PlayableQuery<E, Q> {
	
	protected AbstractPlayableQuery(ParentQuery<E> query) {
		super(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q playedMoreThan(long plays) {
		query.withFieldGreater(PLAYS, plays, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q playedLessThan(long plays) {
		query.withFieldSmaller(PLAYS, plays, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q playedBetween(long min, long max) {
		query.withFieldGreater(PLAYS, min, INCLUDE_BOUND)
		.withFieldSmaller(PLAYS, max, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastPlayedBefore(LocalDate date) {
		query.withFieldSmaller(LAST_PLAYED, date, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastPlayedAfter(LocalDate date) {
		query.withFieldGreater(LAST_PLAYED, date, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skippedMoreThan(long skipped) {
		query.withFieldGreater(SKIPPED, skipped, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skippedLessThan(long skipped) {
		query.withFieldSmaller(SKIPPED, skipped, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skippedBetween(long min, long max) {
		query.withFieldGreater(SKIPPED, min, INCLUDE_BOUND)
		.withFieldSmaller(SKIPPED, max, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastSkippedBefore(LocalDate date) {
		query.withFieldSmaller(LAST_SKIPPED, date, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q lastSkippedAfter(LocalDate date) {
		query.withFieldGreater(LAST_SKIPPED, date, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withDurationGreaterThan(long duration) {
		query.withFieldGreater(DURATION, duration, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withDurationSmallerThan(long duration) {
		query.withFieldSmaller(DURATION, duration, INCLUDE_BOUND);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withDurationBetween(long min, long max) {
		query.withFieldGreater(DURATION, min, INCLUDE_BOUND)
		.withFieldSmaller(DURATION, max, INCLUDE_BOUND);
		return (Q) this;
	}

	
}
