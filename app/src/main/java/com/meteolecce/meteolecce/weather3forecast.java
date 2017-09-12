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
import java.util.Date;

public class weather3forecast extends AppCompatActivity {

    TextView txtTemp3d_1 = null, txtHum3d_1 = null, day1 = null, day2 = null, day3 = null,
            txtTemp3d_2 = null, txtHum3d_2 = null, txtTemp3d_3 = null, txtHum3d_3 = null;
    int len = 0, temp1 = 0, temp2 = 0, temp3 = 0;
    int hum1 = 0, hum2 = 0, hum3 = 0;
    String temp3d_1 = null, hum3d_1 = null, dateJson = null,
            temp3d_2 = null, hum3d_2 = null,
            temp3d_3 = null, hum3d_3 = null;
    final String site3d = "http://ws1.metcheck.com/ENGINE/v9_0/json.asp?lat=40.4&lon=18.2&lid=22553";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // hh:mm:ss

    Date today = new Date();
    long ltime1 = today.getTime()+1*24*60*60*1000;
    long ltime2 = today.getTime()+2*24*60*60*1000;
    long ltime3 = today.getTime()+3*24*60*60*1000;
    String today1 = sdf.format(ltime1);
    String today2 = sdf.format(ltime2);
    String today3 = sdf.format(ltime3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather3forecast);

        new readWeather3Lecce().execute();
    }

    private class readWeather3Lecce extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            day1 = (TextView) findViewById(R.id.textView21);
            txtTemp3d_1 = (TextView) findViewById(R.id.textView18);
            txtHum3d_1 = (TextView) findViewById(R.id.textView19);
            day2 = (TextView) findViewById(R.id.textView22);
            txtTemp3d_2 = (TextView) findViewById(R.id.textView24);
            txtHum3d_2 = (TextView) findViewById(R.id.textView28);
            day3 = (TextView) findViewById(R.id.textView29);
            txtTemp3d_3 = (TextView) findViewById(R.id.textView31);
            txtHum3d_3 = (TextView) findViewById(R.id.textView33);

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

                dateJson = null;
                temp1 = temp2 = temp3 = 0;
                hum1 = hum2 = hum3 = 0;
                for (int i = 0; i < 150; i++) {
                    dateJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(0,10);
                    if (today1.equals(dateJson)) {
                        temp1 = temp1 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        hum1 = hum1 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                    }
                    if (today2.equals(dateJson)) {
                        temp2 = temp2 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        hum2 = hum2 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                    }
                    if (today3.equals(dateJson)) {
                        temp3 = temp3 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        hum3 = hum3 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                    }
                }

                temp3d_1 = "" + temp1/24;
                hum3d_1 = "" + hum1/24;
                temp3d_2 = "" + temp2/24;
                hum3d_2 = "" + hum2/24;
                temp3d_3 = "" + temp3/24;
                hum3d_3 = "" + hum3/24;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            day1.setText(today1);
            txtTemp3d_1.setText(temp3d_1 + " °C");
            txtHum3d_1.setText(hum3d_1 + " %");

            day2.setText(today2);
            txtTemp3d_2.setText(temp3d_2 + " °C");
            txtHum3d_2.setText(hum3d_2 + " %");

            day3.setText(today3);
            txtTemp3d_3.setText(temp3d_3 + " °C");
            txtHum3d_3.setText(hum3d_3 + " %");

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
