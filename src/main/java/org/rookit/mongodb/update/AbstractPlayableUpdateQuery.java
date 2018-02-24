package org.rookit.mongodb.update;

import static org.rookit.api.dm.play.able.Playable.*;

import java.time.Duration;
import java.time.LocalDate;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.api.storage.queries.PlayableQuery;
import org.rookit.api.storage.update.PlayableUpdateFilterQuery;
import org.rookit.api.storage.update.PlayableUpdateQuery;
import org.rookit.mongodb.utils.DatabaseValidator;

abstract class AbstractPlayableUpdateQuery<E extends Playable, F extends PlayableQuery<E, F>, Q extends PlayableUpdateQuery<Q, S>, S extends PlayableUpdateFilterQuery<S>> implements PlayableUpdateQuery<Q, S> {

	protected static final DatabaseValidator LOGGER = DatabaseValidator.getDefault();
	
	protected final UpdateOperations<E> query;
	protected final F filter;
	
	AbstractPlayableUpdateQuery(UpdateOperations<E> query, F filter) {
		super();
		this.query = query;
		this.filter = filter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q play() {
		query.inc(PLAYS, 1);
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
		query.inc(SKIPPED, 1);
		return (Q) this;
	}
	
	

}
