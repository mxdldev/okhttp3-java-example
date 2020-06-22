package com.mxdl.okhttp3_example_01.chain;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.mxdl.okhttp3_example_01.chain.builder.GetBuilder;
import com.mxdl.okhttp3_example_01.chain.builder.PostBuilder;
import com.mxdl.okhttp3_example_01.chain.call.RequestCall;
import com.mxdl.okhttp3_example_01.http.response.OnResponse;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Description: <OkHttpUtil><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class OkHttpUtil {
    private OkHttpClient mOkHttpClient;
    private MainUIThred mMainUIThred;
    class MainUIThred implements Executor {
        private Handler mHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mHandler.post(command);
        }
    }
    private OkHttpUtil(){
        mOkHttpClient = new OkHttpClient();
        mMainUIThred = new MainUIThred();
    }
    static class OkHttpUtilHolder{
        static OkHttpUtil okHttpUtil = new OkHttpUtil();
    }
    public static OkHttpUtil getInstance(){
        return OkHttpUtilHolder.okHttpUtil;
    }
    public <T> void execute(final RequestCall requestCall, final OnResponse<T> onResponse){
        if(onResponse != null){
            onResponse.onStart();
        }
        requestCall.getCall().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFail(e, onResponse);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    if(response.isSuccessful()){
                        String body = response.body().string();
                        Class<T> classT = (Class<T>) ((ParameterizedType) onResponse.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                        final T t = new Gson().fromJson(body, classT);
                        sendSucc(t, onResponse);
                    }else{
                        sendFail(new Exception("未知异常"), onResponse);
                    }
                } catch (final IOException e) {
                    sendFail(e, onResponse);
                }
            }
        });
    }

    public <T> void sendSucc(final T t, final OnResponse<T> onResponse) {
        mMainUIThred.execute(new Runnable() {
            @Override
            public void run() {
                    if(onResponse != null){
                        onResponse.onSucc(t);
                        onResponse.onComplete();
                    }
            }
        });
    }

    public <T> void sendFail(final Exception e, final OnResponse<T> onResponse) {
        mMainUIThred.execute(new Runnable() {
            @Override
            public void run() {
                if(onResponse != null){
                    onResponse.onFail(e);
                    onResponse.onComplete();
                }
            }
        });
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public GetBuilder get(){
        return new GetBuilder();
    }
    public PostBuilder post(){
        return new PostBuilder();
    }
}
