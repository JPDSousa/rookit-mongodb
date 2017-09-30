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
import org.smof.collection.SmofQuery;

import static org.rookit.dm.album.DatabaseFields.*;

@SuppressWarnings("javadoc")
public class AlbumQuery extends AbstractQuery<Album> {

	public AlbumQuery(SmofQuery<Album> query) {
		super(query);
	}
	
	public AlbumQuery withTitle(String albumTitle) {
		query.withField(TITLE, albumTitle);
		return this;
	}
	
	public AlbumQuery withType(TypeAlbum type) {
		query.withField(TYPE, type);
		return this;
	}
	
	public AlbumQuery withReleaseType(TypeRelease type) {
		query.withField(RELEASE_TYPE, type);
		return this;
	}

}
