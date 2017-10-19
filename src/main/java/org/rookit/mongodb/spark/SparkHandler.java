package org.rookit.mongodb.spark;

import java.io.Serializable;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.BsonDocument;
import org.smof.collection.SmofCollection;
import org.smof.element.Element;
import org.smof.parsers.SmofParser;

import com.google.common.collect.Maps;
import com.mongodb.MongoClient;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;

@SuppressWarnings("javadoc")
public class SparkHandler<E extends Element> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String host;
	private final int port;
	private final String databaseName;
	private final Class<E> type;

	public SparkHandler(String host, int port, String databaseName, Class<E> type) {
		this.databaseName = databaseName;
		this.type = type;
		this.host = host;
		this.port = port;
	}
	
	public JavaRDD<E> stream(JavaSparkContext context, SmofCollection<E> collection) {
		final Map<String, String> readOverrides = Maps.newLinkedHashMap();
		readOverrides.put("uri", "mongodb://" + host + ":" + port + "/"+ databaseName + "." + collection.getCollectionName());
		readOverrides.put("database", databaseName);
		readOverrides.put("collection", collection.getCollectionName());
		final ReadConfig readConfig = ReadConfig.create(context.getConf(), readOverrides);
		final SmofParser parser = collection.getParser();
		return MongoSpark.load(context, readConfig)
				.filter(doc -> doc != null)
				.map(doc -> doc.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()))
				.map(bsonDoc -> parser.fromBson(bsonDoc, type));
	}
	
	public Class<E> getType() {
		return type;
	}

}
