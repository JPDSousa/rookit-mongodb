package org.rookit.mongodb.update;

import java.util.Set;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.api.storage.queries.GenreableQuery;
import org.rookit.api.storage.update.GenreableUpdateFilterQuery;
import org.rookit.api.storage.update.GenreableUpdateQuery;

import static org.rookit.api.dm.genre.Genreable.*;

abstract class AbstractGenreableUpdateQuery<E extends Genreable, F extends GenreableQuery<E, F>, Q extends GenreableUpdateQuery<Q, S>, S extends GenreableUpdateFilterQuery<S>> extends AbstractPlayableUpdateQuery<E, F, Q, S> implements GenreableUpdateQuery<Q, S> {

	AbstractGenreableUpdateQuery(UpdateOperations<E> query, F filter) {
		super(query, filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q addGenre(Genre genre) {
		query.addToSet(GENRES, genre);
		return (Q) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q setGenres(Set<Genre> genres) {
		query.set(GENRES, genres);
		return (Q) this;
	}
	
	

}
