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

	private DBManager guineaPig;
	private static DMTestFactory factory;

	@BeforeClass
	public static final void setUp() {
		factory = DMTestFactory.getDefault();
	}

	@Before
	public final void beforeTest() {
		guineaPig = TestResources.createTestConnection();
		guineaPig.init();
	}

	@After
	public final void afterTest() throws IOException {
		guineaPig.clear();
		guineaPig.close();
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
		final BsonDocument expectedQuery = new BsonDocument("query", 
				new BsonDocument(ORIGINAL, new BsonObjectId(original.getId())));
		final TrackQuery query = guineaPig
				.getTracks()
				.withOriginal(original);
		assertEquals(expectedQuery, BsonDocument.parse(query.toString()));
		final List<Track> results = query
				.stream()
				.collect(Collectors.toList());
		assertThat(results, hasItem(remix1));
		assertThat(results, hasItem(remix2));
	}
}
