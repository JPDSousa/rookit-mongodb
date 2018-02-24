package org.rookit.mongodb.inject.morphia;

import org.rookit.mongodb.DatabaseConfig;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public class MongoDatabaseProvider implements Provider<MongoDatabase> {

	private final MongoClient client;
	private final String databaseName;
	
	@Inject
	private MongoDatabaseProvider(final MongoClient client, final DatabaseConfig config) {
		super();
		this.client = client;
		this.databaseName = config.getOptions().getProperty(DatabaseConfig.DB_NAME);
	}

	@Override
	public MongoDatabase get() {
		return client.getDatabase(databaseName);
	}
	
}
