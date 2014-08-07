package com.example.sns;

import org.apache.http.protocol.RequestExpectContinue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class popup extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
	    layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
	    layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	    layoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
	    layoutParams.flags = WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
	    
	    layoutParams.dimAmount = 0.7f;
	 
	    getWindow().setAttributes(layoutParams);
	    
	    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
	    alert_confirm.setIcon(R.drawable.ic_launcher);
	    alert_confirm.setTitle("SomnNsomE");
	    alert_confirm.setMessage(var.message).setCancelable(false).setPositiveButton("확인",
	    new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // 'YES'
	        	PushWakeLock.releaseCpuLock();
	        	finish();
	        }
	    });
	    AlertDialog alert = alert_confirm.create();
	    alert.show();
	    try{
	    	Thread.sleep(5000);
	    }catch(InterruptedException err){
	    	err.printStackTrace();
	    }
	    PushWakeLock.releaseCpuLock();
	    /* // 이 부분이 바로 화면을 깨우는 부분 되시겠다.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);      
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
	    // 화면이 잠겨있을 때 보여주기
	    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
	    // 키잠금 해제하기
	    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
	    // 화면 켜기
	    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);*/
	}
}