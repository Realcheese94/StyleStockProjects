package com.example.sj.stylestockprojects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sj.stylestockprojects.Httpconnection.NetRetrofit;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Transaction_framework extends Fragment {

    Button button;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_layout, container, false);
        button = (Button) view.findViewById(R.id.chatButton);
        textView = (TextView)view.findViewById(R.id.chatText);


                Call<ArrayList<JsonObject>> res = NetRetrofit.getInstance().getService().getListRepos();
                res.enqueue(new Callback<ArrayList<JsonObject>>() {
                    @Override
                    public void onResponse(Call<ArrayList<JsonObject>> call, Response<ArrayList<JsonObject>> response) {
                        textView.setText(response.body().get(0).get("intro_message").toString());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<JsonObject>> call, Throwable t) {
                        textView.setText("지금은 StyleStock 과 채팅할 수 없습니다.");
                    }
                });




        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            }
        }
