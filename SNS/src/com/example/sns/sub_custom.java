package com.example.sns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

//public class sub_custom extends Fragment {
public class sub_custom extends Activity{
	TextView tv1,tv2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.sub_custom);
	tv1=(TextView)findViewById(R.id.tv1);
	tv2=(TextView)findViewById(R.id.tv2);

	tv1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(getBaseContext(), activity_access.class);
			startActivity(intent);
		}
	});
	tv2.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(getBaseContext(), activity_office.class);
			startActivity(intent);
			
		}
	});
	}


}
