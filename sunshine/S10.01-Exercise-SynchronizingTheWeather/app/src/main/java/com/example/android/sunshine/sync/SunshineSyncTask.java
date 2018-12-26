package com.example.android.sunshine.sync;//  TODO (1) Create a class called SunshineSyncTask
//  TODO (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      TODO (3) Within syncWeather, fetch new weather data
//      TODO (4) If we have valid results, delete the old data and insert the new

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class SunshineSyncTask{

    synchronized public static void syncWeather(Context context){

        URL weatherURL = NetworkUtils.getUrl(context);

        try {
            String jsonWeatherRes = NetworkUtils.getResponseFromHttpUrl(weatherURL);
            ContentValues[] weatherValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context,jsonWeatherRes);

            if(weatherValues!=null && weatherValues.length!=0){
                ContentResolver contentResolver = context.getContentResolver();

                contentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI,
                        null,null);

                contentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI,weatherValues);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


}