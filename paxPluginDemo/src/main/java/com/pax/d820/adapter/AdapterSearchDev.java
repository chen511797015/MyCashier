package com.pax.d820.adapter;

import java.util.List;

import com.pax.d820.R;
import com.pax.d820.model.ModelBluetooth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterSearchDev extends AdapterBase {

	private List<ModelBluetooth> bluetooths;
	private Context context;

	public AdapterSearchDev(Context context, List list) {
		super(context, list);
		this.bluetooths = list;
		this.context = context;
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return super.getContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_search_dev, null);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			viewHolder.tv_connect = (TextView) convertView
					.findViewById(R.id.tv_connect);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (bluetooths.get(position).isPos()
				|| bluetooths.get(position).isPrint()) {
			viewHolder.tv_connect.setVisibility(View.VISIBLE);
			if (bluetooths.get(position).isPos()) {
				viewHolder.tv_connect.setText(getResources().getString(
						R.string.pos_device));
			}
			if (bluetooths.get(position).isPrint()) {
				viewHolder.tv_connect.setText(getResources().getString(
						R.string.print_device));
			}
		} else {
			viewHolder.tv_connect.setVisibility(View.GONE);
		}
		viewHolder.tv_name.setText(bluetooths.get(position).getName());
		return convertView;
	}

	class ViewHolder {
		public TextView tv_name, tv_connect;
	}

}
