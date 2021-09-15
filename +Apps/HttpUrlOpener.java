package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlOpener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String url = "http://127.0.0.1/phpinfo.php";
		String url = "http://192.168.1.23:12345";
		
		new HttpUrlOpener().urlConnect(url);
	}
	
	public void urlConnect(String weburl) {
		
		HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(weburl);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            if (code !=  200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                //Log.i("data", line);
            	System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
	}
	

}
