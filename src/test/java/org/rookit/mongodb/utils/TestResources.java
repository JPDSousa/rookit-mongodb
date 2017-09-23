package org.rookit.mongodb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public class TestResources {
	
	public static final Path RESOURCES = Paths.get("src", "test", "resources");

	public static final List<Path> getTrackPaths() throws IOException {
		return Files.list(RESOURCES).collect(Collectors.toList());
	}
	
	public static final Path getRandomTrackPath() throws IOException {
		final Random random = new Random();
		return Files.list(RESOURCES)
				.collect(Collectors.collectingAndThen(Collectors.toList(), 
						collected -> collected.get(random.nextInt(collected.size()))));
	}

}
