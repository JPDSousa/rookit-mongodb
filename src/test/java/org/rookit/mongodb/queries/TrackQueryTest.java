package org.rookit.mongodb.queries;

import static org.assertj.core.api.Assertions.*;
import static org.rookit.api.dm.track.TrackFields.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.storage.DBManager;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.mongodb.test.inject.DatabaseDependentTest;
import org.rookit.mongodb.utils.TestResources;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
@RunWith(ConcurrentTestRunner.class)
public class TrackQueryTest extends DatabaseDependentTest<DBManager> {

	private static final Injector INJECTOR = TestResources.getInjector();
	
	private DMTestFactory factory;
	
	@Before
	public void createFactory() {
		factory = INJECTOR.getInstance(DMTestFactory.class);
	}
	
	@Before
	public final void setGuineaPigAsDatabase() {
		this.guineaPig = this.database;
	}
	
	@Test
	public final void testRemixes() {
		final TrackFactory factory = database.getFactories().getTrackFactory();
		final Set<Artist> main = this.factory.getRandomSetOfArtists();
		final Set<Artist> remixA1 = this.factory.getRandomSetOfArtists();
		final Set<Artist> remixA2 = this.factory.getRandomSetOfArtists(); 
		final Track original = factory.createOriginalTrack("I'm Original");
		final VersionTrack remix1 = factory.createVersionTrack(TypeVersion.REMIX, original);
		final VersionTrack remix2 = factory.createVersionTrack(TypeVersion.REMIX, original);
		original.setMainArtists(main);
		remixA1.forEach(remix1::addVersionArtist);
		remixA2.forEach(remix2::addVersionArtist);
		database.addTrack(original);
		database.addTrack(remix1);
		database.addTrack(remix2);

		assertThat(database.getTracks().count()).isEqualTo(3);
		final BsonDocument expectedQuery = new BsonDocument("query", 
				new BsonDocument(ORIGINAL, new BsonObjectId(original.getId())));
		final TrackQuery query = database
				.getTracks()
				.withOriginal(original);
		assertThat(BsonDocument.parse(query.toString())).isEqualTo(expectedQuery);
		final List<Track> results = query
				.stream()
				.collect(Collectors.toList());
		
		assertThat(results)
		.contains(remix1, remix2);
	}

	@Override
	protected Injector getInjector() {
		return INJECTOR;
	}

	@Override
	protected DBManager createGuineaPig() {
		return mock(DBManager.class);
	}
}
