package org.rookit.mongodb.inject.morphia;

import org.rookit.api.storage.DBManager;
import org.rookit.mongodb.RookitMorphia;
import org.rookit.mongodb.gridfs.Buckets;

import com.google.inject.AbstractModule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public class MorphiaModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MongoClient.class).toProvider(MongoClientProvider.class);
		bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);
		bind(Buckets.class);
		bind(DBManager.class).to(RookitMorphia.class);
	}
}
