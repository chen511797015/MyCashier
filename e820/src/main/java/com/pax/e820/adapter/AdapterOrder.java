package com.pax.e820.adapter;import java.util.List;import android.app.Activity;import android.content.Context;import android.graphics.Bitmap;import android.util.DisplayMetrics;import android.view.LayoutInflater;import android.view.View;import android.view.View.OnClickListener;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.TextView;import com.nostra13.universalimageloader.core.DisplayImageOptions;import com.nostra13.universalimageloader.core.ImageLoader;import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;import com.pax.e820.R;import com.pax.e820.model.ModelOrder;public class AdapterOrder extends AdapterBase {	private List<ModelOrder> Orders;	private Context context;	private int width;	private CallBack callBack;	private DisplayImageOptions options;	public AdapterOrder(Activity context, List list) {		super(context, list);		// TODO Auto-generated constructor stub		this.Orders = list;		this.context = context;		DisplayMetrics dm = new DisplayMetrics();  		context.getWindowManager().getDefaultDisplay().getMetrics(dm);          width = dm.widthPixels;        options =new DisplayImageOptions.Builder()        .cacheInMemory(true)        .cacheOnDisc(true)        .bitmapConfig(Bitmap.Config.RGB_565)  //设置图片的解码类型        .displayer(new RoundedBitmapDisplayer(20))        .build();	}		public void setCallBack(CallBack callBack){		this.callBack = callBack;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		// TODO Auto-generated method stub		ViewHolder viewHolder;		if (convertView == null) {			viewHolder = new ViewHolder();			convertView = LayoutInflater.from(context).inflate(					R.layout.item_order, null);			viewHolder.tv_order_name = (TextView) convertView					.findViewById(R.id.tv_order_name);			viewHolder.tv_order_price = (TextView) convertView					.findViewById(R.id.tv_order_price);			viewHolder.tv_order_number = (TextView) convertView					.findViewById(R.id.tv_order_number);			viewHolder.iv_order_icon = (ImageView) convertView.findViewById(R.id.iv_order_icon);			viewHolder.iv_plus = (ImageView) convertView.findViewById(R.id.iv_plus);			viewHolder.iv_minus = (ImageView) convertView.findViewById(R.id.iv_minus);			convertView.setTag(viewHolder);		} else {			viewHolder = (ViewHolder) convertView.getTag();		}		viewHolder.tv_order_name.setText(Orders.get(position).getName());		viewHolder.tv_order_number.setText(Orders.get(position).getNumber()+"");		viewHolder.tv_order_price.setText("¥ "+Orders.get(position).getPrice());		final int pos = position;		if(Orders.get(position).getNumber()>0){			viewHolder.iv_minus.setVisibility(View.VISIBLE);			viewHolder.iv_minus.setOnClickListener(new OnClickListener() {								@Override				public void onClick(View v) {					// TODO Auto-generated method stub					if(Orders.get(pos).getNumber()>0){						Orders.get(pos).setNumber(Orders.get(pos).getNumber()-1);					}					notifyDataSetChanged();					if(callBack!=null){						callBack.minus(pos);					}				}			});		}else{			viewHolder.iv_minus.setVisibility(View.INVISIBLE);		}		viewHolder.iv_plus.setOnClickListener(new OnClickListener() {						@Override			public void onClick(View v) {				// TODO Auto-generated method stub				Orders.get(pos).setNumber(Orders.get(pos).getNumber()+1);				notifyDataSetChanged();				if(callBack!=null){					callBack.plus(pos);				}			}		});//		viewHolder.iv_order_icon.setBackgroundResource(Orders.get(pos).getImage());		ImageLoader.getInstance().displayImage("drawable://"+Orders.get(pos).getImage(), viewHolder.iv_order_icon,options);		return convertView;	}		class ViewHolder {		public TextView tv_order_name, tv_order_price,tv_order_number;		public ImageView iv_order_icon,iv_plus,iv_minus;	}		public interface CallBack{		void plus(int pos);		void minus(int pos);	}}