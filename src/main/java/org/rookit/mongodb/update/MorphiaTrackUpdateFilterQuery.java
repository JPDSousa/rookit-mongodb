package org.rookit.mongodb.update;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.audio.TrackKey;
import org.rookit.dm.track.audio.TrackMode;
import org.rookit.mongodb.queries.TrackQuery;

import com.google.common.collect.Range;

class MorphiaTrackUpdateFilterQuery extends AbstractGenreableUpdateFilterQuery<Track, TrackQuery, TrackUpdateFilterQuery> implements TrackUpdateFilterQuery {

	private final TrackQuery filter;
	
	protected MorphiaTrackUpdateFilterQuery(TrackQuery filter, UpdateOperations<Track> updateQuery) {
		super(filter, updateQuery);
		this.filter = filter;
	}

	@Override
	public TrackUpdateFilterQuery withTitle(String title) {
		filter.withTitle(title);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withTitle(Pattern regex) {
		filter.withTitle(regex);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withMainArtist(Artist artist) {
		filter.withMainArtist(artist);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withFeature(Artist artist) {
		filter.withFeature(artist);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withProducer(Artist artist) {
		filter.withProducer(artist);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withBPM(short bpm) {
		filter.withBPM(bpm);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withBPM(short min, short max) {
		filter.withBPM(min, max);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withLyrics(String lyrics) {
		filter.withLyrics(lyrics);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withLyrics(Pattern regex) {
		filter.withLyrics(regex);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withExplicitLyrics(boolean explicit) {
		filter.withExplicitLyrics(explicit);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withOriginal(Track track) {
		filter.withOriginal(track);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withOriginal(ObjectId id) {
		filter.withOriginal(id);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withHiddenTrack(String hiddenTrack) {
		filter.withHiddenTrack(hiddenTrack);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withHiddenTrack(Pattern regex) {
		filter.withHiddenTrack(regex);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withVersionArtist(Artist artist) {
		filter.withVersionArtist(artist);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withVersionToken(String token) {
		filter.withVersionToken(token);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withVersionToken(Pattern regex) {
		filter.withVersionToken(regex);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withBPM(Range<Short> range) {
		filter.withBPM(range);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withTrackKey(TrackKey key) {
		filter.withTrackKey(key);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withTrackMode(TrackMode mode) {
		filter.withTrackMode(mode);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withInstrumental(boolean instrumental) {
		filter.withInstrumental(instrumental);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withLive(boolean live) {
		filter.withLive(live);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withAcoustic(boolean acoustic) {
		filter.withAcoustic(acoustic);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withDanceability(double danceability) {
		filter.withDanceability(danceability);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withEnergy(double energy) {
		filter.withEnergy(energy);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withValence(double valence) {
		filter.withValence(valence);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withDanceability(double min, double max) {
		filter.withDanceability(min, max);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withDanceability(Range<Double> range) {
		filter.withDanceability(range);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withEnergy(double min, double max) {
		filter.withEnergy(min, max);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withEnergy(Range<Double> range) {
		filter.withEnergy(range);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withValence(double min, double max) {
		filter.withValence(min, max);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withValence(Range<Double> range) {
		filter.withValence(range);
		return this;
	}

}
