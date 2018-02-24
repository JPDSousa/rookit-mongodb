package org.rookit.mongodb.update;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.api.storage.queries.GenreableQuery;
import org.rookit.api.storage.update.GenreableUpdateFilterQuery;

abstract class AbstractGenreableUpdateFilterQuery<E extends Genreable, Q extends GenreableQuery<E, Q>, U extends GenreableUpdateFilterQuery<U>> 
	extends AbstractPlayableUpdateFilterQuery<E, Q, U> 
	implements GenreableUpdateFilterQuery<U> {

	protected AbstractGenreableUpdateFilterQuery(Q filter, UpdateOperations<E> updateQuery) {
		super(filter, updateQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withGenre(Genre genre) {
		filter.withGenre(genre);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withGenre(ObjectId ids) {
		filter.withGenre(ids);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withGenres(Genre[] genres) {
		filter.withGenres(genres);
		return (U) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public U withGenres(ObjectId[] ids) {
		filter.withGenres(ids);
		return (U) this;
	}
	
	
	
}
