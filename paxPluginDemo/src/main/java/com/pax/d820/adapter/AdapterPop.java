package com.pax.d820.adapter;

import java.util.List;

import com.pax.d820.R;
import com.pax.d820.model.ModelType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterPop extends AdapterBase {

	private Context context;
	private List<ModelType> types;

	public AdapterPop(Context context, List list) {
		super(context, list);
		this.context = context;
		this.types = list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_pop, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tv);
		ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
		tv.setText(types.get(position).getType());
		if (types.get(position).isSelect()) {
			iv.setVisibility(View.VISIBLE);
		} else {
			iv.setVisibility(View.GONE);
		}
		return convertView;
	}

}
