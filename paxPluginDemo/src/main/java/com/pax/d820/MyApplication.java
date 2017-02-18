package com.pax.d820;

import com.pax.api.PrintException;
import com.pax.api.PrintManager;

import android.app.Application;

public class MyApplication extends Application {
	
	
	
	public void onCreate() {
		
		 try {
		 PrintManager printManager = PrintManager.getInstance(this);
		 
		 } catch (PrintException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		
	};

}
