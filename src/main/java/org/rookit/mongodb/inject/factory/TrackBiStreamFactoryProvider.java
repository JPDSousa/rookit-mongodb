package org.rookit.mongodb.inject.factory;

import org.rookit.mongodb.gridfs.Buckets;

import com.google.inject.Inject;

class TrackBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

	@Inject
	private TrackBiStreamFactoryProvider(Buckets buckets) {
		super(buckets);
	}

	@Override
	protected String getBucketName() {
		return Buckets.AUDIO_BUCKET;
	}

	
}
