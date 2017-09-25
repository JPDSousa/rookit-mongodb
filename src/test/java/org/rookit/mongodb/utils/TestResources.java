package org.rookit.mongodb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.rookit.utils.resource.Resources;

@SuppressWarnings("javadoc")
public class TestResources {

	public static final List<Path> getTrackPaths() throws IOException {
		return Files.list(Resources.RESOURCES_TEST).collect(Collectors.toList());
	}
	
	public static final Path getRandomTrackPath() throws IOException {
		final Random random = new Random();
		return Files.list(Resources.RESOURCES_TEST)
				.collect(Collectors.collectingAndThen(Collectors.toList(), 
						collected -> collected.get(random.nextInt(collected.size()))));
	}

}
