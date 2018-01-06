package org.rookit.mongodb.gridfs;

import static com.google.common.base.Preconditions.*;

import java.util.Map;

import org.bson.types.ObjectId;
import org.rookit.dm.utils.bistream.BiStream;
import org.rookit.dm.utils.factory.RookitFactory;

import com.mongodb.client.gridfs.GridFSBucket;

@SuppressWarnings("javadoc")
public class GridFsBiStreamFactory implements RookitFactory<BiStream> {
	
	public static final GridFsBiStreamFactory create(Map<String, GridFSBucket> bucketCache, 
			String bucketName) {
		return new GridFsBiStreamFactory(bucketCache, bucketName);
	}

	private final Map<String, GridFSBucket> bucketCache;
	private final GridFSBucket bucket;
	
	private GridFsBiStreamFactory(Map<String, GridFSBucket> bucketCache, String bucketName) {
		checkArgument(bucketCache != null, "Must specify a bucket cache");
		this.bucketCache = bucketCache;
		this.bucket = bucketCache.get(bucketName);
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
