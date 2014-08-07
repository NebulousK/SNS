package com.example.sns;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


public class activity_signup extends Activity{

	EditText id, password, check_password, name, birthday, age, tel, tel2, tel3,  email1, email2, num1, num2,addr;
	ImageView photo;
	Button id_button;
	String sex="남성";
	TextView id_check,pw_check2,email_check;
	int mYear;
	int mMonth;
	int mDay;
	
	int dstWidth=150;//사진크기 조절
    int dstHeight=150;
    
  
    static Uri selPhotouri=null;
    Uri uri=null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****"; 
    
    FileInputStream mFileInputStream = null;
    URL connectUrl = null;
    private static final int ZIP_REQUEST=1;
	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
	private static final int REQ_CODE_PICK_IMAGE = 0;
	final int REQ_CODE_GALLERY=100;
	static String filePath="";
	 String path="";
	
	ProgressDialog pd;

	String result_e="";//이메일
	String result_p="";//비번
	String result_i="";//아이디

	
	Handler handler=new Handler();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_singup);
	    
	    id=(EditText)findViewById(R.id.id);
	    password=(EditText)findViewById(R.id.password);
	    check_password=(EditText)findViewById(R.id.check_password);
	    name=(EditText)findViewById(R.id.name);
	    birthday=(EditText)findViewById(R.id.birthday);
	    birthday.setFocusable(false);
	    
	    age=(EditText)findViewById(R.id.age);
	    tel=(EditText)findViewById(R.id.tel);
	    tel2=(EditText)findViewById(R.id.tel2);
	    tel3=(EditText)findViewById(R.id.tel3);
	    email1=(EditText)findViewById(R.id.email1);
	    email2=(EditText)findViewById(R.id.email2);
	    email2.setFocusable(false);
	    
	    num1=(EditText)findViewById(R.id.num1);
	    num2=(EditText)findViewById(R.id.num2);
	    addr=(EditText)findViewById(R.id.addr);
	    photo=(ImageView)findViewById(R.id.photo);
	    id_check=(TextView)findViewById(R.id.id_check);
	    pw_check2=(TextView)findViewById(R.id.pw_check2);
	    email_check=(TextView)findViewById(R.id.email_check);
	 	
	  findViewById(R.id.man).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sexChecked(v);
				
			}
		});
	   findViewById(R.id.woman).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sexChecked(v);
			
				
			}
		});
	   
	 
	  
	  }
	
	//성별 선택
	public void sexChecked(View v){
		RadioButton radio=(RadioButton)v;
		
		if(radio.isChecked()){
			sex=radio.getText().toString();
		}
	}
	
	//생년월일 선택
	public void OneDate(View v){
		final Calendar cal= Calendar.getInstance();
	
		mYear = cal.get(Calendar.YEAR);
	    mMonth = cal.get(Calendar.MONTH);
	    mDay = cal.get(Calendar.DAY_OF_MONTH);
	    
	    DatePickerDialog.OnDateSetListener mDateSetListener=
	    		new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						
						String date_selected=String.valueOf(year)+
								"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth);
						
						birthday.setText(date_selected);					
					}
				};
				DatePickerDialog alert=new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
				alert.show();
		   
	}

	//우편번호 받아오기
	public void search_num(View v){
		Intent intent = new Intent(getBaseContext(), activity_web.class);
		startActivityForResult(intent, ZIP_REQUEST);
		
	}
	
	
	//이메일 선택
	public void Onemail(View v){
	final String items[] = {"chol.com", "dreamwiz.com", "empal.com", "hanmir.com", 
								"hanafos.com", "hotmail.com", "lycos.co.kr", "nate.com", 
								"naver.com", "daum.net","hanmail.net","gmail.com",  
								"paran.com","yahoo.co.kr", "직접입력"};
		
		
		AlertDialog.Builder ab=new AlertDialog.Builder(activity_signup.this);
		ab.setTitle(":: 선택 ::");
		ab.setSingleChoiceItems(items, 
				0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(which!=14){
							email2.setText(items[which]);			
						}
						else{
							email2.setText("");
							
						}
						dialog.cancel();					
					}
					});		
		ab.show();
		
		
	}

	//이미지 선택
	public void SelectImg(View v){
	
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
	
	
	//회원가입버튼 클릭
	public void Onsignup(View v){			
		
		if (id.getText().toString().equals("")|| password.getText().toString().equals("") || check_password.getText().toString().equals("")
				|| name.getText().toString().equals("") || birthday.getText().toString().equals("") || age.getText().toString().equals("")
				|| tel.getText().toString().equals("") || tel2.getText().toString().equals("") || tel3.getText().toString().equals("") 
				|| email1.getText().toString().equals("") || email2.getText().toString().equals("") ||email2.getText().toString().equals("::선택::") 
				|| num1.getText().toString().equals("") || num2.getText().toString().equals("") || addr.getText().toString().equals("") 
				|| filePath.equals("")){
			
			
			StartMError();
		}

		else {			
			checkTest();
		}	
		
		
		
	}
	public void StartM(){
		new AlertDialog.Builder(this)
		.setMessage("가입이 완료되었습니다.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				StartMain();
			}
		}).show();
	}
	
	public void StartMError(){
		new AlertDialog.Builder(this)
		.setTitle("회원가입 오류")
		.setMessage("빈칸이 있습니다. 다시 확인해 주세요.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	
	public void StartMError2(){
		new AlertDialog.Builder(this)
		.setTitle("이메일 오류")
		.setMessage("이미 등록된 email 입니다.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	
	public void StartIdMError(){
		new AlertDialog.Builder(this)
		.setMessage("아이디 중복 검사를 해주세요.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	public void StartPSMError(){
		new AlertDialog.Builder(this)
		.setMessage("비밀번호를 확인해주세요.")
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 자동 생성된 메소드 스텁
				dialog.dismiss();
				
			}
		}).show();
	}
	public void StartMain(){
	
		
		Intent intent=new Intent(getBaseContext(),activity_login.class);
		startActivity(intent);
		finish();
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
                    		
                                       
                    photo.setImageBitmap(resized); 
                    // temp.jpg파일을 이미지뷰에 씌운다.
                    
               
                }
           
                else{
                
                	return;
                }
            }
        case ZIP_REQUEST:
        	if (resultCode == RESULT_OK) {            
            	num1.setText(imageData.getStringExtra("pc1"));
            	num2.setText(imageData.getStringExtra("pc2"));
            	addr.setText(imageData.getStringExtra("addr"));
        	}
            break;
        }
    }



	//이미지 가장자리 둥글게 만들기
	public Bitmap getRoundedCornerBitmap(Context context, Bitmap bitmap , int roundLevel) { 
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
	    Canvas canvas = new Canvas(output); 
	  
	    final int color = 0xff424242; 
	    final Paint paint = new Paint(); 
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
	    final RectF rectF = new RectF(rect); 
	    final float roundPx = convertDipsToPixels(context, roundLevel); 
	  
	    paint.setAntiAlias(true); 
	    canvas.drawARGB(0, 0, 0, 0); 
	    paint.setColor(color); 
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
	  
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
	    canvas.drawBitmap(bitmap, rect, rect, paint); 
	  
	    return output; 
	} 
	 
	public static int convertDipsToPixels(Context context, int dips) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dips * scale + 0.5f); 
	}




class XmlData{
	String id_c;
	String email_c;
	String password_c;
}
public void checkTest(){
	
	String m_searchTxt="";
	

	String postURL = "http://192.168.10.43:8080/homepage/android/android_member_check.jsp?email1="+email1.getText().toString()+
						"&email2="+email2.getText().toString()+"&id="+id.getText().toString()+"&password="+password.getText().toString()+
						"&check_password="+check_password.getText().toString();
	
	XmlData xmlData=null;
	ArrayList<XmlData> m_xmlData=new ArrayList<XmlData>();
	String sTag;
	

		try {
			
			XmlPullParserFactory fatory=XmlPullParserFactory.newInstance();
			fatory.setNamespaceAware(true);
			XmlPullParser xpp=fatory.newPullParser();
			
			URL u = new URL(postURL);
			InputStream in=u.openConnection().getInputStream();
			xpp.setInput(in,"utf-8");
			
			int eventType=xpp.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
				if(eventType==XmlPullParser.START_DOCUMENT){
					System.out.println("start");
				}else if(eventType==XmlPullParser.END_DOCUMENT){
					System.out.println("end");
				}else if(eventType==XmlPullParser.START_TAG){
					Log.i("start_tag", xpp.getName());
					sTag=xpp.getName();
					
					if(sTag.equals("email")){
						xmlData=new XmlData();
						xmlData.email_c=xpp.nextText();
						result_e=xmlData.email_c;
					}
					if(sTag.equals("id")){
						xmlData.id_c=xpp.nextText();
						result_i=xmlData.id_c;
					}
					if(sTag.equals("password")){
						xmlData.password_c=xpp.nextText();
						result_p=xmlData.password_c;
					}			
				  }else if(eventType==XmlPullParser.END_TAG){
					  sTag=xpp.getName();
					  if(sTag.equals("check")){
						  m_xmlData.add(xmlData);
						  xmlData=null;
					  }
				  }else if(eventType==XmlPullParser.TEXT){
					  
				  }
				eventType=xpp.next();			
			}			
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
		
		if(result_e.equals("1")){
			Log.i("result_e", result_e);
			Log.i("result_e", result_e);
			email_check.setText(null);
			email_check.setTextColor(Color.parseColor("#FF0000"));
			email_check.setText("이미 등록된 email 입니다.");	
			StartMError2();	
		}
		if(result_e.equals("2")){
			Log.i("result_e", result_e);
			email_check.setText(null);
			email_check.setTextColor(Color.parseColor("#22741C"));
			email_check.setText("사용할 수 있는 email 입니다.");	
		}
		if(result_p.equals("3")){
			Log.i("result_p", result_p);
			pw_check2.setText(null);
			pw_check2.setTextColor(Color.parseColor("#FF0000"));
			pw_check2.setText("비밀번호를 6~12자까지 입력해주세요.");			
			StartPSMError();
		}
		 if(result_p.equals("4")){
			Log.i("result_p", result_p);
			pw_check2.setText(null);
			pw_check2.setTextColor(Color.parseColor("#FF0000"));
			pw_check2.setText("비밀번호가 일치하지 않습니다.");		
			StartPSMError();
		}
		 if(result_p.equals("5")){
			Log.i("result_p", result_p);
			pw_check2.setText(null);
			pw_check2.setTextColor(Color.parseColor("#22741C"));
			pw_check2.setText("일치");		
		}
		if(result_i.equals("6")){
			Log.i("result_i", result_i);
			id_check.setText(null);
			id_check.setTextColor(Color.parseColor("#FF0000"));
			id_check.setText("이미 등록된 ID 입니다.");
		}
		 if(result_i.equals("7")){
			Log.i("result_i", result_i);
			id_check.setText(null);
			id_check.setTextColor(Color.parseColor("#22741C"));
			id_check.setText("사용할 수 있는 ID 입니다.");
		}
		if(result_i.equals("8")){
			Log.i("result_i", result_i);
			id_check.setText(null);
			id_check.setTextColor(Color.parseColor("#22741C"));
			id_check.setText("아이디는 소문자, 숫자만 입력가능합니다.");
		}
		if(result_i.equals("9")){
			Log.i("result_i", result_i);
			id_check.setText(null);
			id_check.setTextColor(Color.parseColor("#22741C"));
			id_check.setText("아이디를 입력해 주세요.");		
						
		}
		if(result_e.equals("2") && result_p.equals("5") && result_i.equals("7")){
			pd=ProgressDialog.show(activity_signup.this, "회원가입", "잠시만 기다려 주세요.");
			pd.setCancelable(true);
			Log.i("result25252525", "result25252525");
			Log.i("result_e:", result_e);
			Log.i("result_p:", result_p);
			Log.i("result_i:", result_i);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Looper.prepare();
					try {
						Thread.sleep(10000);
						
					} catch (InterruptedException e) {
						// TODO 자동 생성된 catch 블록
						e.printStackTrace();
					}
					pd.dismiss();
					
					StartM();
					
					Looper.loop();
				}
			}).start();
			
			try {
				AndroidUploader3 uploader = new AndroidUploader3(id.getText().toString(), password.getText().toString(),
						name.getText().toString(), check_password.getText().toString(), email1.getText().toString(),
						email2.getText().toString(), tel.getText().toString(),tel2.getText().toString(),tel3.getText().toString(), birthday.getText().toString(),
						age.getText().toString(), sex, num1.getText().toString(), num2.getText().toString(), addr.getText().toString());
				
				
				path = Environment.getExternalStorageDirectory()+"/temp.jpg";
				Log.i("Onsignup path", path);
				uploader.uploadPicture(path);
				
			} catch (Exception e) {
				Log.e(e.getClass().getName(),e.getMessage());
			}
		}
		
}
	
}
    



	
