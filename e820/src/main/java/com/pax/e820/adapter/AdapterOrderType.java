package com.pax.e820.adapter;

import java.util.List;

import com.pax.e820.R;
import com.pax.e820.model.ModelOrder;
import com.pax.e820.model.ModelOrdered;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterOrderType extends AdapterBase {

	private List<ModelOrder> Orders;
	private Context context;
	private int selectedPosition = 0;// 选中的位置    
	public AdapterOrderType(Context context, List list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.Orders = list;
		this.context = context;
	}
	
	public void setSelectedPosition(int position) {    
        selectedPosition = position;    
    }    

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_order_type, null);
			viewHolder.tv_type = (TextView) convertView
					.findViewById(R.id.tv_type);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(selectedPosition==position){
			viewHolder.tv_type.setBackgroundResource(R.drawable.right_btn_sel);
			viewHolder.tv_type.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			viewHolder.tv_type.setBackgroundResource(R.drawable.right_btn_def);
			viewHolder.tv_type.setTextColor(context.getResources().getColor(R.color.grey6));
		}
		viewHolder.tv_type.setText(Orders.get(position).getName());
		return convertView;
	}
	
	class ViewHolder {
		public TextView tv_type;
	}

}
