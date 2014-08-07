package com.example.sns;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.Toast;


public class sub_personal extends Activity {
	ImageView l_photo,l_logout,l_lock;
	String keyid="";
	
	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
	private static final int REQ_CODE_PICK_IMAGE = 0;
	static String filePath="";
	 String path="";
		
	int dstWidth=100;//사진크기 조절
	int dstHeight=95;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sub_personal);
        l_photo=(ImageView)findViewById(R.id.l_photo);
   
        l_logout=(ImageView)findViewById(R.id.l_logout);
        l_lock=(ImageView)findViewById(R.id.l_lock);
        
        SharedPreferences pref= getSharedPreferences("cookie", Activity.MODE_PRIVATE);
        keyid=pref.getString("keyid", "");
        
        System.out.println("key_id: "+keyid);
        loadJsp task=new loadJsp();
		task.execute();
	}
	public void Onlock(View v){
		Intent intent=new Intent(getBaseContext(), activity_security.class);
		startActivity(intent);
	}
	public void OnchangePhoto(View v){
		Intent intent = new Intent(
                Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      
		intent.setType("image/*");              // 모든 이미지
        intent.putExtra("crop", "true");        // Crop기능 활성화
       
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
        intent.putExtra("outputFormat",         // 포맷방식
                Bitmap.CompressFormat.JPEG.toString());

     
        
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
        
        

	}
	public void test(){
		  try {
         	 SharedPreferences pref= getSharedPreferences("cookie", Activity.MODE_PRIVATE);
              keyid=pref.getString("keyid", "");
              System.out.println("onActivityResult keyId: "+keyid);
				AndroidUploader3 uploader = new AndroidUploader3(keyid);
				
				
				path = Environment.getExternalStorageDirectory()+"/temp.jpg";
				
				System.out.println("testpath:"+path);
				uploader.changePicture(path);
				
			} catch (Exception e) {
				Log.e(e.getClass().getName(),e.getMessage());
			}
	}
	 /** 임시 저장 파일의 경로를 반환 */
    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }
    
    /** 외장메모리에 임시 이미지 파일을 생성하여 그 파일의 경로를 반환  */
    private File getTempFile() {
        if (isSDCARDMOUNTED()) {
            File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
                    TEMP_PHOTO_FILE);
            try {
                f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
            } catch (IOException e) {
            }
 
            return f;
        } else
            return null;
    }

    /** SD카드가 마운트 되어 있는지 확인 */
    private boolean isSDCARDMOUNTED() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
 
        return false;
    }
    
    /** 다시 액티비티로 복귀하였을때 이미지를 셋팅 */
    protected void onActivityResult(int requestCode, int resultCode,
            Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
 
        switch (requestCode) {
        case REQ_CODE_PICK_IMAGE:
            if (resultCode == RESULT_OK) {
                if (imageData != null) {
                	 filePath = Environment.getExternalStorageDirectory()
                            + "/temp.jpg";
              

                	System.out.println("path" + filePath); // logCat으로 경로확인.
                	
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize=4;                    
                   
                    
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath,options);
                    Bitmap resized = Bitmap.createScaledBitmap(selectedImage, dstWidth, dstHeight, true);
                    		
                                       
                    l_photo.setImageBitmap(resized); 
                    // temp.jpg파일을 이미지뷰에 씌운다.
                    test();
                  
                }
           
                else{
                
                	return;
                }
            }
    
        }
    }
	
	public void Onlogout(View v){
		StartlogOut();
	}
	

	private void StartlogOut() {
		CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		Log.i("cookieManager:" ,"removeAllCookie");
		cookieManager.removeSessionCookie();
		cookieManager.removeAllCookie();
		cookieManager.removeExpiredCookie();
		
		threadSleep();
		cookieSyncManager.sync();
		
		SharedPreferences pref = getSharedPreferences("cookie", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor =pref.edit();
		editor.remove("name");
		editor.remove("keyid");
		editor.remove("value");
		editor.remove("domain");
		editor.remove("path");
		editor.remove("check_AutoLogin");			
		editor.commit();
		

		MainActivity aActivity=(MainActivity)MainActivity.AActivity;
		aActivity.finish();
		Toast.makeText(getBaseContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(getBaseContext(), activity_login.class);
		startActivity(intent);
		finish();
	}
	
	 private void threadSleep() {
	        try {
	        	Log.i("threadSleep:" ,"쉬기");
	            Thread.sleep(500);
	        } catch (Exception e) {
	            Log.d("threadSleep", "thread Exception!");
	        }
	    }
class loadJsp extends AsyncTask<Void, String, Void>{
		
	
		public HttpClient client =new DefaultHttpClient();
		CookieManager cookieManager=CookieManager.getInstance();
		String postURL = "http://192.168.10.43:8080/homepage/android/android_profile.jsp";

		@Override
		protected Void doInBackground(Void... params) {
			try {					
				
				HttpPost post=new HttpPost(postURL);
				ArrayList<NameValuePair> params1 = new ArrayList<NameValuePair>(); 
				//파라미터를 list에 담아 보내기 
				
				params1.add(new BasicNameValuePair("keyid", keyid));
				
				
				UrlEncodedFormEntity ent= new UrlEncodedFormEntity(params1,HTTP.UTF_8);
				post.setEntity(ent);
		          				             
	            HttpResponse responsePOST = client.execute(post); //jsp 결과값 받아오기
				HttpEntity resEntity=responsePOST.getEntity();
							
	         	final String responseData= EntityUtils.toString(resEntity).toString().trim();		
	         	System.out.println("responseData:"+responseData);
				if(resEntity !=null){
				
				
					new Thread(){

						@Override
						public void run() {
						
							try {
								
								URL url=new URL(responseData);
									System.out.println("url"+url);
								 URLConnection conn = url.openConnection(); 
					             conn.connect(); 
					             BufferedInputStream bis = new BufferedInputStream(conn.getInputStream()); 
					             BitmapFactory.Options options = new BitmapFactory.Options();
				                  options.inSampleSize=4;                    
				                   
				                  Bitmap bm = BitmapFactory.decodeStream(bis); 
				                 
				                  Bitmap resized = Bitmap.createScaledBitmap(bm, dstWidth, dstHeight, true);

					             bis.close(); 
								l_photo.setImageBitmap(resized);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						
					}.start();
					
				
					}		  						
							
				
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			return null;
		}
		
	
	
	}
	



}

