package org.rookit.mongodb.gridfs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.RookitModel;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.google.common.base.MoreObjects;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public class GridFsBiStream implements BiStream {
	
	public static final String BUCKET_NAME = "bucket";
	public static final String ID = RookitModel.ID;
	
	private transient GridFSBucket bucket;
	
	// for serialization purposes only
	private final String bucketName;
	
	@Id
	private ObjectId id;
	
	GridFsBiStream(GridFSBucket bucket, ObjectId id) {
		super();
		checkArgument(bucket != null, "The bucket cannot be null");
		this.bucket = bucket;
		this.bucketName = bucket.getBucketName();
		this.id = id;
	}
	
	void setBucket(Map<String, GridFSBucket> bucketCache) {
		this.bucket = bucketCache.get(bucketName);
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public void delete() {
		if(id != null) {
			bucket.delete(id);
		}
	}

	public String getBucketName() {
		return bucketName;
	}
	
	public GridFSFile getMetadata() {
		checkNotNull(id, "Cannot fetch metadata, as there is no id");
		return bucket.find(Filters.eq(ID, id)).first();
	}

	@Override
	public synchronized InputStream toInput() {
		checkNotNull(id, "Cannot open input stream, as there is no id");
		return bucket.openDownloadStream(id);
	}
	
	public synchronized void readTo(OutputStream stream) {
		checkNotNull(id, "Cannot open input stream, as there is no id");
		checkArgument(stream != null, "The stream cannot be null");
		bucket.downloadToStream(id, stream);
	}

	@Override
	public synchronized OutputStream toOutput() {
		if(id == null) {
			id = new ObjectId();
		}
		return bucket.openUploadStream(new BsonObjectId(id), id.toHexString());
	}

	@Override
	public boolean isEmpty() {
		return id == null;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("bucket", bucket)
				.add("bucketName", bucketName).add("id", id).toString();
	}
	
	
	
}