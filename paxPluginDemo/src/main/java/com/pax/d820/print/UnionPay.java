package com.pax.d820.print;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.pax.commonlib.comm.CommException;
import com.pax.commonlib.comm.IComm;
import com.pax.commonlib.comm.IComm.ConnectStatus;
import com.pax.commonlib.convert.Convert;
import com.pax.commonlib.dataformat.Apdu;
import com.pax.commonlib.exception.ExceptionCodeParser;
import com.pax.commonlib.init.CommonLibInit;
import com.pax.commonlib.log.AppDebug;

public class UnionPay {
	private static final String TAG = "UnionPay";
	private IComm comm;
	private Context context;

	private final byte MSG_STX_1 = 0x55;
	private final byte MSG_STX_2 = (byte) 0xAA;
	private final byte MSG_ETX_END_1 = (byte) 0xCC;
	private final byte MSG_ETX_END_2 = (byte) 0x33;

	private static UnionPay instance;
	private final int DEFAULT_RETRY_COUNT = 3;// �?��重传次数
	private int currentPackNum;// 当前包的当前序号
	private int currentFrameNum = 0;// 当前包的当前数据帧序
	private final int MAX_FRAME_SIZE = (1024 + 256 - 12);// 单次交互帧的�?��限制
	// private final int MAX_FRAME_SIZE = (1024 - 12);// 单次交互帧的�?��限制
	private final int MAX_PACKAGE_SIZE = 3072;// 单次交互包的�?��限制
	private final int MAX_PACKAGE_SIZE_PRN = 4096;// 打印数据包的�?��限制

	private final byte PACKAGE_REQ = 0x51;// 请求方包类型
	private final byte PACKAGE_ACK = 0x58;// ACK包类�?
	private final byte PACKAGE_NAK = 0x59;// NAK包类�?

	private final byte PACKAGE_FALSE = 0x60;

	/**
	 * 获取UnionPay 实例
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized UnionPay getInstance(Context context) {

		if (instance == null) {
			instance = new UnionPay(context);
		}

		return instance;
	}

	/**
	 * 设置 通讯 对象
	 * 
	 * @param comm
	 */
	public synchronized void setComm(IComm comm) {
		this.comm = comm;
		// set comm will reset the proto data, thus will do sync before
		// exchanging data.
		// this ensures the proto is always sync in case the peer is changed.
		resetProto();
	}

	/**
	 * 连接 MPOS
	 * 
	 * @return true 连接成功 false 连接失败
	 * @throws UnionPayException
	 */
	public synchronized boolean connect() throws UnionPayException {
		boolean ret = true;
		// TODO Auto-generated method stub
		try {
			comm.connect();
		} catch (CommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentPackNum = 1;
		return ret;
	}

	/**
	 * 判断 上位机与MPOS 是否连接
	 * 
	 * @return true �?false �?
	 */
	public synchronized boolean isConnected() {
		if (comm == null) {
			return false;
		}
		return (comm.getConnectStatus() == ConnectStatus.CONNECTED) ? true
				: false;
	}

	/**
	 * 断开连接
	 * 
	 * @throws UnionPayException
	 */
	public void close() throws UnionPayException {
		comm.cancelRecv();
		synchronized (this) {
			try {
				currentPackNum = 1;
				comm.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发�?数据
	 * 
	 * @param sendData
	 *            待发送数�?
	 * @param timeoutMs
	 *            超时时间(毫秒)
	 * @throws UnionPayException
	 */
	public synchronized void send(byte[] sendData, int timeoutMs)
			throws UnionPayException {
		try {
			sendImpl(sendData, timeoutMs);
		} catch (UnionPayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (CommException ce) {
			// TODO Auto-generated catch block
			ce.printStackTrace();
			throw commExceptionToUnionPayException(ce);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnionPayException(UnionPayException.ERR_PROTO_SEND);
		}
	}

	/**
	 * 接受数据
	 * 
	 * @param timeoutMs
	 *            超时时间(毫秒)
	 * @return 接收到的数据
	 * @throws UnionPayException
	 */
	public synchronized byte[] recv(int timeoutMs) throws UnionPayException {
		try {
			return recvImpl(timeoutMs);
		} catch (UnionPayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (CommException ce) {
			// TODO Auto-generated catch block
			ce.printStackTrace();
			throw commExceptionToUnionPayException(ce);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnionPayException(UnionPayException.ERR_PROTO_RECV);
		}
	}

	public byte[] getBasicInfo() {

		try {
			send(new byte[] { 0x01, 0x01 }, 30);
			return recv(30000);
		} catch (UnionPayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public byte[] print(String data, int timeOut) {
		try {
			byte[] bb = data.getBytes("GBK");
			byte[] b = new byte[bb.length + 4];
			b[0] = 0x08;
			b[1] = 0x01;
			Convert.short2ByteArray((short) bb.length, b, 2);
			// Convert.short2ByteArray((short) 1264, b, 2);
			System.arraycopy(bb, 0, b, 4, bb.length);
			Log.i("iii", "----" + Convert.bcd2Str(b));
			send(b, timeOut);
			return recv(timeOut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public byte[] print(String data, byte[] bitmap, int timeOut) {
		try {
			byte[] bb = data.getBytes("GBK");
			byte[] b = new byte[bb.length + 4 + bitmap.length];
			b[0] = 0x08;
			b[1] = 0x01;
			Convert.short2ByteArray((short) (bb.length + bitmap.length), b, 2);
			System.arraycopy(bb, 0, b, 4, bb.length);
			System.arraycopy(bitmap, 0, b, 4 + bb.length, bitmap.length);
			Log.i("iii", "----" + Convert.bcd2Str(b));
			send(b, 30);
			return recv(30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private class Message {
		public byte[] header;
		public byte[] bady;
		public byte[] tail;
	};

	private class FrameData {
		byte[] data;
		boolean hasNewxtFrame;
	};

	private UnionPay(Context context) {
		this.context = context;
		initExceptionCodeToMsgMapping();
		AppDebug.DEBUG_D = true;
		AppDebug.DEBUG_E = true;
		AppDebug.DEBUG_I = true;
		AppDebug.DEBUG_V = true;
		AppDebug.DEBUG_W = true;
	}

	private void initExceptionCodeToMsgMapping() {
		CommonLibInit.init(context);
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(UnionPayException.ERR_GENERAL, "通用错误");
		map.put(UnionPayException.ERR_ARG, "参数错误");
		map.put(UnionPayException.ERR_PROTO_CONN, "连接失败");
		map.put(UnionPayException.ERR_PROTO_SEND, "发送失败");
		map.put(UnionPayException.ERR_PROTO_RECV, "接收失败");
		map.put(UnionPayException.ERR_PROTO_PACKAGE_TOO_LONG, "数据包太大");
		map.put(UnionPayException.ERR_PROTO_NO_ENOUGH_DATA, "未收到足够数据");
		map.put(UnionPayException.ERR_PROTO_CHECKSUM, "数据校验错误");
		map.put(UnionPayException.ERR_PROTO_DATA_FORMAT, "数据格式错误");
		map.put(UnionPayException.ERR_PROTO_SYNC, "同步失败");
		map.put(UnionPayException.ERR_PROTO_NOT_CONFIRM, "接收确认帧失败");
		map.put(UnionPayException.ERR_PROTO_CANCEL, "取消操作");
		map.put(UnionPayException.ERR_PROTO_NODATA, "未收到数据");
		ExceptionCodeParser.getInstance().setExceptionCodeToMsgMapping(map);
	}

	private UnionPayException commExceptionToUnionPayException(CommException ce) {
		switch (ce.getExceptionCode()) {
		case CommException.COMM_ERR_CONNECT:
			return new UnionPayException(UnionPayException.ERR_PROTO_CONN);
		case CommException.COMM_ERR_SEND:
			return new UnionPayException(UnionPayException.ERR_PROTO_SEND);
		case CommException.COMM_ERR_RECV:
			return new UnionPayException(UnionPayException.ERR_PROTO_RECV);
		case CommException.COMM_ERR_CANCEL:
			return new UnionPayException(UnionPayException.ERR_PROTO_CANCEL);
		default:
			return new UnionPayException(UnionPayException.ERR_GENERAL);
		}
	}

	private void resetProto() {
		// currentPackNum = MSG_PAGE_NO_BASE;
		currentFrameNum = 0;
	}

	private byte lrc(byte[] data, int offset, int len) {
		byte lrc = 0;
		for (int i = 0; i < len; i++) {
			lrc ^= data[i + offset];
		}
		return lrc;
	}

	private void recvMsg(Message msg, int timeoutMs) throws UnionPayException,
			IOException, CommException {

		AppDebug.d(TAG,
				"======================Recv Frame======================");

		comm.setTransTimeout(timeoutMs);

		msg.header = comm.recv(9);
		int recvLen = msg.header.length;
		if (recvLen == 0) {
			AppDebug.d(TAG, "not recv data");
			throw new UnionPayException(UnionPayException.ERR_PROTO_NODATA);
		} else if (recvLen != 9) {
			AppDebug.e(TAG, "recv 9 bytes header failed" + recvLen);
			throw new UnionPayException(
					UnionPayException.ERR_PROTO_NO_ENOUGH_DATA);
		}

		AppDebug.d(TAG, "recv header:" + Convert.bcd2Str(msg.header));

		if (msg.header[0] != MSG_STX_1 || msg.header[1] != MSG_STX_2) {
			AppDebug.e(TAG, "recv first byte not MSG_STX");
			throw new UnionPayException(UnionPayException.ERR_PROTO_DATA_FORMAT);
		}

		byte lrc = lrc(msg.header, 2, 7);

		int dLen = Convert.byteArray2Short(msg.header, 7);
		AppDebug.d(TAG, "data len is " + dLen);

		msg.bady = comm.recv(dLen);

		if (msg.bady.length != dLen) {
			AppDebug.e(TAG, "recv data len " + (dLen) + "not enough");
			throw new UnionPayException(
					UnionPayException.ERR_PROTO_NO_ENOUGH_DATA);
		}

		AppDebug.d(TAG, "recv data:" + Convert.bcd2Str(msg.bady));

		lrc ^= lrc(msg.bady, 0, dLen);

		msg.tail = comm.recv(3);
		if (msg.tail.length != 3) {
			AppDebug.e(TAG, "recv tail  failed!");
			throw new UnionPayException(
					UnionPayException.ERR_PROTO_NO_ENOUGH_DATA);
		}

		AppDebug.d(TAG, "recv tail:" + Convert.bcd2Str(msg.tail));

		if (msg.tail[1] != MSG_ETX_END_1 && msg.tail[2] != MSG_ETX_END_2) {
			AppDebug.e(TAG, "recv tail[0] not MSG_ETX_END or MSG_ETX_NEXT");
			throw new UnionPayException(UnionPayException.ERR_PROTO_DATA_FORMAT);
		}

		if (lrc != msg.tail[0]) {
			AppDebug.e(TAG, "lrc check failed!" + lrc);
			throw new UnionPayException(UnionPayException.ERR_PROTO_CHECKSUM);
		}

		AppDebug.d(TAG, "=========================End=========================");
	}

	private byte recvNACK(int usFrNoIn, int retryCount, int timeoutMs)
			throws IOException, UnionPayException {
		AppDebug.d(TAG, "try to recv confirm frame: " + usFrNoIn);
		Message msg = new Message();
		try {
			recvMsg(msg, timeoutMs);
			int peerPageNo = msg.header[6] & 0xff;
			int peerFrameNo = msg.header[2] & 0xff;
			if (peerPageNo == currentPackNum)// 确认包的序号
			{
				if (peerFrameNo == currentFrameNum)// 确认帧序�?
				{
					if (msg.bady.length == 0 && msg.header[5] == PACKAGE_ACK) {
						return PACKAGE_ACK;
					} else if (msg.bady.length == 0
							&& msg.header[5] == PACKAGE_NAK) {
						return PACKAGE_NAK;
					}
					return PACKAGE_FALSE;
				}
				return PACKAGE_FALSE;
			}
		} catch (CommException e) {
			e.printStackTrace();
			throw new UnionPayException(UnionPayException.ERR_PROTO_NOT_CONFIRM);
		} catch (UnionPayException e) {
			if (e.getExceptionCode() == UnionPayException.ERR_PROTO_NODATA) {
				throw new UnionPayException(
						UnionPayException.ERR_PROTO_NOT_CONFIRM);
			} else {
				e.printStackTrace();
				throw e;
			}
		}
		return PACKAGE_FALSE;
	}

	private void sendConFirmeFrame(int framNum, byte confiremFramCode)
			throws IOException, CommException {
		AppDebug.d(TAG, "send confirm frame: " + framNum);

		byte[] sendBuffer = new byte[12];

		sendBuffer[0] = 0x55;
		sendBuffer[1] = (byte) 0xAA;
		sendBuffer[2] = (byte) framNum;
		sendBuffer[3] = 0;
		sendBuffer[4] = 0x04;
		sendBuffer[5] = confiremFramCode;
		sendBuffer[6] = (byte) currentPackNum;
		sendBuffer[7] = 0;
		sendBuffer[8] = 0;
		sendBuffer[9] = lrc(sendBuffer, 2, 7);
		sendBuffer[10] = (byte) 0xCC;
		sendBuffer[11] = 0x33;
		AppDebug.d(TAG, "frame data: " + Convert.bcd2Str(sendBuffer));
		comm.send(sendBuffer);
	}

	private void recvFrame(FrameData frameData, int timeoutMs)
			throws UnionPayException, IOException {
		AppDebug.d(TAG, "try to recv package: " + currentPackNum + " frame: "
				+ currentFrameNum);

		Message msg = new Message();

		int reTryCount = DEFAULT_RETRY_COUNT;

		do {
			try {
				recvMsg(msg, timeoutMs);
				int peerPackNum = msg.header[6] & 0xff;
				int peerFrameNum = msg.header[2] & 0xff;
				AppDebug.d(TAG, "peerPackNum = " + peerPackNum);
				AppDebug.d(TAG, "peerFrameNum = " + peerFrameNum);
				if (peerPackNum != currentPackNum
						|| ((peerFrameNum != currentFrameNum) && (peerFrameNum != currentFrameNum - 1))) {
					// 已经失步，包�?帧序失步，数据接收无法正常再继续接收，终止接�?
					AppDebug.w(TAG, "outof step,stop interaction");
					throw new UnionPayException(
							UnionPayException.ERR_PROTO_SYNC);
				}

				if (peerFrameNum == currentFrameNum - 1)// 对端可能没有及时收到发�?的ACK导致重发了该数据帧，丢弃继续接收
				{
					// 收到的是前一帧，丢弃，回ACK
					AppDebug.w(TAG,
							"previous frame, discard, send ACK and continue to recv...");
					// 回ACK
					sendConFirmeFrame(currentFrameNum, PACKAGE_ACK);
					continue;
				}

				// 回ACK
				AppDebug.d(TAG, "recv package: " + currentPackNum + " frame: "
						+ currentFrameNum + " sucess");
				sendConFirmeFrame(currentFrameNum, PACKAGE_ACK);
				frameData.data = msg.bady;
				if ((msg.header[3] &= 0x80) != 0) {
					AppDebug.d(TAG,
							"has next frame, continue to recv next frame");
					frameData.hasNewxtFrame = true;
					AppDebug.i(TAG, "true");
				} else {
					AppDebug.d(TAG, "this is the end frame");
					frameData.hasNewxtFrame = false;
				}
				return;

			} catch (UnionPayException e) {
				if (e.getExceptionCode() == UnionPayException.ERR_PROTO_CHECKSUM // 校验错误，可能传输中丢包�?
						|| e.getExceptionCode() == UnionPayException.ERR_PROTO_DATA_FORMAT) // 数据格式错误
				{
					AppDebug.e(TAG, "LRC error,stop interaction");
					return;
				} else if (e.getExceptionCode() == UnionPayException.ERR_PROTO_NODATA) {
					AppDebug.d(TAG, "sent data again");
					continue;
				} else {
					e.printStackTrace();
					AppDebug.e(TAG, "UnionPayException: " + e.getMessage());
					throw e;
				}
			} catch (CommException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new UnionPayException(UnionPayException.ERR_PROTO_RECV);
			}

		} while (reTryCount-- > 1);
		if (reTryCount < 1) {
			AppDebug.e(TAG, "retry recv frame fail");
			throw new UnionPayException(UnionPayException.ERR_PROTO_RECV);
		}
	}

	private int sendFrame(byte[] data, boolean isLasePage, int timeoutMs,
			int sendlength) throws UnionPayException, CommException,
			IOException {

		AppDebug.d(TAG, "try to send package " + currentPackNum + " frame "
				+ currentFrameNum);
		byte[] sendBuffer = new byte[data.length + 12];
		sendBuffer[0] = 0x55;
		sendBuffer[1] = (byte) 0xAA;
		sendBuffer[2] = (byte) currentFrameNum;
		byte[] s = new byte[2];
		Convert.short2ByteArray((short) (data.length + 4), s, 0);
		sendBuffer[3] = s[0];
		sendBuffer[4] = s[1];
		if (!isLasePage) {
			sendBuffer[3] |= 0x80;
		}
		sendBuffer[5] = PACKAGE_REQ;
		sendBuffer[6] = (byte) currentPackNum;
		Convert.short2ByteArray((short) (sendlength), s, 0);
		sendBuffer[7] = s[0];
		sendBuffer[8] = s[1];
		System.arraycopy(data, 0, sendBuffer, 9, data.length);
		sendBuffer[data.length + 9] = lrc(sendBuffer, 2, data.length + 7);
		sendBuffer[data.length + 10] = (byte) 0xCC;
		sendBuffer[data.length + 11] = 0x33;
		AppDebug.d(TAG, "current frame data:" + Convert.bcd2Str(sendBuffer));
		int reTryCount = DEFAULT_RETRY_COUNT;
		do {
			try {
				comm.reset();
				if (reTryCount < DEFAULT_RETRY_COUNT) {
					AppDebug.w(TAG, "re-sending data... countdown: "
							+ (reTryCount + 1));
				} else {
					AppDebug.d(TAG, "sending data...");
				}

				comm.send(sendBuffer);
				AppDebug.d(TAG, "waiting for resp ACK/NAK...");
				int ret = recvNACK(currentFrameNum, DEFAULT_RETRY_COUNT,
						timeoutMs);

				if (ret == PACKAGE_ACK) {
					AppDebug.d(TAG, "has recved ACK, send package: "
							+ currentPackNum + " frame: " + currentFrameNum
							+ " sucess");
					return 0;
				} else if (ret == PACKAGE_NAK)// NAK 取消交互
				{
					AppDebug.d(TAG, "recved NAK,stop interaction");
					return -1;
				} else if (ret == PACKAGE_FALSE) {
					return -2;
				}

			} catch (UnionPayException e) {// 超时重发
				AppDebug.w(TAG, "UnionPayException: " + e.getLocalizedMessage());
				if (e.getExceptionCode() == UnionPayException.ERR_PROTO_NOT_CONFIRM) {
					continue;
				}
				e.printStackTrace();
			} catch (IOException e) {
				AppDebug.w(TAG, "IOException: " + e.getLocalizedMessage()
						+ "try resend this frame...");
				e.printStackTrace();
			}
		} while (reTryCount-- > 1);

		if (reTryCount < 1) {
			AppDebug.e(TAG, "send frame reach max retry counts,send package: "
					+ currentFrameNum + " frame: " + currentFrameNum + " fail");
			throw new UnionPayException(UnionPayException.ERR_PROTO_SEND);
		}
		throw new UnionPayException(UnionPayException.ERR_PROTO_SEND);

	}

	private void sendImpl(byte[] sendData, int timeoutMs) throws CommException,
			UnionPayException, IOException {
		// TODO Auto-generated method stub
		comm.connect();
		if ((sendData.length > MAX_PACKAGE_SIZE && sendData[0] != 0x08 && sendData[1] != 0x01)
				|| (sendData.length > MAX_PACKAGE_SIZE_PRN)) {
			throw new UnionPayException(
					UnionPayException.ERR_PROTO_PACKAGE_TOO_LONG);
		}
		currentFrameNum = 0;
		int index = 0;
		boolean isLastFrame = true;
		byte[] temp;
		while (index < sendData.length) {
			if (index + MAX_FRAME_SIZE < sendData.length) {
				temp = new byte[MAX_FRAME_SIZE];
				System.arraycopy(sendData, index, temp, 0, MAX_FRAME_SIZE);
				index += MAX_FRAME_SIZE;
				isLastFrame = false;
			} else {
				AppDebug.d(TAG, "last remain bytes = "
						+ (sendData.length - index));
				temp = new byte[sendData.length - index];
				System.arraycopy(sendData, index, temp, 0, sendData.length
						- index);
				index += (sendData.length - index);
				isLastFrame = true;
			}

			try {
				AppDebug.d(TAG, "temp.length " + temp.length);
				int ret = sendFrame(temp, isLastFrame, timeoutMs,
						sendData.length);
				if (ret == 0)// send one frame sucess
				{
					if (isLastFrame) {
						AppDebug.d(TAG, "send package: " + currentPackNum
								+ " sucess");
						currentFrameNum = 0;
						return;
					} else {
						currentFrameNum++;
						continue;
					}
				} else if (ret < 0) {
					AppDebug.d(TAG, "resv NAK,stop the whole interaction");
					index = 0;
					currentFrameNum = 0;
					return;
				} else if (ret < -1) {
					AppDebug.e(TAG, "not sysn,stop the whole interaction");
					index = 0;
					currentFrameNum = 0;
					return;
				}
			} catch (UnionPayException e) {
				e.printStackTrace();
				AppDebug.e(TAG, "UnionPayException: " + e.getMessage());
				// comm.disconnect();
				throw e;
			}
		}

	}

	private byte[] recvImpl(int timeoutMs) throws UnionPayException,
			CommException, IOException {
		// TODO Auto-generated method stub
		AppDebug.d(TAG, "begin to recv data");

		FrameData data = new FrameData();

		data.hasNewxtFrame = true;
		currentFrameNum = 0;
		String dataStr = "";

		while (true) {
			try {
				if (data.hasNewxtFrame) {
					recvFrame(data, timeoutMs);
					dataStr += Convert.bcd2Str(data.data);
					currentFrameNum++;
					continue;
				}
				currentPackNum++;// 接收成功，当前包序号�?
				if (currentPackNum > 255) {
					currentPackNum = 1;
				}
				AppDebug.d(TAG,
						"++++++++++++++++++Recved Package++++++++++++++++++++");
				AppDebug.d(TAG, "total len: " + dataStr.length() / 2);
				AppDebug.d(TAG, "recved data : " + dataStr);
				AppDebug.d(TAG,
						"+++++++++++++++++++++++++++++++++++++++++++++++++++++");

				return Convert.str2Bcd(dataStr);

			} catch (UnionPayException e) {
				e.printStackTrace();
				AppDebug.e(TAG, "UnionPayException: " + e.getMessage());
				comm.disconnect();
				throw e;

			} catch (IOException e) {
				e.printStackTrace();
				AppDebug.e(TAG, "IOException: " + e.getMessage());
				comm.disconnect();
				throw e;
			}
		}

	}

}
