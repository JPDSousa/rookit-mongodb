package org.rookit.mongodb.queries;

import static org.assertj.core.api.Assertions.*;
import static org.rookit.api.dm.track.TrackFields.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.storage.queries.TrackQuery;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;

@SuppressWarnings("javadoc")
@RunWith(ConcurrentTestRunner.class)
public class TrackQueryTest extends AbstractDBManagerTest {

	@Test
	public final void testRemixes() {
		final TrackFactory factory = guineaPig.getFactories().getTrackFactory();
		final Set<Artist> main = this.factory.getRandomSetOfArtists();
		final Set<Artist> remixA1 = this.factory.getRandomSetOfArtists();
		final Set<Artist> remixA2 = this.factory.getRandomSetOfArtists(); 
		final Track original = factory.createOriginalTrack("I'm Original");
		final VersionTrack remix1 = factory.createVersionTrack(TypeVersion.REMIX, original);
		final VersionTrack remix2 = factory.createVersionTrack(TypeVersion.REMIX, original);
		original.setMainArtists(main);
		remixA1.forEach(remix1::addVersionArtist);
		remixA2.forEach(remix2::addVersionArtist);
		guineaPig.addTrack(original);
		guineaPig.addTrack(remix1);
		guineaPig.addTrack(remix2);

		assertThat(guineaPig.getTracks().count()).isEqualTo(3);
		final BsonDocument expectedQuery = new BsonDocument("query", 
				new BsonDocument(ORIGINAL, new BsonObjectId(original.getId())));
		final TrackQuery query = guineaPig
				.getTracks()
				.withOriginal(original);
		assertThat(BsonDocument.parse(query.toString())).isEqualTo(expectedQuery);
		final List<Track> results = query
				.stream()
				.collect(Collectors.toList());
		
		assertThat(results)
		.contains(remix1, remix2);
	}
}
