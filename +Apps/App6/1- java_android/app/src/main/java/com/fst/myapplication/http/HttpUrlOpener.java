package com.fst.myapplication.http;

import android.util.Log;

import com.fst.myapplication.databinding.FragmentFiletransferBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlOpener {

    private FragmentFiletransferBinding binding;

    public HttpUrlOpener(FragmentFiletransferBinding binding){
        this.binding = binding;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String url = "http://127.0.0.1/phpinfo.php";
		String url = "http://192.168.1.23:12345";
		
		//new HttpUrlOpener().urlConnect(url);
	}
	
	public void urlConnect(String weburl) {
		
		HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(weburl);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            Log.d("log","code:" + code);
            if (code !=  200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));

            String line;
            String body="";
            while ((line = rd.readLine()) != null) {
                //Log.i("data", line);
            	System.out.println(line);
                Log.d("log","line:" + line);
                body += line;
            }

            JSONArray ja = new JSONArray(body);
            System.out.println("ja:" + ja);

            String fileList="";
            for(int i=0; i< ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                System.out.println("jo:" + jo);

                System.out.println("fileName: " + jo.getString("fileName"));
                System.out.println("fileUrl: " + jo.getString("fileUrl"));

                Log.d("log","fileName: " + jo.getString("fileName"));
                Log.d("log","fileUrl: " + jo.getString("fileUrl"));

                fileList += jo.getString("fileName") + "\n";
            }
            binding.uploadPathRemoteText.setText(fileList);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("log","exception:" + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
	}
	

}
