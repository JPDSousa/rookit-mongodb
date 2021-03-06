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

import static org.rookit.api.dm.play.PlaylistFields.*;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.queries.PlaylistQuery;

class MorphiaPlaylistQuery extends AbstractPlayableQuery<Playlist, PlaylistQuery> implements PlaylistQuery {
	
	MorphiaPlaylistQuery(Datastore datastore, Query<Playlist> query) {
		super(datastore, query);
	}

	@Override
	public PlaylistQuery withName(String name) {
		query.field(NAME).equalIgnoreCase(name);
		return this;
	}

	@Override
	public PlaylistQuery withTrack(Track track) {
		query.field(TRACKS).equal(track.getId());
		return this;
	}

}
