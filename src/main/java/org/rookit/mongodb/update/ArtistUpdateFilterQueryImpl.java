package org.rookit.mongodb.update;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.Musician;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.artist.TypeGender;
import org.rookit.dm.artist.TypeGroup;
import org.rookit.mongodb.queries.ArtistQuery;

class ArtistUpdateFilterQueryImpl extends AbstractGenreableUpdateFilterQuery<Artist, ArtistQuery, ArtistUpdateFilterQuery>
		implements ArtistUpdateFilterQuery {

	protected ArtistUpdateFilterQueryImpl(ArtistQuery filter, UpdateOperations<Artist> updateQuery) {
		super(filter, updateQuery);
	}

	@Override
	public ArtistUpdateFilterQuery withName(String artistName) {
		filter.withName(artistName);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withName(Pattern regex) {
		filter.withName(regex);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withFullName(String fullName) {
		filter.withFullName(fullName);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withFullName(Pattern regex) {
		filter.withFullName(regex);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery relatedTo(Artist artist) {
		filter.relatedTo(artist);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withAlias(String alias) {
		filter.withAlias(alias);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withAlias(Pattern regex) {
		filter.withAlias(regex);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withArtistType(TypeArtist type) {
		filter.withArtistType(type);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withOrigin(String origin) {
		filter.withOrigin(origin);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withIPI(String ipi) {
		filter.withIPI(ipi);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withBeginDate(LocalDate date) {
		filter.withBeginDate(date);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withEndDate(LocalDate date) {
		filter.withEndDate(date);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withISNI(String isni) {
		filter.withISNI(isni);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withGender(TypeGender gender) {
		filter.withGender(gender);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withGroupType(TypeGroup type) {
		filter.withGroupType(type);
		return this;
	}

	@Override
	public ArtistUpdateFilterQuery withMember(Musician member) {
		filter.withMember(member);
		return this;
	}

}
