package com.example.sj.stylestockprojects.Recommend.Crawlers20;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sj.stylestockprojects.Recommend.Recommend_detail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InterruptedIOException;

public class TopJsoupAsyncTask extends AsyncTask<Void,Void,String>{


String result_url;
    @Override
    protected String doInBackground(Void... voids) {

        try {
            String hotping_result ="";
            String sixsis_result ="";

            Document document1 = Jsoup.connect("http://hotping.co.kr/product/list.html?cate_no=29/").get();
            Elements img_list =document1.select("div.thumbnail img");
            Log.e("url_test",img_list.toString());


            for (Element el:img_list){
                String src = el.absUrl("src");
                Log.e("url_test2",src);
                hotping_result=hotping_result+src+"$";
            }

            Document document2 = Jsoup.connect("http://66girls.co.kr/product/list.html?cate_no=70").get();
            Elements img_list2 =document2.select("div.box img");
            Log.e("url_test",img_list.toString());


            for (Element el:img_list2){
                String src = el.absUrl("src");
                Log.e("url_test2",src);
                sixsis_result=sixsis_result+src+"$";
                Log.e("url_result6",sixsis_result);
                result_url = hotping_result+sixsis_result;
            }




        }
        catch (IOException e){
            e.printStackTrace();

        }

        return result_url;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Recommend_detail.item_url = result_url;
    }
}