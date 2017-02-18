package com.pax.e820.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pax.api.PrintException;
import com.pax.api.PrintManager;
import com.pax.e820.R;
import com.pax.e820.adapter.AdapterOrder;
import com.pax.e820.adapter.AdapterOrder.CallBack;
import com.pax.e820.adapter.AdapterOrderType;
import com.pax.e820.adapter.AdapterOrdered;
import com.pax.e820.model.ModelOrder;
import com.pax.e820.model.ModelOrdered;
import com.pax.e820.utils.OrderMenu;
import com.pax.e820.utils.OrderMenu.MenuCategory;
import com.pax.e820.utils.PrintUtil;
import com.pax.e820.utils.Utils;

public class MainActivity extends Activity implements CallBack{

	private ListView lv_ordered,lv_type,lv_print;
	private GridView gv_menu;
	private TextView tv_total_prices;
	private ImageView iv_print;
	private ScrollView sv_print;
	private List<ModelOrder> orderTypes;
	private static HashMap<MenuCategory, List<ModelOrder>> orders;
	private static List<ModelOrder> order;
	private AdapterOrderType adapterOrderType;
	private AdapterOrder adapterOrder;
	private MenuCategory category = MenuCategory.MENU_CAT_APPETIZERS;
	private List<ModelOrdered> ordereds;
	private AdapterOrdered adapterOrdered;
	private double total_prices = 0;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//修改状态栏颜色
		Utils.setWindowStatusBarColor(MainActivity.this,R.color.blue);
		//去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViews();
		bindData();
		bindListener();
	}

	private void bindListener() {
		// TODO Auto-generated method stub
		lv_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				adapterOrderType.setSelectedPosition(arg2);
				adapterOrderType.notifyDataSetChanged();
				category = orderTypes.get(arg2).getCate();
				order.clear();
				order.addAll(orders.get(category));
				adapterOrder.notifyDataSetChanged();
			}
		});
		adapterOrder.setCallBack(this);
		iv_print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("iii", "print-------------------------------------------------------------------");
				// TODO Auto-generated method stub
				lv_print.setAdapter(adapterOrdered);
				Utils.setListViewHeightBasedOnChildren(lv_print);
				TextView tv_print_time = (TextView) findViewById(R.id.tv_print_time);
				tv_print_time.setText(Utils.getSystemTime());
				TextView tv_print_total = (TextView) findViewById(R.id.tv_print_total);
				int total = 0;
				for(ModelOrdered ordered : ordereds){
					total+=ordered.getNumber();
				}
				tv_print_total.setText(total+"");
				Bitmap bitmapPrint = PrintUtil.getViewBitmap(MainActivity.this, sv_print,384);
				ImageView iv_print_bitmap = (ImageView) findViewById(R.id.iv_print_bitmap);
				iv_print_bitmap.setImageBitmap(bitmapPrint);
				PrintManager printManager;
				try {
//					Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("printer_test.bmp"));
//					printManager = PrintManager.getInstance();
//					printManager.prnInit();
//					printManager.prnSetGray(1);
//					printManager.prnBitmap(bitmap);
//		            printManager.prnStart();
//		            Log.i("iii", "打印正常");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("iii", "打印异常");
				}
			}
		});
	}

	private void bindData() {
		// TODO Auto-generated method stub
		orderTypes = OrderMenu.getOrderType();
		orders = OrderMenu.getOrders();
		ordereds = new ArrayList<ModelOrdered>();
		
		tv_total_prices.setText(getResources().getString(R.string.total_prices)+":¥"+total_prices);
		adapterOrdered = new AdapterOrdered(MainActivity.this, ordereds);
		adapterOrderType = new AdapterOrderType(MainActivity.this, orderTypes);
		order = new ArrayList<ModelOrder>();
		order.addAll(orders.get(category));
		adapterOrder = new AdapterOrder(MainActivity.this, order);
		lv_type.setAdapter(adapterOrderType);
		lv_type.setDividerHeight(0);
		gv_menu.setAdapter(adapterOrder);
		lv_ordered.setAdapter(adapterOrdered);
		ordereds.add(new ModelOrdered(order.get(0).getName(), 1, order.get(0).getPrice()));
		ordereds.add(new ModelOrdered(order.get(1).getName(), 1, order.get(1).getPrice()));
		ordereds.add(new ModelOrdered(order.get(2).getName(), 1, order.get(2).getPrice()));
		ordereds.add(new ModelOrdered(order.get(3).getName(), 1, order.get(3).getPrice()));
		adapterOrdered.notifyDataSetChanged();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		lv_ordered = (ListView) findViewById(R.id.lv_ordered);
		lv_print = (ListView) findViewById(R.id.lv_print);
		gv_menu = (GridView) findViewById(R.id.gv_menu);
		sv_print = (ScrollView) findViewById(R.id.sv_print);
		iv_print = (ImageView) findViewById(R.id.iv_print);
		lv_type = (ListView) findViewById(R.id.lv_type);
		tv_total_prices = (TextView) findViewById(R.id.tv_total_prices);
	}

	@Override
	public void plus(int pos) {
		// TODO Auto-generated method stub
		boolean flag = false;
		ModelOrder modelOrder = order.get(pos);
		total_prices += modelOrder.getPrice();
		tv_total_prices.setText(getResources().getString(R.string.total_prices)+":¥"+total_prices);
		for(int i = 0 ; i < ordereds.size() ; i++){
			if(ordereds.get(i).getName().equals(modelOrder.getName())){
				ordereds.get(i).setNumber(ordereds.get(i).getNumber()+1);
				flag = true;
				break;
			}
		}
		if(!flag){
			ordereds.add(new ModelOrdered(modelOrder.getName(), 1,modelOrder.getPrice()));
		}
		adapterOrdered.notifyDataSetChanged();
	}

	@Override
	public void minus(int pos) {
		// TODO Auto-generated method stub
		ModelOrder modelOrder = order.get(pos);
		total_prices -= modelOrder.getPrice();
		tv_total_prices.setText(getResources().getString(R.string.total_prices)+":¥"+total_prices);
		for(int i = 0 ; i < ordereds.size() ; i++){
			if(ordereds.get(i).getName().equals(modelOrder.getName())){
				if(ordereds.get(i).getNumber()==1){
					ordereds.remove(i);
				}else{
					ordereds.get(i).setNumber(ordereds.get(i).getNumber()-1);
				}
				break;
			}
		}
		adapterOrdered.notifyDataSetChanged();
	}
}
