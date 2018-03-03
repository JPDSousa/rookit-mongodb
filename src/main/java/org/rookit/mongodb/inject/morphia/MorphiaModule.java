package org.rookit.mongodb.inject.morphia;

import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.rookit.api.storage.DBManager;
import org.rookit.mongodb.RookitMorphia;
import org.rookit.mongodb.gridfs.Buckets;
import org.rookit.mongodb.log.RookitMorphiaLoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public class MorphiaModule extends AbstractModule {

	@Override
	protected void configure() {
		MorphiaLoggerFactory.registerLogger(RookitMorphiaLoggerFactory.class);
		
		bind(MongoClient.class).toProvider(MongoClientProvider.class);
		bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);
		bind(DBManager.class).to(RookitMorphia.class);
	}
	
	@Provides
	private Buckets getBuckets(final MongoDatabase db) {
		return Buckets.create(db);
	}
}
