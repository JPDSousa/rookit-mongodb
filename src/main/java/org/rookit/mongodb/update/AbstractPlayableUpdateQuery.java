package org.rookit.mongodb.update;

import java.time.Duration;
import java.time.LocalDate;

import org.rookit.dm.play.Playable;
import org.rookit.mongodb.queries.PlayableQuery;
import org.smof.collection.SmofUpdate;

import static org.rookit.dm.play.Playable.*;

abstract class AbstractPlayableUpdateQuery<E extends Playable, F extends PlayableQuery<E, F>, Q extends PlayableUpdateQuery<Q, S>, S extends PlayableUpdateFilterQuery<S>> implements PlayableUpdateQuery<Q, S> {

	protected final SmofUpdate<E> query;
	protected final F filter;
	
	AbstractPlayableUpdateQuery(SmofUpdate<E> query, F filter) {
		super();
		this.query = query;
		this.filter = filter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q play() {
		query.increase(PLAYS, 1);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q setDuration(Duration arg0) {
		query.set(DURATION, arg0);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q setLastPlayed(LocalDate arg0) {
		query.set(LAST_PLAYED, arg0);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q setLastSkipped(LocalDate arg0) {
		query.set(LAST_SKIPPED, arg0);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q setPlays(long arg0) {
		query.set(PLAYS, arg0);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q setSkipped(long arg0) {
		query.set(SKIPPED, arg0);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q skip() {
		query.increase(SKIPPED, 1);
		return (Q) this;
	}
	
	

}
