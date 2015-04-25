package com.handsome.activities;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends Activity{
	private EditText uname=null;
	private String username=null;
	private EditText pwd=null;
	private String password = null;
	private Button bt_login=null;
	private Button bt_regist=null;
	private static final String NAMESPACE ="http://service.handsome.com/";
	private static String URL = "http://172.16.1.68:8080/NoteEvery/UserServicePort?wsdl";
	private static final String ADD_METHOD_NAME = "addUser";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		this.uname=(EditText) super.findViewById(R.id.uname);
		this.pwd=(EditText) super.findViewById(R.id.pwd);
		this.bt_login=(Button) super.findViewById(R.id.bt_login);
		this.bt_regist=(Button) super.findViewById(R.id.bt_regist);
				
		
		bt_regist.setOnClickListener(new OnClickListener(){	//点击登录按钮触发操作
			@Override
			public void onClick(View v) {
				username =uname.getText().toString().trim();			
				password =pwd.getText().toString().trim();	
				
				SoapObject soapObject = new SoapObject(NAMESPACE, ADD_METHOD_NAME);
				System.out.println(soapObject.getName()+"**********");
				soapObject.addProperty("arg0", username);//传入用户名
				soapObject.addProperty("arg1", password);//传入密码
				
				final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);// 版本
				envelope.bodyOut = soapObject ;
				envelope.dotNet = false ;
				envelope.setOutputSoapObject(soapObject) ;
				final HttpTransportSE trans = new HttpTransportSE(URL) ;
				trans.debug = true ;	// 使用调试功能
				
				new Thread(new Runnable() {	//分配线程
					@Override
					public void run() {
						try {
							trans.call(null, envelope) ;
						} catch (IOException e) {
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							e.printStackTrace();
						}
						SoapObject result = (SoapObject) envelope.bodyIn;
						Message message=new Message();  
						if(result.getProperty(0).toString().equals("1"))	//注册成功则返回1，否则返回0
							message.what=1;
						else
							message.what=0;
						mHandler.sendMessage(message); ;
					} }).start();
			}
		});
		bt_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(RegistActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	
	
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			System.out.println(msg.what+"??????????");
			switch(msg.what)  
			{  
			case 1:  
				Toast.makeText(getApplication(), "注册成功,请登录", Toast.LENGTH_LONG).show(); 
				Intent intent =new Intent(RegistActivity.this,LoginActivity.class);
				startActivity(intent);
				break;  
			default:  
				Toast.makeText(getApplication(), "注册失败，请重新注册！", Toast.LENGTH_LONG).show();        
			}  
			super.handleMessage(msg);  
		}  
	};
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
