package org.rookit.mongodb.queries;

import java.util.regex.Pattern;

import org.rookit.dm.genre.Genre;

@SuppressWarnings("javadoc")
public interface GenreQuery extends PlayableQuery<Genre, GenreQuery> {

	GenreQuery withName(String genreName);
	GenreQuery withName(Pattern regex);

	GenreQuery withDescription(String description);
	GenreQuery withDescription(Pattern regex);

}
