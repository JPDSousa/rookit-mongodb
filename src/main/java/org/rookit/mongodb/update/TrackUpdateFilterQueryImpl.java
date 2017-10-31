package org.rookit.mongodb.update;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.TrackQuery;
import org.smof.collection.SmofUpdateQuery;

class TrackUpdateFilterQueryImpl extends AbstractGenreableUpdateFilterQuery<Track, TrackQuery, TrackUpdateFilterQuery> implements TrackUpdateFilterQuery {

	private final TrackQuery filter;
	
	protected TrackUpdateFilterQueryImpl(TrackQuery filter, SmofUpdateQuery<Track> updateQuery) {
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
	public TrackUpdateFilterQuery withBPM(int bpm) {
		filter.withBPM(bpm);
		return this;
	}

	@Override
	public TrackUpdateFilterQuery withBPM(int min, int max) {
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

}
