package com.pax.d820.adapter;

import java.util.List;

import javax.crypto.spec.PSource;

import com.pax.d820.R;
import com.pax.d820.model.ModelType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterScan extends AdapterBase {

	private Context context;
	private List<String> barcodes;

	public AdapterScan(Context context, List list) {
		super(context, list);
		this.context = context;
		this.barcodes = list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_scan, null);
		}
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_barcode = (TextView) convertView.findViewById(R.id.tv_barcode);
		TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
		tv_name.setText("水果"+(position+1));
		tv_barcode.setText(barcodes.get(position));
		tv_price.setText("RMB "+(position+1));
		return convertView;
	}

}
