package com.meteolecce.meteolecce;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import android.os.AsyncTask;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;


public class MainActivity extends AppCompatActivity {

    TextView txtTemp = null, txtPress = null;
    String temp = null, press = null;
    final String site = "http://api.openweathermap.org/data/2.5/weather?q=Lecce,it&appid=35222ccfcb5285d12e8a0e3222d59d9c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new readWeatherLecce().execute();

    }

    private class readWeatherLecce extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            txtTemp = (TextView) findViewById(R.id.textView7);
            txtPress = (TextView) findViewById(R.id.textView2);

            String str = "";
            HttpResponse response;
            DefaultHttpClient myClient = new DefaultHttpClient();
            //HttpClient myClient = HttpClientBuilder.create().build();
            HttpPost myConnection = new HttpPost(site);

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject json = new JSONObject(str);
                temp = json.getJSONObject("main").getString("temp");
                press = json.getJSONObject("main").getString("pressure");
                //txt.setText(module);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            txtTemp.setText(temp);
            txtPress.setText(press);
            super.onPostExecute(result);
        }
    }



    private abstract class GetContacts extends AsyncTask<Void, Void, Void> {
        //public void readWeatherLecce() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        //}

    }
}
