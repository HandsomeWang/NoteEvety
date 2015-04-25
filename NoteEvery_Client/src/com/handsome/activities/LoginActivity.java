package com.handsome.activities;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText uname=null;
	private String username=null;
	private EditText pwd=null;
	private String password = null;
	private Button bt_login=null;
	private Button bt_regist=null;
	private static final String NAMESPACE ="http://service.handsome.com/";
	private static String URL = "http://172.16.1.68:8080/NoteEvery/UserServicePort?wsdl";
	private static final String CHECK_METHOD_NAME = "checkIfLogin";
	private static int UID;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		sharedPreferences = this.getSharedPreferences("userInfo",Context.MODE_WORLD_READABLE);
		this.uname=(EditText) super.findViewById(R.id.uname);
		this.pwd=(EditText) super.findViewById(R.id.pwd);
		this.bt_login=(Button) super.findViewById(R.id.bt_login);
		this.bt_regist=(Button) super.findViewById(R.id.bt_regist);
				
//		if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)) { 
//			UID=sharedPreferences.getInt("UID",0);
//			System.out.println(UID+"------------uid");
//			Intent intent =new Intent(LoginActivity.this,NoteActivity.class);
//			Bundle bundle = new Bundle();   
//			bundle.putInt("UID", UID);  
//			intent.putExtras(bundle); 
//			startActivity(intent);             
//			} 
//		else {  
//			 setContentView(R.layout.activity_login);  
//			 initView();  
//		 }  

		
		bt_login.setOnClickListener(new OnClickListener(){	//点击登录按钮触发操作
			@Override
			public void onClick(View v) {
				username =uname.getText().toString().trim();			
				password =pwd.getText().toString().trim();	
				
				SoapObject soapObject = new SoapObject(NAMESPACE, CHECK_METHOD_NAME);
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
						if(!result.getProperty(0).toString().equals("0")){	//如果查询用户名、密码不匹配则返回0
							message.what=1;
							UID = Integer.parseInt(result.getProperty(0).toString()) ;
					//		System.out.println(UID+"is uid ");
						}
						else
							message.what=0;
						mHandler.sendMessage(message); ;
					} }).start();
			}
		});
		bt_regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(LoginActivity.this,RegistActivity.class);
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
				Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_LONG).show(); 
				
//				Editor editor = sharedPreferences.edit();  
//				editor.putString("username", username);  
//				editor.putString("password", password);  
//				editor.putInt("UID", UID);  
//				editor.putBoolean("AUTO_ISCHECK", false).commit();  
//				editor.commit(); 

				
				
				
				Intent intent =new Intent(LoginActivity.this,NoteActivity.class);
				Bundle bundle = new Bundle();   
				bundle.putInt("UID", UID);  
				intent.putExtras(bundle); 
				startActivity(intent);
				break;  
			default:  
				Toast.makeText(getApplication(), "登录失败", Toast.LENGTH_LONG).show();   
	//以下需要注释	！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！		
//				Intent intent1 =new Intent(LoginActivity.this,NoteActivity.class);
//				Bundle bundle1 = new Bundle();   
//				bundle1.putInt("UID", 1);  
//				intent1.putExtras(bundle1); 
//				startActivity(intent1);
	//以上需要注释！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
			}  
			super.handleMessage(msg);  
		}  
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
