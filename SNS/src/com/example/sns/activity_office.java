package com.example.sns;

import com.example.sns.activity_access.WebClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class activity_office extends Activity {
	ProgressDialog pd;
	private WebView webview;
	private Handler mHandler = new Handler();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_office);
	    pd=ProgressDialog.show(activity_office.this, "이용약관", "Loading");
	    
	    webview=(WebView)findViewById(R.id.webview);
	   
		webview.getSettings().setSupportMultipleWindows(true); 
	
		WebSettings ws = webview.getSettings();
		ws.setJavaScriptEnabled(true);
		
	
		webview.setWebViewClient(new WebClient());
		webview.loadUrl("http://54.178.192.82/homepage/office.jsp");
	}
	
	class WebClient extends WebViewClient{

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO 자동 생성된 메소드 스텁
			super.onPageFinished(view, url);
			if(pd.isShowing()){
				pd.dismiss();
			}
		}
		
		
	}

}
