package com.example.sns;

import java.lang.reflect.Method;

import com.google.android.gcm.GCMRegistrar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;


@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class activity_news extends Activity {
	private Context mContext;
	private Handler mHandler = new Handler();
	private boolean bCmdProcess = false; 
	public WebView mWebView;	
	private ProgressBar prgrBar;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   // setContentView(R.layout.activity_news);
	    Window win = getWindow();
	    setContentView(R.layout.activity_news);
	    
	    GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if("".equals(regId))   //구글 가이드에는 regId.equals("")로 되어 있는데 Exception을 피하기 위해 수정
		      GCMRegistrar.register(this, "682295769917");
		else
		      Log.d("==============", regId);
		
	    //그다음 인플레이션으로 겹치는 레이아웃을 깐다
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.writeover, null);
        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
                      LinearLayout.LayoutParams.FILL_PARENT,
                      LinearLayout.LayoutParams.FILL_PARENT);
        win.addContentView(linear, paramlinear);//이 부분이 레이아웃을 겹치는 부분
        //add는 기존의 레이아웃에 겹쳐서 배치하라는 뜻이다.
        ImageButton writeBtn = (ImageButton) findViewById(R.id.imagebtn);
	    writeBtn.setOnClickListener(
	    		new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myin = new Intent(getApplicationContext(), WriteActivity.class);
						startActivity(myin);
					}
				}
	    ); 
	    mContext = this;    
	    mWebView = (WebView)findViewById(R.id.webView1);
		WebSettings ws = mWebView.getSettings();
		ws.setJavaScriptEnabled(true); 
		ws.setPluginState(WebSettings.PluginState.ON);
 		ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
 		ws.setDomStorageEnabled(true);
 		ws.setAppCacheEnabled(true);
 		ws.setSaveFormData(true);
 		
 		if (Build.VERSION.SDK_INT >= 16) {  
 			try{
 		    Class<?> clazz = mWebView.getSettings().getClass();
 		    Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
 		    if (method != null) {
 		        method.invoke(mWebView.getSettings(), true);
 		    }
 			}catch(Exception err){
 				Log.i("err",""+err);
 			}
 		}
 		mWebView.setNetworkAvailable(true);
		mWebView.setWebChromeClient(new ChromeClient(this));
		mWebView.setWebViewClient(new webviewClient(this));
		mWebView.setScrollbarFadingEnabled(true);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);		
		mWebView.addJavascriptInterface(new Object(){
			@JavascriptInterface
			public String id() {
				return var.id;
			}	
			@JavascriptInterface
			public int no() {
				return var.no; 
			}	 
			@JavascriptInterface
			public String sex() {
				return var.sex;
			}	 
			@JavascriptInterface
			public String photo() {
				return var.photo;
			}	 
			@JavascriptInterface
			public String name() {
				return var.name;
			}
			@JavascriptInterface
			public String some() {
				return var.some;
			}	 
		}, "android");
		String strUrl = "file:///android_asset/www/index.html";
	    mWebView.loadUrl(strUrl);    
	    prgrBar = new ProgressBar(this); 
}
	
	
	// ChromeClient for Alert
    private final class ChromeClient extends WebChromeClient {
    	public Context pCtx;
    	
    	public ChromeClient(Context cxt) {
    		pCtx = cxt;
    	}

		@Override
		public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
			new AlertDialog.Builder(pCtx).setTitle("안내").setMessage(message).setNeutralButton(android.R.string.ok,  
                new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int which) { 
                        	result.confirm();
                        }
            		})
			.setCancelable(false).show();
			return true;
		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
			new AlertDialog.Builder(pCtx).setTitle("안내").setMessage(message).setPositiveButton(android.R.string.ok,  
					new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int which) { 
                        	result.confirm();
                        }
            		})
            .setNegativeButton(android.R.string.cancel,  
                    new AlertDialog.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int which) { 
                        	result.confirm();
                        }
            		})
			.setCancelable(false).show();
			return true;
		}
    }
    
    private class webviewClient extends WebViewClient {
    	private Context pCtx;
    	
    	public webviewClient(Context ctx) {
    		pCtx = ctx;    		
    	}    		
    	@Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) 
        {
        	super.onPageStarted(view, url, favicon);
        	prgrBar.Show();    		
        }
    	 @Override
         public void onPageFinished(WebView view, String url) 
         {
    		 super.onPageFinished(view, url);
    		 prgrBar.Hide();
         }
    }   
}
