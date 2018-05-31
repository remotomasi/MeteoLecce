package com.meteolecce.meteolecce;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

import java.lang.*;

public class weather3forecast extends AppCompatActivity {

    TextView txtTemp3d_1 = null, txtHum3d_1 = null, day1 = null, day2 = null, day3 = null,
            txtTemp3d_2 = null, txtHum3d_2 = null, txtTemp3d_3 = null, txtHum3d_3 = null,
            txtWind3d_1 = null, txtWind3d_2 = null, txtWind3d_3 = null,
            dayENG1 = null, dayENG2 = null, dayENG3 = null;
    int len = 0, temp1 = 0, temp2 = 0, temp3 = 0, tmpTemp = 0,
        tempd1max = -99, tempd1min = 99, tempd2max = -99, tempd2min = 99, tempd3max = -99, tempd3min = 99;
    int hum1 = 0, hum2 = 0, hum3 = 0;
    int wind1 = 0, wind2 = 0, wind3 = 0, wind1p = 0, wind2p = 0, wind3p = 0;
    double dp1 = 0, dp2 = 0, dp3 = 0;
    String temp3d_1 = null, hum3d_1 = null, dateJson = null, hourJson = null,
            temp3d_2 = null, hum3d_2 = null,
            temp3d_3 = null, hum3d_3 = null,
            wind3d_3 = null, wind3d_2 = null, wind3d_1 = null,
            wind1pt = null, wind2pt = null, wind3pt = null,
            fog = null,
            phenomenon11 = null, phenomenon12 = null, phenomenon13 = null, phenomenon14 = null,
            phenomenon21 = null, phenomenon22 = null, phenomenon23 = null, phenomenon24 = null,
            phenomenon31 = null, phenomenon32 = null, phenomenon33 = null, phenomenon34 = null;
    final String site3d = "http://ws1.metcheck.com/ENGINE/v9_0/json.asp?lat=40.35&lon=18.15&lid=22553";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // hh:mm:ss
    ImageView imgIco11 = null, imgIco12 = null, imgIco13 = null, imgIco14 =null,
            imgIco21 = null, imgIco22 = null, imgIco23 = null, imgIco24 = null,
            imgIco31 = null, imgIco32 = null, imgIco33 = null, imgIco34 = null,
            imgFog1 = null, imgFog2 = null, imgFog3 = null;

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
            txtWind3d_1 = (TextView) findViewById(R.id.textView);
            txtHum3d_1 = (TextView) findViewById(R.id.textView19);
            day2 = (TextView) findViewById(R.id.textView22);
            txtTemp3d_2 = (TextView) findViewById(R.id.textView24);
            txtWind3d_2 = (TextView) findViewById(R.id.textView9);
            txtHum3d_2 = (TextView) findViewById(R.id.textView41);
            day3 = (TextView) findViewById(R.id.textView29);
            txtTemp3d_3 = (TextView) findViewById(R.id.textView31);
            txtWind3d_3 = (TextView) findViewById(R.id.textView25);
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
            imgFog1 = (ImageView) findViewById(R.id.imageView6);
            imgFog2 = (ImageView) findViewById(R.id.imageView8);
            imgFog3 = (ImageView) findViewById(R.id.imageView9);

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
                wind1 = wind2 = wind3 = 0;
                wind1p = wind2p = wind3p = 0;
                dp1 = dp2 = dp3 = 0.0;
                for (int i = 0; i < 130; i++) {
                    dateJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(0,10);
                    hourJson = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("utcTime").substring(11,13);
                    if (today1.equals(dateJson)) {
                        tmpTemp = Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        if (tmpTemp > tempd1max) tempd1max = tmpTemp;
                        if ((tmpTemp < tempd1min) && (tmpTemp > (tempd1max-20))) tempd1min = tmpTemp;
                        hum1 = hum1 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                        wind1 = windDir(Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i -1).getString("windspeed")), wind1, Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed")), Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection")));
                        wind1p = wind1p + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed"));
                        Log.i(Integer.toString(wind1), ">>>>>>>>>> WIND DIR: ");
                        dp1 = dp1 + Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("dewpoint"));
                        if (hourJson.equals("00")) phenomenon11 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("06")) phenomenon12 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("15")) phenomenon13 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("18")) phenomenon14 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                    }
                    if (today2.equals(dateJson)) {
                        tmpTemp = Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        if (tmpTemp > tempd2max) tempd2max = tmpTemp;
                        if ((tmpTemp < tempd2min) && (tmpTemp > (tempd2max-20))) tempd2min = tmpTemp;
                        hum2 = hum2 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                        //wind2 = (wind2 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection")))/2;
                        wind2 = windDir(Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i - 1).getString("windspeed")), wind2, Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed")), Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection")));
                        wind2p = wind2p + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed"));
                        dp2 = dp2 + Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("dewpoint"));
                        if (hourJson.equals("00")) phenomenon21 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("06")) phenomenon22 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("12")) phenomenon23 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("18")) phenomenon24 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                    }
                    if (today3.equals(dateJson)) {
                        tmpTemp = Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("temperature"));
                        //Log.i(Integer.toString(tmpTemp).concat(" ").concat(Integer.toString(tempd3max-20)), "tmpTemp3: ");
                        if ((tmpTemp > tempd3max)) tempd3max = tmpTemp;
                        if ((tmpTemp < tempd3min) && (tmpTemp > (tempd3max-20))) tempd3min = tmpTemp;
                        hum3 = hum3 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("humidity"));
                        //wind3 = (wind3 + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection")))/2;
                        wind3 = windDir(Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i - 1).getString("windspeed")), wind3, Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed")), Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection")));
                        wind3p = wind3p + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed"));
                        //Log.i("" + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("windspeed")) + " " + Integer.parseInt(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("winddirection")), "AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        dp3 = dp3 + Double.parseDouble(json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("dewpoint"));
                        if (hourJson.equals("00")) phenomenon31 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("06")) phenomenon32 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("12")) phenomenon33 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                        if (hourJson.equals("18")) phenomenon34 = json.getJSONObject("metcheckData").getJSONObject("forecastLocation").getJSONArray("forecast").getJSONObject(i).getString("iconName");
                    }
                }

                // average on 24 hours
                hum3d_1 = "" + hum1/24;
                hum3d_2 = "" + hum2/24;
                hum3d_3 = "" + hum3/24;
                wind3d_1 = "" + wind1; //24;
                wind3d_2 = "" + wind2; //24;
                wind3d_3 = "" + wind3; //24;
                wind1pt = " / " + (int)(wind1p*1.852/24) + " Km/h";
                wind2pt = " / " + (int)(wind2p*1.852/24) + " Km/h";
                wind3pt = " / " + (int)(wind3p*1.852/24) + " Km/h";
                dp1 = dp1/24;
                dp2 = dp2/24;
                dp3 = dp3/24;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //Log.e("Error phen:", phenomenon11);

            SimpleDateFormat sdf3 = new SimpleDateFormat("E dd/MM/yyyy");
            String today31 = sdf3.format(ltime1);
            day1.setText(dayInITA(today31.substring(0,3)).concat(" ").concat(today31.substring(4,14)));
            txtTemp3d_1.setText(Integer.toString(tempd1min).concat("/").concat(Integer.toString(tempd1max)).concat(" °C"));
            txtWind3d_1.setText(windDirection((int) Double.parseDouble(wind3d_1)) + wind1pt);
            if (hum3d_1 != null) txtHum3d_1.setText(hum3d_1.concat(" %"));
            if ((((tempd1max + tempd1min)/2) - dp1) < 4.0) imgFog1.setVisibility(View.VISIBLE);
                else imgFog1.setVisibility(View.INVISIBLE);
            if (phenomenon11 != null) {
                imgIco11.setVisibility(View.VISIBLE);
                skyIcon(phenomenon11, imgIco11, 0);
            } else {
                imgIco11.setVisibility(View.INVISIBLE);
            }
            if (phenomenon12 != null) {
                imgIco12.setVisibility(View.VISIBLE);
                skyIcon(phenomenon12, imgIco12, 6);
            } else {
                imgIco12.setVisibility(View.INVISIBLE);
            }
            if (phenomenon13 != null) {
                imgIco13.setVisibility(View.VISIBLE);
                skyIcon(phenomenon13, imgIco13, 12);
            } else {
                imgIco13.setVisibility(View.INVISIBLE);
            }
            if (phenomenon14 != null) {
                imgIco14.setVisibility(View.VISIBLE);
                skyIcon(phenomenon14, imgIco14, 18);
            } else {
                imgIco14.setVisibility(View.INVISIBLE);
            }

            String today32 = sdf3.format(ltime2);
            day2.setText(dayInITA(today32.substring(0,3)).concat(" ").concat(today32.substring(4,14)));
            txtTemp3d_2.setText(Integer.toString(tempd2min).concat("/").concat(Integer.toString(tempd2max)).concat(" °C"));
            txtWind3d_2.setText(windDirection((int) Double.parseDouble(wind3d_2)) + wind2pt);
            if (hum3d_2 != null) txtHum3d_2.setText(hum3d_2.concat(" %"));
            if ((((tempd2max + tempd2min)/2) - dp2) < 4.0) imgFog2.setVisibility(View.VISIBLE);
            else imgFog2.setVisibility(View.INVISIBLE);
            if (phenomenon21 != null) {
                imgIco21.setVisibility(View.VISIBLE);
                skyIcon(phenomenon21, imgIco21, 0);
            } else {
                imgIco21.setVisibility(View.INVISIBLE);
            }
            if (phenomenon22 != null) {
                imgIco22.setVisibility(View.VISIBLE);
                skyIcon(phenomenon22, imgIco22, 6);
            } else {
                imgIco22.setVisibility(View.INVISIBLE);
            }
            if (phenomenon23 != null) {
                imgIco23.setVisibility(View.VISIBLE);
                skyIcon(phenomenon23, imgIco23, 12);
            } else {
                imgIco23.setVisibility(View.INVISIBLE);
            }
            if (phenomenon24 != null) {
                imgIco24.setVisibility(View.VISIBLE);
                skyIcon(phenomenon24, imgIco24, 18);
            } else {
                imgIco24.setVisibility(View.INVISIBLE);
            }

            String today33 = sdf3.format(ltime3);
            day3.setText(dayInITA(today33.substring(0,3)).concat(" ").concat(today33.substring(4,14)));
            txtTemp3d_3.setText(Integer.toString(tempd3min).concat("/").concat(Integer.toString(tempd3max)).concat(" °C"));
            txtWind3d_3.setText(windDirection((int) Double.parseDouble(wind3d_3)) + wind3pt);
            if ((((tempd3max + tempd3min)/2) - dp3) < 4.0) imgFog3.setVisibility(View.VISIBLE);
                else imgFog3.setVisibility(View.INVISIBLE);
            if (hum3d_3 != null) txtHum3d_3.setText(hum3d_3.concat(" %"));
            if (phenomenon31 != null) {
                imgIco31.setVisibility(View.VISIBLE);
                skyIcon(phenomenon31, imgIco31, 0);
            } else {
                imgIco31.setVisibility(View.INVISIBLE);
            }
            if (phenomenon32 != null) {
                imgIco32.setVisibility(View.VISIBLE);
                skyIcon(phenomenon32, imgIco32, 6);
            } else {
                imgIco32.setVisibility(View.INVISIBLE);
            }
            if (phenomenon33 != null) {
                imgIco33.setVisibility(View.VISIBLE);
                skyIcon(phenomenon33, imgIco33, 12);
            } else {
                imgIco33.setVisibility(View.INVISIBLE);
            }
            if (phenomenon34 != null) {
                imgIco34.setVisibility(View.VISIBLE);
                skyIcon(phenomenon34, imgIco34, 18);
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

    /** Obtain icons from strings and hour */
    public void skyIcon(String value, ImageView imgV, int hour) {

        switch (value) {
            case "Sunny":
                if (hour < 18) {
                    imgV.setImageResource(R.drawable.sun);
                }else {
                    imgV.setImageResource(R.drawable.moon);
                }
                break;
            case "Fair":
            case "Mainly Clear":
                if (hour < 18) {
                    imgV.setImageResource(R.drawable.few_clouds);
                }else {
                    imgV.setImageResource(R.drawable.moon_fewclouds);
                }
                break;
            case "Scattered Clouds":
            case "Partly Cloudy":
                if (hour < 18) {
                    imgV.setImageResource(R.drawable.partly_cloudy);
                }else {
                    imgV.setImageResource(R.drawable.moon_cloudy);
                }
                break;
            case "Cloudy":
                imgV.setImageResource(R.drawable.cloudy);
                break;
            case "Rain":
                imgV.setImageResource(R.drawable.rain);
                break;
            case "Intermittent Rain":
                imgV.setImageResource(R.drawable.light_rain);
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
                imgV.setImageResource(R.drawable.fog);
                break;
        }
    }

    /**
     * Convert degree in a human comprehensible thing
     */
    public String windDirection(int deg) {
        String dir = null;

        if (deg > 335 || deg <= 25) {
            dir = "N";
        } else if (deg > 25 && deg <= 65) {
            dir = "NE";
        } else if (deg > 65 && deg <= 155) {
            dir = "E";
        } else if (deg > 115 && deg <= 155) {
            dir = "SE";
        } else if (deg > 155 && deg <= 205) {
            dir = "S";
        } else if (deg > 205 && deg <= 245) {
            dir = "SO";
        } else if (deg > 245 && deg <= 295) {
            dir = "O";
        } else if (deg > 295 && deg <= 335) {
            dir = "NO";
        }

        return dir;
    }

    /**
     * Calculate wind direction by summing vector directions
     */
    public int windDir(double mod1, int angle1, double mod2, int angle2) {

        double x = 0, y = 0;
        int finalDir = 0;

        // convert them to radians
        x = Math.toRadians(angle1);
        y = Math.toRadians(angle2);

        finalDir = (int) Math.toDegrees(Math.atan((mod1 * Math.sin(x) + mod2 * Math.sin(y))/(mod1 * Math.cos(x) + mod2 * Math.cos(y))));

        // Based on the value of the arctangent
        //if (Math.cos(x) > 0 && Math.sin(x) > 0 && Math.cos(y) > 0 && Math.sin(y) > 0) finalDir += 360;
        //if (Math.cos(x) < 0 && Math.sin(x) > 0 && Math.cos(y) < 0 && Math.sin(y) > 0) finalDir += 180;
        //if (Math.cos(x) < 0 && Math.sin(x) < 0 && Math.cos(y) < 0 && Math.sin(y) < 0) finalDir += 180;
        //if (Math.cos(x) > 0 && Math.sin(x) < 0 && Math.cos(y) < 0 && Math.sin(y) > 0) finalDir += 180;
        //if (Math.cos(x) < 0 && Math.sin(x) > 0 && Math.cos(y) < 0 && Math.sin(y) > 0) finalDir += 180;
        //if (Math.cos(x) < 0 && Math.sin(x) > 0 && Math.cos(y) < 0 && Math.sin(y) < 0) finalDir += 180;

        if (finalDir >= -270 && finalDir <= -90) finalDir += 180;
        if (finalDir > -90 && finalDir < 0) finalDir += 360;

        return finalDir;
    }

    /**
     * Convert degree in a human comprehensible thing
     */
    public String dayInITA(String day) {
        String dayITA = null;

        if (day.equals("Sun")) {
            dayITA = "Dom";
        } else if (day.equals("Mon")) {
            dayITA = "Lun";
        } else if (day.equals("Tue")) {
            dayITA = "Mar";
        } else if (day.equals("Wed")) {
            dayITA = "Mer";
        } else if (day.equals("Thu")) {
            dayITA = "Gio";
        } else if (day.equals("Fri")) {
            dayITA = "Ven";
        } else if (day.equals("Sat")) {
            dayITA = "Sab";
        }

        return dayITA;
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
