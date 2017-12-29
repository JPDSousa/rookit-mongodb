package org.rookit.mongodb.update;

import org.rookit.dm.genre.Genre;
import org.rookit.mongodb.queries.GenreQuery;

import static org.rookit.dm.genre.DatabaseFields.*;

import org.mongodb.morphia.query.UpdateOperations;

class GenreUpdateQueryImpl extends AbstractPlayableUpdateQuery<Genre, GenreQuery, GenreUpdateQuery, GenreUpdateFilterQuery> implements GenreUpdateQuery {

	GenreUpdateQueryImpl(UpdateOperations<Genre> query, GenreQuery filter) {
		super(query, filter);
	}

	@Override
	public GenreUpdateFilterQuery where() {
		return new GenreUpdateFilterQueryImpl(filter, query);
	}

	@Override
	public GenreUpdateQuery setDescription(String description) {
		query.set(DESCRIPTION, description);
		return this;
	}


}
