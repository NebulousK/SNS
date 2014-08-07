package com.example.sns;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class activity_friend extends Activity {
	private Context mContext;
	private Handler mHandler = new Handler();
	private boolean bCmdProcess = false; 
	public WebView mWebView;	
	private ProgressBar prgrBar;
	/** Called when the activity is first created. */
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
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
		String strUrl = "file:///android_asset/www/find.html";
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
			new AlertDialog.Builder(pCtx)
			.setTitle("�ȳ�").setMessage(message)
			.setNeutralButton(android.R.string.ok,  
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
			new AlertDialog.Builder(pCtx)
			.setTitle("�ȳ�").setMessage(message)
			.setPositiveButton(android.R.string.ok,  
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