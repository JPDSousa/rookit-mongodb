package org.rookit.mongodb.gridfs;

import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.rookit.api.bistream.BiStream;
import org.rookit.mongodb.utils.DatabaseValidator;

import com.mongodb.DBObject;

@SuppressWarnings("javadoc")
public class GridFsBiStreamConverter extends TypeConverter implements SimpleValueConverter {

	private static final DatabaseValidator VALIDATOR = DatabaseValidator.getDefault();
	
	public static final GridFsBiStreamConverter create(Buckets bucketCache) {
		return new GridFsBiStreamConverter(bucketCache);
	}

	private Buckets bucketCache;
	
	private GridFsBiStreamConverter(Buckets bucketCache) {
		super(BiStream.class, GridFsBiStream.class);
		this.bucketCache = bucketCache;
	}

	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		if(value instanceof GridFsBiStream) {
			final GridFsBiStream biStream = (GridFsBiStream) value;
			final ObjectId id = biStream.getId();
			if(id != null) {
				return new BsonDocument(GridFsBiStream.ID, new BsonObjectId(biStream.getId()))
						.append(GridFsBiStream.BUCKET_NAME, new BsonString(biStream.getBucketName()));
			}
			return null;
		}
		throw new IllegalArgumentException("Can't encode " + value);
	}

	@Override
	public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
		if(fromDBObject == null) {
			return null;
		}
		if(fromDBObject instanceof GridFsBiStream) {
			return fromDBObject;
		}
		if(fromDBObject instanceof DBObject) {
			final DBObject dbObject = (DBObject) fromDBObject;
			final ObjectId id = (ObjectId) dbObject.get(GridFsBiStream.ID);
			final String bucketName = (String) dbObject.get(GridFsBiStream.BUCKET_NAME);
			return new GridFsBiStream(bucketCache.getBucket(bucketName), id);
		}

		VALIDATOR.runtimeException("Can't convert to " + BiStream.class.getName() 
				+ " from " + fromDBObject);
		return null;
	}

}
