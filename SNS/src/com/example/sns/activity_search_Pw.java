package com.example.sns;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class activity_search_Pw extends Activity {
	String result_f="";
	String result_id="";
	EditText add_id, add_email, add_num;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchpw);
		add_id=(EditText)findViewById(R.id.add_id);
		add_email=(EditText)findViewById(R.id.add_email);
		add_num=(EditText)findViewById(R.id.add_num);
		
	}

	public void OnKey(View v){
		checkTest();
		if(result_f.equals("3")){
			//메일이 발송 되었습니다.
			SendEamil();
			
		}else if(result_f.equals("2")){
			//가입정보가 없습니다.
			SendEamilError();
		}
	}
	public void SendEamil(){
		new AlertDialog.Builder(this)
		.setTitle("비밀번호 찾기")
		.setMessage("메일이 발송 되었습니다.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	public void SendEamilError(){
		new AlertDialog.Builder(this)
		.setTitle("비밀번호 찾기")
		.setMessage("가입정보가 없습니다.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	public void Onsearch(View v){
		checkConfirm();
		
		Intent intent = new Intent(getBaseContext(),activity_search_Pw2.class);
		intent.putExtra("id", result_id);
		startActivity(intent);
		finish();
	}
	
	class XmlData{
		String flag;
		String id;
	
	}
	public void checkTest(){
		try {
			String m_searchTxt="";	
			String id=add_id.getText().toString();				
		
		
			String postURL = "http://54.178.192.82/homepage/android/android_member_search.jsp?email="+add_email.getText().toString()+
					"&id="+id;
			
			
			XmlData xmlData=null;
			ArrayList<XmlData> m_xmlData=new ArrayList<XmlData>();
			String sTag;
		
			XmlPullParserFactory fatory=XmlPullParserFactory.newInstance();
			fatory.setNamespaceAware(true);
			XmlPullParser xpp=fatory.newPullParser();
			
			URL u = new URL(postURL);
			InputStream in=u.openConnection().getInputStream();
			
			//xpp.setInput(in,"utf-8");
			xpp.setInput(in,"utf-8");
			
			int eventType=xpp.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
				if(eventType==XmlPullParser.START_DOCUMENT){
					System.out.println("start");
				}else if(eventType==XmlPullParser.END_DOCUMENT){
					System.out.println("end");
				}else if(eventType==XmlPullParser.START_TAG){
					Log.i("start_tag", xpp.getName());
					sTag=xpp.getName();
					
					if(sTag.equals("flag")){
						xmlData=new XmlData();
						xmlData.flag=xpp.nextText();
						result_f=xmlData.flag;
					}
				
				  }else if(eventType==XmlPullParser.END_TAG){
					  sTag=xpp.getName();
					  if(sTag.equals("check")){
						  m_xmlData.add(xmlData);
						  xmlData=null;
					  }
				  }else if(eventType==XmlPullParser.TEXT){
					  
				  }
				eventType=xpp.next();			
			}			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	public void checkConfirm(){
		try {
			String m_searchTxt="";	
			
			String postURL = "http://54.178.192.82/homepage/android/android_member_searchcon.jsp?num="+add_num.getText().toString();
			
			
			XmlData xmlData=null;
			ArrayList<XmlData> m_xmlData=new ArrayList<XmlData>();
			String sTag;
			
			XmlPullParserFactory fatory=XmlPullParserFactory.newInstance();
			fatory.setNamespaceAware(true);
			XmlPullParser xpp=fatory.newPullParser();
			
			URL u = new URL(postURL);
			InputStream in=u.openConnection().getInputStream();
			
			
			xpp.setInput(in,"utf-8");
			
			int eventType=xpp.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
				if(eventType==XmlPullParser.START_DOCUMENT){
					System.out.println("start");
				}else if(eventType==XmlPullParser.END_DOCUMENT){
					System.out.println("end");
				}else if(eventType==XmlPullParser.START_TAG){
					Log.i("start_tag", xpp.getName());
					sTag=xpp.getName();
					
					if(sTag.equals("id")){
						xmlData=new XmlData();
						xmlData.id=xpp.nextText();
						
						
						result_id=xmlData.id;
						System.out.println("result:"+result_id);
					}
					
				}else if(eventType==XmlPullParser.END_TAG){
					sTag=xpp.getName();
					if(sTag.equals("check")){
						m_xmlData.add(xmlData);
						xmlData=null;
					}
				}else if(eventType==XmlPullParser.TEXT){
					
				}
				eventType=xpp.next();			
			}			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
}
