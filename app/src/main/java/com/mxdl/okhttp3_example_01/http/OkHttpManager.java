package com.mxdl.okhttp3_example_01.http;

import com.google.gson.Gson;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.mxdl.okhttp3_example_01.http.response.MyCallBack;
import com.mxdl.okhttp3_example_01.http.response.OnResponse;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description: <OkHttpManager><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class OkHttpManager {
    private OkHttpClient mOkHttpClient;

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new LoggingInterceptor
                        .Builder()
                        .setLevel(Level.BASIC)
                        .request("resquest")
                        .response("response")
                        .build()
                )
                .build();
    }

    public static OkHttpManager getInstance() {
        return OKHttpClientHodler.manager;
    }
    static class OKHttpClientHodler {
        static OkHttpManager manager = new OkHttpManager();
    }

    public <T> void get(String url, HashMap<String, String> params, OnResponse<T> response) {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (params != null && !params.isEmpty()) {
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addQueryParameter(key, params.get(key));
            }
        }
        MyCallBack<T> responseCallback = new MyCallBack<>(response);
        responseCallback.onStart();
        mOkHttpClient.newCall(
                new Request
                        .Builder()
                        .url(builder.build())
                        .build()
        ).enqueue(responseCallback);
    }

    public <T> void post(String url, Object body, OnResponse<T> onResponse) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charet=utf-8"), new Gson().toJson(body));
        MyCallBack<T> responseCallback = new MyCallBack<>(onResponse);
        responseCallback.onStart();
        mOkHttpClient.newCall(new Request.Builder().url(url).post(requestBody).build())
                .enqueue(responseCallback);
    }


}
