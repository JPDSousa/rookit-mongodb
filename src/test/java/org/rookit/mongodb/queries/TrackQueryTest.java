package org.rookit.mongodb.queries;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.rookit.dm.track.DatabaseFields.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.dm.track.TypeVersion;
import org.rookit.dm.track.VersionTrack;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.mongodb.DBManager;
import org.rookit.mongodb.utils.TestResources;

@SuppressWarnings("javadoc")
public class TrackQueryTest {

	private static DBManager guineaPig;
	private static DMTestFactory factory;
	
	@BeforeClass
	public static final void setUp() {
		guineaPig = TestResources.createTestConnection();
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
	public final void testRemixes() {
		final TrackFactory factory = TrackFactory.getDefault();
		final Set<Artist> main = TrackQueryTest.factory.getRandomSetOfArtists();
		final Set<Artist> remixA1 = TrackQueryTest.factory.getRandomSetOfArtists();
		final Set<Artist> remixA2 = TrackQueryTest.factory.getRandomSetOfArtists(); 
		final Track original = factory.createOriginalTrack("I'm Original");
		final VersionTrack remix1 = factory.createVersionTrack(TypeVersion.REMIX, original);
		final VersionTrack remix2 = factory.createVersionTrack(TypeVersion.REMIX, original);
		original.setMainArtists(main);
		remixA1.forEach(remix1::addVersionArtist);
		remixA2.forEach(remix2::addVersionArtist);
		guineaPig.addTrack(original);
		guineaPig.addTrack(remix1);
		guineaPig.addTrack(remix2);
		assertEquals(3, guineaPig.getTracks().count());
		System.out.println(guineaPig.getTracks().stream().collect(Collectors.toList()));
		final BsonDocument expectedQuery = new BsonDocument(ORIGINAL, 
				new BsonDocument("$eq", new BsonObjectId(original.getId())));
		final TrackQuery query = guineaPig
				.getTracks()
				.withOriginal(original);
		assertEquals(expectedQuery, query.getBson());
		final List<Track> results = query
				.stream()
				.collect(Collectors.toList());
		assertThat(results, hasItem(remix1));
		assertThat(results, hasItem(remix2));
	}
}
