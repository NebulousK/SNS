package com.example.sns;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;


public class Splash extends Activity {
	public static int pg=0;
	public static ProgressBar proBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		proBar = (ProgressBar)findViewById(R.id.ProgressBar01);
		proBar.setIndeterminate(false);
		proBar.setMax(100);
		proBar.setProgress(pg);
		
		IncThread inc = new IncThread();
		inc.setDaemon(true);
		inc.start();
		
		Handler h =new Handler();
		h.postDelayed(new splashhandler(), 2000);
	}

	class splashhandler implements Runnable{

		
		@Override
		public void run() {
			
			startActivity(new Intent(getApplication(), activity_login.class));
			Splash.this.finish();
			
		}
		
	}
	
	class IncThread extends Thread{

		@Override
		public void run() {
			while(pg != 100){
				pg +=20;
				proBar.setProgress(pg);				
				try {
					sleep(800);
				} catch (InterruptedException e) {
					// TODO 자동 생성된 catch 블록
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
