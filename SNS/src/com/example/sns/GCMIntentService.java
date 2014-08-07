package com.example.sns;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
    private static final String tag = "GCMIntentService";
    private static final String PROJECT_ID = "682295769917";
    //구글 api 페이지 주소 [https://code.google.com/apis/console/#project:긴 번호]
   //#project: 이후의 숫자가 위의 PROJECT_ID 값에 해당한다
   
    //public 기본 생성자를 무조건 만들어야 한다.
    public GCMIntentService(){ this(PROJECT_ID); }
   
    public GCMIntentService(String project_id) { super(project_id); }
 
    /** 푸시로 받은 메시지 */
    @Override
    protected void onMessage(Context context, Intent intent) {
    	
    	try {
    		String msg = intent.getStringExtra("msg");
			String msg2 = URLDecoder.decode(msg, "euc-kr");
	    	Log.e("getmessage", "getmessage:" + msg2);
	    	generateNotification(context,msg2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**에러 발생시*/
    @Override
    protected void onError(Context context, String errorId) {
        Log.d(tag, "onError. errorId : "+errorId);
    }
 
    /**단말에서 GCM 서비스 등록 했을 때 등록 id를 받는다*/
    @Override
    protected void onRegistered(Context context, String regId) {
        try {
        	Log.d(tag, "onRegistered. regId : " + regId);
			String u_id = java.net.URLEncoder.encode(new String(regId.getBytes("UTF-8")));
			String id = java.net.URLEncoder.encode(new String(var.id.getBytes("UTF-8")));
			URL url = new URL("http://54.178.192.82/homepage/pushreg.some?id=" + id + "&regID=" + u_id);
            url.openStream();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**단말에서 GCM 서비스 등록 해지를 하면 해지된 등록 id를 받는다*/
    @Override
    protected void onUnregistered(Context context, String regId) {
        Log.d(tag, "onUnregistered. regId : "+regId);
    }
    
    private static void generateNotification(Context context, String message) {	
    	boolean check = true;
    	int icon = R.drawable.ic_launcher;
    	long when = System.currentTimeMillis();
    	NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    	Notification notification = new Notification(icon, message, when);
    	
    	String title = context.getString(R.string.app_name); 
    	Intent notificationIntent = new Intent(context, Splash.class);
    	notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
    	notification.setLatestEventInfo(context, title, message, intent);	 
    	notification.flags |= Notification.FLAG_AUTO_CANCEL;
    	notificationManager.notify(0, notification);
    	//알림창 끝 이제부터 팝업 메시지
    	Log.i("메세지", message);
    	var.message = message;
    	
    	ActivityManager activityapp = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
    	 List<RunningAppProcessInfo> list = (List<RunningAppProcessInfo>)activityapp.getRunningAppProcesses();
         for(int i = 0 ; i < list.size() ; i++) {
    	     RunningAppProcessInfo info = list.get(i);
    	     if(info.processName.equals("com.example.sns") && info.importance == info.IMPORTANCE_FOREGROUND){
    	    	Log.i("포그라운드", "18");
    	    	check = false;
    	     }
         }
         if(check == true){
        	Log.i("왓다", "컴온");
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	    	boolean isScreenOn = pm.isScreenOn();
	   	  	if(isScreenOn){
	   	  		Log.i("스크린", "트루");
	   	  		 Intent inten2 = new Intent(context, popup.class);
		   		 inten2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
		         context.startActivity(inten2);
	   	  		 
	   	  	}else{
	   	  		Log.i("스크린", "펄스");
	   	  		PushWakeLock.acquireCpuWakeLock(context); 
	   	  		Intent inten2 = new Intent(context, popup.class);
	   	  		inten2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
	   	  		context.startActivity(inten2);
	   	  	}
         }
    }
}