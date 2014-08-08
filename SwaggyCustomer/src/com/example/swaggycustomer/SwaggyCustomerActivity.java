package com.example.swaggycustomer;

import util.ImageCacheManager;
import android.graphics.Bitmap.CompressFormat;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

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
	
	protected void loadImage(final String imageUrl, final ImageView iv) {
		ImageCacheManager.getInstance().getImage(imageUrl, new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(iv == null)
					return;
				
				iv.setImageResource(R.drawable.common_signin_btn_icon_normal_light);
			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if(iv == null)
					return;
				
				iv.setImageBitmap(response.getBitmap());
			}
		});
	}

	
}
