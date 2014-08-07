package com.example.sns;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_password extends Activity {
	ImageView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,numb,check;
	ImageView y1,y2,y3,y4;
	TextView tv;
	int i=0;
	int j=3;
	String test0="";

	String numtest="";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.android_password);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
		final SharedPreferences pref = getSharedPreferences("LOCK", Activity.MODE_PRIVATE);
		final SharedPreferences.Editor editor =pref.edit();	
		
		num0=(ImageView)findViewById(R.id.num0);
		num1=(ImageView)findViewById(R.id.num1);
		num2=(ImageView)findViewById(R.id.num2);
		num3=(ImageView)findViewById(R.id.num3);
		num4=(ImageView)findViewById(R.id.num4);
		num5=(ImageView)findViewById(R.id.num5);
		num6=(ImageView)findViewById(R.id.num6);
		num7=(ImageView)findViewById(R.id.num7);
		num8=(ImageView)findViewById(R.id.num8);
		num9=(ImageView)findViewById(R.id.num9);
		numb=(ImageView)findViewById(R.id.numb);
		check=(ImageView)findViewById(R.id.check);
		y1=(ImageView)findViewById(R.id.y1);
		y2=(ImageView)findViewById(R.id.y2);
		y3=(ImageView)findViewById(R.id.y3);
		y4=(ImageView)findViewById(R.id.y4);
		
		
		tv=(TextView)findViewById(R.id.tv);
		
		
		test0=pref.getString("password", "");				
		System.out.println("shared_ password값"+test0);
		if(test0==null || test0.equals(null)){
			Intent intent=new Intent(getBaseContext(), activity_change_password.class);
			startActivity(intent);
		}
		
		
		num0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				count("0");										
			}
		});
	
		num1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				count("1");			
			}
		});
		
		num2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("2");
			}
		});
		
		num3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("3");
			}
		});
		
		num4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("4");
			}
		});
		
		num5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("5");
			}
		});
		
		num6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("6");
			}
		});
		
		num7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("7");
				
			}
		});
		
		num8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("8");			
			}
		});
		
		num9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count("9");
			}
		});
		
		numb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				back();
			}
		});
		


		check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String loginCheck=pref.getString("loginCheck", "");
				System.out.println("loginche"+loginCheck);
				if(loginCheck=="start" || loginCheck.equals("start")){
					editor.putString("passwordCheck", numtest);
					editor.commit();
					
					String passwordCheck=pref.getString("passwordCheck", "");
					System.out.println("pass:"+passwordCheck);
					System.out.println("test0:"+test0);
					
					if(test0==passwordCheck || test0.equals(passwordCheck)){
						Intent intent=new Intent(getBaseContext(), MainActivity.class);
						startActivity(intent);
						finish();
					}
					else{
						tv.setText("비밀번호가 일치하지 않습니다.");
						y4.setVisibility(View.INVISIBLE);
						y3.setVisibility(View.INVISIBLE);
						y2.setVisibility(View.INVISIBLE);
						y1.setVisibility(View.INVISIBLE);
						i=0;
						numtest = numtest.substring(0,numtest.length()-4);
						System.out.println(numtest);
					}
					
				}
				else{
					editor.putString("passwordCheck", numtest);
					editor.commit();
					
					String passwordCheck=pref.getString("passwordCheck", "");
					System.out.println("pass:"+passwordCheck);
					System.out.println("test0:"+test0);
					
					if(test0==passwordCheck || test0.equals(passwordCheck)){
						Intent intent=new Intent(getBaseContext(), activity_security.class);
						startActivity(intent);
						finish();
					}
					else{
						tv.setText("비밀번호가 일치하지 않습니다.");
						y4.setVisibility(View.INVISIBLE);
						y3.setVisibility(View.INVISIBLE);
						y2.setVisibility(View.INVISIBLE);
						y1.setVisibility(View.INVISIBLE);
						i=0;
						numtest = numtest.substring(0,numtest.length()-4);
						System.out.println(numtest);
						
					}
				}
					
			}
		});
		
	}


	public void count(String num){
		if(i==0){
			y1.setVisibility(View.VISIBLE);
			
			i++;
			if(numtest.length()<4){
			numtest += num;
			}
		
		}
		else if(i==1){			
			y2.setVisibility(View.VISIBLE);
			i++;
			if(numtest.length()<4){
			numtest += num;
			}
		
		}
		else if(i==2){			
			y3.setVisibility(View.VISIBLE);
			i++;
			if(numtest.length()<4){
			numtest += num;
			}
		
		}
		else if(i==3){
			y4.setVisibility(View.VISIBLE);		
			if(numtest.length()<4){
			numtest += num;
			}		
		}
		
	}
	
	public void back(){
		if(i==3){			
			y4.setVisibility(View.INVISIBLE);
			--i;
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
			
		}
		else if(i==2){		
			y3.setVisibility(View.INVISIBLE);
			--i;
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
	
		}
		else if(i==1){		
			y2.setVisibility(View.INVISIBLE);
			--i;
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
		
		}
		else if(i==0){			
			y1.setVisibility(View.INVISIBLE);
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
			
			
		}
	}
	
	
}
