package org.rookit.mongodb.inject.factory;

import org.rookit.mongodb.gridfs.Buckets;

import com.google.inject.Inject;

class AlbumBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

	@Inject
	private AlbumBiStreamFactoryProvider(final Buckets buckets) {
		super(buckets);
	}

	@Override
	protected String getBucketName() {
		return Buckets.COVER_BUCKET;
	}
}
