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
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class activity_search_Pw2 extends Activity {
	EditText add_id, add_email, add_pw, add_pw2;
	TextView tv;
	String result_i="";
	String result_p="";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
	    setContentView(R.layout.activity_searchpw2);
	    add_id=(EditText)findViewById(R.id.add_id);
	    add_email=(EditText)findViewById(R.id.add_email);
	    add_pw=(EditText)findViewById(R.id.add_pw);
	    add_pw2=(EditText)findViewById(R.id.add_pw2);
	    tv = (TextView)findViewById(R.id.tv);
	    Intent intent=getIntent();
	    String result_id=intent.getExtras().getString("id");
	    System.out.println("resultid:"+result_id);
	    add_id.setText(result_id);
	}
	
	public void OnChange(View v){
		 checkTest();
		
		
	}

	public void OnCancle(View v){
		Intent intent=new Intent(getBaseContext(),activity_login.class);
		startActivity(intent);
		finish();
	}
	public void SendEamil(){
		new AlertDialog.Builder(this)
		.setTitle("비밀번호 변경")
		.setMessage("비밀번호가 변경되었습니다.")
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
		.setTitle("비밀번호 변경")
		.setMessage("비밀번호가 변경되지 않았습니다.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	
	class XmlData{
		String pw;
		String id;
	
	}
	public void checkTest(){
		try {
			String m_searchTxt="";	
			
		
			String postURL = "http://54.178.192.82/homepage/android/android_member_change.jsp?email="+add_email.getText().toString()+
					"&id="+add_id.getText().toString()+"&pw="+add_pw.getText().toString()+"&pw2="+add_pw2.getText().toString();
			
			
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
					
				
					if(sTag.equals("id")){
						xmlData=new XmlData();
						xmlData.id=xpp.nextText();
						result_i=xmlData.id;
					}
					if(sTag.equals("pw")){
						xmlData=new XmlData();
						xmlData.pw=xpp.nextText();
						result_p=xmlData.pw;
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
		System.out.println("result_p:" +result_p);
		System.out.println("result_i:" +result_i);
		
		 if(result_p.equals("3")){
			 tv.setText(null);
			 tv.setTextColor(Color.parseColor("#FF0000"));
			 tv.setText("비밀번호를 6~12자까지 입력해주세요.");
			 
		 }else if(result_p.equals("4")){
			 tv.setText(null);
			 tv.setTextColor(Color.parseColor("#FF0000"));
			 tv.setText("비밀번호가 일치하지 않습니다.");
		 }else if(result_p.equals("5")){
			 tv.setText(null);
			 tv.setTextColor(Color.parseColor("#22741C"));
			 tv.setText("일치");		
			 
			 if(result_i.equals("1")){
				 
				 SendEamil();
				 
				 Intent intent=new Intent(getBaseContext(),activity_login.class);
				startActivity(intent);
				finish();
			 }else if(result_i.equals("2")){
				 SendEamilError();
			 }
		 }
		
		 
	}
}
