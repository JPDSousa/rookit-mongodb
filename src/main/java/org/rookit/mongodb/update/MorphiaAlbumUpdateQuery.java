package org.rookit.mongodb.update;

import java.time.LocalDate;
import java.util.Set;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.queries.AlbumQuery;
import org.rookit.api.storage.update.AlbumUpdateFilterQuery;
import org.rookit.api.storage.update.AlbumUpdateQuery;

import static org.rookit.api.dm.album.AlbumFields.*;

class MorphiaAlbumUpdateQuery extends AbstractGenreableUpdateQuery<Album, AlbumQuery, AlbumUpdateQuery, AlbumUpdateFilterQuery>implements AlbumUpdateQuery {
	
	MorphiaAlbumUpdateQuery(UpdateOperations<Album> smofUpdateQuery, AlbumQuery filter) {
		super(smofUpdateQuery, filter);
	}

	@Override
	public AlbumUpdateFilterQuery where() {
		return new MorphiaAlbumUpdateFilterQuery(filter, query);
	}

	@Override
	public AlbumUpdateQuery addArtist(Artist artist) {
		query.addToSet(ARTISTS, artist);
		return this;
	}

	@Override
	public AlbumUpdateQuery setArtists(Set<Artist> artists) {
		query.set(ARTISTS, artists);
		return this;
	}

	@Override
	public AlbumUpdateQuery setReleaseDate(LocalDate date) {
		query.set(RELEASE_DATE, date);
		return this;
	}

	@Override
	public AlbumUpdateQuery setTitle(String title) {
		query.set(TITLE, title);
		return this;
	}

	@Override
	public AlbumUpdateQuery addTrack(TrackSlot trackSlot) {
		addTrack(trackSlot.getTrack(), trackSlot.getNumber(), trackSlot.getDisc());
		return this;
	}

	@Override
	public AlbumUpdateQuery addTrack(Track track, Integer i, String discName) {
		LOGGER.invalidOperation("Operation not supported. Use replace operation instead");
		return this;
	}

	@Override
	public AlbumUpdateQuery addTrackLast(Track track, String discName) {
		LOGGER.invalidOperation("Operation not supported. Use replace operation instead");
		return this;
	}

	@Override
	public AlbumUpdateQuery setCover(byte[] image) {
		LOGGER.invalidOperation("Operation not supported");
		return this;
	}

	@Override
	public void relocate(String discName, Integer number, String newDiscName, Integer newNumber) {
		LOGGER.invalidOperation("Operation not supported. Use replace operation instead");
	}


}
