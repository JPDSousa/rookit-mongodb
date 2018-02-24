package org.rookit.mongodb.update;

import java.util.regex.Pattern;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.storage.queries.GenreQuery;
import org.rookit.api.storage.update.GenreUpdateFilterQuery;

class MorphiaGenreUpdateFilterQuery extends AbstractPlayableUpdateFilterQuery<Genre, GenreQuery, GenreUpdateFilterQuery>
		implements GenreUpdateFilterQuery {

	protected MorphiaGenreUpdateFilterQuery(GenreQuery filter, UpdateOperations<Genre> updateQuery) {
		super(filter, updateQuery);
	}

	@Override
	public GenreUpdateFilterQuery withName(String genreName) {
		filter.withName(genreName);
		return this;
	}

	@Override
	public GenreUpdateFilterQuery withName(Pattern regex) {
		filter.withName(regex);
		return this;
	}

	@Override
	public GenreUpdateFilterQuery withDescription(String description) {
		filter.withDescription(description);
		return this;
	}

	@Override
	public GenreUpdateFilterQuery withDescription(Pattern regex) {
		filter.withDescription(regex);
		return this;
	}

	
}
