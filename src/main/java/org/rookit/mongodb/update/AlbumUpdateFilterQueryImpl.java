package org.rookit.mongodb.update;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.rookit.dm.album.Album;
import org.rookit.dm.album.TypeAlbum;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.rookit.mongodb.queries.AlbumQuery;
import org.smof.collection.SmofUpdateQuery;

class AlbumUpdateFilterQueryImpl extends AbstractGenreableUpdateFilterQuery<Album, AlbumQuery, AlbumUpdateFilterQuery> implements AlbumUpdateFilterQuery {

	AlbumUpdateFilterQueryImpl(AlbumQuery filter, SmofUpdateQuery<Album> updateQuery) {
		super(filter, updateQuery);
	}

	@Override
	public AlbumUpdateFilterQuery withTitle(String albumTitle) {
		filter.withTitle(albumTitle);
		return this;
	}

	@Override
	public AlbumUpdateFilterQuery withTitle(Pattern regex) {
		filter.withTitle(regex);
		return this;
	}

	@Override
	public AlbumUpdateFilterQuery withArtist(Artist artist) {
		filter.withArtist(artist);
		return this;
	}

	@Override
	public AlbumUpdateFilterQuery withType(TypeAlbum type) {
		filter.withType(type);
		return this;
	}

	@Override
	public AlbumUpdateFilterQuery withReleaseType(TypeRelease type) {
		filter.withReleaseType(type);
		return this;
	}

	@Override
	public AlbumUpdateFilterQuery withAnyReleaseType(TypeRelease[] types) {
		filter.withAnyReleaseType(types);
		return this;
	}

	@Override
	public AlbumUpdateFilterQuery withReleaseDate(LocalDate date) {
		filter.withReleaseDate(date);
		return this;
	}

}
