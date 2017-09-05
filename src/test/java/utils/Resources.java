package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public class Resources {
	
	public static final Path RESOURCES = Paths.get("src", "test", "resources");
	
	public static final Path getRandomTrackPath() throws IOException {
		final Random random = new Random();
		return Files.list(RESOURCES)
				.collect(Collectors.collectingAndThen(Collectors.toList(), 
						collected -> collected.get(random.nextInt(collected.size()))));
		
	}

}
