package org.rookit.mongodb.update;

import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackTitle;
import org.rookit.mongodb.queries.TrackQuery;
import org.smof.collection.SmofUpdate;

import static org.rookit.dm.track.DatabaseFields.*;

class TrackUpdateQueryImpl extends AbstractGenreableUpdateQuery<Track, TrackQuery, TrackUpdateQuery, TrackUpdateFilterQuery> implements TrackUpdateQuery {

	TrackUpdateQueryImpl(SmofUpdate<Track> query, TrackQuery filter) {
		super(query, filter);
	}

	@Override
	public TrackUpdateFilterQuery where() {
		return new TrackUpdateFilterQueryImpl(filter, query.where());
	}

	@Override
	public TrackUpdateQuery setTitle(String title) {
		query.set(TITLE, title);
		return this;
	}

	@Override
	public TrackUpdateQuery setTitle(TrackTitle title) {
		query.set(TITLE, title);
		return this;
	}

	@Override
	public TrackUpdateQuery setMainArtists(Set<Artist> artists) {
		query.set(MAIN_ARTISTS, artists);
		return this;
	}

	@Override
	public TrackUpdateQuery addMainArtist(Artist artist) {
		query.addToSet(MAIN_ARTISTS, artist);
		return this;
	}

	@Override
	public TrackUpdateQuery setFeatures(Set<Artist> features) {
		query.set(FEATURES, features);
		return this;
	}

	@Override
	public TrackUpdateQuery addFeature(Artist artist) {
		query.addToSet(FEATURES, artist);
		return this;
	}

	@Override
	public TrackUpdateQuery setHiddenTrack(String hiddenTrack) {
		query.set(HIDDEN_TRACK, hiddenTrack);
		return this;
	}

	@Override
	public TrackUpdateQuery addProducer(Artist producer) {
		query.addToSet(PRODUCERS, producer);
		return this;
	}

	@Override
	public TrackUpdateQuery setProducers(Set<Artist> producer) {
		query.set(PRODUCERS, producer);
		return this;
	}

	@Override
	public TrackUpdateQuery setBPM(short bpm) {
		query.set(BPM, bpm);
		return this;
	}

	@Override
	public TrackUpdateQuery setLyrics(String lyrics) {
		query.set(LYRICS, lyrics);
		return this;
	}

	@Override
	public TrackUpdateQuery setExplicit(boolean explicit) {
		query.set(EXPLICIT, explicit);
		return this;
	}


}
