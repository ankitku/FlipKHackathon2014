package com.example.swaggycustomer;

import android.graphics.Bitmap.CompressFormat;
import android.support.v4.app.FragmentActivity;
import util.ImageCacheManager;

public class SwaggyCustomerActivity extends FragmentActivity{

	protected static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	protected static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	protected static int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so
														// quality is ignored
														// but must be provided
	
	protected void createImageCache() {
		ImageCacheManager.getInstance().init(this, this.getPackageCodePath(),
				DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
				DISK_IMAGECACHE_QUALITY);
	}
	
}
