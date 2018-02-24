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

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.storage.queries.GenreQuery;

import static org.rookit.api.dm.genre.GenreFields.*;

import java.util.regex.Pattern;

class MorphiaGenreQuery extends AbstractPlayableQuery<Genre, GenreQuery> implements GenreQuery {

	public MorphiaGenreQuery(Datastore datastore, Query<Genre> query) {
		super(datastore, query);
	}
	
	@Override
	public GenreQuery withName(String genreName) {
		query.field(NAME).equalIgnoreCase(genreName);
		return this;
	}
	
	@Override
	public GenreQuery withName(Pattern regex) {
		query.field(NAME).equal(regex);
		return this;
	}

	@Override
	public GenreQuery withDescription(String description) {
		query.field(DESCRIPTION).containsIgnoreCase(description);
		return this;
	}

	@Override
	public GenreQuery withDescription(Pattern regex) {
		query.field(DESCRIPTION).equal(regex);
		return this;
	}

}
