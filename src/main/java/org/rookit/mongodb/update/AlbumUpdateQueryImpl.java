package org.rookit.mongodb.update;

import java.time.LocalDate;
import java.util.Set;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.TrackSlot;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;
import org.rookit.mongodb.queries.AlbumQuery;
import org.rookit.utils.exception.InvalidOperationException;

import static org.rookit.dm.album.DatabaseFields.*;


class AlbumUpdateQueryImpl extends AbstractGenreableUpdateQuery<Album, AlbumQuery, AlbumUpdateQuery, AlbumUpdateFilterQuery>implements AlbumUpdateQuery {
	
	AlbumUpdateQueryImpl(UpdateOperations<Album> smofUpdateQuery, AlbumQuery filter) {
		super(smofUpdateQuery, filter);
	}

	@Override
	public AlbumUpdateFilterQuery where() {
		return new AlbumUpdateFilterQueryImpl(filter, query);
	}

	@Override
	public AlbumUpdateQuery addArtist(Artist artist) {
		throw new InvalidOperationException("Not supported yet");
	}

	@Override
	public AlbumUpdateQuery addTrack(Track arg0, Integer arg1) {
		throw new InvalidOperationException("Not supported yet");
	}

	@Override
	public AlbumUpdateQuery addTrack(Track arg0, Integer arg1, String arg2) {
		throw new InvalidOperationException("Not supported yet");
	}

	@Override
	public AlbumUpdateQuery addTrackLast(Track arg0) {
		throw new InvalidOperationException("Not supported yet");
	}

	@Override
	public AlbumUpdateQuery addTrackLast(Track arg0, String arg1) {
		throw new InvalidOperationException("Not supported yet");
	}

	@Override
	public void relocate(String arg0, Integer arg1, String arg2, Integer arg3) {
		throw new InvalidOperationException("Not supported");
	}

	@Override
	public AlbumUpdateQuery setArtists(Set<Artist> artists) {
		query.set(ARTISTS, artists);
		return this;
	}

	@Override
	public AlbumUpdateQuery setCover(byte[] arg0) {
		throw new InvalidOperationException("Not supported yet");
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


}
