package com.tang.allmap.httpvisit;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpGet {

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private static boolean debuge = true;
    private static final String tag = "HttpGet";
    public static final String Http_Error = "Http_Error";
    public static final String Http_onFailure = "Http_onFailure";

    public static void getData(final String url, final OnHttpGetStringCallBack onHttpGetStringCallBack){

        if(debuge)Log.e(tag, "---HttpGet--getData-url="+url);
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .get()//默认就是GET请求，可以不写
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            if(debuge)Log.e(tag, "onFailure: ");
                            onHttpGetStringCallBack.getData(Http_onFailure);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(debuge)Log.e(tag, "onResponse: " + response.body().string());
                            onHttpGetStringCallBack.getData(response.body().string());
                        }


                    });
                }catch (Exception e){
                    e.printStackTrace();
                    if(debuge)Log.e(tag, "---HttpGet--e="+e.getMessage());
                    onHttpGetStringCallBack.getData(Http_Error);

                }

              cachedThreadPool.shutdown();
            }
        });

    }


    public interface  OnHttpGetStringCallBack{

        public void getData(String data);

    }
}
