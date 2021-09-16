package com.fst.myapplication.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlOpener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String url = "http://127.0.0.1/phpinfo.php";
		String url = "http://192.168.1.23:12345";
		
		new HttpUrlOpener().urlConnect(url);
	}
	
	public void urlConnect2(String fileURL) {

        URL url = null;
        try {
            url = new URL(fileURL);
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
                System.out.println("fileName = " + fileName);

                Log.d("log", "Content-Type = " + contentType);
                Log.d("log", "Content-Disposition = " + disposition);
                Log.d("log", "Content-Length = " + contentLength);
                Log.d("log", "fileName = " + fileName);

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(
                                httpConn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.d("log", line);
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



	}

    public void urlConnect(String weburl) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(weburl);
            Log.d("log", "url = " + url);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type","application/json; charset=utf-8");
            urlConnection.setRequestProperty("Accept","application/json; charset=utf-8");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            // Starts the query

            //urlConnection.connect();

            int code = urlConnection.getResponseCode();
            Log.d("log", "code = " + code);

            if (code !=  200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(
                                    new InputStreamReader(
                                        urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Log.d("log", line);
            	System.out.println(line);
            }
            rd.close();

        } catch (Exception e) {
            Log.d("log", "Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
