package org.rookit.mongodb.queries;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.rookit.dm.album.Album;
import org.rookit.dm.album.TypeAlbum;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;

@SuppressWarnings("javadoc")
public interface AlbumQuery extends GenreableQuery<Album, AlbumQuery> {

	AlbumQuery withTitle(String albumTitle);
	AlbumQuery withTitle(Pattern regex);

	AlbumQuery withArtist(Artist artist);
	
	AlbumQuery withType(TypeAlbum type);

	AlbumQuery withReleaseType(TypeRelease type);
	AlbumQuery withAnyReleaseType(TypeRelease[] types);
	
	AlbumQuery withReleaseDate(LocalDate date);
	
}
