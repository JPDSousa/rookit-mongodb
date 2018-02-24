package org.rookit.mongodb.inject.morphia;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.rookit.mongodb.DatabaseConfig;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

@SuppressWarnings("javadoc")
public class MongoClientProvider implements Provider<MongoClient> {

	private final List<ServerAddress> address;
	private final MongoClientOptions options;
	
	private MongoClient currentClient;
	
	@Inject
	private MongoClientProvider(final DatabaseConfig config) {
		super();
		final Properties properties = config.getOptions();
		final int port = Integer.valueOf(properties.getProperty(DatabaseConfig.PORT));
		final String host = properties.getProperty(DatabaseConfig.HOST);
		
		this.address = Collections.singletonList(new ServerAddress(host, port));
		this.options = MongoClientOptions.builder()
				.connectTimeout(1000)
				.build();
	}

	@Override
	public synchronized MongoClient get() {
		if (Objects.isNull(currentClient) || isClosed()) {
			currentClient = new MongoClient(address, options);
		}
		return currentClient;
	}

	private boolean isClosed() {
		try {
			currentClient.getAddress();
			return false;
		} catch(Exception e) {
			return true;
		}
	}
	
}
