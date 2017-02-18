package com.pax.e820.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaRouter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pax.api.PrintException;
import com.pax.api.PrintManager;
import com.pax.e820.R;
import com.pax.e820.adapter.AdapterOrder;
import com.pax.e820.adapter.AdapterOrder.CallBack;
import com.pax.e820.adapter.AdapterOrderType;
import com.pax.e820.adapter.AdapterOrdered;
import com.pax.e820.adapter.AdapterOrderedPrint;
import com.pax.e820.model.ModelOrder;
import com.pax.e820.model.ModelOrdered;
import com.pax.e820.utils.OrderMenu;
import com.pax.e820.utils.OrderMenu.MenuCategory;
import com.pax.e820.utils.PrintUtil;
import com.pax.e820.utils.Utils;
import com.pax.e820.view.LoopViewPager;
import com.youth.banner.Banner;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ActivityOrder extends Activity implements CallBack {

	private GridView gv_order;
	private ListView lv_type, lv_order, lv_print;
	private TextView tv_amount;
	private RelativeLayout rl_print, rl_amount;
	private ScrollView sv_print;
	private List<ModelOrder> orderTypes;
	private static HashMap<MenuCategory, List<ModelOrder>> orders;
	private static List<ModelOrder> order;
	private AdapterOrderType adapterOrderType;
	private AdapterOrder adapterOrder;
	private MenuCategory category = MenuCategory.MENU_CAT_APPETIZERS;
	private static List<ModelOrdered> ordereds;
	private AdapterOrdered adapterOrdered;
	private double total_prices = 0;
	private DifferentDislay mPresentation;
	private PrintManager printManager;
	private ProgressDialog progressDialog;
	private AdapterOrderedPrint adapterOrderedPrint;
	private MediaRouter mMediaRouter;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 修改状态栏颜色
		Utils.setWindowStatusBarColor(ActivityOrder.this, R.color.blue);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mMediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);
		setContentView(R.layout.activity_order);
		initView();
		bindData();
		updatePresentation();
		bindListener();
		getFragmentManager();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("iii", "onResume");
		if (mPresentation != null) {
			if (!mPresentation.isShowing()) {
				mPresentation.show();
			}
		} else {
			updatePresentation();
		}
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

		gv_order.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ActivityOrder.this);
				View view = LayoutInflater.from(ActivityOrder.this).inflate(
						R.layout.dialog, null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv);
				TextView tv = (TextView) view.findViewById(R.id.tv);
				ImageLoader.getInstance().displayImage(
						"drawable://" + order.get(arg2).getImage(), iv);
				tv.setText(order.get(arg2).getName());
				builder.setView(view);
				builder.create().show();
			}
		});

		rl_print.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("iii",
						"print-------------------------------------------------------------------");
				// TODO Auto-generated method stub
				if (ordereds.size() > 0) {
					if (adapterOrderedPrint == null) {
						adapterOrderedPrint = new AdapterOrderedPrint(
								ActivityOrder.this, ordereds);
					}
					lv_print.setAdapter(adapterOrderedPrint);
					lv_print.setDividerHeight(0);
					Utils.setListViewHeightBasedOnChildren(lv_print);
					TextView tv_print_time = (TextView) findViewById(R.id.tv_print_time);
					tv_print_time.setText(Utils.getSystemTime());
					TextView tv_print_total = (TextView) findViewById(R.id.tv_print_total);
					int total = 0;
					for (ModelOrdered ordered : ordereds) {
						total += ordered.getNumber();
					}
					tv_print_total.setText(total + "");
					Log.i("iii", "createBitmap");
					final Bitmap bitmap = PrintUtil.getViewBitmap(
							ActivityOrder.this, sv_print, 576);

					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								// Bitmap bitmap =
								// BitmapFactory.decodeStream(getAssets().open("printer_test.bmp"));
								printBitmap(bitmap);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								if (progressDialog != null) {
									handler.post(new Runnable() {
										public void run() {
											progressDialog.dismiss();
										}
									});
								}
								Log.i("iii", "打印异常---" + e.getMessage());
							}
						}
					}).start();
				} else {
					Toast.makeText(
							ActivityOrder.this,
							getResources().getString(
									R.string.the_menu_can_not_be_empty), 200)
							.show();
				}
			}
		});
		rl_amount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityOrder.this,
						ActivityPayType.class);
				intent.putExtra("price", total_prices);
				startActivityForResult(intent, 1);
			}
		});
	}

	@Override
	protected void onStop() {
		// Be sure to call the super class.
		super.onStop();

		// Dismiss the presentation when the activity is not visible.
		if (mPresentation != null) {
			Log.i("iii",
					"Dismissing presentation because the activity is no longer visible.");
			mPresentation.dismiss();
			mPresentation = null;
			adapterOrderedPrint = null;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1 && data != null) {
			String info = data.getStringExtra("info");
			String number = data.getStringExtra("number");
			int code = data.getIntExtra("code", 0);
			Toast.makeText(ActivityOrder.this,
					"code---" + code + "info---" + info, Toast.LENGTH_LONG)
					.show();
			if (number != null) {
				Toast.makeText(ActivityOrder.this, "pan---" + number,
						Toast.LENGTH_LONG).show();
			}
			if (code == 9000) {
				tv_amount.setText("¥ 0");
				for (ModelOrder o : order) {
					o.setNumber(0);
				}
				adapterOrder.notifyDataSetChanged();
				ordereds.clear();
				adapterOrdered.notifyDataSetChanged();
			}
		}
	}

	private void bindData() {
		// TODO Auto-generated method stub
		try {
			printManager = PrintManager.getInstance(ActivityOrder.this);
			//添加一个灰度设置
			printManager.prnSetGray(0);
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderTypes = OrderMenu.getOrderType();
		orders = OrderMenu.getOrders();
		ordereds = new ArrayList<ModelOrdered>();
		adapterOrdered = new AdapterOrdered(ActivityOrder.this, ordereds);
		adapterOrderType = new AdapterOrderType(ActivityOrder.this, orderTypes);
		order = new ArrayList<ModelOrder>();
		order.addAll(orders.get(category));
		adapterOrder = new AdapterOrder(ActivityOrder.this, order);
		lv_type.setAdapter(adapterOrderType);
		lv_type.setDividerHeight(0);
		gv_order.setAdapter(adapterOrder);
		lv_order.setAdapter(adapterOrdered);
		lv_order.setDividerHeight(0);
	}

	private void initView() {
		// TODO Auto-generated method stub
		gv_order = (GridView) findViewById(R.id.gv_order);
		lv_type = (ListView) findViewById(R.id.lv_type);
		lv_order = (ListView) findViewById(R.id.lv_order);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		rl_print = (RelativeLayout) findViewById(R.id.rl_print);
		lv_print = (ListView) findViewById(R.id.lv_print);
		sv_print = (ScrollView) findViewById(R.id.sv_print);
		rl_amount = (RelativeLayout) findViewById(R.id.rl_amount);
	}

	@Override
	public void plus(int pos) {
		// TODO Auto-generated method stub
		boolean flag = false;
		ModelOrder modelOrder = order.get(pos);
		total_prices += modelOrder.getPrice();
		DecimalFormat df = new java.text.DecimalFormat("#.##");
		tv_amount.setText("¥ " + df.format(total_prices));
		for (int i = 0; i < ordereds.size(); i++) {
			if (ordereds.get(i).getName().equals(modelOrder.getName())) {
				ordereds.get(i).setNumber(ordereds.get(i).getNumber() + 1);
				flag = true;
				break;
			}
		}
		if (!flag) {
			ordereds.add(new ModelOrdered(modelOrder.getName(), 1, modelOrder
					.getPrice()));
		}
		adapterOrdered.notifyDataSetChanged();
		updateContents(true);
	}

	@Override
	public void minus(int pos) {
		// TODO Auto-generated method stub
		ModelOrder modelOrder = order.get(pos);
		total_prices -= modelOrder.getPrice();
		DecimalFormat df = new java.text.DecimalFormat("#.##");
		tv_amount.setText("¥ " + df.format(total_prices));
		for (int i = 0; i < ordereds.size(); i++) {
			if (ordereds.get(i).getName().equals(modelOrder.getName())) {
				if (ordereds.get(i).getNumber() == 1) {
					ordereds.remove(i);
				} else {
					ordereds.get(i).setNumber(ordereds.get(i).getNumber() - 1);
				}
				break;
			}
		}
		adapterOrdered.notifyDataSetChanged();
		if (ordereds.size() > 0) {
			updateContents(true);
		} else {
			updateContents(false);
		}
	}

	private void printBitmap(final Bitmap bitmap) throws PrintException {
		Log.i("iii", "height---" + bitmap.getHeight());
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// progressDialog = ProgressDialog.show(ActivityOrder.this, "",
				// "Printing...");
				// progressDialog.setCancelable(false);
			}
		});
		printManager.prnInit();
		// printManager.prnSetGray(1);
		printManager.prnBitmap(bitmap);
		printManager.prnStep((short) 100);
		printManager.prnStartCut(1);
		printManager.prnStart();
		printManager.prnClose();
		Log.i("iii", "close");

		handler.post(new Runnable() {
			public void run() {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				updateContents(false);
				ordereds.clear();
				adapterOrdered.notifyDataSetChanged();
				tv_amount.setText("¥ 0");
				orders = OrderMenu.getOrders();
				order.clear();
				order.addAll(orders.get(category));
				adapterOrder.notifyDataSetChanged();
			}
		});

	}

	private void updatePresentation() {
		// Get the current route and its presentation display.
		MediaRouter.RouteInfo route = mMediaRouter
				.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
		Display presentationDisplay = route != null ? route
				.getPresentationDisplay() : null;

		// Dismiss the current presentation if the display has changed.
		if (mPresentation != null
				&& mPresentation.getDisplay() != presentationDisplay) {
			Log.i("iii",
					"Dismissing presentation because the current route no longer "
							+ "has a presentation display.");
			mPresentation.dismiss();
			mPresentation = null;
		}

		// Show a new presentation if needed.
		if (mPresentation == null && presentationDisplay != null) {
			Log.i("iii", "Showing presentation on display: "
					+ presentationDisplay);
			mPresentation = new DifferentDislay(this, presentationDisplay,
					getFragmentManager());
			mPresentation.setOnDismissListener(mOnDismissListener);
			try {
				mPresentation.show();
			} catch (WindowManager.InvalidDisplayException ex) {
				Log.w("iii",
						"Couldn't show presentation!  Display was removed in "
								+ "the meantime.", ex);
				mPresentation = null;
			}
		}

		// Update the contents playing in this activity.
		updateContents(false);
	}

	private void updateContents(boolean flag) {
		// Show either the content in the main activity or the content in the
		// presentation
		// along with some descriptive text about what is happening.
		if (mPresentation != null) {
			if (adapterOrderedPrint != null) {
				adapterOrderedPrint.notifyDataSetChanged();
			} else {
				adapterOrderedPrint = new AdapterOrderedPrint(
						ActivityOrder.this, ordereds);
				mPresentation.getLv_different().setAdapter(adapterOrderedPrint);
			}
			int total = 0;
			for (ModelOrdered ordered : ordereds) {
				total += ordered.getNumber();
			}
			mPresentation.getTv_print_total().setText(total + "");
			if (flag
					&& mPresentation.getLoopViewPager().getVisibility() == View.VISIBLE) {
				mPresentation.getLoopViewPager().setVisibility(View.INVISIBLE);
			} else if (!flag
					&& mPresentation.getLoopViewPager().getVisibility() == View.INVISIBLE) {
				mPresentation.getLoopViewPager().setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * Listens for when presentations are dismissed.
	 */
	private final DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			if (dialog == mPresentation) {
				Log.i("iii", "Presentation was dismissed.");
				mPresentation = null;
				adapterOrderedPrint = null;
			}
		}
	};

	private final static class DifferentDislay extends Presentation {

		private ListView lv_different;
		private TextView tv_print_total;
		private LoopViewPager viewpager;
		private Context context;
		private Timer timer;
		private int currentPosition = 0;
		private List<Object> images;
		private Banner banner;
		private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				currentPosition++;
				viewpager.setCurrentItem(currentPosition % 5);
			};
		};

		public DifferentDislay(Context context, Display display,
				FragmentManager fragmentManager) {
			super(context, display);
			this.context = context;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			Resources r = getContext().getResources();
			setContentView(R.layout.activity_different);
			lv_different = (ListView) findViewById(R.id.lv_different);
			tv_print_total = (TextView) findViewById(R.id.tv_different_total);
			viewpager = (LoopViewPager) findViewById(R.id.viewpager);
			// banner = (Banner) findViewById(R.id.banner);
			// images = new ArrayList<Object>();
			// images.add(R.drawable.alfredo_pasta);
			// images.add(R.drawable.alioleo_pasta);
			// images.add(R.drawable.andrea_s_favorite_salad);
			// images.add(R.drawable.arrabiata_pasta_hot_hot_hot);
			// images.add(R.drawable.avery_s_salad);
			// images.add(R.drawable.banana_special_pizza);
			// banner.setImages(images).setImageLoader(new
			// GlideImageLoader()).start();
			viewpager.setAdapter(new SamplePagerAdapter());
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.sendEmptyMessage(0);
				}
			}, 2000, 2000);
		}

		public ViewPager getLoopViewPager() {
			return viewpager;
		}

		public ListView getLv_different() {
			return lv_different;
		}

		public TextView getTv_print_total() {
			return tv_print_total;
		}

		private class GlideImageLoader extends
				com.youth.banner.loader.ImageLoader {
			public void displayImage(Context context, Object id,
					ImageView imageView) {
				/**
				 * 注意： 1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
				 * 2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
				 * 传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行， 切记不要胡乱强转！
				 */
				int resId = (Integer) id;
				ImageLoader.getInstance().displayImage("drawable://" + resId,
						imageView);
			}

			@Override
			public ImageView createImageView(Context context) {
				// TODO Auto-generated method stub
				return null;
			}

			// 提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建

		}

		public class SamplePagerAdapter extends PagerAdapter {
			private final Random random = new Random();
			private int mSize;

			public SamplePagerAdapter() {
				mSize = 5;
			}

			public SamplePagerAdapter(int count) {
				mSize = count;
			}

			@Override
			public int getCount() {
				return mSize;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public void destroyItem(ViewGroup view, int position, Object object) {
				view.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup view, int position) {
				View item_view = LayoutInflater.from(context).inflate(
						R.layout.item_banner, null);
				ImageView iv_photo = (ImageView) item_view
						.findViewById(R.id.iv_photo);
				ImageLoader.getInstance().displayImage(
						"drawable://" + order.get(position).getImage(),
						iv_photo);
				view.addView(item_view);
				return item_view;
			}

			// 增加item
			public void addItem() {
				mSize++;
				notifyDataSetChanged();
			}

			// 删除item
			public void removeItem() {
				mSize--;
				mSize = mSize < 0 ? 0 : mSize;
				notifyDataSetChanged();
			}
		}

	}

}
