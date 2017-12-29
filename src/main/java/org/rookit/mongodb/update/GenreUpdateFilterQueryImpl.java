package org.rookit.mongodb.update;

import java.util.regex.Pattern;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.genre.Genre;
import org.rookit.mongodb.queries.GenreQuery;

class GenreUpdateFilterQueryImpl extends AbstractPlayableUpdateFilterQuery<Genre, GenreQuery, GenreUpdateFilterQuery>
		implements GenreUpdateFilterQuery {

	protected GenreUpdateFilterQueryImpl(GenreQuery filter, UpdateOperations<Genre> updateQuery) {
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
