package com.example.projectcovid_19.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL= "http://minangtech.com/home/";
    public static Retrofit setInit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient builder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.connectTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        okHttpClient.readTimeout(20, TimeUnit.SECONDS);
        return okHttpClient.build();
    }
    public static Service getInstance() {
        return setInit().create(Service.class);
    }
}
