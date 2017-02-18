package com.pax.d820.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.pax.base.api.BaseController;
import com.pax.base.listener.BaseResetListener;
import com.pax.base.listener.BaseScanRepeatedListener;
import com.pax.base.mis.BaseEnum.PortType;
import com.pax.d820.R;
import com.pax.d820.activitybase.ActivityFrame;
import com.pax.d820.adapter.AdapterScan;

public class ActivityScan extends ActivityFrame {
	
	private ListView lv_barcode;
	private Button btn_submit;
	private ArrayList<String> barcodes;
	private AdapterScan adapterScan;
	private String scanType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		initView();
		bindData();
	}

	private void bindData() {
		// TODO Auto-generated method stub
		scanType = getIntent().getStringExtra("scanType");
		PortType portType;
		String baudRate;
		if(scanType!=null&&scanType.equals("USB")){
			portType = PortType.USB;
			baudRate = "";
		}else{
			portType = PortType.COM;
			baudRate = "115200,8,n,1";
		}
		barcodes = new ArrayList<String>();
		adapterScan = new AdapterScan(ActivityScan.this, barcodes);
		lv_barcode.setAdapter(adapterScan);
		BaseController.getInstance(ActivityScan.this).scanBarcodeRepeated(portType, baudRate, new BaseScanRepeatedListener() {
			
			@Override
			public void onScaned(String arg0) {
				// TODO Auto-generated method stub
				barcodes.add(arg0);
				adapterScan.notifyDataSetChanged();
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.putExtra("info", "finish");
//				intent.putStringArrayListExtra("barcodes", barcodes);
//				intent.putExtra("code", 9000);
//				setResult(RESULT_OK, intent);
//				finish();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("info", arg1);
				intent.putExtra("code", arg0);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseController.getInstance(ActivityScan.this).reset(new BaseResetListener() {
					
					@Override
					public void onSucc() {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("info", "finish");
						intent.putStringArrayListExtra("barcodes", barcodes);
						intent.putExtra("code", 9000);
						setResult(RESULT_OK, intent);
						finish();
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("info", "finish");
						intent.putStringArrayListExtra("barcodes", barcodes);
						intent.putExtra("code", 9000);
						setResult(RESULT_OK, intent);
						finish();
					}
				});
				
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		lv_barcode = (ListView) findViewById(R.id.lv_barcode);
		btn_submit = (Button) findViewById(R.id.btn_submit);
	}

}
