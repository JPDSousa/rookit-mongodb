/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
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
