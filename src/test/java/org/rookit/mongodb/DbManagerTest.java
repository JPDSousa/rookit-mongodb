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
import org.rookit.dm.utils.DMTestFactory;
import org.rookit.mongodb.DBManager;
import org.rookit.mongodb.utils.TestResources;
import org.smof.gridfs.SmofGridRef;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class DbManagerTest {
	
	private static final String HOST = "localhost";
	private static final int PORT = 27020;
	private static final String DB_NAME = "rookit_test";
	
	private static DBManager guineaPig;
	private static DMTestFactory factory;
	
	@BeforeClass
	public static final void setUp() {
		guineaPig = DBManager.open(HOST, PORT, DB_NAME);
		factory = DMTestFactory.getDefault();
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
		final Track expected = factory.getRandomTrack();
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
	public final void testAddDuplicateTrack() throws IOException {
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		final String title = factory.randomString();
		final Track expected = factory.createOriginalTrack(title, mainArtists, Sets.newLinkedHashSet(), Sets.newLinkedHashSet());
		final Track expectedDup = factory.createOriginalTrack(title, mainArtists, Sets.newLinkedHashSet(), Sets.newLinkedHashSet());
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
	
	@Test
	public final void testSparkArtist() {
		final Artist expected = factory.getRandomArtist();
		guineaPig.addArtist(expected);
		assertEquals(expected, guineaPig.streamArtists().first());
	}

}
