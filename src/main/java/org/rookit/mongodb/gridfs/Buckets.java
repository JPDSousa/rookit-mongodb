package org.rookit.mongodb.gridfs;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

@SuppressWarnings("javadoc")
public class Buckets {

	public static final String AUDIO_BUCKET = "audio";
	public static final String COVER_BUCKET = "album_covers";
	public static final String PICTURE_BUCKET = "artist_pictures";
	
	private final Map<String, GridFSBucket> bucketCache;
	private final Map<String, GridFsBiStreamFactory> factories;

	@Inject
	private Buckets(MongoDatabase db) {
		bucketCache = Maps.newHashMap();
		bucketCache.put(AUDIO_BUCKET, GridFSBuckets.create(db, AUDIO_BUCKET));
		bucketCache.put(COVER_BUCKET, GridFSBuckets.create(db, COVER_BUCKET));
		bucketCache.put(PICTURE_BUCKET, GridFSBuckets.create(db, PICTURE_BUCKET));
		
		factories = Maps.newHashMapWithExpectedSize(bucketCache.size());
		factories.put(AUDIO_BUCKET, GridFsBiStreamFactory.create(this, AUDIO_BUCKET));
		factories.put(COVER_BUCKET, GridFsBiStreamFactory.create(this, COVER_BUCKET));
		factories.put(PICTURE_BUCKET, GridFsBiStreamFactory.create(this, PICTURE_BUCKET));
	}

	public GridFSBucket getBucket(String bucketName) {
		return bucketCache.get(bucketName);
	}
	
	public GridFsBiStreamFactory getFactory(String bucketName) {
		return factories.get(bucketName);
	}
}
