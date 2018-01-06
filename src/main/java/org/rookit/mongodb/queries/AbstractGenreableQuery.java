/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.mongodb.queries;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.Genreable;

import com.google.common.collect.Lists;

import static org.rookit.dm.genre.Genreable.*;

import java.util.Arrays;

@SuppressWarnings("javadoc")
public abstract class AbstractGenreableQuery<E extends Genreable, Q extends GenreableQuery<E, Q>> extends AbstractPlayableQuery<E, Q> implements GenreableQuery<E, Q> {

	protected AbstractGenreableQuery(Datastore datastore, Query<E> query) {
		super(datastore, query);
	}

	@Override
	public Q withGenre(Genre genre) {
		return withGenre(genre.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Q withGenre(ObjectId id) {
		query.field(GENRES).equal(id);
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
		query.field(GENRES).in(Lists.newArrayList(ids));
		return (Q) this;
	}

}
