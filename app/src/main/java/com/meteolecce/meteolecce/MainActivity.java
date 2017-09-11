package com.meteolecce.meteolecce;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;


public class MainActivity extends AppCompatActivity {

    TextView txtDate = null, txtTemp = null, txtPress = null, txtHum = null, txtWPow = null, txtWDir = null, txtClouds = null,
        txtPhenomen = null;
    String temp = null, press = null, hum = null, wPow = null, wDir = null, clouds = null, phenomenon = null;
    final String site = "http://api.openweathermap.org/data/2.5/weather?q=Lecce,it&appid=35222ccfcb5285d12e8a0e3222d59d9c";
    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // hh:mm:ss
    String today = sdf.format(date);
    //return sdf.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity
        Intent intent = getIntent();

        new readWeatherLecce().execute();
    }

    private class readWeatherLecce extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            txtDate = (TextView) findViewById(R.id.textView20);
            txtTemp = (TextView) findViewById(R.id.textView7);
            txtPress = (TextView) findViewById(R.id.textView2);
            txtHum = (TextView) findViewById(R.id.textView4);
            txtWPow = (TextView) findViewById(R.id.textView8);
            txtWDir = (TextView) findViewById(R.id.textView10);
            txtClouds = (TextView) findViewById(R.id.textView13);
            txtPhenomen = (TextView) findViewById(R.id.textView14);

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
                hum = json.getJSONObject("main").getString("humidity");
                wPow = json.getJSONObject("wind").getString("speed");
                wDir = json.getJSONObject("wind").getString("deg");
                clouds = json.getJSONObject("clouds").getString("all");
                phenomenon = "" + json.getJSONArray("weather").getJSONObject(0).getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Double tt = Double.parseDouble(temp);
            Double wp = Double.parseDouble(wPow);
            wp = wp * 3.6;
            tt = tt - 273.15;

            txtDate.setText(today);

            txtTemp.setText(String.format("%.2f", tt) + " °C");
            txtPress.setText(press + " hPa");
            txtHum.setText(hum + " %");
            txtWPow.setText(String.format("%.2f", wp) + " Km/h");
            txtWDir.setText(windDirection(Integer.parseInt(wDir)));
            txtClouds.setText(clouds + " %");
            txtPhenomen.setText(skyConversion(phenomenon));
            super.onPostExecute(result);
        }
    }

    /** Called when the user taps the Update button */
    public void update(View view) {
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /** Called when the user taps the Send button */
    public void forecast3weather(View view) {
        Intent intent=new Intent(MainActivity.this,weather3forecast.class);
        startActivity(intent);
    }

    /** Convert degree in a human comprehensible thing */
    public String windDirection(int deg) {
        String dir = null;

        if (deg > 335 || deg <= 25) {
            dir = "Nord";
        } else if (deg > 25 && deg <= 65) {
            dir = "Nord-Est";
        } else if (deg > 65 && deg <= 155) {
            dir = "Est";
        } else if (deg > 115 && deg <= 155) {
            dir = "Sud-Est";
        } else if (deg > 155 && deg <= 205) {
            dir = "Sud";
        } else if (deg > 205 && deg <= 245) {
            dir = "Sud-Ovest";
        } else if (deg > 245 && deg <= 295) {
            dir = "Ovest";
        } else if (deg > 295 && deg <= 335) {
            dir = "Nord-Ovest";
        }

        return dir;
    }

    /** Convert degree in a human comprehensible thing */
    public String skyConversion(String value) {
        String sky = null;

        if (value.equals("few clouds")) {
            sky = "Poche nubi";
        } else if (value.equals("thunderstorm")) {
            sky = "Temporale";
        } else if (value.equals("clear sky")) {
            sky = "Sereno";
        } else if (value.equals("scattered clouds")) {
            sky = "Nubi sparse";
        } else if (value.equals("broken clouds")) {
            sky = "Nuvoloso";
        } else if (value.equals("shower rain")) {
            sky = "Pioggia intensa";
        } else if (value.equals("rain")) {
            sky = "Pioggia";
        } else if (value.equals("light rain")) {
            sky = "Pioggia leggera";
        } else if (value.equals("snow")) {
            sky = "Neve";
        } else if (value.equals("mist")) {
            sky = "Nebbia";
        }

        return sky;
    }

    private abstract class GetContacts extends AsyncTask<Void, Void, Void> {
        //public void readWeatherLecce() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }
    }
}
