package org.rookit.mongodb.queries;

import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rookit.api.storage.DBManager;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.mongodb.utils.TestResources;

import com.google.inject.Injector;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public abstract class AbstractDBManagerTest {
	
	private static final Object SETUP_LOCK = new Object();
	private static Injector injector;
	
	protected DBManager guineaPig;
	protected DMTestFactory factory;

	@BeforeClass
	public static final void setUp() {
		synchronized (SETUP_LOCK) {
			injector = TestResources.getInjector();
		}
	}

	@AfterClass
	public static synchronized final void tearDown() throws IOException {
		synchronized (SETUP_LOCK) {
			final DBManager instance = injector.getInstance(DBManager.class);
			instance.close();
		}
	}

	@Before
	public final void beforeTest() {
		final MongoDatabase database = injector.getInstance(MongoDatabase.class);
		assumeTrue(Objects.isNull(database.listCollectionNames().first()));
		factory = injector.getInstance(DMTestFactory.class);
		guineaPig = injector.getInstance(DBManager.class);
		guineaPig.init();
	}

	@After
	public final void afterTest() {
		guineaPig.clear();
	}

}