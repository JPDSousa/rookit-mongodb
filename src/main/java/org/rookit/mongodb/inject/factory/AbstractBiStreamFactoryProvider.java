package org.rookit.mongodb.inject.factory;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.mongodb.gridfs.Buckets;

import com.google.inject.Provider;

abstract class AbstractBiStreamFactoryProvider implements Provider<RookitFactory<BiStream>> {

	protected final Buckets buckets;
	
	AbstractBiStreamFactoryProvider(Buckets buckets) {
		this.buckets = buckets;
	}

	protected abstract String getBucketName();
	
	@Override
	public RookitFactory<BiStream> get() {
		return buckets.getFactory(getBucketName());
	}
	
	
}
