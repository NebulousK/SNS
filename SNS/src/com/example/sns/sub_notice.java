package com.example.sns;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class sub_notice extends Activity {
	ListView listView;
	ArrayList<String> arrayList;
	ArrayAdapter<String> adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	   setContentView(R.layout.sub_notice);
	   arrayList=new ArrayList<String>();
	//   arrayList.add("")
	   
	}



}
