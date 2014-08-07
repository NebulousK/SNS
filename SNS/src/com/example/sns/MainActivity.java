package com.example.sns;


import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity  implements OnTabChangeListener  {
	final static String NEWS="뉴스피드";
	final static String FR="친구목록";
	final static String SNS="썸";
	final static String GPS="지도";
	final static String MSG="쪽지";
	
	
	private TabHost tabHost;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	 
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	
	public static Activity AActivity;

	private void setupTabHost(){
		tabHost=(TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AActivity = MainActivity.this;
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		setupTabHost();
		
		Toast.makeText(getBaseContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
		
		//StartlogOut();
		setupTab(new TextView(this),NEWS);
		setupTab(new TextView(this),FR);
		setupTab(new TextView(this),SNS);
		setupTab(new TextView(this),GPS);
		setupTab(new TextView(this),MSG);
		tabHost.setCurrentTab(0);
		
		for (int tab = 0; tab < tabHost.getTabWidget().getChildCount(); tab++) {
			tabHost.getTabWidget().getChildAt(tab).getLayoutParams().height=130;
		}
		tabHost=getTabHost();
		tabHost.setOnTabChangedListener(this);
		
		setTitle("Some N Some");
		getActionBar().setIcon(R.drawable.actiontitle);
		
		mTitle = mDrawerTitle = getTitle();
		 // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
      
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
     
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
       
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
       
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
      
        navMenuIcons.recycle();
 
       
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
    
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer,
                R.string.action_test,
                R.string.app_name 
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);              
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);               
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
           
           displayView(0);
        }

	
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	}
	
	
   
	
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
     
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);
		}
    }
   
    private void displayView(int position) {
        Intent intent;
        switch (position) {
        case 0:
         
            break;
        case 1:        	       	
        	 intent=new Intent(getBaseContext(),sub_notice.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        	break;
        case 2:       	
             intent=new Intent(getBaseContext(),sub_push.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            break;
        case 3: 
        	  intent=new Intent(getBaseContext(), sub_personal.class);
        	startActivity(intent);
        	overridePendingTransition(android.R.anim.slide_in_left, R.anim.slide_out_left);
            break;        
        case 4:        	
        	intent=new Intent(getBaseContext(),sub_custom.class);
        	startActivity(intent);
        	overridePendingTransition(android.R.anim.slide_in_left, R.anim.slide_out_left);
        	break;
        	
        default:
            break;
        }
 
     
    }


	 private void setupTab(TextView view, String tag) {
		View tabview = createTabView(tabHost.getContext(),tag);
		
		TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview);
	
		
		if(tag.equals(NEWS)){
			setContent.setContent(new Intent(this, activity_news.class));
		
		}
		else if(tag.equals(FR)){
			setContent.setContent(new Intent(this, activity_friend.class));
		
		}
		else if(tag.equals(SNS)){
			setContent.setContent(new Intent(this, activity_sns.class));
		
		}
		else if(tag.equals(GPS)){
			setContent.setContent(new Intent(this, activity_gps.class));
			
		}
		else if(tag.equals(MSG)){
			setContent.setContent(new Intent(this, activity_msg.class));
			
		}
	
		tabHost.addTab(setContent);
		
	}
     
	
	//tab에 나타날 view 구성
	private View createTabView(Context context, String text) {
		//layoutinflater를 이용해 xml리소스를 읽어옴
		View view =LayoutInflater.from(context).inflate(R.layout.activity_tabwidget, null);
		ImageView img;
		
		if(text.equals(NEWS)){
			img=(ImageView)view.findViewById(R.id.tabs_image);
			img.setImageResource(R.drawable.n);
		}
		else if(text.equals(FR)){
			img=(ImageView)view.findViewById(R.id.tabs_image);
			img.setImageResource(R.drawable.f);
		}
		
		else if(text.equals(SNS)){
			img=(ImageView)view.findViewById(R.id.tabs_image);
			img.setImageResource(R.drawable.s);
		}
		
		else if(text.equals(GPS)){
			img=(ImageView)view.findViewById(R.id.tabs_image);
			img.setImageResource(R.drawable.g);
		}
		
		else if(text.equals(MSG)){
			img=(ImageView)view.findViewById(R.id.tabs_image);
			img.setImageResource(R.drawable.m);
		}
		
		TextView tv=(TextView)view.findViewById(R.id.tabs_text);
		tv.setTypeface(Typeface.createFromAsset(getAssets(), "365.ttf"));
		tv.setText(text);
		return view;
	}

	//탭클릭시 배경 변경
	@Override
	public void onTabChanged(String tabId) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}		 
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#dedede"));
		
	}


	//메뉴
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
	 
		 getMenuInflater().inflate(R.menu.main, menu);
	     return true;

	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	 
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
     
        switch (item.getItemId()) {
        case R.id.action_menu:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }

	}
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_menu).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
   
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
     
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	
	 private void threadSleep() {
	        try {
	        	Log.i("threadSleep:" ,"쉬기");
	            Thread.sleep(500);
	        } catch (Exception e) {
	            Log.d("threadSleep", "thread Exception!");
	        }
	    }

	 //어플 종료시 캐시 삭제
	 private void clearApplicationCache(java.io.File dir){
	        if(dir==null)
	            dir = getCacheDir();
	        else;
	        if(dir==null)
	            return;
	        else;
	        java.io.File[] children = dir.listFiles();
	        try{
	            for(int i=0;i<children.length;i++)
	                if(children[i].isDirectory())
	                    clearApplicationCache(children[i]);
	                else children[i].delete();
	        }
	        catch(Exception e){}
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        clearApplicationCache(null);
	    } 

}
