package org.rookit.mongodb.update;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.storage.queries.AlbumQuery;
import org.rookit.api.storage.update.AlbumUpdateFilterQuery;

class MorphiaAlbumUpdateFilterQuery extends AbstractGenreableUpdateFilterQuery<Album, AlbumQuery, AlbumUpdateFilterQuery> implements AlbumUpdateFilterQuery {

	MorphiaAlbumUpdateFilterQuery(AlbumQuery filter, UpdateOperations<Album> updateQuery) {
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
