//try {
//			printManager = PrintManager.getInstance();
//			printManager.prnInit();
//			printManager.prnSetGray(gray);
//			printManager.prnStr("------------------------------------\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnSpaceSet((byte) 1, (byte) 1);
//			printManager.prnFontSet((byte) 0, (byte) 0);
//			printManager.prnStr("\n�̻����     MERCHANT COPY", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("\n----------------------------------\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("�̻�����(MERCHANT NAME):   \n", "UTF-8");
//			printManager.prnStr("                        PAX-----\n", "UTF-8");
//
//			printManager.prnStr("�̻���(MERCHANT NO):       \n", "UTF-8");
//			printManager.prnStr("                         01-----\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("�ն˺�(TERMINAL NO):       \n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			printManager.prnStr("                   01-----\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("����Ա��(OPERATOR NO):    01-----\n", "UTF-8");
//			printManager.prnStr("�յ��к�(ACQ NO):         01-----\n", "UTF-8");
//			printManager.prnStr("������(ISSURE):     �й�����\n", "UTF-8");
//			printManager.prnStr("����(CARD NO):\n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			printManager.prnStr("1234567890123456\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//
////	            �ַ����еĿո���Ϊ�˺�ߵ���Ч�ڶ���� 
//			printManager.prnStr("��������(TRAN TYPE):\n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 6);
//			printManager.prnStr("           ����(SALE)\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("��Ч��(EXP DATE): -----2009/01/01\n", "UTF-8");
//			printManager.prnStr("���κ�(BATCH NO):     -----090101\nƾ֤��(VOUCHER NO):   -----123456\n", "UTF-8");
//			printManager.prnStr("��Ȩ��(AUTH NO):      -----987654\n", "UTF-8");
//			printManager.prnStr("�ο���(REF NO):\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("����/ʱ��(DATE/TIME):\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			Time t = new Time();
//			t.setToNow(); // ȡ��ϵͳʱ�䡣
//			printManager.prnStr(t.format("%Y-%m-%d/%H:%M:%S\n"), "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("���(AMOUNT):\n", "UTF-8");
//			printManager.prnFontSet((byte) 6, (byte) 1);
//			printManager.prnStr("              -----RMB1.00\n", "UTF-8");
//
//			printManager.prnFontSet((byte) 1, (byte) 1);
//
////	            ��ӡԭ������Ϣ 
//			printManager.prnStr("----------------------------------\n", "UTF-8");
//
//			printManager.prnStr("��ע/REFERENCE\n", "UTF-8");
//			printManager.prnStr("", "UTF-8");
//			printManager.prnStr("\n", "UTF-8");
//			printManager.prnStr("----------------------------------\n", "UTF-8");
//
//			printManager.prnStr("�ֿ���ǩ��CARDHOLDER SIGNATURE\n\n\n\n\n---------------------------------\n", "UTF-8");
//			printManager.prnFontSet((byte) 0, (byte) 0);
//			printManager.prnStr("����ȷ�����Ͻ��ף�ͬ�⽫����뱾���˻�\n", "UTF-8");
//			printManager.prnFontSet((byte) 1, (byte) 1);
//			printManager.prnStr("\n\n\n\n\n\n\n\n", "UTF-8");
//            Log.d("shsh", "start cut....111");
//            int cutRet = printManager.prnStartCut(1); //��ֽ
//            Log.d("shsh", "start cut....222, cutRet=" + cutRet);
////			Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("prndata.png"));
////			printManager.prnInit();
////			printManager.prnSetGray(gray);
////			printManager.prnBitmap(bitmap);
//            printManager.prnStart();
//		} catch (PrintException e) {
//			e.printStackTrace();
//		}