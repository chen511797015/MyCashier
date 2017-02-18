package com.pax.e820.receiver;

import com.pax.api.PrintException;
import com.pax.api.PrintManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootupReceiver extends BroadcastReceiver {

	private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (ACTION_BOOT.equals(intent.getAction())) {
			try {
				PrintManager mPrintManager = PrintManager.getInstance(context);
				mPrintManager.prnStr("PRINT TEST...", "GBK");
			} catch (PrintException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
