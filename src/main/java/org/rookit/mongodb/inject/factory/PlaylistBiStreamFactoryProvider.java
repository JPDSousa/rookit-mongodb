package org.rookit.mongodb.inject.factory;

import org.rookit.mongodb.gridfs.Buckets;

import com.google.inject.Inject;

class PlaylistBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

	@Inject
	private PlaylistBiStreamFactoryProvider(Buckets buckets) {
		super(buckets);
	}

	@Override
	protected String getBucketName() {
		return Buckets.PICTURE_BUCKET;
	}
}
