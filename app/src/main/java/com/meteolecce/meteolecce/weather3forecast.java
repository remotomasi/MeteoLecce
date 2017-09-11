package com.meteolecce.meteolecce;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class weather3forecast extends AppCompatActivity {

    TextView txtTemp3d_1 = null, txtHum3d_1 = null, temp = null;
    int len = 0;
    String temp3d_1 = null, hum3d_1 = null;
    final String site3d = "http://ws1.metcheck.com/ENGINE/v9_0/json.asp?lat=40.4&lon=18.2&lid=22553";
    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // hh:mm:ss
    String today = sdf.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather3forecast);

        new readWeather3Lecce().execute();
    }

    private class readWeather3Lecce extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            txtTemp3d_1 = (TextView) findViewById(R.id.textView18);
            txtHum3d_1 = (TextView) findViewById(R.id.textView19);

            String str3d = "";
            HttpResponse response;
            DefaultHttpClient myClient3d = new DefaultHttpClient();
            //HttpClient myClient = HttpClientBuilder.create().build();
            HttpPost myConnection = new HttpPost(site3d);

            try {
                response = myClient3d.execute(myConnection);
                str3d = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject json = new JSONObject(str3d);
                temp3d_1 = "" + json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(0).getString("temperature");
                hum3d_1 = "" + json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(0).getString("humidity");

                len = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").length();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            txtTemp3d_1.setText(temp3d_1 + " °C");
            txtHum3d_1.setText(hum3d_1 + " %");
            super.onPostExecute(result);
        }
    }

    /** Called when the user taps the Send button */
    public void actualSituation(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    private abstract class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(weather3forecast.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }
    }
}
