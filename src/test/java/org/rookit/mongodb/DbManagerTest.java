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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.album.Album;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.dm.track.TypeVersion;
import org.rookit.dm.track.VersionTrack;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.mongodb.DBManager;
import org.rookit.mongodb.utils.TestResources;
import org.smof.gridfs.SmofGridRef;

@SuppressWarnings("javadoc")
public class DBManagerTest {
	
	private static DBManager guineaPig;
	private static DMTestFactory factory;
	private static TrackFactory trackFactory;
	
	@BeforeClass
	public static final void setUp() {
		guineaPig = TestResources.createTestConnection();
		factory = DMTestFactory.getDefault();
		trackFactory = TrackFactory.getDefault();
	}
	
	@AfterClass
	public static final void drop() throws IOException {
		guineaPig.close();
	}
	
	@Before
	public final void beforeTest() {
		guineaPig.init();
	}
	
	@After
	public final void afterTest() {
		guineaPig.clear();
	}
	
	@Test
	public final void testAddTrack() throws IOException {
		final Track expected = factory.getRandomOriginalTrack();
		final Path path = TestResources.getRandomTrackPath();
		final SmofGridRef dbRef = expected.getPath();
		dbRef.attachFile(path);
		guineaPig.addTrack(expected);
		final Track actual = guineaPig.getTracks().byElement(expected);
		assertEquals(expected, actual);
		final byte[] expectedContent = Files.readAllBytes(path);
		final byte[] actualContent = guineaPig.download(actual.getPath());
		assertArrayEquals(expectedContent, actualContent);
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
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		final String title = factory.randomString();
		final Track expected = trackFactory.createOriginalTrack(title);
		final Track expectedDup = trackFactory.createOriginalTrack(title);
		expected.setMainArtists(mainArtists);
		expectedDup.setMainArtists(mainArtists);
		assertEquals("Both tracks must be equal in order for the test to make sense", expected, expectedDup);
		final List<Path> paths = TestResources.getTrackPaths();
		assertTrue("Not enought track paths", paths.size() >= 2);
		final SmofGridRef dbRef1 = expected.getPath();
		final SmofGridRef dbRef2 = expectedDup.getPath();
		dbRef1.attachFile(paths.get(0));
		dbRef2.attachFile(paths.get(1));
		guineaPig.addTrack(expected);
		guineaPig.addTrack(expectedDup);
		assertEquals(1, guineaPig.getTracks().count());
	}
	
	@Test
	public final void testAddGenre() {
		final Genre expected = factory.getRandomGenre();
		guineaPig.addGenre(expected);
		final Genre actual = guineaPig.getGenres().byElement(expected);
		assertEquals(expected, actual);
	}
	
	@Test
	public final void testAddAlbum() throws IOException {
		final Album expected = factory.getRandomAlbum();
		final Path randomCoverPath = TestResources.getRandomCoverPath();
		final byte[] expectedContent = Files.readAllBytes(randomCoverPath);
		expected.getCover().attachFile(randomCoverPath);
		guineaPig.addAlbum(expected);
		final Album actual = guineaPig.getAlbums().byElement(expected);
		final byte[] actualContent = guineaPig.download(actual.getCover());
		assertEquals(expected, actual);
		assertArrayEquals(expectedContent, actualContent);
	}
	
	@Test
	public final void testAddArtist() {
		final Artist expected = factory.getRandomArtist();
		guineaPig.addArtist(expected);
		final Artist actual = guineaPig.getArtists().byElement(expected);
		assertEquals(expected, actual);
	}

}
