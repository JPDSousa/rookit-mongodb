package org.rookit.mongodb.gridfs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Logger;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.RookitModel;
import org.rookit.mongodb.utils.DatabaseValidator;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public class GridFsBiStream implements BiStream {
	
	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();
	private static final Logger logger = VALIDATOR.getLogger(GridFsBiStream.class);
	
	public static final String BUCKET_NAME = "bucket";
	public static final String ID = RookitModel.ID;
	
	private transient GridFSBucket bucket;
	
	// for serialization purposes only
	private final String bucketName;
	
	@Id
	private ObjectId id;
	
	GridFsBiStream(final GridFSBucket bucket) {
		super();
		VALIDATOR.checkArgumentNotNull(bucket, "The bucket cannot be null");
		this.bucket = bucket;
		this.bucketName = bucket.getBucketName();
	}
	
	GridFsBiStream(final GridFSBucket bucket, final ObjectId id) {
		super();
		VALIDATOR.checkArgumentNotNull(bucket, "The bucket cannot be null");
		VALIDATOR.checkArgumentNotNull(id, "The id cannot be null");
		this.bucket = bucket;
		this.bucketName = bucket.getBucketName();
		this.id = id;
	}
	
	void setBucket(final Map<String, GridFSBucket> bucketCache) {
		this.bucket = bucketCache.get(bucketName);
	}
	
	public Optional<ObjectId> getId() {
		return Optional.fromNullable(this.id);
	}

	public void setId(final ObjectId id) {
		VALIDATOR.checkArgumentNotNull(id, "The id cannot be null");
		this.id = id;
	}
	
	public void delete() {
		if(Objects.nonNull(id)) {
			bucket.delete(id);
		}
	}

	public String getBucketName() {
		return bucketName;
	}
	
	public GridFSFile getMetadata() {
		return getId()
				.toJavaUtil()
				.map(id -> Filters.eq(ID, id))
				.map(bucket::find)
				.map(GridFSFindIterable::first)
				.map(Optional::fromNullable)
				.flatMap(opt -> opt.toJavaUtil())
				.orElseThrow(() -> new IllegalStateException("Cannot find metadata."));
	}

	@Override
	public synchronized InputStream toInput() {
		return getId()
				.transform(bucket::openDownloadStream)
				.toJavaUtil()
				.orElseThrow(() -> new IllegalStateException("Cannot open input stream, as there is no id"));
	}
	
	public synchronized void readTo(final OutputStream stream) {
		VALIDATOR.checkArgumentNotNull(stream, "The stream cannot be null");
		getId()
		.transform(id -> {
			logger.info("Writting to bucket {} with id {}", bucketName, id);
			bucket.downloadToStream(id, stream);
			return id;
		}).toJavaUtil()
		.orElseThrow(() -> new IllegalStateException("Cannot open input stream, as there is no id"));
	}

	@Override
	public synchronized OutputStream toOutput() {
		final ObjectId id = getId().or(new ObjectId());
		setId(id);
		
		logger.info("Creating output stream over bucket {} and id {}", bucketName, id);
		return bucket.openUploadStream(new BsonObjectId(id), id.toHexString());
	}

	@Override
	public boolean isEmpty() {
		return Objects.isNull(this.id);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("bucket", bucket)
				.add("bucketName", bucketName).add("id", id).toString();
	}
	
}