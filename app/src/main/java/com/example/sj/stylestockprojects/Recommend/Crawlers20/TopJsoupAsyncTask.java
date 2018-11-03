package com.example.sj.stylestockprojects.Recommend.Crawlers20;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.telecom.RemoteConference;
import android.util.Log;

import com.example.sj.stylestockprojects.Recommend.Recommend_detail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InterruptedIOException;

public class TopJsoupAsyncTask extends AsyncTask<String, Void, String> {
    String result_url;
    String target_page="";
    private Context mContext;
    public static ProgressDialog mDig;

    public TopJsoupAsyncTask(Context mContext){
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Log.e("AsyncTask_list",strings[0].toString());
            String url_list1 = "-";
            String url_list2 = "-";
            String url_list3 = "-";
            String url_list4 = "-";

            String item_name = strings[0];
            Log.e("crawl_item",item_name);

            //소녀나라
            target_page = "https://www.sonyunara.com/search/search.php?orby=best&crema-query="+item_name+"&search_keyword="+item_name;
            Document document1 = Jsoup.connect(target_page).get();
            Elements img_list = document1.select("div.thumb img");
            Log.e("url_test", img_list.toString());


            for (Element el : img_list) {
                String src = el.absUrl("src");
                Log.e("url_test1", src);
                url_list1 = url_list1 + src + "-";
            }

            //메롱샵
            target_page = "http://merongshop.com/product/search.html?banner_action=&keyword="+item_name;
            Document document2 = Jsoup.connect(target_page).get();
            Elements img_list2 = document2.select("div.thumbnail img");
            Log.e("url_test", img_list.toString());


            for (Element el : img_list2) {
                String src = el.absUrl("src");
                Log.e("url_test2", src);
                url_list2 = url_list2 + src + "-";
                Log.e("url_result6", url_list2);

            }
            result_url = url_list1 + url_list2;

            //리틀블랙
            target_page = "http://littleblack.co.kr/product/search.html?banner_action=&keyword="+item_name;
            Document document3 = Jsoup.connect(target_page).get();
            Elements img_list3 = document3.select("div.thumbnail img");
            Log.e("url_test", img_list3.toString());


            for (Element el : img_list3) {
                String src = el.absUrl("src");
                Log.e("url_test3", src);
                url_list3 = url_list3 + src + "-";

            }
            result_url = result_url + url_list3;


            ///임블리

            target_page = "http://imvely.com/product/search.html?view_type=&supplier_code=&category_no=&search_type=product_name&keyword="+item_name;
            Document document4 = Jsoup.connect(target_page).get();
            Elements img_list4 = document4.select("div.box img");
            Log.e("url_test", img_list4.toString());


            for (Element el : img_list4) {
                String src = el.absUrl("src");
                Log.e("url_test4", src);
                url_list4 = url_list4 + src + "-";



            }
            result_url = result_url + url_list4;


            Log.e("final_url",result_url);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return result_url;

    }

    @Override
    protected void onPreExecute() {
        mDig = new ProgressDialog(mContext);
        mDig.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDig.setMessage("추천정보 가져오기....");
        mDig.show();


        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(String s) {

        Recommend_detail.item_url = result_url;
        super.onPostExecute(s);
        //mDig.dismiss();



    }
}