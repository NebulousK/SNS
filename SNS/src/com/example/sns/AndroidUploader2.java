package com.example.sns;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import android.os.AsyncTask;
import android.util.Log;

public class AndroidUploader2 {
	// static String serviceDomain = "http://192.168.10.11:8080/homepage";
	 static String serviceDomain = "http://54.178.192.82/homepage";
	 //http://192.168.0.28/homepage/index.html
	    static String postUrl = serviceDomain + "/someNsome/menu1/apacheUpload.jsp";
	    static String CRLF = "\r\n"; 
	    static String twoHyphens = "--"; 
	    static String boundary = "*****b*o*u*n*d*a*r*y*****"; 
	
	    private String pictureFileName = null;
	    private String id = null;
	    private String content = null;
	    private int no = 0;
	    private DataOutputStream dataStream = null;

	    enum ReturnCode { noPicture, unknown, http201, http400, http401, http403, http404, http500};

	    private String TAG = "멀티파트테스트";  

	    public AndroidUploader2(String id, int no,String content){
			this.id = id;
			this.no = no;
	        this.content = content;
	    }

	    public static void setServiceDomain(String domainName){
	        serviceDomain = domainName;
	    }

	    public static String getServiceDomain()	    {
	        return serviceDomain;
	    }
	     	   
	    public ReturnCode uploadPicture(String pictureFileName)	    {
	    	System.out.println("if1");
	    	System.out.println("if");
	    	System.out.println(pictureFileName != null); 
	        if (pictureFileName != null || pictureFileName.equals(null)){
	        	this.pictureFileName = pictureFileName;
		        File uploadFile = new File(pictureFileName);
	            try { 
	                FileInputStream fileInputStream = new FileInputStream(uploadFile); 
	                URL connectURL = new URL(postUrl);
	                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection(); 
	                
	                conn.setDoInput(true); 
	                conn.setDoOutput(true); 
	                conn.setUseCaches(false); 
	                conn.setRequestMethod("POST"); 

	                //conn.setRequestProperty("User-Agent", "myFileUploader");
	                
	                conn.setRequestProperty("Connection","Keep-Alive"); 
	                //conn.setRequestProperty("accept-language","ko"); 
	                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary); 

	                conn.connect();
	                //String name1 = new String(name.getBytes("8859_1"));
	                dataStream = new DataOutputStream(conn.getOutputStream()); 
	                
	                System.out.println(id);
	                System.out.println(no);
	                writeFormField("id", id);
	                writeFormField("id_no", Integer.toString(no));
	                writeFormField("content", content);
	                writeFileField("photo1", pictureFileName, "image/jpg", fileInputStream);

	                // final closing boundary line

	                dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF); 

	                fileInputStream.close(); 

	                dataStream.flush(); 
	                dataStream.close();
	                dataStream = null;
	                
	                Log.d("업로드Test", "***********전송완료***********");

	                String response = getResponse(conn);
	                int responseCode = conn.getResponseCode();
	                
	                if (response.contains("uploaded successfully"))
	                    return ReturnCode.http201;
	                else 
	                    // for now assume bad name/password
	                    return ReturnCode.http401; 
	            }
	     
	            catch (MalformedURLException mue) { 
	            	System.out.println("400");
	                Log.e(TAG, "error: " + mue.getMessage(), mue); 
	                return ReturnCode.http400;
	            } 

	            catch (IOException ioe) { 
	            	System.out.println("500");
	                Log.e(TAG, "error: " + ioe.getMessage(), ioe); 

	                return ReturnCode.http500;

	            } 

	            catch (Exception e) { 
	            	System.out.println("un");
	                Log.e(TAG, "error: " + e.getMessage(), e); 

	                return ReturnCode.unknown;

	            }
	        }
	        return null;
	    }
	     
	    public ReturnCode onlyuploadText(){ 
	            try { 
	         
	                URL connectURL = new URL(postUrl);
	                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection(); 
	                
	                conn.setDoInput(true); 
	                conn.setDoOutput(true); 
	                conn.setUseCaches(false); 
	                conn.setRequestMethod("POST"); 

	                //conn.setRequestProperty("User-Agent", "myFileUploader");
	                
	                conn.setRequestProperty("Connection","Keep-Alive"); 
	                //conn.setRequestProperty("accept-language","ko"); 
	                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary); 

	                conn.connect();
	                //String name1 = new String(name.getBytes("8859_1"));
	                dataStream = new DataOutputStream(conn.getOutputStream()); 
	                
	                System.out.println(id);
	                writeFormField("id", id);
	                writeFormField("id_no", Integer.toString(no));
	                writeFormField("content", content);

	                // final closing boundary line

	                dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF);  

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
	            	System.out.println("400");
	                Log.e(TAG, "error: " + mue.getMessage(), mue); 
	                return ReturnCode.http400;
	            } 

	            catch (IOException ioe) { 
	            	System.out.println("500");
	                Log.e(TAG, "error: " + ioe.getMessage(), ioe); 

	                return ReturnCode.http500;

	            } 

	            catch (Exception e) { 
	            	System.out.println("un");
	                Log.e(TAG, "error: " + e.getMessage(), e); 

	                return ReturnCode.unknown;

	            }
	        }
	    
	    
	    
	    private String getResponse(HttpURLConnection conn)	    {
	        try {
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
	            Log.e("왓더", "AndroidUploader: "+e);
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
	            // scoop up the reply from the server
	            int ch; 
	            StringBuffer sb = new StringBuffer(); 
	            while( ( ch = is.read() ) != -1 ) { 
	                sb.append( (char)ch ); 
	            } 
	            return sb.toString();   // TODO Auto-generated method stub
	        }
	        catch(Exception e)   {
	            //System.out.println("GeoPictureUploader: biffed it getting HTTPResponse");
	            Log.e("법규", "AndroidUploader: "+e);
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
	            //dataStream.writeBytes(fieldValue);
	            dataStream.writeUTF(fieldValue);
	            dataStream.writeBytes(CRLF);
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
	            //System.out.println("GeoPictureUploader.writeFormField: got: " + e.getMessage());
	            Log.e(TAG, "AndroidUploader.writeFormField: got: " + e.getMessage());
	        }
	    }

	    public static void main(String[] args)  {

	        if (args.length >= 0)  {

	            AndroidUploader2 gpu = new AndroidUploader2("john", 0, "notmyrealpassword");

	            String picName = args[0];

	            ReturnCode rc = gpu.uploadPicture(picName);

	            //System.out.printf("done");

	        }

	    }

	}


