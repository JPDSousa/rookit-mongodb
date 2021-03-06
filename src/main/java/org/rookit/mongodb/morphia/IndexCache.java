package org.rookit.mongodb.morphia;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.rookit.api.dm.RookitModel;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mongodb.client.model.Filters;

@SuppressWarnings("javadoc")
public class IndexCache implements Closeable {

	private final Multimap<Class<?>, Index> indices;
	
	public IndexCache() {
		this.indices = HashMultimap.create();
	}

	public Bson createUniqueQuery(final Class<?> clazz, final Document dbObject) {
		final Collection<Index> indices = putIfAbsent(clazz);
		final List<Bson> filters = Lists.newLinkedList();
		filters.add(Filters.eq(RookitModel.ID, dbObject.get(RookitModel.ID)));
		indices.stream()
		.filter(i -> i.options().unique())
		.map(i -> createFilterFromIndex(i, dbObject))
		.forEach(filters::add);

		return Filters.or(filters);
	}
	
	private Bson createFilterFromIndex(Index index, Document dbObject) {
		final List<Bson> filters = Lists.newLinkedList();

		for(Field key : index.fields()) {
			filters.add(Filters.eq(key.value(), dbObject.get(key.value())));
		}
		return Filters.and(filters);
	}

	private Collection<Index> putIfAbsent(final Class<?> clazz) {
		if(indices.containsKey(clazz)) {
			return indices.get(clazz);
		}
		final Indexes annotation = clazz.getAnnotation(Indexes.class);
		final List<Index> indexes = Lists.newLinkedList();
		if(annotation == null) {
			final Class<?> superclass = clazz.getSuperclass();
			if(superclass != null) {
				indexes.addAll(putIfAbsent(superclass));
			}
			for(Class<?> i : clazz.getInterfaces()) {
				indexes.addAll(putIfAbsent(i));
			}
		}
		else {
			indexes.addAll(Arrays.asList(annotation.value()));
		}
		indices.putAll(clazz, indexes);
		return indexes;
	}
	
	public void clear() {
		indices.clear();
	}

	@Override
	public void close() throws IOException {
		clear();
	}
	
}
