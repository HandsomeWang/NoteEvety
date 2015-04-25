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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EditActivity extends Activity{

	private static final String NAMESPACE ="http://service.handsome.com/";
	private static String URL = "http://172.16.1.68:8080/NoteEvery/NoteServicePort?wsdl";
	private static final String UPDATE_METHOD_NAME = "updateNote";
	private static final String DELETE_METHOD_NAME = "deleteNote";

	private EditText myEditText=null;
	private Button btn_sure=null;
	private Button btn_cancel=null;
	private Button btn_delete=null;
	private static int NID;
	private static int UID;
	private static String CONTENT;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//扩充视图
		setContentView(R.layout.edit);
		//获取对UI控件的引用
		myEditText = (EditText ) findViewById(R.id.myEditText );
		btn_sure = (Button ) findViewById(R.id.btn_sure );
		btn_cancel = (Button ) findViewById(R.id.btn_cancel );
		btn_delete = (Button ) findViewById(R.id.btn_delete );
		Bundle bundle = this.getIntent().getExtras();  
		NID = bundle.getInt("NID"); 
		UID = bundle.getInt("UID"); 
		CONTENT = bundle.getString("CONTENT"); 
		myEditText.setText(CONTENT);
		
		btn_cancel.setOnClickListener(new OnClickListener(){	//点击添加按钮触发操作
			@Override
			public void onClick(View v) {
				finish();
			}}
			);
		
		btn_sure.setOnClickListener(new OnClickListener(){	//点击添加按钮触发操作
			@Override
			public void onClick(View v) {
				String newcontent =myEditText.getText().toString();		
				if(newcontent.isEmpty())
					Toast.makeText(getApplication(), "您还没有输入内容~", Toast.LENGTH_LONG).show();
				else	
					UpdateNote(newcontent);		
			}

			private void UpdateNote(String newcontent) {
				// TODO Auto-generated method stub
				SoapObject soapObject = new SoapObject(NAMESPACE, UPDATE_METHOD_NAME);
				soapObject.addProperty("arg0", NID);//传入用户id
				soapObject.addProperty("arg1", newcontent);//传入新便笺内容
				System.out.println(NID+"+++++++++++++"+newcontent);
				
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
						if(!result.getProperty(0).toString().equals("0")){	//如果添加失败则返回0
							message.what=1;
						}
						else
							message.what=0;
						mHandler.sendMessage(message); ;
					} }).start();
				
				
				
				myEditText.setText(null);
			}
		});
		
		btn_delete.setOnClickListener(new OnClickListener(){	//点击删除按钮触发操作
			@Override
			public void onClick(View v) {
				
				SoapObject soapObject = new SoapObject(NAMESPACE, DELETE_METHOD_NAME);
				soapObject.addProperty("arg0", NID);//传入用户id
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
						if(!result.getProperty(0).toString().equals("0")){	//如果添加失败则返回0
							message.what=2;
						}
						else
							message.what=0;
						mHandler.sendMessage(message); ;
					} }).start();

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
				Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_LONG).show(); 
				Intent intent =new Intent(EditActivity.this,NoteActivity.class);
				Bundle bundle = new Bundle();   
				bundle.putInt("UID", UID);  
				intent.putExtras(bundle); 
				startActivity(intent);
				break;  
			case 2:  
				Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_LONG).show(); 
				Intent intent1 =new Intent(EditActivity.this,NoteActivity.class);
				Bundle bundle1 = new Bundle();   
				bundle1.putInt("UID", UID);  
				intent1.putExtras(bundle1); 
				startActivity(intent1);
				break;  
			default:  
				Toast.makeText(getApplication(), "失败", Toast.LENGTH_LONG).show();        
			}  
			super.handleMessage(msg);  
		}  
	};
}
