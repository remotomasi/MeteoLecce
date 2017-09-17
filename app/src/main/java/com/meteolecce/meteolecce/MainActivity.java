package com.meteolecce.meteolecce;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
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
    ImageView imgIco = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new readWeatherLecce().execute();
    }

    private class readWeatherLecce extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            txtDate = (TextView) findViewById(R.id.textView20);
            txtTemp = (TextView) findViewById(R.id.textView7);
            txtHum = (TextView) findViewById(R.id.textView4);
            txtWPow = (TextView) findViewById(R.id.textView8);
            txtWDir = (TextView) findViewById(R.id.textView10);
            txtClouds = (TextView) findViewById(R.id.textView13);
            txtPhenomen = (TextView) findViewById(R.id.textView14);
            imgIco = (ImageView) findViewById(R.id.imageViewIcon);

            String str = "";
            HttpResponse response;
            DefaultHttpClient myClient = new DefaultHttpClient();
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
                hum = json.getJSONObject("main").getString("humidity");
                wPow = json.getJSONObject("wind").getString("speed");
                if (json.getJSONObject("wind").has("deg"))
                    wDir = json.getJSONObject("wind").getString("deg");
                if (json.getJSONObject("clouds").has("all"))
                    clouds = json.getJSONObject("clouds").getString("all");
                if (json.getJSONArray("weather").getJSONObject(0).has("description"))
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

            txtTemp.setText(String.format("%.1f", tt).concat(" °C"));
            txtHum.setText(hum.concat(" %"));
            txtWPow.setText(String.format("%.1f", wp).concat(" Km/h"));
            //Log.e("Error wDir:", "" + Double.parseDouble(wDir));
            if (wDir != null) txtWDir.setText(windDirection((int) Double.parseDouble(wDir)));
            if (clouds != null) txtClouds.setText(clouds.concat(" %"));
            Log.i(phenomenon, "phen1 :");
            if (phenomenon != null) {
                imgIco.setVisibility(View.VISIBLE);
                txtPhenomen.setText(skyConversion(phenomenon));
                skyIcon(skyConversion(phenomenon), imgIco);
            } else {
                imgIco.setVisibility(View.INVISIBLE);
            }
            super.onPostExecute(result);
        }
    }

    /**
     * Called when the user taps the Update button
     */
    public void update(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Called when the user taps the Send button
     */
    public void forecast3weather(View view) {
        Intent intent = new Intent(MainActivity.this, weather3forecast.class);
        startActivity(intent);
    }

    /**
     * Convert degree in a human comprehensible thing
     */
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

    /**
     * Convert degree in a human comprehensible thing
     */
    public String skyConversion(String value) {
        String sky = null;

        switch (value) {
            case "few clouds":
                sky = "poche nubi";
                break;
            case "thunderstorm":
                sky = "temporale";
                break;
            case "clear sky":
                sky = "sereno";
                break;
            case "scattered clouds":
                sky = "nubi sparse";
                break;
            case "broken clouds":
                sky = "nuvoloso";
                break;
            case "shower rain":
                sky = "pioggia intensa";
                break;
            case "rain":
                sky = "pioggia";
                break;
            case "light rain":
                sky = "pioggia leggera";
                break;
            case "snow":
                sky = "neve";
                break;
            case "mist":
                sky = "nebbia";
                break;
        }

        return sky;
    }

    /**
     * Convert degree in a human comprehensible thing
     */
    public void skyIcon(String value, ImageView imgV) {

        switch (value) {
            case "sereno":
                imgV.setImageResource(R.drawable.sun);
                break;
            case "Poche nubi":
                imgV.setImageResource(R.drawable.sun_and_cloud);
                break;
            case "nubi sparse":
                imgV.setImageResource(R.drawable.bit_cloudy);
                break;
            case "nuvoloso":
                imgV.setImageResource(R.drawable.cloudy);
                break;
            case "pioggia":
                imgV.setImageResource(R.drawable.rain);
                break;
            case "pioggia leggera":
                imgV.setImageResource(R.drawable.very_light_rain);
                break;
            case "pioggia intensa":
                imgV.setImageResource(R.drawable.heavy_rain);
                break;
            case "temporale":
                imgV.setImageResource(R.drawable.thunderstorm);
                break;
            case "neve":
                imgV.setImageResource(R.drawable.snow);
                break;
            case "nebbia":
                imgV.setImageResource(R.drawable.fog3);
                break;
        }
    }
}
