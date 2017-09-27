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

	public static final Path COVERS = Resources.RESOURCES_TEST.resolve("covers");
	public static final Path TRACKS = Resources.RESOURCES_TEST.resolve("tracks");
	
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
