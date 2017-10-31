package org.rookit.mongodb.queries.filter;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.Musician;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.artist.TypeGender;
import org.rookit.dm.artist.TypeGroup;

@SuppressWarnings("javadoc")
public interface ArtistFilter<Q> extends GenreableFilter<Q> {
	
	Q withName(String artistName);
	Q withName(Pattern regex);
	
	Q withFullName(String fullName);
	Q withFullName(Pattern regex);
	
	Q relatedTo(Artist artist);
	
	Q withAlias(String alias);
	Q withAlias(Pattern regex);

	Q withArtistType(TypeArtist type);

	Q withOrigin(String origin);
	
	Q withIPI(String ipi);
	
	Q withBeginDate(LocalDate date);
	Q withEndDate(LocalDate date);
	
	Q withISNI(String isni);
	
	Q withGender(TypeGender gender);
	
	Q withGroupType(TypeGroup type);
	
	Q withMember(Musician member);

}
