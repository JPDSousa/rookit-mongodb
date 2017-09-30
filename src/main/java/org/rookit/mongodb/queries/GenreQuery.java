package org.rookit.mongodb.queries;

import org.rookit.dm.genre.Genre;

@SuppressWarnings("javadoc")
public interface GenreQuery extends RookitQuery<Genre> {

	GenreQuery withName(String genreName);

	GenreQuery withDescription(String description);

}
