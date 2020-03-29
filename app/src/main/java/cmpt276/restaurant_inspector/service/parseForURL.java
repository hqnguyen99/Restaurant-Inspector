package cmpt276.restaurant_inspector.service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//based on https://abhishekprogramming.blogspot.com/2017/07/android-json-data-fetching-and-parsing.html
public class parseForURL extends AsyncTask<Void,Void,Void> {
    String restaurantData =""
    String restaurantCSV ="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://data.surrey.ca/api/3/action/package_show?id=restaurants");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                restaurantData = restaurantData + line;
            }

            JSONArray JA = new JSONArray(restaurantData);
            for(int i =0 ;i < JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                if(JO.getString("format").equals("CSV")) { //if format == csv
                    restaurantCSV = JO.getString("URL");
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
