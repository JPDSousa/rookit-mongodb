package org.rookit.mongodb.queries;

import org.bson.types.ObjectId;
import org.rookit.dm.genre.Genre;
import org.smof.collection.ParentQuery;
import org.smof.element.Element;

import static org.rookit.dm.genre.Genreable.*;

import java.util.Arrays;

abstract class AbstractGenreableQuery<E extends Element, Q extends RookitQuery<E>> extends AbstractPlayableQuery<E, Q> implements GenreableQuery<E, Q> {

	protected AbstractGenreableQuery(ParentQuery<E> query) {
		super(query);
	}

	@Override
	public Q withGenre(Genre genre) {
		return withGenre(genre.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withGenre(ObjectId id) {
		query.withFieldEquals(GENRES, id);
		return (Q) this;
	}

	@Override
	public Q withGenres(Genre[] genres) {
		return withGenres(Arrays.stream(genres)
				.map(g -> g.getId())
				.toArray(ObjectId[]::new));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withGenres(ObjectId[] ids) {
		query.withFieldIn(GENRES, toObjectArray(ids));
		return (Q) this;
	}

}
