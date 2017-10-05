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

import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.Musician;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.artist.TypeGender;
import org.rookit.dm.artist.TypeGroup;
import org.smof.collection.ParentQuery;

import static org.rookit.dm.artist.DatabaseFields.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

class ArtistQueryImpl extends AbstractGenreableQuery<Artist, ArtistQuery> implements ArtistQuery{

	public ArtistQueryImpl(ParentQuery<Artist> query) {
		super(query);
	}

	@Override
	public ArtistQuery withArtistType(TypeArtist type) {
		query.withFieldEquals(TYPE, type);
		return this;
	}

	@Override
	public ArtistQuery withName(String artistName) {
		query.withFieldEquals(NAME, artistName);
		return this;
	}

	@Override
	public ArtistQuery withName(Pattern regex) {
		query.withFieldRegex(NAME, regex);
		return this;
	}

	@Override
	public ArtistQuery withFullName(String fullName) {
		query.withFieldEquals(FULL_NAME, fullName);
		return this;
	}

	@Override
	public ArtistQuery withFullName(Pattern regex) {
		query.withFieldRegex(FULL_NAME, regex);
		return this;
	}

	@Override
	public ArtistQuery relatedTo(Artist artist) {
		query.withFieldEquals(RELATED, artist.getId());
		return this;
	}

	@Override
	public ArtistQuery withOrigin(String origin) {
		query.withFieldEquals(ORIGIN, origin);
		return this;
	}

	@Override
	public ArtistQuery withIPI(String ipi) {
		query.withFieldEquals(IPI, ipi);
		return this;
	}

	@Override
	public ArtistQuery withISNI(String isni) {
		query.withFieldEquals(ISNI, isni);
		return this;
	}

	@Override
	public ArtistQuery withGender(TypeGender gender) {
		query.withFieldEquals(GENDER, gender);
		return this;
	}

	@Override
	public ArtistQuery withGroupType(TypeGroup type) {
		query.withFieldEquals(GROUP_TYPE, type);
		return this;
	}

	@Override
	public ArtistQuery withAlias(String alias) {
		query.withFieldEquals(ALIASES, alias);
		return this;
	}

	@Override
	public ArtistQuery withAlias(Pattern regex) {
		query.withFieldRegex(ALIASES, regex);
		return this;
	}

	@Override
	public ArtistQuery withBeginDate(LocalDate date) {
		query.withFieldEquals(BEGIN_DATE, date);
		return this;
	}

	@Override
	public ArtistQuery withEndDate(LocalDate date) {
		query.withFieldEquals(END_DATE, date);
		return this;
	}

	@Override
	public ArtistQuery withMember(Musician member) {
		query.withFieldEquals(MEMBERS, member.getId());
		return this;
	}
	
}
