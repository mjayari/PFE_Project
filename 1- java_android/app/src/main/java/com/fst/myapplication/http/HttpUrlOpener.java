package com.fst.myapplication.http;

import android.util.Log;

import com.fst.myapplication.databinding.FragmentFiletransferBinding;
import com.fst.myapplication.db.FileTransfer;
import com.fst.myapplication.ui.Filetransfer.FiletransferFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

public class HttpUrlOpener {

    FragmentFiletransferBinding binding = null ;
    FiletransferFragment ftFragment;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String url = "http://127.0.0.1/phpinfo.php";
		String url = "http://192.168.1.23:12345";
		
		//new HttpUrlOpener().urlConnect(url);
	}

	public HttpUrlOpener ( FragmentFiletransferBinding binding) {
	    this.binding = binding;
    }

    public void setFileTransferFragment(FiletransferFragment ftFragment){
	    this.ftFragment = ftFragment;
    }
	
	public void urlConnect(String weburl) {
		
		HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(weburl);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            Log.d("log","code:" + code);
            if (code ==  200) {
                FiletransferFragment.userAuthenticated = true;
                binding.serverStatusTextView.setText("Server is running! | Status: Connected! ");
            }else if (code == 403) {
                FiletransferFragment.userAuthenticated = false;
                binding.serverStatusTextView.setText("Server is running! | User cannot be authenticated! ");
            }else{
                throw new IOException("Invalid response from server: " + code);
            }


            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));

            this.ftFragment.uploadFileList = new ArrayList<String>();
            this.ftFragment.uploadFileUrlTable = new Hashtable<String,String>();

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

                this.ftFragment.uploadFileList.add(jo.getString("fileName"));
                this.ftFragment.uploadFileUrlTable.put(jo.getString("fileName"), jo.getString("fileUrl"));

                fileList += jo.getString("fileName") + "\n";
            }


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
