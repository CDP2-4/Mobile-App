package com.cdp2.schemi.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/*
 * 서버와의 통신을 담당하는 클래스 입니다.
 * 이 클래스는 Thread를 상속받은 클래스 입니다.
 * */
public class HttpClass extends Thread {
	private String TAG = "HttpClass";
	private String mUploadURL_V2 = "http://schemi.0za.kr/app/app_api.php";


	int mOrder;										//요청할 명령번호 입니다.
	Handler mHandler;								//서버 응답후 호출한 클래스의 Handler입니다.
	ArrayList<String> mParams;					//서버 호출시 Parameter가 필요하면 추가합니다.
	HashMap<String, String> mParams_map;	//서버 호출시 Parameter가 필요하면 추가합니다.	포스트 방식에서 사용
	Context mContext;		//Context

	ArrayList<String>	mFileList;					//여러개의 이미지 파일을 업로드할때 사용하는 놈.!!



	public static final int ACTION_01 = 301;
	public static final int ACTION_02 = 302;
	public static final int ACTION_03 = 303;
	public static final int ACTION_04 = 304;
	public static final int ACTION_05 = 305;
	public static final int ACTION_06 = 306;
	public static final int ACTION_07 = 307;
	public static final int ACTION_08 = 308;

	/** 파일전송도 같이하는 녀석.*/
	public static final int ACTION_11 = 311;
	public static final int ACTION_12 = 312;


	public static final int DISCONNECTION_NETWORK = 999;		//네트워크가 연결되지 않았을때


	public HttpClass(Context _context, int _order, Handler _handler, HashMap<String, String> _params){
		mOrder = _order;
		mHandler = _handler;
		mParams_map = _params;
		mContext = _context;
	}

	public HttpClass(Context _context, int _order, Handler _handler, ArrayList<String> _fileList, HashMap<String, String> _params){
		mContext = _context;
		mOrder = _order;
		mHandler = _handler;
		mParams_map = _params;
		mFileList = _fileList;
	}



	//각각의 명령번호마다 URL를 만든다음 getDataFromServer() 메소드를 호출합니다.
	public void run(){
		switch(mOrder){
			case ACTION_01:
			case ACTION_02:
			case ACTION_03:
			case ACTION_04:
			case ACTION_05:
			case ACTION_06:
			case ACTION_07:
			case ACTION_08:

				httpMyInfoUpload_MultiImage( mUploadURL_V2 );
				break;
		}
	}



	private String lineEnd = "\r\n";
	private String twoHyphens = "--";
	private String boundary="*****";

	private FileInputStream mFileInputStream = null;
	private URL connectUrl = null;

	/**
	 * 여러개의 이미지를 서버에 전송할때 사용
	 * ArrayList에 저장된 파일만큼
	 * */

	DataOutputStream mDos = null;

	private void httpMyInfoUpload_MultiImage(String _url) {
		KjyLog.i(TAG, "httpMyInfoUpload_MultiImage() / _url : "+_url);

		try {
			connectUrl = new URL(_url);

			// open connection
			HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",	"multipart/form-data;boundary=" + boundary);

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"), true);


			for(Entry<String, String> entry : mParams_map.entrySet()){
				KjyLog.i(TAG, entry.getKey() +" : "+entry.getValue());

				pw.write(twoHyphens + boundary + lineEnd);
				pw.write("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + lineEnd);	//key
				pw.write(lineEnd);
				pw.write(entry.getValue());	//value
				pw.write(lineEnd);
			}

			pw.flush();

			if(mFileList != null && mFileList.size() > 0) {
				sendImg(conn, mDos, pw, "img", mFileList);
			}


			String ch;
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuffer b = new StringBuffer();

			while( (ch = br.readLine()) != null){
				b.append(ch);
			}

			String s = b.toString();
			KjyLog.i(TAG, "result = " + s);

			br.close();
			is.close();


			pw.close();
			conn.disconnect();

			//명령번호, 서버로 부터 받은 문자열을 조합하여 호출한 핸들러에게 전달해 줍니다.
			if(mHandler != null){
				Message msg = new Message();
				msg.what = mOrder;
				msg.obj = s;
				mHandler.sendMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}



	private void sendImg(HttpURLConnection conn, DataOutputStream dos, PrintWriter pw, String _key, ArrayList<String> _imgArr) {
		try {
			boolean _isImg = true;

			if(_isImg){

				for(int i=0 ; i<_imgArr.size() ; i++){
					if( _imgArr.get(i).equals("") || _imgArr.get(i) == null) {
						continue;
					}

					File f = new File(_imgArr.get(i));
					if(f.exists() && f.length() > 0){
						dos = new DataOutputStream(conn.getOutputStream());

						dos.writeBytes(twoHyphens + boundary + lineEnd);
						dos.writeBytes("Content-Disposition: form-data; name=\""+_key+""+i+"\";filename=\""	+ URLEncoder.encode(_imgArr.get(i), "UTF-8") + "\"" + lineEnd);
						dos.writeBytes(lineEnd);
						KjyLog.i(TAG, _key+""+i + " : " + _imgArr.get(i));

						mFileInputStream = new FileInputStream(_imgArr.get(i));

						int bytesAvailable = mFileInputStream.available();
						int maxBufferSize = 1024;
						int bufferSize = Math.min(bytesAvailable, maxBufferSize);
						byte[] buffer = new byte[bufferSize];
						int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

						KjyLog.i(TAG, "httpMyInfoUpload() / image byte is " + bytesRead);
						// read image

						while (bytesRead > 0) {
							dos.write(buffer, 0, bufferSize);
							bytesAvailable = mFileInputStream.available();
							bufferSize = Math.min(bytesAvailable, maxBufferSize);
							bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
						}

						dos.writeBytes(lineEnd);
						//					dos.flush(); // finish upload...
						//=====================원본파일 전송 완료========================//
					}else {
						KjyLog.i(TAG, "파일 없음  / " + _imgArr.get(i));

						pw.write(twoHyphens + boundary + lineEnd);
						pw.write("Content-Disposition: form-data; name=\"" + _key+""+i+ "\"" + lineEnd);	//key
						pw.write(lineEnd);
						pw.write(_imgArr.get(i));	//value
						pw.write(lineEnd);
					}
				}

				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				KjyLog.i(TAG, "httpMyInfoUpload() / File is written");
				mFileInputStream.close();
				dos.close();
			}
		}catch (Exception e) {
			KjyLog.e(TAG, e);
		}
	}


}

