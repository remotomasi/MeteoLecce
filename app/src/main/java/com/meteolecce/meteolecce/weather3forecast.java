package com.meteolecce.meteolecce;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class weather3forecast extends AppCompatActivity {

    final String site3wf = "http://ws1.metcheck.com/ENGINE/v9_0/json.asp?lat=40.4&lon=18.2&lid=22553";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather3forecast);

        new readWeather3Lecce().execute();
    }

    private class readWeather3Lecce extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
        }
    }

    /** Called when the user taps the Send button */
    public void actualSituation(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    private abstract class GetContacts extends AsyncTask<Void, Void, Void> {
        //public void readWeatherLecce() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(weather3forecast.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }
    }
}
