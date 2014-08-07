package com.example.sns;



import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.sns.AndroidUploader2.ReturnCode;

public class WriteActivity2 extends Activity {
	private static final String TAG = null;
	//int REQUEST_CODE = 999;
	File imgF;
	String path = null;
	private ImageView mPhotoImageView;
	InputStream in;
	
	
	Uri uriparam;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_write);
	    
	    //setLayout();
	    
	    Button b3 = (Button) findViewById(R.id.b3);
	    
	    b3.setOnClickListener(
	    		new OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View v) {
	    				int sdk = android.os.Build.VERSION.SDK_INT;
	    				int kitkat = Build.VERSION_CODES.KITKAT;
	    				
	    				//킷캣구별하여 여는 갤러리 다르게
	    				if(sdk == kitkat){
	    					/*Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
	    					//intent.setAction(Intent.ACTION_GET_CONTENT);
	    					intent1.addCategory(Intent.CATEGORY_OPENABLE);
	    					intent1.setType("image/*");			

	    					startActivityForResult(intent1, 222);*/
	    					Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    				intent.setType("image/*");
		    				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    				startActivityForResult(intent, 222);
	    				}
	    				else{
	    					Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    				intent.setType("image/*");
		    				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    				startActivityForResult(intent, 222);
	    				}
	    			}
	    			
	    		}
	   );
	    
	    Button b4 = (Button) findViewById(R.id.b4);
	    
	    b4.setOnClickListener(
	    		new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent();
						intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, 222);

					}
					
				}
	    );
	    
	    Button btn5 = (Button) findViewById(R.id.b5);
	    btn5.setOnClickListener(
	    		new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						try {
							EditText edittext = (EditText) findViewById(R.id.editText1);
							String content = edittext.getText().toString();
							
				            AndroidUploader2 uploader = new AndroidUploader2(var.id, var.no, content);
				            //String path = Environment.getExternalStorageDirectory()+"/DCIM/Camera/test.jpg";
				            if(path != null){
				            	ReturnCode ecode = uploader.uploadPicture(path);
				            	finish();
				            }
				            else{
				            	ReturnCode ecode = uploader.onlyuploadText();
				            	finish();
				            }
				            //System.out.println(ecode);
				        } catch (Exception e) {
				            Log.e(e.getClass().getName(), e.getMessage());
				        }
					}
				}
	    );
	    
	    
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		/*case 999:
			if (resultCode == RESULT_CANCELED)
				return;
			if (data != null) {
				Uri uri = data.getData();

				// int sdk = android.os.Build.VERSION.SDK_INT;
				// int kitkat = Build.VERSION_CODES.KITKAT;
				// Log.d("uri", uri.toString());
				//String path = getPath(uri);
				// Log.d("path", path);

				// File imgF = new File(uri.toString());
				File imgF = new File(path);

				try {
					in = new FileInputStream(imgF);
					Bitmap bm = BitmapFactory.decodeStream(in);
					mPhotoImageView.setImageBitmap(bm);
				} catch (IOException e) {
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// imgUri = createSaveCropFile();
				// File cpoy_file = new File(imgUri.getPath());

				// SD카드에 저장된 파일을 이미지 Crop을 위해 복사한다.
				// copyFile(original_file , cpoy_file);
			}
			break;
*/		
		/*case 000:
			if (resultCode == RESULT_CANCELED)
				return;
			if (data != null) {
				Uri imgUri = data.getData();
				try {
					ImageView img1 = (ImageView) findViewById(R.id.img1);
					Bitmap bitmap = Images.Media.getBitmap(
							getContentResolver(), imgUri);
					img1.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;*/
		//갤러리 부분
		case 222:
			if (resultCode == RESULT_CANCELED)
				return;
			if (data != null) {
				Uri uri = null;
				if (data != null) {
					
					uri = data.getData();
					//String path = uri.getPath();
					path = getPath(getBaseContext(), uri);
					Log.d("path", path);
					showImage(uri);
					//imgF = new File(path);
					//System.out.println("file" + imgF);
					//Bitmap bm = 
					
					//mPhotoImageView.setImageBitmap(bm);
				}
			}
			break;
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int column_index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(column_index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
	
	
	
	//이미지뷰에 셋팅
	private void showImage(Uri uri){
		AsyncTask<Uri, Void, Bitmap> imageLoadAsyncTask = new AsyncTask<Uri, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Uri... params) {
				//uriparam = params[0];
				return getBitmapFromUri(params[0]);
				
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				//ImageView imagView = (ImageView) findViewById(R.id.img1);
				/*ImageView iv = new ImageView(this);
				iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				
				
				BitmapFactory.Options options = new BitmapFactory.Options();
			    options.inSampleSize = 4;
			    Bitmap resized = Bitmap.createScaledBitmap(result, 100, 100, true);
				iv.setImageBitmap(resized);*/
				createImageView(result);
			}
			
		};
		imageLoadAsyncTask.execute(uri); 
	}

	//이미지를 비트맵으로 세팅	크기가 크면 세팅 불가
	private Bitmap getBitmapFromUri(Uri uri) {
		   ParcelFileDescriptor parcelFileDescriptor = null;
		   try {
		      parcelFileDescriptor = getApplicationContext().getContentResolver().openFileDescriptor(uri, "r");
		      FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		      Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
		      parcelFileDescriptor.close();
		      return image;
		    } catch (Exception e) {
		      Log.e(TAG, "Failed to load image.", e);
		      return null;
		    } finally {
		      try {
		           if (parcelFileDescriptor != null) {
		               parcelFileDescriptor.close();
		           }
		      } catch (IOException e) {
		          e.printStackTrace();
		          Log.e(TAG, "Error closing ParcelFile Descriptor");
		      }
		    }
		}
	
	public void createImageView(Bitmap result){
		final ImageView iv = new ImageView(this);
		//EditText ed = (EditText) findViewById(R.id.editText1);
		final LinearLayout ll = (LinearLayout) findViewById(R.id.lienear1);
		iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ll.addView(iv);
		BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inSampleSize = 4;
	    Bitmap resized = Bitmap.createScaledBitmap(result, 200, 200, true);
		iv.setImageBitmap(resized);
		
		iv.setOnClickListener(
				new OnClickListener() {
					
					//삭제 확인창
					@Override
					public void onClick(View v) {
						//System.out.println("왔냐??");
						AlertDialog.Builder ab = new AlertDialog.Builder(WriteActivity2.this);
				        ab.setMessage("삭제하시겠습니까?")
				        .setCancelable(false)
				        .setPositiveButton("예", 
				         new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog, int which) {
				            	//LinearLayout ll = (LinearLayout) findViewById(R.id.lienear1);
				            	
				            	ll.removeView(iv);
				            	path = null;
				            	//System.out.println(path == null);
				            }
				         }).setNegativeButton("아니오", 
				         new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog, int which) {
				               dialog.cancel();
				               //System.out.println(path);
				            }
				         });
				      AlertDialog alert = ab.create();
				      alert.show();
						
					}
				}
		);
	}
	/*
	private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        //Log.d("path", "test1");
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        //Log.d("path", "test2");
        Cursor mCursor = getContentResolver().query(uri, projection, null, null, 
                MediaStore.Images.Media.DATE_MODIFIED + " desc");
        //Log.d("path", "test3");
        if(mCursor == null || mCursor.getCount() < 1) {
        	Log.d("path", "test4");
            return null; // no cursor or no record
        }
        int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        mCursor.moveToFirst();
 
        String path = mCursor.getString(column_index);
 
        if (mCursor !=null ) {
            mCursor.close();
            mCursor = null;
        }
 
        return path;
    }
	
	private String getPathK(Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {

			cursor = getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}

		} finally {
			if (cursor != null)
				cursor.close();
		}

		return null;
    }
	
	private void setLayout(){
	        mPhotoImageView = (ImageView)findViewById(R.id.img1);
	}

	private Uri createSaveCropFile(){
        Uri uri;
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        return uri;
    }

	public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally  {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
	
	private static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            OutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

*/
	
	/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
			 if (resultCode == RESULT_OK) {
	                if (data != null) {
	                	 Uri uri = data.getData(); // 선택 사진 URI 정보.
	                	 
	                	 int sdk = android.os.Build.VERSION.SDK_INT;	
	                	 
	                	 String filePath;
	                	// if(sdk == Build.VERSION_CODES.KITKAT){
	                		 filePath = getPath1(uri);
	                	 //}
	                	 //else{
	                	//	 String filePath1 = getPath1(uri);
	                		 
	                	 //}
	 
	                    Log.d("path" , filePath); // logCat으로 경로확인.
	 
	                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
	                    // temp.jpg파일을 Bitmap으로 디코딩한다.
	 
	                    ImageView _image = (ImageView) findViewById(R.id.img1);
	                    _image.setImageBitmap(selectedImage); 
	                    // temp.jpg파일을 이미지뷰에 씌운다.
	                }
	            }

		}
	}
	
	
	public String getPath1(Uri uri) {
		   String[] projection = { MediaStore.Images.Media.DATA };
		   Cursor cursor = managedQuery(uri, projection, null, null, null);
		   startManagingCursor(cursor);
		   int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		   cursor.moveToFirst();
		   //Log.d("ptest" , "test");
		 return cursor.getString(column_index);
		}
*/	
	/*
	// 선택한 이미지 경로 가져오기.
	public final String getPath(Uri uri) {

		// TEST
		// HTC:
		// content://com.android.providers.media.documents/document/image:.....
		// GalaxyS4 : content://media/external/images/media/....

		// * 기존에 갤러리에서 호출했던 방식.
		// Cursor c =
		// mActivity.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI,
		// null,
		// Images.Media._ID + "=?",
		// new String[] { uri.getLastPathSegment() } ,
		// null);
		// @@@@@@
		// content://com.android.providers.media.documents/document/image%....
		// getAuthority = "com.android.providers.media.documents"
		//
		// content://media/external/images/media/....
		// getAuthority = "media"
		//

		final boolean isAndroidVersionKitKat = Build.VERSION.SDK_INT >= 19; // (
																			// ==
																			// Build.VERSION_CODE.KITKAT
		int sdk = android.os.Build.VERSION.SDK_INT;						// )
		int kitkat = Build.VERSION_CODES.KITKAT;
		// Check Google Drive.
		if (isGooglePhotoUri(uri)) {
			return uri.getLastPathSegment();
		}

		// 1. 안드로이드 버전 체크
		// com.android.providers.media.documents/document/image :: uri로 전달 받는
		// 경로가 킷캣으로 업데이트 되면서 변경 됨.
		if (isAndroidVersionKitKat && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {

			// com.android.providers.media.documents/document/image:1234 ...
			//
			if (isMediaDocument(uri) && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;

				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

				} else if ("video".equals(type)) {
					return null; // 필자는 이미지만 처리할 예정이므로 비디오 및 오디오를 불러오는 부분은 작성하지
									// 않음.

				} else if ("audio".equals(type)) {
					return null;
				}

				final String selection = Images.Media._ID + "=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(mActivity.getApplicationContext(),
						contentUri, selection, selectionArgs);
			}

		}

		// content://media/external/images/media/....
		// 안드로이드 버전에 관계없이 경로가 com.android... 형식으로 집히지 않을 수 도 있음. [ 겔럭시S4 테스트 확인
		// ]
		if (isPathSDCardType(uri)) {

			final String selection = Images.Media._ID + "=?";
			final String[] selectionArgs = new String[] { uri
					.getLastPathSegment() };

			return getDataColumn(mActivity.getApplicationContext(),
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection,
					selectionArgs);
		}

		// File 접근일 경우
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	// URI 를 받아서 Column 데이터 접근.
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {

			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}

		} finally {
			if (cursor != null)
				cursor.close();
		}

		return null;
	}

	// 킷캣에서 추가된 document식 Path
	public static boolean isMediaDocument(Uri uri) {

		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	// 기본 경로 ( 킷캣 이전버전 )
	public static boolean isPathSDCardType(Uri uri) {
		// Path : external/images/media/ID(1234...)
		return "external".equals(uri.getPathSegments().get(0));
	}

	// 구글 드라이브를 통한 업로드 여부 체크.
	public static boolean isGooglePhotoUri(Uri uri) {

		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

*/
}
