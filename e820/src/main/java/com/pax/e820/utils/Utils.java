package com.pax.e820.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utils {
	
	//list�߶�
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listView.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}

	//�ı�״̬����ɫ
	@SuppressLint("NewApi")
	public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                Window window = activity.getWindow();
//
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//                window.setStatusBarColor(activity.getResources().getColor(colorResId));
//
//                //�ײ�������
//
//                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
//            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
	
	 /**
     * ��ȡ��Ļ�ֱ���
     *
     * @param context
     * @return
     */
    public static final Integer[] getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return new Integer[]{screenWidth, screenHeight};
    }
    
	public static Bitmap getbBitmap(ListView listView) {
		int h = 0;
		Bitmap bitmap = null;
		// ��ȡlistViewʵ�ʸ߶�
		for (int i = 0; i < listView.getChildCount(); i++) {
			h += listView.getChildAt(i).getHeight();
		}
		Log.d("iii", "ʵ�ʸ߶�:" + h);
		Log.d("iii", "list �߶�:" + listView.getHeight());
		// ������Ӧ��С��bitmap
		bitmap = Bitmap.createBitmap(576, listView.getHeight(),
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
		// �������
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("/sdcard/screen_test.png");
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

	/**
     * ListView ����
     * @param listView
     * @param context
     * @return
     */
    public  static Bitmap createBitmap(ListView listView, Context context){
        int titleHeight,width, height, rootHeight=0;
        Bitmap bitmap;
        Canvas canvas;
        int yPos = 0;
        int listItemNum;
        List<View> childViews = null;

        width = getDisplayMetrics(context)[0];//��ȵ�����Ļ��

        ListAdapter listAdapter = listView.getAdapter();
        listItemNum = listAdapter.getCount();
        childViews = new ArrayList<View>(listItemNum);
        View itemView;
        //��������߶�:
        for(int pos=0; pos < listItemNum; ++pos){
            itemView = listAdapter.getView(pos, null, listView);
            //measure����
            itemView.measure(View.MeasureSpec.makeMeasureSpec(width, View. MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childViews.add(itemView);
            rootHeight += itemView.getMeasuredHeight();
        }

        height = rootHeight;
        // ������Ӧ��С��bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), height,
                Bitmap.Config.ARGB_8888);
        //bitmap = BitmapUtil.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        Bitmap itemBitmap;
        int childHeight;
        //��ÿ��ItemView����ͼƬ������������������
        for(int pos=0; pos < childViews.size(); ++pos){
            itemView = childViews.get(pos);
            childHeight = itemView.getMeasuredHeight();
            itemBitmap = viewToBitmap(itemView,width,childHeight);
            if(itemBitmap!=null){
                canvas.drawBitmap(itemBitmap, 0, yPos, null);
            }
            yPos = childHeight +yPos;
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }

    private static Bitmap viewToBitmap(View view, int viewWidth, int viewHeight){
        view.layout(0, 0, viewWidth, viewHeight);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * ѹ��ͼƬ
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
        while (baos.toByteArray().length / 1024 > 100) {
            // ����baos
            baos.reset();
            // ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // ÿ�ζ�����10
            options -= 10;
        }
        // ��ѹ���������baos��ŵ�ByteArrayInputStream��
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // ��ByteArrayInputStream��������ͼƬ
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
    
    //��ȡϵͳʱ��
    public static String getSystemTime(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//�������ڸ�ʽ
    	return df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
    }
}
