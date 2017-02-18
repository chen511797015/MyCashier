package com.pax.d820.activitybase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.pax.d820.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * @className: ActivityBase2
 * @description: TODO
 * @author shihui
 * @date 2013-5-1 下午7:07:18
 */
public class ActivityBase extends Activity {
	public static final int SHOW_TIME = 200;
	public static final int IMAGE_CAPTURE = 0;
	public static final String TYPE = "type";
	private static final String ImageExtension = ".jpg";
	private String photoPath;
	public static final String XMLURL = "http://172.16.5.105/mobilecenter/app/collect!collect_save.action";
	public static final String FILEURL = "http://172.16.5.106/mobilecenter/app/collect!test.action";
	public static List<Activity> activityList = new ArrayList<Activity>();

	/**
	 * open Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Context context, Class<?> pClass) {
		Intent _intent = new Intent();
		_intent.setClass(context, pClass);
		startActivity(_intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}

	/**
	 * open Activity by param
	 * 
	 * @param pClass
	 */
	protected void openActivityByParam(Context context, Class<?> pClass,
			String type) {
		Intent _intent = new Intent();
		_intent.setClass(context, pClass);
		_intent.putExtra("type", type);
		startActivity(_intent);

		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}

	/**
	 * get Object from Intent
	 * 
	 * @param type
	 * @return Object
	 */
	protected Object getFromIntent(String type) {
		Bundle bundle = getIntent().getExtras();
		return bundle.getSerializable(type);
	}

	/**
	 * show message by string
	 * 
	 * @param msg
	 */
	protected void showMsg(String msg) {
		Toast.makeText(this, msg, SHOW_TIME).show();
	}

	/**
	 * show message by resId
	 * 
	 * @param resId
	 */
	public void showMsg(int resId) {
		Toast.makeText(this, resId, SHOW_TIME).show();
	}

	/**
	 * get LayoutInflate instance
	 */
	public LayoutInflater getLayoutInflater() {
		return LayoutInflater.from(this);
	}

	/**
	 * convert double latitude to int
	 * 
	 * @param latitude
	 * @return
	 */
	public int getE6Latitude(double latitude) {
		return (int) (latitude * 1e6);
	}

	/**
	 * convert e6 latitude to double
	 * 
	 * @param latitude
	 * @return
	 */
	public double getDoubleLatitude(int latitude) {
		return (double) (latitude * 1e-6);
	}

	/**
	 * convert latitude to int
	 * 
	 * @param latitude
	 * @return
	 */
	public int getE6Longitude(double longitude) {
		return (int) (longitude * 1e6);
	}

	/**
	 * convert e6 longitude to double
	 * 
	 * @param longitude
	 * @return
	 */
	public double getDoubleLongitude(int longitude) {
		return (double) (longitude * 1e-6);
	}

	/**
	 * 设置图片文件
	 * 
	 * @param requestCode
	 * @param fileName
	 */
	protected void startCameraForResult(int requestCode, String fileName) {
		String filePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
		// isDirExist(filePath + fileName);
		// Utils.isDirExist(filePath + fileName);
		String dateFormat = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINESE).format(Calendar.getInstance().getTime());
		photoPath = filePath + fileName + File.separator + dateFormat
				+ ImageExtension;
		getCameraIntent(requestCode);

	}

	/**
	 * 启动相机
	 * 
	 * @param requestCode
	 */
	private void getCameraIntent(int requestCode) {

		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photoFile = new File(photoPath);
		Uri imageUri = Uri.fromFile(photoFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, requestCode);
	}

	/**
	 * 获取图片路径
	 * 
	 * @return photoPath
	 */
	public String getImagePath() {
		return this.photoPath;
	}
}
