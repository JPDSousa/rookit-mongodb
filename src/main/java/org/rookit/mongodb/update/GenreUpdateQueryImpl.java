package org.rookit.mongodb.update;

import org.rookit.dm.genre.Genre;
import org.rookit.mongodb.queries.GenreQuery;
import org.smof.collection.SmofUpdate;

import static org.rookit.dm.genre.DatabaseFields.*;

class GenreUpdateQueryImpl extends AbstractPlayableUpdateQuery<Genre, GenreQuery, GenreUpdateQuery, GenreUpdateFilterQuery> implements GenreUpdateQuery {

	GenreUpdateQueryImpl(SmofUpdate<Genre> query, GenreQuery filter) {
		super(query, filter);
	}

	@Override
	public GenreUpdateFilterQuery where() {
		return new GenreUpdateFilterQueryImpl(filter, query.where());
	}

	@Override
	public GenreUpdateQuery setDescription(String description) {
		query.set(DESCRIPTION, description);
		return this;
	}


}
