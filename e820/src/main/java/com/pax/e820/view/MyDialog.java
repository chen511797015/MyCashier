package com.pax.e820.view;import com.nostra13.universalimageloader.core.ImageLoader;import com.pax.e820.R;import android.app.Dialog;import android.content.Context;import android.os.Bundle;import android.widget.ImageView;import android.widget.TextView;public class MyDialog extends Dialog{	private ImageView iv;	private TextView tv;		public MyDialog(Context context) {		super(context);		// TODO Auto-generated constructor stub		setContentView(R.layout.dialog);		iv = (ImageView) findViewById(R.id.iv);		tv = (TextView) findViewById(R.id.tv);	}			@Override	protected void onCreate(Bundle savedInstanceState) {		// TODO Auto-generated method stub		super.onCreate(savedInstanceState);	}		public ImageView getImageView(){		return iv;	}		public TextView getTextView(){		return tv;	}	}