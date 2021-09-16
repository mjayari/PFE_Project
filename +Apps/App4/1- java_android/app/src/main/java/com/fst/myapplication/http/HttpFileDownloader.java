package com.fst.myapplication.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
 
/**
 * A utility that downloads a file from a URL.
 * @author www.codejava.net
 *
 */
public class HttpFileDownloader {
    private static final int BUFFER_SIZE = 4096;
    
    
    public static void main(String[] args) {    	
    	//String fileURL = "http://localhost:12345/toSave/Projects/BlueStacks_4.32.90.1001_x64.zip";
    	//String fileURL = "http://localhost:12345/toSave/Projects/Detective.Chinatown.3.2021.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4";
    	String fileURL = "http://127.0.0.1/Detective.Chinatown.3.2021.1080p.BluRay.x264.AAC5.1-%5bYTS.MX%5d.mp4";
    	
    	
    	String saveDir = "C:\\+Downloads";
        
    	new Thread() {
    		public void run() {
    			try {
    				sleep(5000);
    	    		new HttpFileDownloader().downloadFile(fileURL, saveDir);
    	    	} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}.start();
    	System.out.println("test");
         
    }
 
    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public void downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        Log.d("log", "responseCode = " + responseCode);

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("File Size = " + getFileSize(contentLength));
            System.out.println("fileName = " + fileName);

            Log.d("log", "Content-Type = " + contentType);
            Log.d("log", "Content-Disposition = " + disposition);
            Log.d("log", "Content-Length = " + contentLength);
            Log.d("log", "File Size = " + getFileSize(contentLength));
            Log.d("log", "fileName = " + fileName);



            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            int writtenBytes = 0 ;

            while ((bytesRead = inputStream.read(buffer)) != -1) {        
			    outputStream.write(buffer, 0, bytesRead);
			    
			    writtenBytes += bytesRead ; 
			    //float prog = (float) (30 / 100) * 100;
			    float prog = ( (float) writtenBytes/contentLength ) * 100;	
			    
			    //System.out.println("prog = " + new DecimalFormat("##.##").format(prog));
			    //System.out.println("prog = " + String.format("%.2f", prog));
			    
			    System.out.println(
			    		writtenBytes 
			    		+ " | " + contentLength + " | " 
			    		+ String.format("%.2f", prog) + " % | " 
			    		+ getFileSize(writtenBytes));

                Log.d("log",writtenBytes
                        + " | " + contentLength + " | "
                        + String.format("%.2f", prog) + " % | "
                        + getFileSize(writtenBytes) );

            }
            
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
    
    public String getFileSize(long sizeInBytes) {
    	if ( sizeInBytes < 1024 )
			return sizeInBytes + " bytes";
		else if ( sizeInBytes < 1024 * 1024 )
			return sizeInBytes/1024 + "." + (sizeInBytes%1024/10%100) + " KB";
		else
			return sizeInBytes/(1024*1024) + "." + sizeInBytes%(1024*1024)/10%100 + " MB";
    }
    
    
}	
