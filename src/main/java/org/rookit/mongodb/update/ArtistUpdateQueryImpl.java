package org.rookit.mongodb.update;

import java.time.LocalDate;
import java.util.Set;

import org.mongodb.morphia.query.UpdateOperations;
import org.rookit.dm.artist.Artist;
import org.rookit.mongodb.queries.ArtistQuery;
import org.rookit.utils.exception.InvalidOperationException;

import static org.rookit.dm.artist.DatabaseFields.*;

class ArtistUpdateQueryImpl extends AbstractGenreableUpdateQuery<Artist, ArtistQuery, ArtistUpdateQuery, ArtistUpdateFilterQuery> implements ArtistUpdateQuery {

	ArtistUpdateQueryImpl(UpdateOperations<Artist> query, ArtistQuery filter) {
		super(query, filter);
	}

	@Override
	public ArtistUpdateFilterQuery where() {
		return new ArtistUpdateFilterQueryImpl(filter, query);
	}

	@Override
	public ArtistUpdateQuery addRelatedArtist(Artist artist) {
		query.addToSet(RELATED, artist);
		return this;
	}

	@Override
	public ArtistUpdateQuery setOrigin(String origin) {
		query.set(ORIGIN, origin);
		return this;
	}

	@Override
	public ArtistUpdateQuery addAlias(String alias) {
		query.addToSet(ALIASES, alias);
		return this;
	}

	@Override
	public ArtistUpdateQuery setAliases(Set<String> aliases) {
		query.set(ALIASES, aliases);
		return this;
	}

	@Override
	public ArtistUpdateQuery setBeginDate(LocalDate beginDate) {
		query.set(BEGIN_DATE, beginDate);
		return this;
	}

	@Override
	public ArtistUpdateQuery setEndDate(LocalDate endDate) {
		query.set(END_DATE, endDate);
		return this;
	}

	@Override
	public ArtistUpdateQuery setIPI(String ipi) {
		query.set(IPI, ipi);
		return this;
	}

	@Override
	public ArtistUpdateQuery setISNI(String isni) {
		query.set(ISNI, isni);
		return this;
	}

	@Override
	public ArtistUpdateQuery setPicture(byte[] picture) {
		throw new InvalidOperationException("Not supported yet");
	}
	
}
