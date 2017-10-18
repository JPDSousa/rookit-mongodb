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

import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface TrackQuery extends GenreableQuery<Track, TrackQuery> {
	
	TrackQuery withTitle(String title);
	TrackQuery withTitle(Pattern regex);
	
	TrackQuery withMainArtist(Artist artist);
	
	TrackQuery withFeature(Artist artist);
	
	TrackQuery withProducer(Artist artist);
	
	TrackQuery withBPM(int bpm);
	TrackQuery withBPM(int min, int max);
	
	TrackQuery withLyrics(String lyrics);
	TrackQuery withLyrics(Pattern regex);
	
	TrackQuery withExplicitLyrics(boolean explicit);
	
	TrackQuery withOriginal(Track track);
	TrackQuery withOriginal(ObjectId id);

	TrackQuery withHiddenTrack(String hiddenTrack);
	TrackQuery withHiddenTrack(Pattern regex);

	TrackQuery withVersionArtist(Artist artist);
	
	TrackQuery withVersionToken(String token);
	TrackQuery withVersionToken(Pattern regex);
	
}
