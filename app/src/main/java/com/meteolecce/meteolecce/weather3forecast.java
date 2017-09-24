package com.meteolecce.meteolecce;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    int len = 0, temp1 = 0, temp2 = 0, temp3 = 0, tmpTemp = 0,
        tempd1max = -99, tempd1min = 99, tempd2max = -99, tempd2min = 99, tempd3max = -99, tempd3min = 99;
    int hum1 = 0, hum2 = 0, hum3 = 0;
    String temp3d_1 = null, hum3d_1 = null, dateJson = null, hourJson = null,
            temp3d_2 = null, hum3d_2 = null,
            temp3d_3 = null, hum3d_3 = null,
            phenomenon11 = null, phenomenon12 = null, phenomenon13 = null, phenomenon14 = null,
            phenomenon21 = null, phenomenon22 = null, phenomenon23 = null, phenomenon24 = null,
            phenomenon31 = null, phenomenon32 = null, phenomenon33 = null, phenomenon34 = null;
    final String site3d = "http://ws1.metcheck.com/ENGINE/v9_0/json.asp?lat=40.4&lon=18.2&lid=22553";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // hh:mm:ss
    ImageView imgIco11 = null, imgIco12 = null, imgIco13 = null, imgIco14 =null,
            imgIco21 = null, imgIco22 = null, imgIco23 = null, imgIco24 = null,
            imgIco31 = null, imgIco32 = null, imgIco33 = null, imgIco34 = null;

    Date today = new Date();
    SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
    long ltime1 = today.getTime()+24*60*60*1000;
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
            imgIco11 = (ImageView) findViewById(R.id.imageViewIcon);
            imgIco12 = (ImageView) findViewById(R.id.imageViewIcon12);
            imgIco13 = (ImageView) findViewById(R.id.imageViewIcon13);
            imgIco14 = (ImageView) findViewById(R.id.imageViewIcon14);
            imgIco21 = (ImageView) findViewById(R.id.imageViewIcon2);
            imgIco22 = (ImageView) findViewById(R.id.imageViewIcon22);
            imgIco23 = (ImageView) findViewById(R.id.imageViewIcon23);
            imgIco24 = (ImageView) findViewById(R.id.imageViewIcon24);
            imgIco31 = (ImageView) findViewById(R.id.imageViewIcon3);
            imgIco32 = (ImageView) findViewById(R.id.imageViewIcon32);
            imgIco33 = (ImageView) findViewById(R.id.imageViewIcon33);
            imgIco34 = (ImageView) findViewById(R.id.imageViewIcon34);

            String str3d = "";
            HttpResponse response;
            DefaultHttpClient myClient3d = new DefaultHttpClient();
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
                for (int i = 0; i < 130; i++) {
                    dateJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(0,10);
                    hourJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(11,13);
                    if (today1.equals(dateJson)) {
                        tmpTemp = Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        if (tmpTemp > tempd1max) tempd1max = tmpTemp;
                        if ((tmpTemp < tempd1min) && (tmpTemp > (tempd1max-20))) tempd1min = tmpTemp;
                        //temp1 = temp1 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        hum1 = hum1 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                        if (hourJson.equals("00")) phenomenon11 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("06")) phenomenon12 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("15")) phenomenon13 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("18")) phenomenon14 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                    }
                    if (today2.equals(dateJson)) {
                        tmpTemp = Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        if (tmpTemp > tempd2max) tempd2max = tmpTemp;
                        if ((tmpTemp < tempd2min) && (tmpTemp > (tempd2max-20))) tempd2min = tmpTemp;
                        //temp2 = temp2 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        hum2 = hum2 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                        if (hourJson.equals("00")) phenomenon21 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("06")) phenomenon22 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("12")) phenomenon23 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("18")) phenomenon24 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                    }
                    if (today3.equals(dateJson)) {
                        tmpTemp = Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        Log.i(Integer.toString(tmpTemp).concat(" ").concat(Integer.toString(tempd3max-20)), "tmpTemp3: ");
                        if ((tmpTemp > tempd3max)) tempd3max = tmpTemp;
                        if ((tmpTemp < tempd3min) && (tmpTemp > (tempd3max-20))) tempd3min = tmpTemp;
                        //temp3 = temp3 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        hum3 = hum3 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                        if (hourJson.equals("00")) phenomenon31 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("06")) phenomenon32 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("12")) phenomenon33 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("18")) phenomenon34 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                    }
                }

                //temp3d_1 = "" + temp1/24;
                hum3d_1 = "" + hum1/24;
                //temp3d_2 = "" + temp2/24;
                hum3d_2 = "" + hum2/24;
                //temp3d_3 = "" + temp3/24;
                hum3d_3 = "" + hum3/24;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            //Log.e("Error phen:", phenomenon11);

            SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
            String today31 = sdf3.format(ltime1);
            day1.setText(today31);
            txtTemp3d_1.setText(Integer.toString(tempd1min).concat("/").concat(Integer.toString(tempd1max)).concat(" °C"));
            //txtTemp3d_1.setText(temp3d_1.concat(" °C").concat(" ").concat(Integer.toString(tempd1max)));
            if (hum3d_1 != null) txtHum3d_1.setText(hum3d_1.concat(" %"));
            if (phenomenon11 != null) {
                imgIco11.setVisibility(View.VISIBLE);
                skyIcon(phenomenon11, imgIco11);
            } else {
                imgIco11.setVisibility(View.INVISIBLE);
            }
            if (phenomenon12 != null) {
                imgIco12.setVisibility(View.VISIBLE);
                skyIcon(phenomenon12, imgIco12);
            } else {
                imgIco12.setVisibility(View.INVISIBLE);
            }
            if (phenomenon13 != null) {
                imgIco13.setVisibility(View.VISIBLE);
                skyIcon(phenomenon13, imgIco13);
            } else {
                imgIco13.setVisibility(View.INVISIBLE);
            }
            if (phenomenon14 != null) {
                imgIco14.setVisibility(View.VISIBLE);
                skyIcon(phenomenon14, imgIco14);
            } else {
                imgIco14.setVisibility(View.INVISIBLE);
            }

            String today32 = sdf3.format(ltime2);
            day2.setText(today32);
            txtTemp3d_2.setText(Integer.toString(tempd2min).concat("/").concat(Integer.toString(tempd2max)));
            //txtTemp3d_1.setText(temp3d_1.concat(" °C").concat(" ").concat(Integer.toString(tempd1max)));
            if (hum3d_2 != null) txtHum3d_2.setText(hum3d_2.concat(" %"));
            if (phenomenon21 != null) {
                imgIco21.setVisibility(View.VISIBLE);
                skyIcon(phenomenon21, imgIco21);
            } else {
                imgIco21.setVisibility(View.INVISIBLE);
            }
            if (phenomenon22 != null) {
                imgIco22.setVisibility(View.VISIBLE);
                skyIcon(phenomenon22, imgIco22);
            } else {
                imgIco22.setVisibility(View.INVISIBLE);
            }
            if (phenomenon23 != null) {
                imgIco23.setVisibility(View.VISIBLE);
                skyIcon(phenomenon23, imgIco23);
            } else {
                imgIco23.setVisibility(View.INVISIBLE);
            }
            if (phenomenon24 != null) {
                imgIco24.setVisibility(View.VISIBLE);
                skyIcon(phenomenon24, imgIco24);
            } else {
                imgIco24.setVisibility(View.INVISIBLE);
            }

            String today33 = sdf3.format(ltime3);
            day3.setText(today33);
            txtTemp3d_3.setText(Integer.toString(tempd3min).concat("/").concat(Integer.toString(tempd3max)));
            //txtTemp3d_1.setText(temp3d_1.concat(" °C").concat(" ").concat(Integer.toString(tempd1max)));
            if (hum3d_3 != null) txtHum3d_3.setText(hum3d_3.concat(" %"));
            if (phenomenon31 != null) {
                imgIco31.setVisibility(View.VISIBLE);
                skyIcon(phenomenon31, imgIco31);
            } else {
                imgIco31.setVisibility(View.INVISIBLE);
            }
            if (phenomenon32 != null) {
                imgIco32.setVisibility(View.VISIBLE);
                skyIcon(phenomenon32, imgIco32);
            } else {
                imgIco32.setVisibility(View.INVISIBLE);
            }
            if (phenomenon33 != null) {
                imgIco33.setVisibility(View.VISIBLE);
                skyIcon(phenomenon33, imgIco33);
            } else {
                imgIco33.setVisibility(View.INVISIBLE);
            }
            if (phenomenon34 != null) {
                imgIco34.setVisibility(View.VISIBLE);
                skyIcon(phenomenon34, imgIco34);
            } else {
                imgIco34.setVisibility(View.INVISIBLE);
            }

            super.onPostExecute(result);
        }
    }

    /**
     * Called when the user taps the Update button
     */
    public void update(View view) {
        Intent intent = new Intent(weather3forecast.this, weather3forecast.class);
        startActivity(intent);
        finish();
    }

    /** Called when the user taps the Send button */
    public void actualSituation(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /** Convert degree in a human comprehensible thing */
    public void skyIcon(String value, ImageView imgV) {

        switch (value) {
            case "Fair":
            case "Sunny":
                Log.i(value, "imgV: ");
                imgV.setImageResource(R.drawable.sun);
                break;
            case "Scattered Clouds":
            case "Mainly Clear":
                imgV.setImageResource(R.drawable.sun_and_cloud);
                break;
            case "Partly Cloudy":
                imgV.setImageResource(R.drawable.bit_cloudy);
                break;
            case "Cloudy":
                imgV.setImageResource(R.drawable.cloudy);
                break;
            case "Rain":
                imgV.setImageResource(R.drawable.rain);
                break;
            case "Intermittent Rain":
                imgV.setImageResource(R.drawable.very_light_rain);
                break;
            case "Showers":
                imgV.setImageResource(R.drawable.heavy_rain);
                break;
            case "Thunderstorms":
                imgV.setImageResource(R.drawable.thunderstorm);
                break;
            case "Snow":
            case "Light Snow":
                imgV.setImageResource(R.drawable.snow);
                break;
            case "Mist or Fog":
                imgV.setImageResource(R.drawable.fog3);
                break;
        }
    }
}
