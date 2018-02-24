package org.rookit.mongodb.update;

import static org.rookit.api.dm.genre.GenreFields.*;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.storage.queries.GenreQuery;
import org.rookit.api.storage.update.GenreUpdateFilterQuery;
import org.rookit.api.storage.update.GenreUpdateQuery;

class MorphiaGenreUpdateQuery extends AbstractPlayableUpdateQuery<Genre, GenreQuery, GenreUpdateQuery, GenreUpdateFilterQuery> implements GenreUpdateQuery {

	MorphiaGenreUpdateQuery(UpdateOperations<Genre> query, GenreQuery filter) {
		super(query, filter);
	}

	@Override
	public GenreUpdateFilterQuery where() {
		return new MorphiaGenreUpdateFilterQuery(filter, query);
	}

	@Override
	public GenreUpdateQuery setDescription(String description) {
		query.set(DESCRIPTION, description);
		return this;
	}


}
