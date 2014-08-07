package com.example.sns;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import android.widget.TextView;

public class activity_security extends Activity {
	TextView tv2;
	CheckBox ch1;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_security);
	    
	    final SharedPreferences pref = getSharedPreferences("LOCK", Activity.MODE_PRIVATE);
	    final SharedPreferences.Editor editor =pref.edit();	

	    ch1=(CheckBox)findViewById(R.id.ch1);
	    tv2=(TextView)findViewById(R.id.tv2);
	    
        Boolean chk1=pref.getBoolean("check", false);
        ch1.setChecked(chk1);
        
	    tv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("되되");
				Intent intent=new Intent(getBaseContext(), activity_change_password.class);
				startActivity(intent);		
				
			}
		});
	    
	   
	    ch1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox ch=(CheckBox)v;
				 if(ch.isChecked()){
					editor.putBoolean("check",ch.isChecked()); 
					editor.commit();
					 System.out.println("된당");
				    	Intent intent=new Intent(getBaseContext(), activity_password.class);
						startActivity(intent);				
					
				    }	
				 else{
					 editor.putBoolean("check",false);
					 editor.commit();
				 }
			}
		});
	    
	   
	   
	}


}
