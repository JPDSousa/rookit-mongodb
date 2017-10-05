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

import org.rookit.dm.album.Album;
import org.rookit.dm.album.TypeAlbum;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.smof.collection.ParentQuery;

import static org.rookit.dm.album.DatabaseFields.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

class AlbumQueryImpl extends AbstractGenreableQuery<Album, AlbumQuery> implements AlbumQuery {

	AlbumQueryImpl(ParentQuery<Album> query) {
		super(query);
	}
	
	@Override
	public AlbumQuery withTitle(String albumTitle) {
		query.withFieldEquals(TITLE, albumTitle);
		return this;
	}
	
	@Override
	public AlbumQuery withTitle(Pattern regex) {
		query.withFieldRegex(TITLE, regex);
		return this;
	}

	@Override
	public AlbumQuery withType(TypeAlbum type) {
		query.withFieldEquals(TYPE, type);
		return this;
	}
	
	@Override
	public AlbumQuery withReleaseType(TypeRelease type) {
		query.withFieldEquals(RELEASE_TYPE, type);
		return this;
	}

	@Override
	public AlbumQuery withAnyReleaseType(TypeRelease[] types) {
		query.withFieldIn(RELEASE_TYPE, toObjectArray(types));
		return this;
	}

	@Override
	public AlbumQuery withArtist(Artist artist) {
		query.withFieldEquals(ARTISTS, artist.getId());
		return this;
	}

	@Override
	public AlbumQuery withReleaseDate(LocalDate date) {
		query.withFieldEquals(RELEASE_DATE, date);
		return this;
	}

}
