package com.meteolecce.meteolecce;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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


public class
MainActivity extends AppCompatActivity {

    TextView txtDate = null, txtTemp = null, txtPress = null, txtHum = null, txtWPow = null, txtWDir = null, txtClouds = null,
            txtPhenomen = null;
    String temp = null, press = null, hum = null, wPow = null, wDir = null, clouds = null, phenomenon = null, phNext = null,
            dp = null, dateJson = null, hourJson = null;
    Double fogVis = 0.0;
    // final String site = "http://api.openweathermap.org/data/2.5/weather?q=Lecce,it&appid=35222ccfcb5285d12e8a0e3222d59d9c";
    final String metcheck = "http://ws1.metcheck.com/ENGINE/v9_0/json.asp?lat=40.45&lon=18.15&lid=22553&Fc=No"; // metcheck json site
    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss"); // hh:mm:ss
    SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfmN = new SimpleDateFormat("dd/MM/yyyy");
    String today = sdf.format(date);
    long ltime = date.getTime(); // - 1*60*60*1000; // subtract an hour or two hours for UTC time
    String today1 = sdf.format(ltime);
    String todaym = sdfm.format(ltime);
    String todaymN = sdfmN.format(date);
    ImageView imgIco = null, imgNext = null, imgFog = null;

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
            txtWPow = (TextView) findViewById(R.id.textView10);
            txtWDir = (TextView) findViewById(R.id.textView8);
            txtClouds = (TextView) findViewById(R.id.textView13);
            txtPhenomen = (TextView) findViewById(R.id.textView14);
            imgIco = (ImageView) findViewById(R.id.imageViewIcon);
            imgNext = (ImageView) findViewById(R.id.imageView7);
            imgFog = (ImageView) findViewById(R.id.imageView5);

            String str = "";
            HttpResponse response;
            DefaultHttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(metcheck);

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            str = str.replace("}{", "}, {");
            str = str.replace("} {", "}, {");
            Log.i(">>> JSON: ", str);

            try {
                dateJson = null;
                JSONObject json = new JSONObject(str);

                for (int i = 0; i < 125; i++) {
                    dateJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(0, 10);
                    hourJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(11, 13);
                    if (todaym.substring(0, 10).equals(dateJson) && (today1.substring(11,13).equals(hourJson))) {
                        //Log.i("VALORI: ", today1.substring(11,13).concat(" ").concat(hourJson).concat(" ").concat(dateJson).concat(" ").concat(today1).concat(" ").concat(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName")));
                        temp = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature");
                        hum = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity");
                        wPow = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed");
                        wDir = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection");
                        clouds = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("totalcloud");
                        phenomenon = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        dp = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("dewpoint");
                        fogVis = Double.parseDouble(temp) - Double.parseDouble(dp);

                        if (i < 21)         // 3 hours from now
                            i = i + 3;
                        else
                            i = 23;

                        phNext = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i + 3).getString("iconName");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Double tt = null;
            Double wp = null;
            if (temp!= null) tt = Double.parseDouble(temp);
            if (wPow != null) {
                wp = Double.parseDouble(wPow);
                wp = wp / 1.619; // miglia // m/s * 3.6;
            }
            //tt = tt - 273.15;*/

            txtDate.setText("Ultimo aggiornamento ".concat(today1.substring(11, 16)));

            txtTemp.setText(String.format("%.1f", tt).concat(" °C"));
            if (hum != null) txtHum.setText(hum.concat(" %"));
            txtWPow.setText(String.format("%.1f", wp).concat(" Km/h"));
            if (wDir != null) txtWDir.setText(windDirection((int) Double.parseDouble(wDir)));
            if (clouds != null) txtClouds.setText(clouds.concat(" %"));
            Log.i(phenomenon, "phen1 :");
            if (phenomenon != null) {
                imgIco.setVisibility(View.VISIBLE);
                txtPhenomen.setText(skyConversion(phenomenon));
                skyIcon(phenomenon, imgIco);

                if (fogVis < 4.0 && (!phenomenon.equals("Rain")
                        && !phenomenon.equals("Intermittent Rain") && !phenomenon.equals("Drizzle")
                        && !phenomenon.equals("Light Rain") && !phenomenon.equals("Showers")
                        && !phenomenon.equals("Rain Showers") && !phenomenon.equals("Heavy Rain")
                        && !phenomenon.equals("Thunderstorm"))) imgFog.setVisibility(View.VISIBLE);
                else imgFog.setVisibility(View.INVISIBLE);
            } else {
                imgIco.setVisibility(View.INVISIBLE);
            }

            if (phNext != null) {
                imgNext.setVisibility(View.VISIBLE);
                skyIcon(phNext, imgNext);
            } else {
                imgNext.setVisibility(View.INVISIBLE);
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
     * Translation weather strings
     */
    public String skyConversion(String value) {
        String sky = null;

        switch (value) {
            case "Sunny":
                sky = "Sereno";
                break;
            case "Fair":
                sky = "Poche nubi";
                break;
            case "Partly Cloudy":
                sky = "Parzialmente nuvoloso";
                break;
            case "Mostly Cloudy":
                sky = "Molto nuvoloso";
                break;
            case "Cloudy":
                sky = "Nuvoloso";
                break;
            case "Mist":
                sky = "nebbia";
                break;
            case "Intermittent Rain":
                sky = "Pioggia intermittente";
                break;
            case "Drizzle":
                sky = "Pioggerella";
                break;
            case "Light Rain":
                sky = "Pioggia leggera";
                break;
            case "Showers":
                sky = "Pioggia";
                break;
            case "Rain Showers":
                sky = "Rovescio";
                break;
            case "Heavy Rain":
                sky = "Pioggia forte";
                break;
            case "Light Snow":
                sky = "Neve leggera";
                break;
            case "Light Sleet":
                sky = "Nevischio";
                break;
            case "Heavy Snow":
                sky = "Forte nevicata";
                break;
            case "Heavy Sleet":
                sky = "Pesante nevischio";
                break;
            case "Thunderstorms":
                sky = "Temporale";
                break;
            case "Wet & Windy":
                sky = "Umido e ventoso";
                break;
            case "Hail":
                sky = "Grandine";
                break;
            case "Snow Showers":
                sky = "Scarica di neve";
                break;
            case "Dry & Windy":
                sky = "Secco e ventoso";
                break;
        }
        return sky;
    }

    /**
     * Convert string to an icon
     */
    public void skyIcon(String value, ImageView imgV) {

        Date date = Calendar.getInstance().getTime();
        long htime = date.getTime();
        String hsunset = sdf.format(htime).substring(11, 13);

        switch (value) {
            case "Sunny":
                if (Integer.parseInt(hsunset) < 18 && Integer.parseInt(hsunset) > 6) {
                    imgV.setImageResource(R.drawable.sun);
                }else {
                    imgV.setImageResource(R.drawable.moon);
                }
                break;
            case "Fair":
                if (Integer.parseInt(hsunset) < 18 && Integer.parseInt(hsunset) > 6) {
                    imgV.setImageResource(R.drawable.few_clouds);
                }else {
                    imgV.setImageResource(R.drawable.moon_fewclouds);
                }
                break;
            case "Partly Cloudy":
                if (Integer.parseInt(hsunset) < 18 && Integer.parseInt(hsunset) > 6) {
                    imgV.setImageResource(R.drawable.partly_cloudy);
                }else {
                    imgV.setImageResource(R.drawable.moon_cloudy);
                }
                break;
            case "Cloudy":
                imgV.setImageResource(R.drawable.cloudy);
                break;
            case "Light Rain":
                imgV.setImageResource(R.drawable.light_rain);
                break;
            case "Intermittent Rain":
                imgV.setImageResource(R.drawable.light_rain);
                break;
            case "Showers":
                imgV.setImageResource(R.drawable.rain);
                break;
            case "Drizzle":
                imgV.setImageResource(R.drawable.light_rain);
                break;
            case "Rain Showers":
                imgV.setImageResource(R.drawable.heavy_rain);
                break;
            case "Thunderstorms":
                imgV.setImageResource(R.drawable.thunderstorm);
                break;
            case "Light Sleet":
                imgV.setImageResource(R.drawable.sleet);
                break;
            case "Light Snow":
                imgV.setImageResource(R.drawable.sleet);
                break;
            case "Heavy Snow":
                imgV.setImageResource(R.drawable.snow);
                break;
            case "Heavy Sleet":
                imgV.setImageResource(R.drawable.snow);
                break;
            case "Mist":
                imgV.setImageResource(R.drawable.fog);
                break;
        }
    }

    /**
     * Open MetCheck page clicking on the image
     * @param view
     */
    public void openBrowserMetCheck(View view){

        //Get url from tag
        String url = (String)view.getTag();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
