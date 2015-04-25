package com.handsome.activities;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import com.handsome.bean.Note;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NoteActivity extends Activity {
	
	private EditText myEditText=null;
	private ListView myListView=null;
	private Button btn_add=null;
	private Button btn_f=null;
	
	private static final String NAMESPACE ="http://service.handsome.com/";
	private static String URL = "http://172.16.1.68:8080/NoteEvery/NoteServicePort?wsdl";
	private static final String LIST_METHOD_NAME = "listNoteByUid";
	private static final String ADD_METHOD_NAME = "addNote";
	private static int UID;
	private static int NID;
	private static String CONTENT;
	private ArrayList<String> liststring=null;
	private ArrayList<Note> listitem=null;
	private ArrayAdapter<String> myadp=null;
	
	protected void AddNote(String newcontent){
		SoapObject soapObject = new SoapObject(NAMESPACE, ADD_METHOD_NAME);
		soapObject.addProperty("arg0", UID);//传入用户id
		soapObject.addProperty("arg1", newcontent);//传入新便笺内容
		
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
		
		
		
		myEditText.setText(null);
		
	}
	
	protected void listAllNote(){

		liststring.clear();
		SoapObject soapObject = new SoapObject(NAMESPACE, LIST_METHOD_NAME);
		soapObject.addProperty("arg0", UID);//传入用户名
		
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
				
				SoapObject result;
				
//				try {
					result =  (SoapObject)envelope.bodyIn;
//					System.out.println(envelope.getResponse());
//					System.out.println(result.getPropertyCount());				
                for(int   i=0; i <result.getPropertyCount(); i++){ 
                    SoapObject soapChilds = (SoapObject)result.getProperty(i); 
                 //   System.out.println(soapChilds+"-----------soapChilds");
                    Note n = new Note();
                    n.setNcontent(soapChilds.getProperty(0).toString());
                    
                    String dateString = soapChilds.getProperty(1).toString();  
                    try  
                    {  
                       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");  
                        Date date = sdf.parse(dateString);                
                        n.setNdate(date);
                    }  
                    catch (ParseException e)  
                    {  
            //            System.out.println(e.getMessage());  
                    }       
                    n.setNid(Integer.parseInt(soapChilds.getProperty(2).toString()));
                    n.setUid(Integer.parseInt(soapChilds.getProperty(3).toString()));
//                    System.out.println(n.getNcontent()+"-------------------content!");
//                    System.out.println(n.getNid()+"-------------------getNid!");
//                    System.out.println(n.getUid()+"-------------------getUid!");
//                    System.out.println(n.getNdate()+"-------------------getNdate!");
                    
                    java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String s = format1.format(n.getNdate());
//                    System.out.println(s+"-------------------getNdate!");
                    
                    liststring.add(" "+"编号:"+" "+n.getNid()+" "+'\n'+" "+"时间:"+" "+s+" "+'\n'+" "+n.getNcontent());
         //           listitem.add(n);
            }
//				} catch (SoapFault e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
				Message message=new Message();  
				if(liststring!=null){
					message.what=1;
				}
				else
					message.what=0;
				mHandler.sendMessage(message); 
				} }).start();
		// 创建ArrayAdapter,以便将数组绑定到ListView
		myadp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,liststring);
		
	}
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//扩充视图
		setContentView(R.layout.mynote);
		//获取对UI控件的引用
		myListView = (ListView) findViewById(R.id.myListView);
		myEditText = (EditText ) findViewById(R.id.myEditText );
		btn_add = (Button ) findViewById(R.id.btn_add );
		btn_f = (Button ) findViewById(R.id.btn_f );
		
		Bundle bundle = this.getIntent().getExtras();  
		UID = bundle.getInt("UID"); 
		//创建待办事项的表ArrayList
		liststring = new ArrayList<String>();
		listAllNote();
		
//		myEditText.setOnClickListener(new OnClickListener(){	//点击编辑框触发操作
//			@Override
//			public void onClick(View v) {
//				myEditText.setText(null);
//			}
//			});
		myListView.setOnItemClickListener(new OnItemClickListener(){//点击listview每一个条目触发操作

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String str=liststring.get(arg2);
				String[] words = str.split(" ");
//				for(int i=0;i<words.length;i++){
//					System.out.println(i+"--------"+words[i]);
//				} 
				NID=Integer.parseInt(words[2]);
				CONTENT=words[8];
//				System.out.println(NID+"------nid");
				Intent intent =new Intent(NoteActivity.this,EditActivity.class);
				Bundle bundle = new Bundle();     
				bundle.putInt("NID", NID);  
				bundle.putInt("UID", UID);  
				bundle.putString("CONTENT", CONTENT);   
				intent.putExtras(bundle); 
				startActivity(intent);
			}
			
		});
		
		btn_add.setOnClickListener(new OnClickListener(){	//点击添加按钮触发操作
			@Override
			public void onClick(View v) {
				String newcontent =myEditText.getText().toString();		
				if(newcontent.isEmpty())
					Toast.makeText(getApplication(), "您还没有输入内容~", Toast.LENGTH_LONG).show();
				else	
					AddNote(newcontent);	
					 
				
			}
		});
		btn_f.setOnClickListener(new OnClickListener(){	//点击刷新按钮执行操作
			@Override
			public void onClick(View v) {		
				listAllNote();
				myListView.setAdapter(myadp);
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
				//Toast.makeText(getApplication(), "查询成功", Toast.LENGTH_LONG).show(); 
				//将ArrayAdapter绑定到ListView
				myListView.setAdapter(myadp);
				break;  
			case 2:  
				listAllNote();
				myListView.setAdapter(myadp);
				Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_LONG).show(); 
				break;  
			default:  
				Toast.makeText(getApplication(), "失败", Toast.LENGTH_LONG).show();        
			}  
			super.handleMessage(msg);  
		}  
	};

	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if (keyCode == KeyEvent.KEYCODE_BACK) {  
			dialog();  
			return true;  
		}  
		return true;  
	} 
	protected void dialog() {  
		AlertDialog.Builder builder = new Builder(NoteActivity.this);  
		builder.setMessage("确定要退出吗?");  
		builder.setTitle("提示");  
		builder.setPositiveButton("确认",  
				new android.content.DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) { 
				NoteActivity.this.finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
//				dialog.dismiss();  
//				NoteActivity.this.finish();  
//				System.exit(1);  
			//	android.os.Process.killProcess(android.os.Process.myPid());  
			}  
		});  
		builder.setNegativeButton("取消",  
				new android.content.DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();  
			}  
		});  
		builder.create().show();  
	}  


}


