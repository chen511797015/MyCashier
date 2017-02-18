package com.pax.d820.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pax.api.PrintException;
import com.pax.api.PrintManager;
import com.pax.base.api.BaseController;
import com.pax.base.listener.BasePrintListener;
import com.pax.d820.R;
import com.pax.d820.activitybase.ActivityFrame;
import com.pax.d820.print.PrintUtil;
import com.pax.imglib.ImgLib;

public class ActivityPrint extends ActivityFrame {

	private String num;
	private TextView tv_amount, tv_time, tv_card_no;
	private Bitmap bitmap;
	private ImageView iv_signature;
	private Button btn_print, btn_cancel;
	private ScrollView sl;
	private String cardNo;
	private ProgressDialog progressDialog;
	private ImageView iv;
	private SharedPreferences sharedPreferences;
	private int posType;

	// cdd修改后
	PrintManager printManager;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// cdd修改后
		try {
			printManager = PrintManager.getInstance(ActivityPrint.this);
			printManager.prnInit();
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initView();
		bindData();
		bindListener();
	}

	private void initView() {
		setContentView(R.layout.activity_print_img);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_signature = (ImageView) findViewById(R.id.iv_signature);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_print = (Button) findViewById(R.id.btn_print);
		sl = (ScrollView) findViewById(R.id.sl);
		tv_card_no = (TextView) findViewById(R.id.tv_card_no);
		iv = (ImageView) findViewById(R.id.iv);
	}

	private void bindData() {
		num = getIntent().getStringExtra("num");
		tv_amount.setText("RMB  " + num);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date()); // 获得系统时间
		tv_time.setText(getResources().getString(R.string.date_time) + date);
		bitmap = getIntent().getParcelableExtra("bitmap");
		iv_signature.setImageBitmap(bitmap);
		cardNo = getIntent().getStringExtra("cardNo");
		tv_card_no.setText(cardNo);
		sharedPreferences = getSharedPreferences("fish", Context.MODE_APPEND);
		posType = sharedPreferences.getInt("posType",
				ActivityDevManage.MPOSBASE);
		try {
			InputStream inputStream = getAssets().open("receipt_ums.png");
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			iv.setImageBitmap(bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void bindListener() {
		// TODO Auto-generated method stub
		btn_print.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	private void printBitmap(final Bitmap bitmap) throws PrintException {
		// ImgLib imgLib = ImgLib.getInstance(ActivityPrint.this);
		// byte[] b = imgLib.bitmapToMonoBmp(bitmap);
		// Bitmap bitmapPrint = BitmapFactory.decodeByteArray(b, 0, b.length);
		// PrintManager printManager = PrintManager
		// .getInstance(ActivityPrint.this);
		printManager.prnInit();// 暂时注释掉
		// printManager.prnSetGray(1);
		printManager.prnBitmap(bitmap);
		printManager.prnStep((short) 100);
		printManager.prnStartCut(1);
		printManager.prnStart();
		printManager.prnClose();
	}

	// public static Bitmap loadBitmapFromView(View v) {
	// if (v == null) {
	// return null;
	// }
	// Bitmap screenshot;
	// v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
	// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	// screenshot = Bitmap.createBitmap(v.getMeasuredWidth(),
	// v.getMeasuredHeight(), Config.RGB_565);
	// Canvas c = new Canvas(screenshot);
	// c.translate(-v.getScrollX(), -v.getScrollY());
	// v.draw(c);
	// return screenshot;
	// }
	// public static Bitmap loadBitmapFromView(View v) {
	// if (v == null) {
	// return null;
	// }
	// Bitmap screenshot;
	// v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
	// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	// screenshot = Bitmap.createBitmap(v.getMeasuredWidth(),
	// v.getMeasuredHeight(), Config.RGB_565);
	// Canvas c = new Canvas(screenshot);
	// v.draw(c);
	// return screenshot;
	// }

	// public static Bitmap getBitmapByView(ScrollView scrollView) {
	// int h = 0;
	// Bitmap bitmap = null;
	// // 获取scrollview实际高度
	// for (int i = 0; i 100) {
	// // 重置baos
	// baos.reset();
	// // 这里压缩options%，把压缩后的数据存放到baos中
	// image.compress(Bitmap.CompressFormat.JPEG, options, baos);
	// // 每次都减少10
	// options -= 10;
	// }
	// // 把压缩后的数据baos存放到ByteArrayInputStream中
	// ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
	// // 把ByteArrayInputStream数据生成图片
	// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
	// return bitmap;
	// }
	/**
	 * 截取scrollview的屏幕
	 * 
	 * @param scrollView
	 * @return
	 */
	// public static Bitmap getBitmapByView(ScrollView scrollView) {
	// int h = 0;
	// Bitmap bitmap = null;
	// // 获取scrollview实际高度
	// for (int i = 0; i < scrollView.getChildCount(); i++) {
	// h += scrollView.getChildAt(i).getHeight();
	// scrollView.getChildAt(i).setBackgroundColor(
	// Color.parseColor("#ffffff"));
	// }
	// // 创建对应大小的bitmap
	// bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
	// Bitmap.Config.RGB_565);
	// final Canvas canvas = new Canvas(bitmap);
	// scrollView.draw(canvas);
	// return bitmap;
	// }
	//

	private String saveMyBitmap(Bitmap mBitmap) {
		SimpleDateFormat format = new SimpleDateFormat("hhmmss");
		String date = format.format(new java.util.Date());
		String filePath = Environment.getExternalStorageDirectory() + "/test"
				+ date + ".png";
		File f = new File(filePath);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return filePath;
	}

	/**
	 * 截屏方法
	 * 
	 * @return
	 */
	public static Bitmap getBitmapByView(ScrollView scrollView) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			// scrollView.getChildAt(i).setBackgroundResource(R.drawable.bg3);
		}
		Log.d("iii", "实际高度:" + h);
		Log.d("iii", " 高度:" + scrollView.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		// 测试输出
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(
					Environment.getExternalStorageDirectory()
							+ "/screen_test.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (null != out) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		return bitmap;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		String info = null;
		switch (v.getId()) {
		case R.id.btn_cancel:
			info = getResources().getString(R.string.deal_succeed);
			intent = new Intent();
			intent.putExtra("info", info);
			intent.putExtra("code", 9000);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.btn_print:
			WindowManager windowManager = getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			Log.i("iii", "屏幕宽度---" + screenWidth);
			Log.i("iii", "屏幕高度---" + screenHeight);
			// 打开图像缓存
			// sl.setDrawingCacheEnabled(true);
			// //必须调用measure和layout方法才能成功保存可视组件的截图到png图像文件
			// //测量View大小
			// sl.measure(MeasureSpec.makeMeasureSpec(0,
			// MeasureSpec.UNSPECIFIED),
			// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// //发送位置和尺寸到View及其所有的子View
			// sl.layout(0, 0, sl.getMeasuredWidth(), sl.getMeasuredHeight());

			// 获得可视组件的截图
			// Bitmap bmp = getBitmapByView(sl);
			// saveMyBitmap(bmp);
			// Bitmap bmp = loadBitmapFromView(sl);
			final Bitmap bmp = PrintUtil.getViewBitmap(ActivityPrint.this, sl);
			// saveMyBitmap(bmp);
			Log.i("iii",
					"with---" + bmp.getWidth() + "heigth---" + bmp.getHeight());
			Log.i("iii",
					"with---" + bitmap.getWidth() + "heigth---"
							+ bitmap.getHeight());
			progressDialog = ProgressDialog.show(ActivityPrint.this, "",
					getResources().getString(R.string.printing));
			progressDialog.setCancelable(false);
			if (posType == ActivityDevManage.MPOSE820) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Bitmap bitmap = scaleWithWH(bmp, 576);
							printBitmap(bitmap);
							handler.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									progressDialog.dismiss();
									Log.i("iii", "打印成功!");
									String info = getResources().getString(
											R.string.deal_succeed);
									Intent intent = new Intent();
									intent.putExtra("info", info);
									intent.putExtra("code", 9000);
									setResult(RESULT_OK, intent);
									finish();
								}
							});
						} catch (PrintException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							final PrintException ee = e;
							handler.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									Log.i("iii", "打印错误---" + ee.getMessage());
									progressDialog.dismiss();
									String info = getResources().getString(
											R.string.deal_succeed);
									Intent intent = new Intent();
									intent.putExtra("info", info);
									intent.putExtra("code", 9000);
									setResult(RESULT_OK, intent);
									finish();
								}
							});
						}
					}
				}).start();

			} else {
				Bitmap bitmap = scaleWithWH(bmp, 384);
				BaseController.getInstance(ActivityPrint.this).printImage(
						bitmap, new BasePrintListener() {

							@Override
							public void onSucc() {
								progressDialog.dismiss();
								Log.i("iii", "打印成功!");
								String info = getResources().getString(
										R.string.deal_succeed);
								Intent intent = new Intent();
								intent.putExtra("info", info);
								intent.putExtra("code", 9000);
								setResult(RESULT_OK, intent);
								finish();
							}

							@Override
							public void onError(int errCode, String errDesc) {
								// TODO Auto-generated method stub
								Log.i("iii", "打印错误!");
								progressDialog.dismiss();
								String info = getResources().getString(
										R.string.deal_succeed);
								Intent intent = new Intent();
								intent.putExtra("info", info);
								intent.putExtra("code", 9000);
								setResult(RESULT_OK, intent);
								finish();
							}
						});
			}

			break;

		default:
			break;
		}
		super.onClick(v);
	}

	private Bitmap scaleWithWH(Bitmap src, double w) {
		double h = w / src.getWidth() * src.getHeight();
		if (w == 0 || h == 0 || src == null) {
			return src;
		} else {
			// 记录src的宽高
			int width = src.getWidth();
			int height = src.getHeight();
			// 创建一个matrix容器
			Matrix matrix = new Matrix();
			// 计算缩放比例
			float scaleWidth = (float) (w / width);
			float scaleHeight = (float) (h / height);
			// 开始缩放
			matrix.postScale(scaleWidth, scaleHeight);
			// 创建缩放后的图片
			return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

		}
		return true;
	}

}
