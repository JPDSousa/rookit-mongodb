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

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.play.Playlist;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.dm.track.TypeVersion;
import org.rookit.dm.track.VersionTrack;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.bistream.BiStream;
import org.rookit.mongodb.DBManager;
import org.rookit.mongodb.gridfs.GridFsBiStream;
import org.rookit.mongodb.utils.TestResources;

import com.mongodb.client.gridfs.model.GridFSFile;

@SuppressWarnings("javadoc")
public class DatabaseTest {
	
	private DBManager guineaPig;
	private static DMTestFactory factory;
	private static TrackFactory trackFactory;
	
	@BeforeClass
	public static final void setUp() {
		factory = DMTestFactory.getDefault();
		trackFactory = TrackFactory.getDefault();
	}
	
	@Before
	public final void beforeTest() {
		guineaPig = TestResources.createTestConnection();
	}
	
	@After
	public final void afterTest() throws IOException {
		guineaPig.clear();
		guineaPig.close();
	}
	
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
		assertEquals(expected, actual);
		final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(bytes.length);
		IOUtils.copy(actual.getPath().toInput(), actualContent);
		assertArrayEquals(bytes, actualContent.toByteArray());
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
		assertEquals(expected, actual);
		final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(bytes.length);
		IOUtils.copy(actual.getImage().toInput(), actualContent);
		assertArrayEquals(bytes, actualContent.toByteArray());
	}
	
	@Test
	public final void testAddRemix() {
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
		assertEquals(4, guineaPig.getTracks().count());
	}
	
	@Test
	public final void testAddDuplicateTrack() throws IOException {
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
		assertEquals("Both tracks must be equal in order for the test to make sense", expected, expectedDup);
		assertTrue("Not enought track paths", paths.size() >= 2);
		output1.write(Files.readAllBytes(paths.get(0)));
		output1.close();
		output2.write(Files.readAllBytes(paths.get(1)));
		output2.close();
		guineaPig.addTrack(expected);
		guineaPig.addTrack(expectedDup);
		assertEquals(1, guineaPig.getTracks().count());
	}
	
	@Test
	public final void testAddGenre() {
		final Genre expected = factory.getRandomGenre();
		guineaPig.addGenre(expected);
		final Genre actual = guineaPig.getGenres()
				.withId(expected.getId())
				.first();
		assertEquals(expected, actual);
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
		assertEquals(expected, actual);
		
		// stream
		assertThat(actual.getCover(), is(instanceOf(GridFsBiStream.class)));
		final GridFsBiStream stream = (GridFsBiStream) actual.getCover();
		
		// gridfs file
		final GridFSFile file = stream.getMetadata();
		assertEquals(expectedContent.length, file.getLength());
		
		// readTo
		final ByteArrayOutputStream arrayOut = new ByteArrayOutputStream(expectedContent.length);
		stream.readTo(arrayOut);
		assertArrayEquals(expectedContent, arrayOut.toByteArray());
		
		// toInput
		final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(expectedContent.length);
		IOUtils.copy(actual.getCover().toInput(), actualContent);
		assertArrayEquals(expectedContent, actualContent.toByteArray());
	}
	
	
	
	@Test
	public final void testAddArtist() {
		final Artist expected = factory.getRandomArtist();
		expected.setGenres(factory.getRandomSetOfGenres());
		guineaPig.addArtist(expected);
		final Artist actual = guineaPig.getArtists()
				.withId(expected.getId())
				.first();
		assertEquals(expected, actual);
		assertEquals(expected.getGenres(), actual.getGenres());
	}

}
