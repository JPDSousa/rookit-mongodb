package org.rookit.mongodb.queries;

import org.rookit.dm.genre.Genre;

@SuppressWarnings("javadoc")
public interface GenreQuery extends RookitQuery<Genre> {

	GenreQueryImpl withName(String genreName);

	GenreQueryImpl withDescription(String description);

}
