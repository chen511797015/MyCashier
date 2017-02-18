package com.pax.d820.view;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.pax.d820.R;
import com.pax.d820.activity.ActivityDevManage;
import com.pax.d820.model.ModelBluetooth;

public class CustomDialog extends Dialog {

	private int layoutRes;// 布局文件
	private Context context;
	private int posType;
	private int misPort,e820Port;
	private boolean isDisconnect;
	private OnCheckedChangeListener misListener,disconnectListener,e820Listener;
	private com.pax.d820.view.MutilRadioGroup.OnCheckedChangeListener modeListener;
	private RadioGroup rg_mis,rg_disconnect,rg_e820;
	private MutilRadioGroup rg_mode;
	private TextView tv_version;
	private LinearLayout ll_mis,ll_e820; 
	
	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义布局的构造方法
	 * 
	 * @param context
	 * @param resLayout
	 */
	public CustomDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方法
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public CustomDialog(Context context, int theme, int resLayout, int posType,int misPort,boolean isDisconnect,int e820Port) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
		this.posType = posType;
		this.misPort = misPort;
		this.e820Port = e820Port;
		this.isDisconnect = isDisconnect;
	}

	public void setChechLister(com.pax.d820.view.MutilRadioGroup.OnCheckedChangeListener modeListener,
			OnCheckedChangeListener misListener,OnCheckedChangeListener disconnectListener,OnCheckedChangeListener e820Listener) {
		this.modeListener = modeListener;
		this.misListener = misListener;
		this.disconnectListener = disconnectListener;
		this.e820Listener = e820Listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutRes);
		rg_mode = (MutilRadioGroup) findViewById(R.id.rg_mode);
		rg_mis = (RadioGroup) findViewById(R.id.rg_mis);
		rg_e820 = (RadioGroup) findViewById(R.id.rg_e820);
		rg_disconnect = (RadioGroup) findViewById(R.id.rg_disconnect);
		ll_mis = (LinearLayout) findViewById(R.id.ll_mis);
		ll_e820 = (LinearLayout) findViewById(R.id.ll_e820);
		tv_version = (TextView) findViewById(R.id.tv_version);
		String pkName = context.getPackageName();
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_version.setText("version:V"+versionName);
		if(isDisconnect){
			rg_disconnect.check(R.id.rb_yes);
		}else{
			rg_disconnect.check(R.id.rb_no);
		}
		switch (posType) {
		case ActivityDevManage.MPOSBASE:
			rg_mode.check(R.id.rb_mposbase);
			ll_mis.setVisibility(View.INVISIBLE);
			ll_e820.setVisibility(View.INVISIBLE);
			break;
		case ActivityDevManage.MPOS:
			rg_mode.check(R.id.rb_mpos);
			ll_mis.setVisibility(View.INVISIBLE);
			ll_e820.setVisibility(View.INVISIBLE);
			break;
		case ActivityDevManage.BASEMIS:
			rg_mode.check(R.id.rb_basemis);
			ll_mis.setVisibility(View.VISIBLE);
			ll_e820.setVisibility(View.INVISIBLE);
			break;
		case ActivityDevManage.MPOSE820:
			rg_mode.check(R.id.rb_pose820);
			ll_mis.setVisibility(View.INVISIBLE);
			ll_e820.setVisibility(View.VISIBLE);
			break;
		}
		switch (misPort) {
		case ActivityDevManage.COM:
			rg_mis.check(R.id.rb_com);
			break;
		case ActivityDevManage.PINPAD:
			rg_mis.check(R.id.rb_pin);
			break;
		}
		switch (e820Port) {
		case ActivityDevManage.COM:
			rg_e820.check(R.id.rb_e820_com);
			break;

		case ActivityDevManage.BT:
			rg_e820.check(R.id.rb_e820_bt);
			break;
		}
		rg_disconnect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				disconnectListener.onCheckedChanged(group, checkedId);
			}
		});
		rg_mode.setOnCheckedChangeListener(new com.pax.d820.view.MutilRadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(modeListener!=null){
					modeListener.onCheckedChanged(group, checkedId);				
				}
				switch (checkedId) {
				case R.id.rb_mposbase:
					ll_mis.setVisibility(View.INVISIBLE);
					ll_e820.setVisibility(View.INVISIBLE);
					break;
				case R.id.rb_mpos:
					ll_mis.setVisibility(View.INVISIBLE);
					ll_e820.setVisibility(View.INVISIBLE);
					break;
				case R.id.rb_basemis:
					ll_mis.setVisibility(View.VISIBLE);
					ll_e820.setVisibility(View.INVISIBLE);
					break;
				case R.id.rb_pose820:
					ll_mis.setVisibility(View.INVISIBLE);
					ll_e820.setVisibility(View.VISIBLE);
					break;
				}
			}
		});
//		rg_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				if(modeListener!=null){
//					modeListener.onCheckedChanged(group, checkedId);
//				}
//				switch (checkedId) {
//				case R.id.rb_mposbase:
//					ll_mis.setVisibility(View.INVISIBLE);
//					break;
//				case R.id.rb_mpos:
//					ll_mis.setVisibility(View.INVISIBLE);
//					break;
//				case R.id.rb_basemis:
//					ll_mis.setVisibility(View.VISIBLE);
//					break;
//				}
//			}
//		});
		rg_mis.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(misListener!=null){
					misListener.onCheckedChanged(group, checkedId);
				}
				
			}
		});
		rg_e820.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(e820Listener!=null){
					e820Listener.onCheckedChanged(group, checkedId);
				}
				
			}
		});
	}

}
