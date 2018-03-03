package org.rookit.mongodb.gridfs;

import java.util.Map;

import org.bson.types.ObjectId;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.mongodb.utils.DatabaseValidator;

import com.mongodb.client.gridfs.GridFSBucket;

@SuppressWarnings("javadoc")
public class GridFsBiStreamFactory implements RookitFactory<BiStream> {
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();
	
	public static final GridFsBiStreamFactory create(final GridFSBucket bucket) {
		return new GridFsBiStreamFactory(bucket);
	}

	private final GridFSBucket bucket;
	
	private GridFsBiStreamFactory(final GridFSBucket bucket) {
		VALIDATOR.checkArgumentNotNull(bucket, "The bucket cannot be null");
		this.bucket = bucket;
	}

	@Override
	public BiStream createEmpty() {
		return new GridFsBiStream(bucket);
	}

	@Override
	public BiStream create(final Map<String, Object> data) {
		final Object id = data.get(GridFsBiStream.ID);
		if(id != null && id instanceof ObjectId) {
			return new GridFsBiStream(bucket, (ObjectId) id);
		}
		return createEmpty();
	}
}
