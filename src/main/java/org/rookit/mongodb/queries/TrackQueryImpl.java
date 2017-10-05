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
import org.rookit.dm.track.Track;
import org.smof.collection.ParentQuery;

import static org.rookit.dm.track.DatabaseFields.*;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;

class TrackQueryImpl extends AbstractGenreableQuery<Track, TrackQuery> implements TrackQuery {
	
	TrackQueryImpl(ParentQuery<Track> query) {
		super(query);
	}

	@Override
	public TrackQuery withHiddenTrack(String hiddenTrack) {
		query.withFieldEquals(HIDDEN_TRACK, hiddenTrack);
		return this;
	}

	@Override
	public TrackQuery withTitle(String title) {
		query.withFieldEquals(TITLE, title);
		return this;
	}

	@Override
	public TrackQuery withTitle(Pattern regex) {
		query.withFieldRegex(TITLE, regex);
		return this;
	}

	@Override
	public TrackQuery withMainArtist(Artist artist) {
		query.withFieldEquals(MAIN_ARTISTS, artist.getId());
		return this;
	}

	@Override
	public TrackQuery withOriginal(Track track) {
		return withOriginal(track.getId());
	}

	@Override
	public TrackQuery withOriginal(ObjectId id) {
		query.withFieldEquals(ORIGINAL, id);
		return this;
	}

	@Override
	public TrackQuery withFeature(Artist artist) {
		query.withFieldEquals(FEATURES, artist.getId());
		return this;
	}

	@Override
	public TrackQuery withProducer(Artist artist) {
		query.withFieldEquals(PRODUCERS, artist.getId());
		return this;
	}

	@Override
	public TrackQuery withBPM(int min, int max) {
		query.withFieldGreater(BPM, min, INCLUDE_BOUND)
		.withFieldSmaller(BPM, max, INCLUDE_BOUND);
		return this;
	}

	@Override
	public TrackQuery withBPM(int bpm) {
		query.withFieldEquals(BPM, bpm);
		return this;
	}

	@Override
	public TrackQuery withLyrics(String lyrics) {
		query.withFieldEquals(LYRICS, lyrics);
		return this;
	}

	@Override
	public TrackQuery withLyrics(Pattern regex) {
		query.withFieldRegex(LYRICS, regex);
		return this;
	}

	@Override
	public TrackQuery withExplicitLyrics(boolean explicit) {
		query.withFieldEquals(EXPLICIT, explicit);
		return this;
	}

	@Override
	public TrackQuery withHiddenTrack(Pattern regex) {
		query.withFieldRegex(HIDDEN_TRACK, regex);
		return this;
	}

	@Override
	public TrackQuery withVersionArtist(Artist artist) {
		query.withFieldEquals(VERSION_ARTISTS, artist.getId());
		return this;
	}

	@Override
	public TrackQuery withVersionToken(String token) {
		query.withFieldEquals(VERSION_TOKEN, token);
		return this;
	}

	@Override
	public TrackQuery withVersionToken(Pattern regex) {
		query.withFieldEquals(VERSION_TOKEN, regex);
		return this;
	}
	
}
