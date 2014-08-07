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

public class activity_change_password extends Activity {
	ImageView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,numb,check;
	ImageView y1,y2,y3,y4;
	TextView tv;
	int i=0;
	int j=3;
	
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
		
		
		editor.remove("password");
		tv=(TextView)findViewById(R.id.tv);
		
		num0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//editor.putInt("num0", 0);
			//	editor.commit();
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
				editor.putString("password",numtest);
				editor.commit();
				
				Intent intent=new Intent(getBaseContext(), activity_security.class);
				startActivity(intent);
				finish();				
			}
		});
		
	}


	public void count(String num){
		if(i==0){
			y1.setVisibility(View.VISIBLE);
			i++;
			if(numtest.length()<4){
			numtest = num;
			}
			System.out.println("numtest00"+numtest);
		}
		else if(i==1){
			
			y2.setVisibility(View.VISIBLE);
			i++;
			if(numtest.length()<4){
			numtest += num;
			}
			System.out.println("numtest11"+numtest);
		}
		else if(i==2){
			y3.setVisibility(View.VISIBLE);
			i++;
			if(numtest.length()<4){
			numtest += num;
			}
			System.out.println("numtest22"+numtest);
		}
		else if(i==3){		
			y4.setVisibility(View.VISIBLE);
			i++;
			if(numtest.length()<4){
			numtest += num;
			}
			System.out.println("numtest22"+numtest);
		}
		
	}
	
	public void back(){
		if(i==3){
			y4.setVisibility(View.INVISIBLE);
			--i;
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
			System.out.println("numtest"+numtest);
		}
		else if(i==2){
		
			y3.setVisibility(View.INVISIBLE);
			--i;
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
			System.out.println("numtest"+numtest);
		}
		else if(i==1){
		
			y2.setVisibility(View.INVISIBLE);
			--i;
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
			System.out.println("numtest"+numtest);
		}
		else if(i==0){
		
			y1.setVisibility(View.INVISIBLE);		
			if(0<numtest.length()){
			numtest = numtest.substring(0,numtest.length()-1);
			}
			System.out.println("numtest"+numtest);
		}
	}
}
