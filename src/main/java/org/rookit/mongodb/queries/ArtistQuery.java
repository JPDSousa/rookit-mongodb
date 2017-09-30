package org.rookit.mongodb.queries;

import org.rookit.dm.artist.Artist;

@SuppressWarnings("javadoc")
public interface ArtistQuery extends RookitQuery<Artist> {

	ArtistQueryImpl withName(String artistName);

}
