package com.fst.myapplication.http;

import android.app.Dialog;
import android.util.Log;

import com.fst.myapplication.databinding.FragmentFiletransferBinding;
import com.fst.myapplication.db.FileTransfer;
import com.fst.myapplication.ui.Connexion.ConnexionFragment;
import com.fst.myapplication.ui.Filetransfer.FiletransferFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A utility that downloads a file from a URL.
 * @author www.codejava.net
 *
 */

public class HttpFileDownloader {
    private static final int BUFFER_SIZE = 4096;
    //private FragmentFiletransferBinding binding;
    FragmentFiletransferBinding binding = null;

    public HttpFileDownloader ( FragmentFiletransferBinding binding) {
        this.binding = binding;
    }


    public static void main(String[] args) {    	
    	//String fileURL = "http://localhost:12345/toSave/Projects/BlueStacks_4.32.90.1001_x64.zip";
    	//String fileURL = "http://localhost:12345/toSave/Projects/Detective.Chinatown.3.2021.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4";
    	//String fileURL = "http://127.0.0.1/Detective.Chinatown.3.2021.1080p.BluRay.x264.AAC5.1-%5bYTS.MX%5d.mp4";
        String fileURL = "http://192.168.1.3:12345/Camera/20210915_160855.mp4";

        String saveDir = "/storage/emulated/0/DCIM/Download";
        
    	new Thread() {
    		public void run() {
    			try {
    				//sleep(5000);
    	    		//new HttpFileDownloader().downloadFile(fileURL, saveDir);
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
        //binding.progressTextView.setText("Text view");

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
            FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            int writtenBytes = 0 ;

            while ((bytesRead = inputStream.read(buffer)) != -1) {        
			    fileOutputStream.write(buffer, 0, bytesRead);
			    
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

                binding.progressDownloadText.setText(
                        String.format("%.2f", prog) + " % | "
                        + getFileSize(writtenBytes));
            }

            String progress = binding.progressDownloadText.getText().toString();
            if(progress.startsWith("100.00 %")) {
                Log.d("log", "Download progress completed");

                String fn = fileURL.substring( fileURL.lastIndexOf('/')+1, fileURL.length() );
                Log.d("log","File Name : " + fn);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date currentDate = new Date();
                System.out.println(formatter.format(currentDate));
                Log.d("log","Date : " +formatter.format(currentDate));

                int pk = FiletransferFragment.db.getFileTransferRowsNumber() + 1 ;
                FiletransferFragment.db.addFileTransfer(new FileTransfer(
                        pk,
                        fn,
                        "download",
                        formatter.format(currentDate),
                        ConnexionFragment.connexionID));

                ConnexionFragment.numberDownloads++;
                FiletransferFragment.db.updateConnexion(
                        ConnexionFragment.connexionID,
                        ConnexionFragment.numberDownloads,
                        ConnexionFragment.numberUploads
                );


            }






            
 
            fileOutputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    /*public Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS: //we set this to 0
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }*/
    
    public String getFileSize(long sizeInBytes) {
    	if ( sizeInBytes < 1024 )
			return sizeInBytes + " bytes";
		else if ( sizeInBytes < 1024 * 1024 )
			return sizeInBytes/1024 + "." + (sizeInBytes%1024/10%100) + " KB";
		else
			return sizeInBytes/(1024*1024) + "." + sizeInBytes%(1024*1024)/10%100 + " MB";
    }


}	
