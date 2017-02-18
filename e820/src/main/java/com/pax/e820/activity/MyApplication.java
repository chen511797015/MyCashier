package com.pax.e820.activity;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.pax.api.PrintException;
import com.pax.api.PrintManager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application{
	
	private SharedPreferences mPref;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//super.onCreate();
		mPref = getSharedPreferences("fish", MODE_PRIVATE);
		initImageLoader(getApplicationContext());
		
//		try {
//			PrintManager printManager = PrintManager.getInstance(this);
//		} catch (PrintException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.		
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.denyCacheImageMultipleSizesInMemory()
//				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(2 * 1024 * 1024)  
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(20 * 1024 * 1024) // 20 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiskCache(cacheDir))
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	public SharedPreferences preferences() {
		return mPref;
	}

}
