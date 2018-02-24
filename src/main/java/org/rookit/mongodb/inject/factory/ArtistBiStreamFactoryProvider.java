package org.rookit.mongodb.inject.factory;

import org.rookit.mongodb.gridfs.Buckets;

import com.google.inject.Inject;

class ArtistBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

	@Inject
	private ArtistBiStreamFactoryProvider(final Buckets buckets) {
		super(buckets);
	}

	@Override
	protected String getBucketName() {
		return Buckets.PICTURE_BUCKET;
	}
}
