package org.rookit.mongodb.update;

import org.rookit.dm.genre.GenreableSetter;

@SuppressWarnings("javadoc")
public interface GenreableUpdateQuery<Q extends GenreableUpdateQuery<Q, S>, S extends GenreableUpdateFilterQuery<S>> extends PlayableUpdateQuery<Q, S>, GenreableSetter<Q> {

	//
	
}
