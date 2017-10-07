package org.rookit.mongodb.queries;

import java.time.LocalDate;

import org.smof.collection.ParentQuery;
import org.smof.element.Element;

import static org.rookit.dm.play.Playable.*;

abstract class AbstractPlayableQuery<E extends Element, Q extends RookitQuery<E>> extends AbstractRookitQuery<E> implements PlayableQuery<E, Q> {
	
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
