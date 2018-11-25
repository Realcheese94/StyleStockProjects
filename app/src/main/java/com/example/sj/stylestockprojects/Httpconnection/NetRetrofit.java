package com.example.sj.stylestockprojects.Httpconnection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit {

    private static NetRetrofit ourInstance = new NetRetrofit();

    public static  NetRetrofit getInstance(){
        return ourInstance;
    }
    private NetRetrofit(){ }
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.36:80/").addConverterFactory(GsonConverterFactory.create()).build();
    RetrofitService service = retrofit.create(RetrofitService.class);

    public RetrofitService getService() {
        return service;
    }
}
