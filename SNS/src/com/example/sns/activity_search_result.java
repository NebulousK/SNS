package com.example.sns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activity_search_result extends Activity {
	TextView tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchid_result);
		tv=(TextView)findViewById(R.id.tv);
		Intent intent=getIntent();
		String name;
		String result_id;
	
		result_id=intent.getExtras().getString("result_id");
		tv.setText(result_id);
	
	}
	
	public void Onlogin(View v){
		Intent intent=new Intent(getBaseContext(), activity_login.class);
		startActivity(intent);
		finish();
		
	}
	public void OnSearch(View v){
		Intent intent=new Intent(getBaseContext(), activity_search_Pw.class);
		startActivity(intent);
		finish();
	}
}
