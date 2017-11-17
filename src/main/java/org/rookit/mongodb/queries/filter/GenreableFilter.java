package org.rookit.mongodb.queries.filter;

import org.bson.types.ObjectId;
import org.rookit.dm.genre.Genre;

@SuppressWarnings("javadoc")
public interface GenreableFilter<Q extends GenreableFilter<Q>> extends PlayableFilter<Q> {

	Q withGenre(Genre genre);
	Q withGenre(ObjectId ids);
	
	Q withGenres(Genre[] genres);
	Q withGenres(ObjectId[] ids);
	
}
