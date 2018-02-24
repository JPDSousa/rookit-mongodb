/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.mongodb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

import org.rookit.api.storage.DBManager;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.mongodb.DatabaseConfig;
import org.rookit.mongodb.RookitMorphia;
import org.rookit.mongodb.gridfs.Buckets;
import org.rookit.mongodb.inject.factory.MorphiaFactoriesModule;
import org.rookit.mongodb.inject.morphia.MongoClientProvider;
import org.rookit.mongodb.test.inject.MongoDatabaseProvider;
import org.rookit.utils.resource.Resources;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public class TestResources {

	public static final Path COVERS = Resources.RESOURCES_TEST.resolve("covers");
	public static final Path TRACKS = Resources.RESOURCES_TEST.resolve("tracks");
	
	private static final Injector INJECTOR = Guice.createInjector(
			DMTestFactory.getModule(),
			new TestModule(), 
			new MorphiaFactoriesModule());
	
	private static final String HOST = "localhost";
	private static final int PORT = 27020;
	
	public static Injector getInjector() {
		return INJECTOR;
	}
	
	public static final List<Path> getCoverPaths() throws IOException {
		return listAsList(COVERS);
	}
	
	public static final List<Path> getTrackPaths() throws IOException {
		return listAsList(TRACKS);
	}
	
	private static final List<Path> listAsList(Path path) throws IOException {
		return Files.list(path).collect(Collectors.toList());
	}
	
	public static final Path getRandomTrackPath() throws IOException {
		final Random random = new Random();
		return Files.list(TRACKS)
				.collect(Collectors.collectingAndThen(Collectors.toList(), 
						collected -> collected.get(random.nextInt(collected.size()))));
	}
	
	public static final Path getRandomCoverPath() throws IOException {
		final Random random = new Random();
		return Files.list(COVERS)
				.collect(Collectors.collectingAndThen(Collectors.toList(), 
						collected -> collected.get(random.nextInt(collected.size()))));
	}
	
	private static class TestModule extends AbstractModule {

		@Override
		protected void configure() {
			final DatabaseConfig config = new DatabaseConfig();
			final Properties props = new Properties();
			props.put(DatabaseConfig.HOST, HOST);
			props.put(DatabaseConfig.PORT, Integer.toString(PORT));
			config.setOptions(props);
			
			bind(DatabaseConfig.class).toInstance(config);
			bind(MongoClient.class).toProvider(MongoClientProvider.class);
			bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);
			bind(Buckets.class);
			bind(DBManager.class).to(RookitMorphia.class);
		}
		
	}

}
