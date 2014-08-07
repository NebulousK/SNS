package com.example.sns;

/*
 * 강혜리 양의 작품
 * 7월 11일
 * */


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.TextView;

public class activity_login extends Activity {
	loadJsp task;
	EditText id, password;	
	TextView tv,id_search,pass_search;
	String result;
	TextView join;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
	
		id=(EditText) findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		tv=(TextView)findViewById(R.id.error);
		//tv.setTypeface(Typeface.createFromAsset(getAssets(), "365.ttf"));
		id_search=(TextView)findViewById(R.id.id_search);
		id_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), activity_search_Id.class );
				startActivity(intent);
				
			}
		});
		join=(TextView)findViewById(R.id.join);
		//join.setTypeface(Typeface.createFromAsset(getAssets(), "365.ttf"));
		join.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getBaseContext(), activity_signup.class);
				startActivity(intent);
				
				
			}
		});
		
		pass_search=(TextView)findViewById(R.id.pass_search);
		//pass_search.setTypeface(Typeface.createFromAsset(getAssets(), "365.ttf"));
		pass_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getBaseContext(), activity_search_Pw.class);
				startActivity(intent);
				
			}
		});
		
		CookieSyncManager.createInstance(getBaseContext());
		CookieManager.getInstance().removeAllCookie();
		CookieSyncManager.getInstance().startSync();
		task=new loadJsp();		
		task.updateCookie(getBaseContext());
			
	}

	public void Onlogin(View v){
		loadJsp task1=new loadJsp();		
		task1.execute();			
	}

		
	@Override
	protected void onPause()
	{
	    super.onPause();
	    CookieSyncManager.getInstance().stopSync();
	}

	@Override
	protected void onResume()
	{
	    super.onResume();
	    CookieSyncManager.getInstance().startSync();
	}

	class loadJsp extends AsyncTask<Void, String, Void>{
		

		public HttpClient client =new DefaultHttpClient();
		CookieManager cookieManager=CookieManager.getInstance();
		String postURL = "http://54.178.192.82/homepage/android/adroid_member_login.jsp";

		@Override
		protected Void doInBackground(Void... params) {
			try {
							
				
				HttpPost post=new HttpPost(postURL);
				ArrayList<NameValuePair> params1 = new ArrayList<NameValuePair>(); 
				//�Ķ���͸� list�� ��� ������ 
				params1.add(new BasicNameValuePair("id", id.getText().toString()) );
				params1.add(new BasicNameValuePair("password", password.getText().toString()));
				UrlEncodedFormEntity ent= new UrlEncodedFormEntity(params1,HTTP.UTF_8);
				post.setEntity(ent);
				// jsp에서 out.println을 받아오는곳
		        //  ResponseHandler<String> reshandler = new BasicResponseHandler();
		        // result = client.execute(post, reshandler).trim(); //이상하게 계속 공백도 같이 저장된다. 공백제거를 위해 trim(). 
	            HttpResponse responsePOST = client.execute(post); //jsp ��� �޾ƿ���
				HttpEntity resEntity=responsePOST.getEntity();
			
				String responseData= EntityUtils.toString(resEntity).toString().trim();		
	
				if(resEntity !=null){
					if(responseData.equals("a")){
						Log.i("doInBackground: ","비밀번호 오류");
						tv.setText("비밀 번호가 틀렸습니다. 비밀번호를 확인 하세요");
					}
					else if(responseData.equals("b")){
						Log.i("doInBackground: ","존재하지 않는 아이디");
						tv.setText("존재 하지 않는 아이디 입니다. 아이디를 확인하세요");
					}else{
						SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("id", id.getText().toString());          //사용자에게 입력받은 strid를 저장 
						editor.putString("pw", password.getText().toString());      //사용자에게 입력받은 strpw를 저장
						editor.commit();                            //commit
						String a[] = responseData.split(",");
						var.id = a[0];
						var.no = Integer.parseInt(a[1]);
						var.sex = a[2];
						var.photo = a[3];
						var.name = a[4];
						var.some = a[5];
						Log.i("doInBackground: ","로그인성공");
						final ResponseHandler<String> responseHandler=new ResponseHandler<String>() {
							@Override
							public String handleResponse(HttpResponse response)
								
									throws ClientProtocolException, IOException {
									List<Cookie> cookies=((DefaultHttpClient)client).getCookieStore().getCookies();
									if(!cookies.isEmpty()){
										for(int i=0;i<cookies.size();i++){
											String cookieString = cookies.get(i).getName()+"="+cookies.get(i).getValue();
											cookieManager.setCookie(postURL,cookieString);
											Log.i("handleResponse",cookieString.toString());
										}
									}
									CookieSyncManager.getInstance().sync();
									try {
										Thread.sleep(500);
									} catch (Exception e) {	}
									
									
									Intent intent = new Intent(getBaseContext(), MainActivity.class);
									startActivity(intent);
									finish();
										
									return "";
							}
						};
						getCookie();
						new Thread(){
							@Override
							public void run() {
								try {
									ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
									nameValuePairs.add(new BasicNameValuePair("id", "아이디"));
									nameValuePairs.add(new BasicNameValuePair("password", "비밀번호"));
									
									HttpParams params=client.getParams();
									HttpConnectionParams.setConnectionTimeout(params, 5000);
									HttpConnectionParams.setSoTimeout(params, 5000);
									HttpPost httpPost=new HttpPost(postURL);
									UrlEncodedFormEntity entityRequest= new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
									Log.i("run", nameValuePairs.toString());
									Log.i("params", params.toString());
									
									httpPost.setEntity(entityRequest);
									client.execute(httpPost,responseHandler);
								} catch (Exception e) {
									// TODO: handle exception
								}
							}		
						}.start();
					}
				}
			} catch (Exception e) {
				// TODO �ڵ� ��� catch ���
				e.printStackTrace();
			}
			return null;
		}
		/* HttpClient ���ִ� ��Ű ��������
		* ����ڰ� �α������� ȣ���Ͽ� Preferences�� ������
		* return Cookie
		*/ 
        public Cookie getCookie(){
        	List<Cookie> cookies = ((DefaultHttpClient)client).getCookieStore().getCookies();
        	
        	//������ �����ϱ�
        	SharedPreferences pref = getSharedPreferences("cookie", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor =pref.edit();
			editor.putString("keyid", id.getText().toString());
			editor.putString("name",cookies.get(0).getName());
			editor.putString("value", cookies.get(0).getValue());
			editor.putString("domain", cookies.get(0).getDomain());
			editor.putString("path", cookies.get(0).getPath());
			editor.putBoolean("check_AutoLogin",true);			
			editor.commit();
			System.out.println("key:"+id.getText().toString());
			return cookies.get(0);
        }
     
        /*서버에 접속하기전에 client의 쿠키를 이전에 저장된
         * SharedPreference에 있는 값으로 대체하는 메소드 
         */
		public void updateCookie(Context context){
			//client 에서 제공해주는 쿠키 스토어 가져오기
        	CookieStore cookieStore =((DefaultHttpClient)client).getCookieStore();
        	List<Cookie> cookieList = cookieStore.getCookies();
        	
        	//Preferences에서 쿠키 정보 가져오기
        	//NapsStorage napsCookie=new NapsStorage(context,"cookie");
            SharedPreferences pref;
        	pref=context.getSharedPreferences("cookie", Activity.MODE_PRIVATE);
			String name=pref.getString("name", "");
			String value=pref.getString("value", "");
			String domain=pref.getString("domain", "");
			String path=pref.getString("path", "");
			pref.getBoolean("check_AutoLogin", false);
			
			//현재 client 저장소에 쿠키가 없고 &&
        	//preferences에 이전에 저장된 쿠키가 있는지 검사
				if(cookieList.size() == 0 && !name.isEmpty()){	
				//client의 쿠키스토어에서 가져온 쿠키 name과 value를 셋팅
        		BasicClientCookie cookie=new BasicClientCookie(name, value);
        		cookie.setDomain(domain);
        		cookie.setPath(path);
        		cookieStore.addCookie(cookie);
        		checkPassword();        		
        	}
        }
          
        
		public void checkPassword(){
	       	SharedPreferences pref1 = getSharedPreferences("LOCK", Activity.MODE_PRIVATE);	
	       	SharedPreferences.Editor editor =pref1.edit();      	
	             	
	       	Boolean chk1=pref1.getBoolean("check", false);
			System.out.println("chk1:"+chk1);
			    
			    if(chk1==true){
			    	editor.putString("loginCheck", "start");
			       	editor.commit();
			    	Intent intent=new Intent(getBaseContext(), activity_password.class);
			    	startActivity(intent);
			    	finish();
			    }else{
			    	/*Intent intent = new Intent(getBaseContext(), MainActivity.class);
	        		startActivity(intent);
	        		finish();*/
	        		loadJsp2 task2=new loadJsp2();		
	        		task2.execute();		
			    }
	       }
	
	}
	
class loadJsp2 extends AsyncTask<Void, String, Void>{
		
		public HttpClient client =new DefaultHttpClient();
		CookieManager cookieManager=CookieManager.getInstance();
		String postURL = "http://54.178.192.82/homepage/android/adroid_member_login.jsp";

		@Override
		protected Void doInBackground(Void... params) {
			try {
				HttpPost post=new HttpPost(postURL);
				ArrayList<NameValuePair> params1 = new ArrayList<NameValuePair>();
				SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
				String strid = prefs.getString("id", "");
        		String strpw = prefs.getString("pw", "");
				//파라미터를 list에 담아 보내기 
				params1.add(new BasicNameValuePair("id", strid) );
				params1.add(new BasicNameValuePair("password", strpw));
				UrlEncodedFormEntity ent= new UrlEncodedFormEntity(params1,HTTP.UTF_8);
				post.setEntity(ent);
	             // jsp에서 out.println을 받아오는곳
	           //  ResponseHandler<String> reshandler = new BasicResponseHandler();
	            // result = client.execute(post, reshandler).trim(); //이상하게 계속 공백도 같이 저장된다. 공백제거를 위해 trim(). 
	            HttpResponse responsePOST = client.execute(post); //jsp 결과값 받아오기
				HttpEntity resEntity=responsePOST.getEntity();
							
	         
				String responseData= EntityUtils.toString(resEntity).toString().trim();		
	
				if(resEntity !=null){
					if(responseData.equals("a")){
						Log.i("doInBackground: ","비밀번호 오류");
						tv.setText("비밀 번호가 틀렸습니다. 비밀번호를 확인 하세요");
					}
					else if(responseData.equals("b")){
						Log.i("doInBackground: ","존재하지 않는 아이디");
						tv.setText("존재 하지 않는 아이디 입니다. 아이디를 확인하세요");
					}else{
						String a[] = responseData.split(",");
						var.id = a[0];
						var.no = Integer.parseInt(a[1]);
						var.sex = a[2];
						var.photo = a[3];
						var.name = a[4];
						var.some = a[5];
						Log.i("수박바",""+a[0]+","+a[1]+","+a[2]+","+a[3]+","+a[4]+","+a[5]);
						Log.i("fuck",""+a.length);
						Log.i("doInBackground: ","로그인성공");
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						startActivity(intent);
						finish(); //스택에서 로그인 엑티비티 제거 
					}	
				}
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			return null;
		}
	}
}
	


