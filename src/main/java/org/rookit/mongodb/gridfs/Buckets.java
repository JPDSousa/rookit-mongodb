package org.rookit.mongodb.gridfs;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.rookit.mongodb.utils.DatabaseValidator;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

@SuppressWarnings("javadoc")
public final class Buckets {

	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();
	
	/**
	 * Logger for Buckets.
	 */
	private static final Logger logger = VALIDATOR.getLogger(Buckets.class);
	
	private static final Map<String, Buckets> BUCKETS = Maps.newHashMap();
	
	public static synchronized Buckets create(final MongoDatabase db) {
		return BUCKETS.computeIfAbsent(db.getName(), name -> new Buckets(db));
	}
	
	public static final String AUDIO_BUCKET = "audio";
	public static final String COVER_BUCKET = "album_covers";
	public static final String PICTURE_BUCKET = "artist_pictures";
	
	private final Map<String, GridFSBucket> bucketCache;
	private final Map<String, GridFsBiStreamFactory> factories;

	private Buckets(final MongoDatabase db) {
		logger.info("Creating buckets in collection: {}", db.getName());
		bucketCache = Maps.newHashMap();
		bucketCache.put(AUDIO_BUCKET, GridFSBuckets.create(db, AUDIO_BUCKET));
		bucketCache.put(COVER_BUCKET, GridFSBuckets.create(db, COVER_BUCKET));
		bucketCache.put(PICTURE_BUCKET, GridFSBuckets.create(db, PICTURE_BUCKET));
		
		factories = Maps.newHashMapWithExpectedSize(bucketCache.size());
		factories.put(AUDIO_BUCKET, GridFsBiStreamFactory.create(getBucket(AUDIO_BUCKET)));
		factories.put(COVER_BUCKET, GridFsBiStreamFactory.create(getBucket(COVER_BUCKET)));
		factories.put(PICTURE_BUCKET, GridFsBiStreamFactory.create(getBucket(PICTURE_BUCKET)));
	}

	public GridFSBucket getBucket(final String bucketName) {
		return bucketCache.get(bucketName);
	}
	
	public GridFsBiStreamFactory getFactory(final String bucketName) {
		return factories.get(bucketName);
	}
}
