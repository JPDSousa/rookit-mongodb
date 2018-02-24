package org.rookit.mongodb.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.grapher.graphviz.GraphvizGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;

@SuppressWarnings("javadoc")
public class InjectorVisualizer {

	public static void generateInjectorGraph(final Path filePath, final Injector injector) throws IOException {
		final PrintWriter out = new PrintWriter(filePath.toString(), "UTF-8");
	    final Injector vizInjector = Guice.createInjector(new GraphvizModule());
	    GraphvizGrapher grapher = vizInjector.getInstance(GraphvizGrapher.class);
	    grapher.setOut(out);
	    grapher.setRankdir("TB");
	    grapher.graph(injector);
	}
}
