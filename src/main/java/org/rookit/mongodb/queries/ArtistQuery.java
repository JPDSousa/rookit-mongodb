package org.rookit.mongodb.queries;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.artist.TypeGender;
import org.rookit.dm.artist.TypeGroup;

@SuppressWarnings("javadoc")
public interface ArtistQuery extends RookitQuery<Artist> {

	ArtistQuery withName(String artistName);

	ArtistQuery withArtistType(TypeArtist type);

	ArtistQuery withOrigin(String origin);
	
	ArtistQuery withIPI(String ipi);
	
	ArtistQuery withISNI(String isni);
	
	ArtistQuery withGender(TypeGender gender);
	
	ArtistQuery withGroupType(TypeGroup type);

}
