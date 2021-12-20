package com.example.apiimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    ImageView kartin;
    String put;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kartin = findViewById(R.id.imageView2);

        new JokeLoader().execute();
    }

    private class JokeLoader extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            String hrefAPI = "https://rickandmortyapi.com/api/character/1";
            String jsonString = getJson(hrefAPI);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                put = jsonObject.getString("image");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            Glide.with(MainActivity.this).load(put).into(kartin);
        }
    }

    private String getJson(String href)
    {
        String data = "";

        try {
            URL url = new URL(href);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                data = bufferedReader.readLine();
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}