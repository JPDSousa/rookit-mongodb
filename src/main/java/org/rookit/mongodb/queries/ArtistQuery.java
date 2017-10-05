package org.rookit.mongodb.queries;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.Musician;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.artist.TypeGender;
import org.rookit.dm.artist.TypeGroup;

@SuppressWarnings("javadoc")
public interface ArtistQuery extends GenreableQuery<Artist, ArtistQuery> {

	ArtistQuery withName(String artistName);
	ArtistQuery withName(Pattern regex);
	
	ArtistQuery withFullName(String fullName);
	ArtistQuery withFullName(Pattern regex);
	
	ArtistQuery relatedTo(Artist artist);
	
	ArtistQuery withAlias(String alias);
	ArtistQuery withAlias(Pattern regex);

	ArtistQuery withArtistType(TypeArtist type);

	ArtistQuery withOrigin(String origin);
	
	ArtistQuery withIPI(String ipi);
	
	ArtistQuery withBeginDate(LocalDate date);
	ArtistQuery withEndDate(LocalDate date);
	
	ArtistQuery withISNI(String isni);
	
	ArtistQuery withGender(TypeGender gender);
	
	ArtistQuery withGroupType(TypeGroup type);
	
	ArtistQuery withMember(Musician member);

}
