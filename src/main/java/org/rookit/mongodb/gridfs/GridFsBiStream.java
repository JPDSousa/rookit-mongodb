package org.rookit.mongodb.gridfs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.rookit.dm.RookitModel;
import org.rookit.dm.utils.bistream.BiStream;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;

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
		return new InputStream() {
			
			private final InputStream innerStream = bucket.openDownloadStream(id);

			@Override
			public int read() throws IOException {
				return innerStream.read();
			}

			@Override
			public int hashCode() {
				return innerStream.hashCode();
			}

			@Override
			public int read(byte[] b) throws IOException {
				int n = 0;
				int pos = 0;
				while(n != -1) {
					n = read(b, pos, b.length);
					if(n != -1) {
						pos += n;
					}
				}
				return pos == 0 ? n : pos;
			}

			@Override
			public boolean equals(Object obj) {
				return innerStream.equals(obj);
			}

			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				return innerStream.read(b, off, len);
			}

			@Override
			public long skip(long n) throws IOException {
				return innerStream.skip(n);
			}

			@Override
			public String toString() {
				return innerStream.toString();
			}

			@Override
			public int available() throws IOException {
				return innerStream.available();
			}

			@Override
			public void close() throws IOException {
				innerStream.close();
			}

			@Override
			public void mark(int readlimit) {
				innerStream.mark(readlimit);
			}

			@Override
			public void reset() throws IOException {
				innerStream.reset();
			}

			@Override
			public boolean markSupported() {
				return innerStream.markSupported();
			}
			
			
		};
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
	
}