//try {
//			printManager = PrintManager.getInstance();
//			printManager.prnInit();
//			printManager.prnSetGray(gray);
//			printManager.prnStr("------------------------------------\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnSpaceSet((byte) 1, (byte) 1);
//			printManager.prnFontSet((byte) 0, (byte) 0);
//			printManager.prnStr("\n商户存根     MERCHANT COPY", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("\n----------------------------------\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("商户名称(MERCHANT NAME):   \n", "UTF-8");
//			printManager.prnStr("                        PAX-----\n", "UTF-8");
//
//			printManager.prnStr("商户号(MERCHANT NO):       \n", "UTF-8");
//			printManager.prnStr("                         01-----\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("终端号(TERMINAL NO):       \n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			printManager.prnStr("                   01-----\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("操作员号(OPERATOR NO):    01-----\n", "UTF-8");
//			printManager.prnStr("收单行号(ACQ NO):         01-----\n", "UTF-8");
//			printManager.prnStr("发卡行(ISSURE):     中国银行\n", "UTF-8");
//			printManager.prnStr("卡号(CARD NO):\n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			printManager.prnStr("1234567890123456\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//
////	            字符串中的空格是为了后边的有效期而设的 
//			printManager.prnStr("交易类型(TRAN TYPE):\n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 6);
//			printManager.prnStr("           消费(SALE)\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("有效期(EXP DATE): -----2009/01/01\n", "UTF-8");
//			printManager.prnStr("批次号(BATCH NO):     -----090101\n凭证号(VOUCHER NO):   -----123456\n", "UTF-8");
//			printManager.prnStr("授权码(AUTH NO):      -----987654\n", "UTF-8");
//			printManager.prnStr("参考号(REF NO):\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("日期/时间(DATE/TIME):\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			Time t = new Time();
//			t.setToNow(); // 取得系统时间。
//			printManager.prnStr(t.format("%Y-%m-%d/%H:%M:%S\n"), "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("金额(AMOUNT):\n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			printManager.prnStr("              -----RMB1.00\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//
////	            打印原交易信息 
//			printManager.prnStr("----------------------------------\n", "UTF-8");
//
//			printManager.prnStr("备注/REFERENCE\n", "UTF-8");
//			printManager.prnStr("", "UTF-8");
//			printManager.prnStr("\n", "UTF-8");
//			printManager.prnStr("----------------------------------\n", "UTF-8");
//
//			printManager.prnStr("持卡人签名CARDHOLDER SIGNATURE\n\n\n\n\n---------------------------------\n", "UTF-8");
//			printManager.prnFontSet((byte) 0, (byte) 0);
//			printManager.prnStr("本人确认以上交易，同意将其计入本卡账户\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("\n\n\n\n\n\n\n\n", "UTF-8");
//            Log.d("shsh", "start cut....111");
//            int cutRet = printManager.prnStartCut(1); //切纸
//            Log.d("shsh", "start cut....222, cutRet=" + cutRet);
////			Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("prndata.png"));
////			printManager.prnInit();
////			printManager.prnSetGray(gray);
////			printManager.prnBitmap(bitmap);
//            printManager.prnStart();
//		} catch (PrintException e) {
//			e.printStackTrace();
//		}