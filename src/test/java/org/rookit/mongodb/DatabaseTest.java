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
package org.rookit.mongodb;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.mongodb.gridfs.GridFsBiStream;
import org.rookit.mongodb.queries.AbstractDBManagerTest;
import org.rookit.mongodb.utils.TestResources;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.mongodb.client.gridfs.model.GridFSFile;

@SuppressWarnings("javadoc")
@RunWith(ConcurrentTestRunner.class)
public class DatabaseTest extends AbstractDBManagerTest {
	
	@Test
	public final void testAddTrack() throws IOException {
		final Track expected = factory.getRandomOriginalTrack();
		final byte[] bytes = Files.readAllBytes(TestResources.getRandomTrackPath());
		final BiStream dbRef = expected.getPath();
		
		final OutputStream output = dbRef.toOutput();
		output.write(bytes);
		output.close();
		guineaPig.addTrack(expected);
		final Track actual = guineaPig.getTracks()
				.withId(expected.getId())
				.first();
		assertThat(actual).isEqualTo(expected);
		final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(bytes.length);
		IOUtils.copy(actual.getPath().toInput(), actualContent);
		assertThat(actualContent.toByteArray()).isEqualTo(bytes);
	}
	
	@Test
	public final void testAddPlaylist() throws IOException {
		final Playlist expected = factory.getRandomPlaylist();
		final byte[] bytes = Files.readAllBytes(TestResources.getRandomCoverPath());
		final BiStream dbRef = expected.getImage();
		
		final OutputStream output = dbRef.toOutput();
		output.write(bytes);
		output.close();
		guineaPig.addPlaylist(expected);
		final Playlist actual = guineaPig.getPlaylists()
				.withId(expected.getId())
				.first();
		assertThat(actual).isEqualTo(expected);
		final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(bytes.length);
		IOUtils.copy(actual.getImage().toInput(), actualContent);
		assertThat(actualContent.toByteArray()).isEqualTo(bytes);
	}
	
	@Test
	public final void testAddRemix() {
		final TrackFactory trackFactory = guineaPig.getFactories().getTrackFactory();
		final Track original = trackFactory.createOriginalTrack("I'm original");
		final VersionTrack remix1 = trackFactory.createVersionTrack(TypeVersion.REMIX, original);
		final VersionTrack remix2 = trackFactory.createVersionTrack(TypeVersion.REMIX, original);
		final VersionTrack acoustic1 = trackFactory.createVersionTrack(TypeVersion.ACOUSTIC, original);
		final Set<Artist> remixA1 = factory.getRandomSetOfArtists();
		final Set<Artist> remixA2 = factory.getRandomUniqueSetOfArtists(remixA1);
		remix1.setVersionArtists(remixA1);
		remix2.setVersionArtists(remixA2);
		guineaPig.addTrack(original);
		guineaPig.addTrack(remix1);
		guineaPig.addTrack(remix2);
		guineaPig.addTrack(acoustic1);
		assertThat(guineaPig.getTracks().count()).isEqualTo(4);
	}
	
	@Test
	public final void testAddDuplicateTrack() throws IOException {
		final TrackFactory trackFactory = guineaPig.getFactories().getTrackFactory();
		final List<Path> paths = TestResources.getTrackPaths();
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		final String title = factory.randomString();
		final Track expected = trackFactory.createOriginalTrack(title);
		final Track expectedDup = trackFactory.createOriginalTrack(title);
		final BiStream dbRef1 = expected.getPath();
		final BiStream dbRef2 = expectedDup.getPath();
		final OutputStream output1 = dbRef1.toOutput();
		final OutputStream output2 = dbRef2.toOutput();
		
		expected.setMainArtists(mainArtists);
		expectedDup.setMainArtists(mainArtists);
		assertThat(expectedDup).as("Both tracks must be equal in order for the test to make sense").isEqualTo(expected);
		assertThat(paths.size() >= 2).as("Not enought track paths").isTrue();
		output1.write(Files.readAllBytes(paths.get(0)));
		output1.close();
		output2.write(Files.readAllBytes(paths.get(1)));
		output2.close();
		guineaPig.addTrack(expected);
		guineaPig.addTrack(expectedDup);
		assertThat(guineaPig.getTracks().count()).isEqualTo(1);
	}
	
	@Test
	public final void testAddGenre() {
		final Genre expected = factory.getRandomGenre();
		guineaPig.addGenre(expected);
		final Genre actual = guineaPig.getGenres()
				.withId(expected.getId())
				.first();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public final void testAddAlbum() throws IOException {
		// preparing data
		final Album expected = factory.getRandomAlbum();
		final byte[] expectedContent = Files.readAllBytes(TestResources.getRandomCoverPath());
		final OutputStream output = expected.getCover().toOutput();
		output.write(expectedContent);
		output.close();
		guineaPig.addAlbum(expected);
		
		// assertions
		final Album actual = guineaPig.getAlbums().withId(expected.getId()).first();
		assertThat(actual).isEqualTo(expected);
		
		// stream
		assertThat(actual.getCover())
		.isInstanceOf(GridFsBiStream.class);
		final GridFsBiStream stream = (GridFsBiStream) actual.getCover();
		
		// gridfs file
		final GridFSFile file = stream.getMetadata();
		assertThat(file.getLength()).isEqualTo(expectedContent.length);
		
		// readTo
		final ByteArrayOutputStream arrayOut = new ByteArrayOutputStream(expectedContent.length);
		stream.readTo(arrayOut);
		assertThat(arrayOut.toByteArray()).isEqualTo(expectedContent);
		
		// toInput
		final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(expectedContent.length);
		IOUtils.copy(actual.getCover().toInput(), actualContent);
		assertThat(actualContent.toByteArray()).isEqualTo(expectedContent);
	}
	
	
	
	@Test
	public final void testAddArtist() {
		final Artist expected = factory.getRandomArtist();
		expected.setGenres(factory.getRandomSetOfGenres());
		guineaPig.addArtist(expected);
		final Artist actual = guineaPig.getArtists()
				.withId(expected.getId())
				.first();
		assertThat(actual).isEqualTo(expected);
		assertThat(actual.getGenres()).isEqualTo(expected.getGenres());
	}

}
