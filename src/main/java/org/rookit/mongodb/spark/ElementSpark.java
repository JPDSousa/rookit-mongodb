package org.rookit.mongodb.spark;

import java.io.Closeable;
import java.io.IOException;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.bson.BsonDocument;
import org.smof.collection.SmofCollection;
import org.smof.element.Element;
import org.smof.parsers.SmofParser;

import com.mongodb.spark.MongoSpark;

@SuppressWarnings("javadoc")
public class ElementSpark<E extends Element> implements Closeable {

	private final JavaSparkContext context;
	private final SmofParser parser;
	private final Class<E> type;

	public ElementSpark(String databaseName, SmofCollection<E> collection) {
		final String connection = new StringBuilder("mongodb://127.0.0.1/")
				.append(databaseName)
				.append(".")
				.append(collection.getCollectionName())
				.toString();
		final SparkSession session = SparkSession.builder()
				.master("local")
				.appName("rookit-spark")
				.config("spark.mongodb.input.uri", connection)
				.config("spark.mongodb.output.uri", connection)
				.getOrCreate();
		parser = collection.getParser();
		type = collection.getType();
		context = new JavaSparkContext(session.sparkContext());
	}
	
	public JavaRDD<E> stream() {
		return MongoSpark.load(context)
				.map(doc -> doc.toBsonDocument(BsonDocument.class, parser.getRegistry()))
				.map(bsonDoc -> parser.fromBson(bsonDoc, type));
	}

	@Override
	public void close() throws IOException {
		context.close();
	}

}
