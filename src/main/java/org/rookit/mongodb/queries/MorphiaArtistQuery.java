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
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGender;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.storage.queries.ArtistQuery;

import static org.rookit.api.dm.artist.ArtistFields.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

class MorphiaArtistQuery extends AbstractGenreableQuery<Artist, ArtistQuery> implements ArtistQuery {

	public MorphiaArtistQuery(Datastore datastore, Query<Artist> query) {
		super(datastore, query);
	}

	@Override
	public ArtistQuery withArtistType(TypeArtist type) {
		query.field(TYPE).equal(type);
		return this;
	}

	@Override
	public ArtistQuery withName(String artistName) {
		query.field(NAME).equalIgnoreCase(artistName);
		return this;
	}

	@Override
	public ArtistQuery withName(Pattern regex) {
		query.field(NAME).equal(regex);
		return this;
	}

	@Override
	public ArtistQuery withFullName(String fullName) {
		query.field(FULL_NAME).equalIgnoreCase(fullName);
		return this;
	}

	@Override
	public ArtistQuery withFullName(Pattern regex) {
		query.field(FULL_NAME).equal(regex);
		return this;
	}

	@Override
	public ArtistQuery relatedTo(Artist artist) {
		query.field(RELATED).equal(artist.getId());
		return this;
	}

	@Override
	public ArtistQuery withOrigin(String origin) {
		query.field(ORIGIN).equal(origin);
		return this;
	}

	@Override
	public ArtistQuery withIPI(String ipi) {
		query.field(IPI).equal(ipi);
		return this;
	}

	@Override
	public ArtistQuery withISNI(String isni) {
		query.field(ISNI).equal(isni);
		return this;
	}

	@Override
	public ArtistQuery withGender(TypeGender gender) {
		query.field(GENDER).equal(gender);
		return this;
	}

	@Override
	public ArtistQuery withGroupType(TypeGroup type) {
		query.field(GROUP_TYPE).equal(type);
		return this;
	}

	@Override
	public ArtistQuery withAlias(String alias) {
		query.field(ALIASES).equal(alias);
		return this;
	}

	@Override
	public ArtistQuery withAlias(Pattern regex) {
		query.field(ALIASES).equal(regex);
		return this;
	}

	@Override
	public ArtistQuery withBeginDate(LocalDate date) {
		query.field(BEGIN_DATE).equal(date);
		return this;
	}

	@Override
	public ArtistQuery withEndDate(LocalDate date) {
		query.field(END_DATE).equal(date);
		return this;
	}

	@Override
	public ArtistQuery withMember(Musician member) {
		query.field(MEMBERS).equalIgnoreCase(member.getId());
		return this;
	}
	
}
