package com.example.sns;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class activity_web extends Activity {
	ProgressDialog pd;
	private WebView webview;
	private Handler mHandler = new Handler();
	private TextView num1,num2,addr;
	/** Called when the activity is first created. */
	@SuppressLint("JavascriptInterface")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.zip_webview);
	    
	    pd=ProgressDialog.show(activity_web.this, "�����ȣ ã��", "Loading");
	    
	    webview=(WebView)findViewById(R.id.webview);
	    num1=(TextView)findViewById(R.id.num1);
	    num2=(TextView)findViewById(R.id.num2);
	    addr=(TextView)findViewById(R.id.addr);
		webview.getSettings().setSupportMultipleWindows(true); 
	
		WebSettings ws = webview.getSettings();
		ws.setJavaScriptEnabled(true);
		
		webview.addJavascriptInterface(new AndroidBridge(), "android");
		webview.setWebViewClient(new WebClient());
		webview.loadUrl("http://54.178.192.82/homepage/android/android_member_search_zip.jsp");
		
	}

	
	public class AndroidBridge{
			
		@JavascriptInterface
		public void setMessage(final String arg, final String arg1, final String arg2){
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
				
					num1.setText(arg);
					num2.setText(arg1);
					addr.setText(arg2);
					
					if(num1.getText().toString().equals("") || num2.getText().toString().equals("") || addr.getText().toString().equals("")){
						AlertMessage();
					}
					else{
					Intent intent = new Intent(getBaseContext(), activity_signup.class);
					
					intent.putExtra("pc1", num1.getText().toString());
					intent.putExtra("pc2", num2.getText().toString());
					intent.putExtra("addr", addr.getText().toString());
					setResult(RESULT_OK, intent);
					finish();}
				}
			});
		}
		
		public void AlertMessage(){
			AlertDialog.Builder ab=new AlertDialog.Builder(activity_web.this);
			ab.setMessage("�ּҸ� �������ּ���.")
			.setTitle("�ּ�Ȯ��")
			.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO �ڵ� ��� �޼ҵ� ����
					dialog.dismiss();					
				}
			}).show();
		}
	}

	class WebClient extends WebViewClient{

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO �ڵ� ��� �޼ҵ� ����
			super.onPageFinished(view, url);
			if(pd.isShowing()){
				pd.dismiss();
			}
		}
		
		
	}
	
	
}
