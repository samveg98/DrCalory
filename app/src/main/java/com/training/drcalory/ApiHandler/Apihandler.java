package com.training.drcalory.ApiHandler;

import static android.os.Build.HOST;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class Apihandler
{
    //For our application, the databse in localhost, so our laptop and device
    //both should be connected to the same network
    private static final String BASE_URL ="http://192.168.0.12/db_drcalory/";

    private static Webservice apiService;

    public static Webservice getApiService() {
        if (apiService == null) {

            //creating adapter for connection
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(new Gson()))
                    .build();
            apiService = restAdapter.create(Webservice.class);
            return apiService;
        }
        else
        {
            return apiService;
        }
    }
}
