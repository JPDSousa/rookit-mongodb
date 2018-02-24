package org.rookit.mongodb.gridfs;

import static com.google.common.base.Preconditions.*;

import java.util.Map;

import org.bson.types.ObjectId;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;

import com.mongodb.client.gridfs.GridFSBucket;

@SuppressWarnings("javadoc")
public class GridFsBiStreamFactory implements RookitFactory<BiStream> {
	
	public static final GridFsBiStreamFactory create(Buckets bucketCache, 
			String bucketName) {
		return new GridFsBiStreamFactory(bucketCache, bucketName);
	}

	private final GridFSBucket bucket;
	
	private GridFsBiStreamFactory(Buckets bucketCache, String bucketName) {
		checkArgument(bucketCache != null, "Must specify a bucket cache");
		this.bucket = bucketCache.getBucket(bucketName);
	}

	@Override
	public BiStream createEmpty() {
		return new GridFsBiStream(bucket, null);
	}

	@Override
	public BiStream create(Map<String, Object> data) {
		final Object id = data.get(GridFsBiStream.ID);
		if(id != null && id instanceof ObjectId) {
			return new GridFsBiStream(bucket, (ObjectId) id);
		}
		return createEmpty();
	}
}
