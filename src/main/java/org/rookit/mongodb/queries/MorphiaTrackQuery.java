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
import org.rookit.dm.track.audio.TrackKey;
import org.rookit.dm.track.audio.TrackMode;

import com.google.common.collect.BoundType;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import static org.rookit.dm.track.DatabaseFields.*;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

class MorphiaTrackQuery extends AbstractGenreableQuery<Track, TrackQuery> implements TrackQuery {
	
	MorphiaTrackQuery(Datastore datastore, Query<Track> query) {
		super(datastore, query);
	}

	@Override
	public TrackQuery withHiddenTrack(String hiddenTrack) {
		query.field(HIDDEN_TRACK).equalIgnoreCase(hiddenTrack);
		return this;
	}

	@Override
	public TrackQuery withTitle(String title) {
		query.field(TITLE).equalIgnoreCase(title);
		return this;
	}

	@Override
	public TrackQuery withTitle(Pattern regex) {
		query.filter(TITLE, regex);
		return this;
	}

	@Override
	public TrackQuery withMainArtist(Artist artist) {
		query.field(MAIN_ARTISTS).equal(artist.getId());
		return this;
	}

	@Override
	public TrackQuery withOriginal(Track track) {
		return withOriginal(track.getId());
	}

	@Override
	public TrackQuery withOriginal(ObjectId id) {
		query.field(ORIGINAL).equal(id);
		return this;
	}

	@Override
	public TrackQuery withFeature(Artist artist) {
		query.field(FEATURES).equal(artist.getId());
		return this;
	}

	@Override
	public TrackQuery withProducer(Artist artist) {
		query.field(PRODUCERS).equal(artist.getId());
		return this;
	}

	@Override
	public TrackQuery withLyrics(String lyrics) {
		query.field(LYRICS).equalIgnoreCase(lyrics);
		return this;
	}

	@Override
	public TrackQuery withLyrics(Pattern regex) {
		query.filter(LYRICS, regex);
		return this;
	}

	@Override
	public TrackQuery withExplicitLyrics(boolean explicit) {
		query.field(EXPLICIT).equal(explicit);
		return this;
	}

	@Override
	public TrackQuery withHiddenTrack(Pattern regex) {
		query.filter(HIDDEN_TRACK, regex);
		return this;
	}

	@Override
	public TrackQuery withVersionArtist(Artist artist) {
		query.filter(VERSION_ARTISTS, artist.getId());
		return this;
	}

	@Override
	public TrackQuery withVersionToken(String token) {
		query.field(VERSION_TOKEN).equal(token);
		return this;
	}

	@Override
	public TrackQuery withVersionToken(Pattern regex) {
		query.filter(VERSION_TOKEN, regex);
		return this;
	}
	
	private void handleMinMax(String fieldName, Number min, Number max) {
		query.and(
				query.criteria(fieldName).greaterThan(min),
				query.criteria(fieldName).lessThan(max)
				);
	}
	
	private void handleRange(String fieldName, Range<?> range) {
		final List<Criteria> criterias = Lists.newArrayListWithCapacity(2);
		if(range.hasLowerBound()) {
			if(range.lowerBoundType() == BoundType.OPEN) {
				criterias.add(query.criteria(fieldName).greaterThanOrEq(range.lowerEndpoint()));
			}
			else {
				criterias.add(query.criteria(fieldName).greaterThan(range.lowerEndpoint()));
			}
		}
		if(range.hasUpperBound()) {
			if(range.upperBoundType() == BoundType.OPEN) {
				criterias.add(query.criteria(fieldName).lessThanOrEq(range.upperEndpoint()));
			}
			else {
				criterias.add(query.criteria(fieldName).lessThan(range.upperEndpoint()));
			}
		}
		query.and(criterias.toArray(new Criteria[criterias.size()]));
	}

	@Override
	public TrackQuery withBPM(short min, short max) {
		handleMinMax(BPM, min, max);
		return this;
	}

	@Override
	public TrackQuery withBPM(short bpm) {
		query.field(BPM).equal(bpm);
		return this;
	}

	@Override
	public TrackQuery withBPM(Range<Short> range) {
		handleRange(BPM, range);
		return this;
	}

	@Override
	public TrackQuery withTrackKey(TrackKey key) {
		query.field(KEY).equal(key);
		return this;
	}

	@Override
	public TrackQuery withTrackMode(TrackMode mode) {
		query.field(MODE).equal(mode);
		return this;
	}

	@Override
	public TrackQuery withInstrumental(boolean instrumental) {
		query.field(INSTRUMENTAL).equal(instrumental);
		return this;
	}

	@Override
	public TrackQuery withLive(boolean live) {
		query.field(LIVE).equal(live);
		return this;
	}

	@Override
	public TrackQuery withAcoustic(boolean acoustic) {
		query.field(ACOUSTIC).equal(acoustic);
		return this;
	}

	@Override
	public TrackQuery withDanceability(double danceability) {
		query.field(DANCEABILITY).equal(danceability);
		return this;
	}

	@Override
	public TrackQuery withDanceability(double min, double max) {
		handleMinMax(DANCEABILITY, min, max);
		return this;
	}

	@Override
	public TrackQuery withDanceability(Range<Double> range) {
		handleRange(DANCEABILITY, range);
		return this;
	}

	@Override
	public TrackQuery withEnergy(double energy) {
		query.field(ENERGY).equal(energy);
		return this;
	}

	@Override
	public TrackQuery withEnergy(double min, double max) {
		handleMinMax(ENERGY, min, max);
		return this;
	}

	@Override
	public TrackQuery withEnergy(Range<Double> range) {
		handleRange(ENERGY, range);
		return this;
	}

	@Override
	public TrackQuery withValence(double valence) {
		query.field(VALENCE).equal(valence);
		return this;
	}

	@Override
	public TrackQuery withValence(double min, double max) {
		handleMinMax(VALENCE, min, max);
		return this;
	}

	@Override
	public TrackQuery withValence(Range<Double> range) {
		handleRange(VALENCE, range);
		return this;
	}
	
}
