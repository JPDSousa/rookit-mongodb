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
import java.util.Random;
import java.util.stream.Collectors;

import org.rookit.mongodb.DBManager;
import org.rookit.utils.resource.Resources;

@SuppressWarnings("javadoc")
public class TestResources {

	public static final Path COVERS = Resources.RESOURCES_TEST.resolve("covers");
	public static final Path TRACKS = Resources.RESOURCES_TEST.resolve("tracks");
	
	private static final String HOST = "localhost";
	private static final int PORT = 27020;
	private static final String DB_NAME = "rookit_test";
	
	public static DBManager createTestConnection() {
		return DBManager.open(HOST, PORT, DB_NAME);
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

}
