package com.pax.e820.adapter;

import java.util.List;

import com.pax.e820.R;
import com.pax.e820.model.ModelOrdered;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterOrdered extends AdapterBase {

	private List<ModelOrdered> Ordereds;
	private Context context;
	public AdapterOrdered(Context context, List list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.Ordereds = list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_ordered, null);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			viewHolder.tv_number = (TextView) convertView
					.findViewById(R.id.tv_number);
			viewHolder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(Ordereds.get(position).getName());
		viewHolder.tv_number.setText("X "+Ordereds.get(position).getNumber());
		viewHolder.tv_price.setText("¥ "+Ordereds.get(position).getPrice());
		return convertView;
	}
	
	class ViewHolder {
		public TextView tv_name, tv_number,tv_price;
	}

}
