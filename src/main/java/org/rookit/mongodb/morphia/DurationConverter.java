package org.rookit.mongodb.morphia;

import java.time.Duration;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import com.mongodb.DBObject;

@SuppressWarnings("javadoc")
public class DurationConverter extends TypeConverter implements SimpleValueConverter {

	private static final String NANOS = "nanos";
	private static final String SECONDS = "seconds";
	
	private static final DurationConverter SINGLETON = new DurationConverter();
	
	public static final DurationConverter getDefault() {
		return SINGLETON;
	}
	
	private DurationConverter() {
		super(Duration.class);
	}
	
	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		if(value instanceof Duration) {
			final Duration duration = (Duration) value;
			return new BsonDocument(SECONDS, new BsonInt64(duration.getSeconds()))
					.append(NANOS, new BsonInt32(duration.getNano()));
		}
		return super.encode(value, optionalExtraInfo);
	}

	@Override
	public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
		if(fromDBObject == null || fromDBObject instanceof Duration) {
			return fromDBObject;
		}
		if(fromDBObject instanceof DBObject) {
			final DBObject dbObject = (DBObject) fromDBObject;
			final Object secondsRaw = dbObject.get(SECONDS);
			final long seconds;
			if (secondsRaw instanceof Integer) {
				seconds = ((Integer) secondsRaw).longValue();
			}
			else {
				seconds = (Long) secondsRaw;
			}
			return Duration.ofSeconds(seconds, (int) dbObject.get(NANOS));
		}
		throw new IllegalArgumentException(new StringBuilder("Can't convert to ")
				.append(Duration.class.getName())
				.append(" from ")
				.append(fromDBObject)
				.append(" (")
				.append(fromDBObject.getClass())
				.append("")
				.toString());
	}
}
