package org.rookit.mongodb.queries;

import org.bson.types.ObjectId;
import org.rookit.dm.genre.Genre;
import org.smof.element.Element;

@SuppressWarnings("javadoc")
public interface GenreableQuery<E extends Element, Q extends RookitQuery<E>> extends PlayableQuery<E, Q> {

	Q withGenre(Genre genre);
	Q withGenre(ObjectId ids);
	
	Q withGenres(Genre[] genres);
	Q withGenres(ObjectId[] ids);
	
}
