package gms.hs.kr;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;

public class RegisterStudent extends Activity {
	public final String SERVER_ADDDRESS = "http://jubeat.kr:8080/";	
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);
        WebView w = (WebView) findViewById(R.id.webView1);
        w.loadUrl("file:///android_asset/street_bg.html"); //
        
        getJSON g = new getJSON();
        new Thread(g).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    class getJSON implements Runnable {
		public void run() {
			Log.i("LIST", "학생 목록을 받아옵니다.");
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://jubeat.kr:8080/get/student_list");
			HttpResponse response;
			try {
				response = client.execute(get);
				if(response.getStatusLine().getStatusCode() == 200){
					Log.i("LIST", "학생 목록을 받아오는데 성공하였습니다.");
					HttpEntity entity = response.getEntity();
					String jsonString = EntityUtils.toString(entity);
					Log.i("LIST", jsonString);
					JSONArray json = new JSONArray(jsonString);
					JSONArray grade = json.getJSONArray(0);
					JSONArray kurasu = grade.getJSONArray(0);
					JSONObject fuck = kurasu.getJSONObject(0);
					Log.i("LIST", fuck.get("stu_name").toString());
					
				}
				
			} catch (Exception e) {
				Log.e("LIST", "학생 목록을 받아오는데 실패하였습니다.");
				Log.e("LIST", e.toString());
			}
			
			
		}
    }
    
}

