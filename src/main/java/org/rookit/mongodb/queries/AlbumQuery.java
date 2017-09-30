package org.rookit.mongodb.queries;

import org.rookit.dm.album.Album;
import org.rookit.dm.album.TypeAlbum;
import org.rookit.dm.album.TypeRelease;

@SuppressWarnings("javadoc")
public interface AlbumQuery extends RookitQuery<Album> {

	AlbumQuery withTitle(String albumTitle);

	AlbumQuery withType(TypeAlbum type);

	AlbumQuery withReleaseType(TypeRelease type);
	
}
