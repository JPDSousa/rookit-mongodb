package org.rookit.mongodb.update;

import java.time.Duration;
import java.time.LocalDate;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.play.able.Playable;
import org.rookit.mongodb.queries.PlayableQuery;

abstract class AbstractPlayableUpdateFilterQuery<E extends Playable, Q extends PlayableQuery<E, Q>, U extends PlayableUpdateFilterQuery<U>> 
	extends AbstractUpdateFilterQuery<E, Q, U> 
	implements PlayableUpdateFilterQuery<U> {

	protected AbstractPlayableUpdateFilterQuery(Q filter, UpdateOperations<E> updateQuery) {
		super(filter, updateQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public U playedMoreThan(long plays) {
		filter.playedMoreThan(plays);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U playedLessThan(long plays) {
		filter.playedLessThan(plays);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U playedBetween(long min, long max) {
		filter.playedBetween(min, max);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U lastPlayedBefore(LocalDate date) {
		filter.lastPlayedBefore(date);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U lastPlayedAfter(LocalDate date) {
		filter.lastPlayedAfter(date);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U skippedMoreThan(long skipped) {
		filter.skippedMoreThan(skipped);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U skippedLessThan(long skipped) {
		filter.skippedLessThan(skipped);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U skippedBetween(long min, long max) {
		filter.skippedBetween(min, max);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U lastSkippedBefore(LocalDate date) {
		filter.lastSkippedBefore(date);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U lastSkippedAfter(LocalDate date) {
		filter.lastSkippedAfter(date);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withDurationGreaterThan(Duration duration) {
		filter.withDurationGreaterThan(duration);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withDurationSmallerThan(Duration duration) {
		filter.withDurationSmallerThan(duration);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withDurationBetween(Duration min, Duration max) {
		filter.withDurationBetween(min, max);
		return (U) this;
	}
	
	
}
