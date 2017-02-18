package com.pax.d820.activity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pax.base.api.BaseController;
import com.pax.base.api.BaseController.IRouting;
import com.pax.base.listener.BaseSetRouteParamListener;
import com.pax.base.mis.BaseEnum.EComPortType;
import com.pax.base.mis.BaseRouteParam;
import com.pax.base.mis.BaseRoutingResp;
import com.pax.commonlib.comm.CommException;
import com.pax.commonlib.comm.IComm;
import com.pax.commonlib.convert.Convert;
import com.pax.d820.R;
import com.pax.d820.activitybase.ActivityFrame;
import com.pax.s300.api.PaxMposS300;
import com.pax.s300.listener.ConnectListener;
import com.pax.yumei.api.YuMeiPaxMpos;
import com.pax.yumei.listener.CheckCardListener;
import com.pax.yumei.listener.MagProcessListener;
import com.pax.yumei.listener.StartPBOCListener;
import com.pax.yumei.mis.Enum.CardType;
import com.pax.yumei.mis.MagProcessResult;
import com.pax.yumei.mis.StartPBOCParam;
import com.pax.yumei.mis.StartPBOCResult;

public class ActivityReadCard extends ActivityFrame {

	private TextView tv_title, tv_num, tv_lift;
	private YuMeiPaxMpos yuMeiPaxMpos;
	private BaseController controller;
	private String num;
	private int timeout = 60;
	private ImageView iv_gif;
	private int index = 0;
	private List<Integer> indexs = new ArrayList<Integer>();
	private Timer timer;
	private String number;
	private SharedPreferences sharedPreferences;
	private int posType;
	private int misPort;
	private int e820Port;
	private PaxMposS300 manager;
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			iv_gif.setBackgroundResource(indexs.get(index));
			if (index == 3) {
				index = 0;
			} else {
				index++;
			}
			super.handleMessage(msg);
		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("iii", "ActivityReadCard: ---" + "onCreate");
		initView();
		bindData();
		bindListener();
	}

	private void initView() {
		setContentView(R.layout.activity_read_card);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_num = (TextView) findViewById(R.id.tv_num);
		iv_gif = (ImageView) findViewById(R.id.iv_gif);
		tv_lift = (TextView) findViewById(R.id.tv_lift);
	}

	private byte lrc(byte[] data, int offset, int len) {
		byte lrc = 0;
		for (int i = 0; i < len; i++) {
			lrc ^= data[i + offset];
		}
		return lrc;
	}

	@SuppressLint("NewApi")
	private void bindData() {
		setFinishOnTouchOutside(false);
		tv_lift.setVisibility(View.GONE);
		num = getIntent().getStringExtra("num");
		tv_title.setText(getResources().getString(R.string.swiping_card));
		tv_num.setText(num + getResources().getString(R.string.rmb));
		sharedPreferences = getSharedPreferences("fish", Context.MODE_APPEND);
		posType = sharedPreferences.getInt("posType",
				ActivityDevManage.MPOSBASE);
		controller = BaseController.getInstance(this);
		indexs.add(R.drawable.pay01);
		indexs.add(R.drawable.pay02);
		indexs.add(R.drawable.pay03);
		indexs.add(R.drawable.pay04);
		timer = new Timer();
		timer.schedule(new MyTimeTask(), 300, 300);
		if (posType == ActivityDevManage.MPOS
				|| posType == ActivityDevManage.MPOSBASE ) {
			yuMeiPaxMpos = YuMeiPaxMpos.getInstance(this);
			myReadCardListener listener = new myReadCardListener();
			Log.i("iii", "readcard");
			yuMeiPaxMpos.checkCard(CardType.CLSS_MAG_IC_CARD, timeout, listener);// mag
		} else if (posType == ActivityDevManage.MPOSE820){
			e820Port = sharedPreferences.getInt("e820Port", ActivityDevManage.PINPAD);
			if(e820Port==ActivityDevManage.BT){
				yuMeiPaxMpos = YuMeiPaxMpos.getInstance(this);
				myReadCardListener listener = new myReadCardListener();
				Log.i("iii", "readcard");
				yuMeiPaxMpos.checkCard(CardType.CLSS_MAG_IC_CARD, timeout, listener);// mag
			}else if(e820Port==ActivityDevManage.COM){
				manager = PaxMposS300.getInstance(ActivityReadCard.this);
				new Thread(new Runnable() {
				    
                    @Override
                    public void run() {
                        
                        manager.connect("COM1", new ConnectListener() {
                            
                            @Override
                            public void onSucc() {
                            	myCheckCardListener listener = new myCheckCardListener();
                				int timeout = 60;//60s
                				manager.checkCard(com.pax.s300.mis.Enum.CardType.CLSS_MAG_IC_CARD, timeout, listener);
                            }
                            
                            @Override
                            public void onError(int errCode, String errDesc) {
                            	Intent intent = new Intent();
                    			intent.putExtra("info", errDesc);
                    			intent.putExtra("code", errCode);
                    			setResult(RESULT_OK, intent);
                    			timer.cancel();
                    			finish();
                            }
                        });
                    }
                }).start();
    
			}
			
		} else if (posType == ActivityDevManage.BASEMIS) {
			BaseRouteParam param = new BaseRouteParam();
			misPort = sharedPreferences.getInt("misPort", ActivityDevManage.PINPAD);
			if(misPort==ActivityDevManage.PINPAD){
				param.setRouting("0301");
			}else if(misPort == ActivityDevManage.COM){
				param.setRouting("0401");
			}
			final IRouting routing = controller.getRoutingFunc();
			routing.setRouteParam(param, new BaseSetRouteParamListener() {
				
				@Override
				public void onSucc() {
					// TODO Auto-generated method stub
//					final IComm iComm = controller.getBaseIComm();
					yuMeiPaxMpos = YuMeiPaxMpos.getInstance(ActivityReadCard.this, new IComm() {
						@Override
			            public void cancelRecv() {
			                // TODO Auto-generated method stub
			                //baseController.reset();
			            }

			            @Override
			            public void connect() throws CommException {
			                // TODO Auto-generated method stub
			                
			            }

			            @Override
			            public void disconnect() throws CommException {
			                // TODO Auto-generated method stub
			            }

			            @Override
			            public ConnectStatus getConnectStatus() {
			                // TODO Auto-generated method stub
			                if (controller.isConnected()) {
			                    return ConnectStatus.CONNECTED;
			                }else {
			                    return ConnectStatus.DISCONNECTED;
			                }
			            }

			            @Override
			            public int getConnectTimeout() {
			                // TODO Auto-generated method stub
			                return 0;
			            }

			            @Override
			            public int getTransTimeout() {
			                // TODO Auto-generated method stub
			                return 0;
			            }

			            @Override
			            public byte[] recv(int expLen) throws CommException {
			                // TODO Auto-generated method stub
			            	EComPortType comPortType = EComPortType.COM1;
			            	if(misPort==ActivityDevManage.PINPAD){
								comPortType = EComPortType.PINPAD;
							}else if(misPort == ActivityDevManage.COM){
								comPortType = EComPortType.COM1;
							}
			                BaseRoutingResp ret = controller.getRoutingFunc().recv(comPortType, null, expLen, 30*1000);
			                if (ret.respCode == 0x9000) {
			                    Log.d("iii","baiFuTong pro recv:" + Convert.bcd2Str(ret.respData));
			                    return ret.respData;
			                }else {
			                    return new byte[0];
			                }
			            }

			            @Override
			            public void reset() {
			                // TODO Auto-generated method stub
			            }

			            @Override
			            public void send(byte[] arg0) throws CommException {
			                // TODO Auto-generated method stub
			                Log.d("iii","baiFuTong pro send:" + Convert.bcd2Str(arg0));
			                EComPortType comPortType = EComPortType.COM1;
							if(misPort==ActivityDevManage.PINPAD){
								comPortType = EComPortType.PINPAD;
							}else if(misPort == ActivityDevManage.COM){
								comPortType = EComPortType.COM1;
							}
			                controller.getRoutingFunc().send(comPortType, null, arg0);
			            }

			            @Override
			            public void setConnectTimeout(int arg0) {
			                // TODO Auto-generated method stub
			                
			            }

			            @Override
			            public void setTransTimeout(int arg0) {
			                // TODO Auto-generated method stub
			                //baseController.getRoutingFunc().setTransTimeout(arg0);
			            }

						@Override
						public byte[] recvNonBlocking() throws CommException {
							// TODO Auto-generated method stub
							return null;
						}

					});
					myReadCardListener listener = new myReadCardListener();
					Log.i("iii", "readcard");
					yuMeiPaxMpos.checkCard(CardType.CLSS_MAG_IC_CARD, timeout, listener);// mag
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}

	}

	private void bindListener() {
		// TODO Auto-generated method stub
	}
	
	class myCheckCardListener implements com.pax.s300.listener.CheckCardListener {

		@Override
		public void onError(int errCode, String errDesc) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("info", errDesc);
			intent.putExtra("code", errCode);
			setResult(RESULT_OK, intent);
			timer.cancel();
			finish();
		}

		@Override
		public void onSucc(com.pax.s300.mis.Enum.CardType cardType) {
			// TODO Auto-generated method stub
			Log.i("iii", "cardType---"+cardType);
			if(cardType == com.pax.s300.mis.Enum.CardType.MAGNETIC_CARD){
				
				String amount = "000000000012";
				int timeout = 60; //60s
				
				myE820MagProcessListener listener = new myE820MagProcessListener();
				manager.magProcess(amount, timeout, listener);
				
				
			}else if(cardType == com.pax.s300.mis.Enum.CardType.IC_CARD){
		
				com.pax.s300.mis.StartPBOCParam param = new com.pax.s300.mis.StartPBOCParam();
			  
			   
			    param.setTransType((byte)0x00);
			    param.setDateTime("150504163400");
			    param.setAuthAmount("000000000001");
			    param.setOtherAmount("000000000000");
			   
			    myE820StartPBOCListener listener = new myE820StartPBOCListener();
			    manager.startPBOC(param, listener);
			}else if(cardType == com.pax.s300.mis.Enum.CardType.CLSS_CARD){
				number = "6212261202006859632";
				Log.i("iii", "card---" + number);
				Intent intent = new Intent(ActivityReadCard.this,
						ActivitySignature.class);
				intent.putExtra("num", num);
				intent.putExtra("cardNo", number);
				timer.cancel();
				startActivityForResult(intent, 1);
			}
			
		}
	}
	
	class myE820StartPBOCListener implements com.pax.s300.listener.StartPBOCListener {

		@Override
		public void onError(int errCode, String errDesc) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("info", errDesc);
			intent.putExtra("code", errCode);
			setResult(RESULT_OK, intent);
			timer.cancel();
			finish();
		}

		@Override
		public void onSucc(com.pax.s300.mis.StartPBOCResult result) {
			// TODO Auto-generated method stub
			number = result.getPan();
			Log.i("iii", "card---" + number);
			Intent intent = new Intent(ActivityReadCard.this,
					ActivitySignature.class);
			intent.putExtra("num", num);
			intent.putExtra("cardNo", number);
			timer.cancel();
			startActivityForResult(intent, 1);						
		}
	}
	
	class myE820MagProcessListener implements com.pax.s300.listener.MagProcessListener {

		@Override
		public void onError(int errCode, String errDesc) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("info", errDesc);
			intent.putExtra("code", errCode);
			setResult(RESULT_OK, intent);
			timer.cancel();
			finish();
		}

		@Override
		public void onSucc(com.pax.s300.mis.MagProcessResult result) {
			// TODO Auto-generated method stub
			
			String pinBlock = "";
			if (result.getPinblock() != null) {
			    pinBlock = Convert.bcd2Str(result.getPinblock());
			}
			number = result.getPan();
			Log.i("iii", "card---" + number);
			Intent intent = new Intent(ActivityReadCard.this,
					ActivitySignature.class);
			intent.putExtra("num", num);
			intent.putExtra("cardNo", number);
			timer.cancel();
			startActivityForResult(intent, 1);
			
		}
	}


	class myReadCardListener implements CheckCardListener {

		@Override
		public void onError(int arg0, String arg1) {
			Intent intent = new Intent();
			intent.putExtra("info", arg1);
			intent.putExtra("code", arg0);
			setResult(RESULT_OK, intent);
			timer.cancel();
			finish();
		}

		@Override
		public void onSucc(CardType cardInfo) {
			// TODO Auto-generated method stub
			long a = (long) (Float.valueOf(num) * 100);
			DecimalFormat df = new DecimalFormat("000000000000");
			String amount = df.format(a);
			if (cardInfo == CardType.IC_CARD) {
				Log.d("iii", "IC Card, start pboc...");
				StartPBOCParam param = new StartPBOCParam();
				param.setTransType((byte) 0x00);
				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyMMddhhmmss");
				String date = sDateFormat.format(new java.util.Date()); // ���ϵͳʱ��
				Log.i("iii", date);
				param.setDateTime(date);
				param.setAuthAmount(amount);
				param.setOtherAmount("000000000000");
				myStartPBOCListener listener = new myStartPBOCListener();
				yuMeiPaxMpos.startPBOC(param, listener);
			} else if (cardInfo == CardType.MAGNETIC_CARD) {
				MyMagProcessListener listener = new MyMagProcessListener();
				yuMeiPaxMpos.magProcess(amount, timeout, listener);
			} else if (cardInfo == CardType.CLSS_CARD) {
				yuMeiPaxMpos.closeDevice();
				number = "6212261202006859632";
				Log.i("iii", "card---" + number);
				Intent intent = new Intent(ActivityReadCard.this,
						ActivitySignature.class);
				intent.putExtra("num", num);
				intent.putExtra("cardNo", number);
				timer.cancel();
				startActivityForResult(intent, 1);
				
			}
		}
	}

	class MyMagProcessListener implements MagProcessListener {

		@Override
		public void onError(int arg0, String arg1) {
			Intent intent = new Intent();
			intent.putExtra("info", arg1);
			intent.putExtra("code", arg0);
			setResult(RESULT_OK, intent);
			timer.cancel();
			finish();
		}

		@Override
		public void onSucc(MagProcessResult arg0) {
			// yuMeiPaxMpos.closeDevice();
			number = arg0.getPan();
			Log.i("iii", "card---" + number);
			Intent intent = new Intent(ActivityReadCard.this,
					ActivitySignature.class);
			intent.putExtra("num", num);
			intent.putExtra("cardNo", number);
			startActivityForResult(intent, 1);
			timer.cancel();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

		}
		return true;
	}

	class myStartPBOCListener implements StartPBOCListener {

		@Override
		public void onError(int errCode, String errDesc) {
			// TODO Auto-generated method stub
			// showMsg("start pboc error:" + errDesc);
			Intent intent = new Intent();
			intent.putExtra("info", errDesc);
			intent.putExtra("code", errCode);
			setResult(RESULT_OK, intent);
			timer.cancel();
			finish();
		}

		@Override
		public void onSucc(StartPBOCResult result) {
			// yuMeiPaxMpos.closeDevice();
			number = result.getPan();
			Log.i("iii", "card---" + number);
			Intent intent = new Intent(ActivityReadCard.this,
					ActivitySignature.class);
			intent.putExtra("num", num);
			intent.putExtra("cardNo", number);
			timer.cancel();
			startActivityForResult(intent, 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String info = data.getStringExtra("info");
		int code = data.getIntExtra("code", 0);
		Intent intent = new Intent();
		intent.putExtra("info", info);
		intent.putExtra("code", code);
		if (number != null && !"".equals(number)) {
			Log.i("iii", "card---" + number);
			intent.putExtra("number", number);
		}
		setResult(RESULT_OK, intent);
		finish();
	};

	private class MyTimeTask extends TimerTask {

		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}

	}

}
