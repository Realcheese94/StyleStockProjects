package com.example.sj.stylestockprojects.Httpconnection;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("intro")
    Call<ArrayList<JsonObject>> getListRepos();

}
