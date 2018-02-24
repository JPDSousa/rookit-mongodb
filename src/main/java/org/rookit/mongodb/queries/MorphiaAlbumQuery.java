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
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.storage.queries.AlbumQuery;

import static org.rookit.api.dm.album.AlbumFields.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Pattern;

class MorphiaAlbumQuery extends AbstractGenreableQuery<Album, AlbumQuery> implements AlbumQuery {

	MorphiaAlbumQuery(Datastore datastore, Query<Album> query) {
		super(datastore, query);
	}
	
	@Override
	public AlbumQuery withTitle(String albumTitle) {
		query.field(TITLE).equalIgnoreCase(albumTitle);
		return this;
	}
	
	@Override
	public AlbumQuery withTitle(Pattern regex) {
		query.field(TITLE).equal(regex);
		return this;
	}

	@Override
	public AlbumQuery withType(TypeAlbum type) {
		query.field(TYPE).equal(type);
		return this;
	}
	
	@Override
	public AlbumQuery withReleaseType(TypeRelease type) {
		query.field(RELEASE_TYPE).equal(type);
		return this;
	}

	@Override
	public AlbumQuery withAnyReleaseType(TypeRelease[] types) {
		query.field(RELEASE_TYPE).in(Arrays.asList(types));
		return this;
	}

	@Override
	public AlbumQuery withArtist(Artist artist) {
		query.field(ARTISTS).equal(artist.getId());
		return this;
	}

	@Override
	public AlbumQuery withReleaseDate(LocalDate date) {
		query.field(RELEASE_DATE).equal(date);
		return this;
	}

}
