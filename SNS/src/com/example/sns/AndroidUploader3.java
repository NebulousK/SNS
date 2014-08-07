package com.example.sns;


import java.io.*;
import java.net.*;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import android.util.Log;




	public class AndroidUploader3	{

	    static String serviceDomain = "http://54.178.192.82/homepage";
	 
	    static String CRLF = "\r\n"; 

	    static String twoHyphens = "--"; 

	    static String boundary = "*****b*o*u*n*d*a*r*y*****"; 




	    private String pictureFileName = null;

	    private String id = null;

	    private String password = null;
	    
	    private String name = null;
	    
	    private String check_password = null;
	    
	    private String email1 = null;
	    
	    private String email2 = null;
	    
	    private String tel = null;
	    
	    private String tel2 = null;
	    
	    private String tel3 = null;
	    
	    private String birthday = null;
	    
	    private String age = null;
	    
	    private String sex = null;   
	    
	    private String num1=null;
	    
	    private String num2=null;
	    
	    private String addr=null;

	    private DataOutputStream dataStream = null;




	    enum ReturnCode { noPicture, unknown, http201, http400, http401, http403, http404, http500};




	    private String TAG = "��Ƽ��Ʈ �׽�Ʈ";

	    public AndroidUploader3(String id){
	    	this.id=id;
	    }

	    public AndroidUploader3(String id, String password, String name, String check_password, String email1, String email2, String tel,String tel2,String tel3,
	    							String birthday, String age, String sex, String num1, String num2, String addr){
	    	this.id = id;
	    	this.password = password;
	    	this.name = name;
	    	this.check_password=check_password;
	    	this.email1=email1;
	    	this.email2=email2;
	    	this.tel=tel;
	    	this.tel2=tel2;
	    	this.tel3=tel3;
	    	this.birthday=birthday;
	    	this.age=age;
	    	this.sex=sex;
	    	this.num1=num1;
	    	this.num2=num2;
	    	this.addr=addr;
	    	
	        Log.i("AndroidUploader ����: ", id+ ","+password+","+name +","
	        		+check_password+", "+email1+ ","+email2+ ","+tel+ ","+tel2+ ","+tel3+ ","
	        		+birthday+ ","+age + ","+sex+", "+num1+", "+num2+", "+addr);
	    }




	    public static void setServiceDomain(String domainName)	    {

	        serviceDomain = domainName;

	    }




	    public static String getServiceDomain()	    {

	        return serviceDomain;

	    }




	    public ReturnCode uploadPicture(String pictureFileName)	    {

	        this.pictureFileName = pictureFileName;

	        File uploadFile = new File(pictureFileName); 

	    
	        String postUrl =serviceDomain + "/android/android_member_join.jsp";
	    	System.out.println("uploadPicture:"+postUrl);
	        if (uploadFile.exists()){
	        	Log.i("pictureFileName", pictureFileName);
	        	Log.i("uploadFile", uploadFile.toString());

	            try 	{ 
	            	
	                FileInputStream fileInputStream = new FileInputStream(uploadFile); 

	                URL connectURL = new URL(postUrl);

	                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection(); 

	                Log.i("uploadFile.exists", "uploadFile.exists");


	                conn.setDoInput(true); 
	               

	                conn.setDoOutput(true); 

	                conn.setUseCaches(false); 

	                conn.setRequestMethod("POST"); 




	                

	                conn.setRequestProperty("Connection","Keep-Alive"); 

	                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary); 
	               
	                conn.setRequestProperty("accept-language","ko"); 



	                conn.connect();


	                new Thread(){
	                	
	                }.start();

	                dataStream = new DataOutputStream(conn.getOutputStream()); 



	                writeFormField("login", id);
	                writeFormField("password", password);
	                writeFormField("name", name );
	                writeFormField("check_password", check_password);
	                writeFormField("email1", email1);
	                writeFormField("email2", email2);
	                writeFormField("tel", tel);
	                writeFormField("tel2", tel2);
	                writeFormField("tel3", tel3);
	                writeFormField("birthday", birthday);
	                writeFormField("age",age);
	                writeFormField("sex", sex);
	                writeFormField("num1", num1);
	                writeFormField("num2", num2);
	                writeFormField("addr", addr);
	                
	                writeFileField("photo1", pictureFileName, "image/jpg", fileInputStream);

	                



	                // final closing boundary line

	                dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF); 


	               

	                fileInputStream.close(); 

	                dataStream.flush(); 

	                dataStream.close();

	                dataStream = null;

	                

	                Log.d("���ε� �׽�Ʈ", "***********��ۿϷ�***********");

	                

	                String response = getResponse(conn);

	                int responseCode = conn.getResponseCode();




	                if (response.contains("uploaded successfully"))

	                    return ReturnCode.http201;

	                else 

	                    // for now assume bad name/password

	                    return ReturnCode.http401; 

	            } 

	            catch (MalformedURLException mue) { 

	                Log.e(TAG, "errorM: " + mue.getMessage(), mue); 

	                return ReturnCode.http400;

	            } 

	            catch (IOException ioe) { 

	                Log.e(TAG, "errori: " + ioe.getMessage(), ioe); 

	                return ReturnCode.http500;

	            } 

	            catch (Exception e) { 

	                Log.e(TAG, "errorE: " + e.getMessage(), e); 

	                return ReturnCode.unknown;

	            }
	    }	
	        else    {

	            return ReturnCode.noPicture;  

	        }

	    }
	    public ReturnCode changePicture(String pictureFileName)	    {
	    	System.out.println("changePicture");
	    	this.pictureFileName = pictureFileName;
	    	
	    	File uploadFile = new File(pictureFileName); 
	    	
	    	 String postUrl=serviceDomain + "/android/android_profile_photo.jsp";
	    	
	    	 System.out.println("changePicture:"+postUrl);
	    	
	    	if (uploadFile.exists()){
	    		Log.i("changePicturepictureFileName", pictureFileName);
	    		Log.i("changePictureuploadFile", uploadFile.toString());
	    		
	    		try 	{ 
	    			
	    			FileInputStream fileInputStream = new FileInputStream(uploadFile); 
	    			
	    			URL connectURL = new URL(postUrl);
	    			
	    			HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection(); 
	    			
	    			Log.i("uploadFile.exists", "uploadFile.exists");
	    			
	    			
	    			conn.setDoInput(true); 
	    			
	    			
	    			conn.setDoOutput(true); 
	    			
	    			conn.setUseCaches(false); 
	    			
	    			conn.setRequestMethod("POST"); 
	    			
	    			
	    			
	    			
	    		
	    			
	    			conn.setRequestProperty("Connection","Keep-Alive"); 
	    			
	    			conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary); 
	    			
	    			conn.setRequestProperty("accept-language","ko"); 
	    			
	    			
	    			
	    			conn.connect();
	    			
	    			
	    			new Thread(){
	    				
	    			}.start();
	    			
	    			dataStream = new DataOutputStream(conn.getOutputStream()); 
	    			
	    			
	    			
	    			writeFormField("id", id);
	    				    			
	    			writeFileField("photo1", pictureFileName, "image/jpg", fileInputStream);
	    			
	    			
	    			
	    			
	    			
	    			// final closing boundary line
	    			
	    			dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF); 
	    			
	    			
	    			
	    			
	    			fileInputStream.close(); 
	    			
	    			dataStream.flush(); 
	    			
	    			dataStream.close();
	    			
	    			dataStream = null;
	    			
	    			
	    			
	    			Log.d("���ε� �׽�Ʈ222222", "***********��ۿϷ�***********");
	    			
	    			
	    			
	    			String response = getResponse(conn);
	    			
	    			int responseCode = conn.getResponseCode();
	    			
	    			
	    			
	    			
	    			if (response.contains("uploaded successfully"))
	    				
	    				return ReturnCode.http201;
	    			
	    			else 
	    				
	    			
	    				
	    				return ReturnCode.http401; 
	    			
	    		} 
	    		
	    		catch (MalformedURLException mue) { 
	    			
	    			Log.e(TAG, "errorM: " + mue.getMessage(), mue); 
	    			
	    			return ReturnCode.http400;
	    			
	    		} 
	    		
	    		catch (IOException ioe) { 
	    			
	    			Log.e(TAG, "errori: " + ioe.getMessage(), ioe); 
	    			
	    			return ReturnCode.http500;
	    			
	    		} 
	    		
	    		catch (Exception e) { 
	    			
	    			Log.e(TAG, "errorE: " + e.getMessage(), e); 
	    			
	    			return ReturnCode.unknown;
	    			
	    		}
	    	}	
	    	else    {
	    		
	    		return ReturnCode.noPicture;  
	    		
	    	}
	    	
	    }




	    private String getResponse(HttpURLConnection conn)	    {
	        try 	        {

	            DataInputStream dis = new DataInputStream(conn.getInputStream()); 

	            byte []        data = new byte[1024];

	            int             len = dis.read(data, 0, 1024);
	           




	            dis.close();

	            int responseCode = conn.getResponseCode();




	            if (len > 0)

	                return new String(data, 0, len);

	            else

	                return "";

	        }

	        catch(Exception e)     {

	            //System.out.println("AndroidUploader: "+e);

	            Log.e(TAG, "AndroidUploader: "+e);

	            return "";

	        }

	    }




	    /**

	     *  this mode of reading response no good either

	     */

	    private String getResponseOrig(HttpURLConnection conn)	    {

	        InputStream is = null;

	        try   {

	            is = conn.getInputStream(); 

	           

	            int ch; 

	            StringBuffer sb = new StringBuffer(); 

	            while( ( ch = is.read() ) != -1 ) { 

	                sb.append( (char)ch ); 

	            } 
	          
	            return sb.toString();   // TODO Auto-generated method stub
	            

	        }

	        catch(Exception e)   {

	          
	            Log.e(TAG, "AndroidUploader: "+e);

	        }

	        finally   {

	            try {

	            if (is != null)

	                is.close();

	            } catch (Exception e) {}

	        }




	        return "";

	    }




	    /**

	     * write one form field to dataSream

	     * @param fieldName

	     * @param fieldValue

	     */

	    private void writeFormField(String fieldName, String fieldValue)  {

	        try  {
	  
	            dataStream.writeBytes(twoHyphens + boundary + CRLF);    

	            dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + CRLF);

	            dataStream.writeBytes(CRLF);

	        
	            dataStream.writeUTF(fieldValue);

	            dataStream.writeBytes(CRLF);
	            Log.i("writeFormField", fieldName+fieldValue);
	          

	        }    catch(Exception e)   {

	            //System.out.println("AndroidUploader.writeFormField: got: " + e.getMessage());

	            Log.e(TAG, "AndroidUploader.writeFormField: " + e.getMessage());

	        }

	    }
	  




	    /**

	     * write one file field to dataSream

	     * @param fieldName - name of file field

	     * @param fieldValue - file name

	     * @param type - mime type

	     * @param fileInputStream - stream of bytes that get sent up

	     */

	    private void writeFileField(

	        String fieldName,

	        String fieldValue,

	        String type,

	        FileInputStream fis)  {

	        try {

	            // opening boundary line

	            dataStream.writeBytes(twoHyphens + boundary + CRLF);    

	            dataStream.writeBytes("Content-Disposition: form-data; name=\""

	                                  + fieldName

	                                  + "\";filename=\"" 

	                                  + fieldValue

	                                  + "\"" 

	                                  + CRLF);

	            dataStream.writeBytes("Content-Type: " + type +  CRLF);

	            dataStream.writeBytes(CRLF); 




	            // create a buffer of maximum size 

	            int bytesAvailable = fis.available(); 

	            int maxBufferSize = 1024; 

	            int bufferSize = Math.min(bytesAvailable, maxBufferSize); 

	            byte[] buffer = new byte[bufferSize]; 

	            // read file and write it into form... 

	            int bytesRead = fis.read(buffer, 0, bufferSize); 

	            while (bytesRead > 0)   { 

	                dataStream.write(buffer, 0, bufferSize); 

	                bytesAvailable = fis.available(); 

	                bufferSize = Math.min(bytesAvailable, maxBufferSize); 

	                bytesRead = fis.read(buffer, 0, bufferSize); 

	            } 




	            // closing CRLF

	            dataStream.writeBytes(CRLF);

	        }

	        catch(Exception e)  {

	        

	            Log.e(TAG, "AndroidUploader.writeFormField: got: " + e.getMessage());

	        }

	    }




	    public static void main(String[] args)  {
	    	//���� �ȵ�
	        if (args.length >= 0)  {

	     
	            Log.i("main","main");
	            String picName = args[0];

	        

	        }

	    }
}