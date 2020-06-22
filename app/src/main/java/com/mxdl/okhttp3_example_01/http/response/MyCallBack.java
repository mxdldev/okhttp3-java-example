package com.mxdl.okhttp3_example_01.http.response;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Description: <MyCallBack><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyCallBack<T> implements Callback {
    public static final int ON_START = 0;//请求开始
    public static final int ON_SUCC = 1;//请求成功
    public static final int ON_FAIL = 2;//请求失败
    public static final int ON_COMPLETE = 3;//请求完成
    private OnResponse<T> mResponse;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case ON_START:
                    if (mResponse != null) {
                        mResponse.onStart();
                    }
                    break;
                case ON_SUCC:
                    if (mResponse != null) {
                        String body = (String) msg.obj;
                        Class<T> tClass = (Class<T>) ((ParameterizedType) mResponse.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                        T t = new Gson().fromJson(body, tClass);
                        mResponse.onSucc(t);
                    }
                    break;
                case ON_FAIL:
                    if(mResponse != null){
                        mResponse.onFail((Exception) msg.obj);
                    }
                    break;
                case ON_COMPLETE:
                    if(mResponse != null){
                        mResponse.onComplete();
                    }
                    break;
            }
        }
    };

    public MyCallBack(OnResponse response) {
        mResponse = response;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        sendMessage(ON_FAIL, e);
        sendMessage(ON_COMPLETE,"");
    }

    public void sendMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            if (response.isSuccessful()) {
                sendMessage(ON_SUCC, response.body().string());
            } else {
                sendMessage(ON_FAIL, new Exception("未知异常"));
            }
            sendMessage(ON_COMPLETE,"");
        } catch (IOException e) {
            sendMessage(ON_FAIL, e);
            sendMessage(ON_COMPLETE,"");
        }
    }
    public void onStart(){
        if(mResponse != null){
            mResponse.onStart();
        }
    }
}
