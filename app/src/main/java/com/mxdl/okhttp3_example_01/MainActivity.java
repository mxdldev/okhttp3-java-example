package com.mxdl.okhttp3_example_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.mxdl.okhttp3_example_01.bean.AddUserResponse;
import com.mxdl.okhttp3_example_01.bean.BaseResponse;
import com.mxdl.okhttp3_example_01.bean.User;
import com.mxdl.okhttp3_example_01.chain.OkHttpUtil;
import com.mxdl.okhttp3_example_01.http.OkHttpManager;
import com.mxdl.okhttp3_example_01.http.response.OnResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
    }

    public void getTest(View view) {
        Log.v("MYTAG", "testGet start...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //1.创建一个HttpClient
                // 缓存目录
                //File file = new File(Environment.getExternalStorageDirectory(), "okhttp_cache");
                File file = new File(getExternalCacheDir().toString(),"okhttp_cache");
                // 缓存大小
                int cacheSize = 10 * 1024 * 1024;
                OkHttpClient httpClient = new OkHttpClient.Builder().cache(new Cache(file,cacheSize)).build();
                //2.创建一个Request
                Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
                //3.创建一个请求命令
                Call call = httpClient.newCall(request);
                try {
                    //4.执行这个命令
                    Response response = call.execute();
                    //Log.v("MYTAG", response.body().string());
                    if (response.isSuccessful()) {
                        if (response.networkResponse() != null) {
                            Log.e("MYTAG", "network:"+response.body().string() + "");
                        } else if (response.cacheResponse() != null) {
                            Log.e("MYTAG","cache:"+ response.body().string() + "");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //1.用户登陆
    public void login(View view) {
        Log.v("MYTAG", "login start...");
        //1.创建一个okhttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor.Builder()
                        .setLevel(Level.BASIC)
                        .build()
                )
                .build();
        //2.创建一个HttpUrl
        HttpUrl httpUrl = HttpUrl.parse("http://192.168.31.105:8080/user/login").newBuilder()
                .addQueryParameter("username", "mxdl")
                .addQueryParameter("password", "123456")
                .build();

        //3.创建一个Request
        final Request request = new Request.Builder().url(httpUrl).build();

        //4.创建一个登录命令
        Call call = okHttpClient.newCall(request);

        //5.执行这个命令
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("MYTAG", "onFail start...");
                Log.v("MYTAG", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("MYTAG", "onResponse start...");
                Log.v("MYTAG", response.body().string());
            }
        });
    }

    public void addUser(View view) {
        Log.v("MYTAG", "addUser start...");
        //1.创建一个OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor.Builder()
                        .setLevel(Level.BODY)
                        .build()
                )
                .build();

        //2.创建一个RequestBody
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=utf-8"), new Gson().toJson(new User("zhangsan", 123456)));

        //3.创建一个Request
        final Request request = new Request.Builder()
                .url("http://192.168.31.105:8080/user/addUser")
                .post(requestBody).build();
        //4.创建一个用户注册请求命名
        Call call = okHttpClient.newCall(request);

        //5.请求命令
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("MYTAG", "onFail start...");
                Log.v("MYTAG", e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.v("MYTAG", "onResponse start...");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
        });
    }

    public void login1(View view) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "mxdl");
        params.put("password", "123456");
        OkHttpManager.getInstance().get(
                "http://192.168.31.105:8080/user/login",
                params,
                new OnResponse<BaseResponse>() {
            @Override
            public void onStart() {
                Log.v("MYTAG", "onStart start...");
            }

            @Override
            public void onSucc(BaseResponse baseResponse) {
                Log.v("MYTAG", "onSucc start...");
                Log.v("MYTAG", baseResponse.toString());
            }

            @Override
            public void onFail(Exception e) {
                Log.v("MYTAG", "onFail start...");
                Log.v("MYTAG", e.toString());
            }

            @Override
            public void onComplete() {
                Log.v("MYTAG", "onComplete start...");
            }
        });
    }

    public void addUser1(View view) {
        OkHttpManager.getInstance()
                .post("http://192.168.31.105:8080/user/addUser",
                        new User("zhangsan", 123),
                        new OnResponse<AddUserResponse>() {
                            @Override
                            public void onStart() {
                                Log.v("MYTAG","onStrat start...");
                            }

                            @Override
                            public void onSucc(AddUserResponse addUserResponse) {
                                Log.v("MYTAG","onSucc start...");
                                Log.v("MYTAG",addUserResponse.toString());
                            }

                            @Override
                            public void onFail(Exception e) {
                                Log.v("MYTAG","onFail start...");
                            }

                            @Override
                            public void onComplete() {
                                Log.v("MYTAG","onComplete start...");
                            }
                        }
                );
    }

    public void login2(View view){
        OkHttpUtil.getInstance()
                .get()
                .url("http://192.168.31.105:8080/user/login")
                .addParams("username","mxdl")
                .addParams("password","123456")
                .builer()
                .builderCall()
                .execute(new OnResponse<BaseResponse>() {
                    @Override
                    public void onStart() {
                        Log.v("MYTAG","onStart start...");
                    }

                    @Override
                    public void onSucc(BaseResponse baseResponse) {
                        Log.v("MYTAG","onSucc start...");
                        Log.v("MYTAG",baseResponse.toString());
                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.v("MYTAG","onFail start...");
                        Log.v("MYTAG",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.v("MYTAG","onComplete start...");
                    }
                });
    }
    public void addUser2(View view){
        OkHttpUtil.getInstance()
                .post()
                .url("http://192.168.31.105:8080/user/addUser")
                .body(new User("lisi",888888))
                .builer()
                .builderCall()
                .execute(new OnResponse<AddUserResponse>() {
                    public void onStart() {
                        Log.v("MYTAG","onStart start...");
                    }

                    @Override
                    public void onSucc(AddUserResponse baseResponse) {
                        Log.v("MYTAG","onSucc start...");
                        Log.v("MYTAG",baseResponse.toString());
                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.v("MYTAG","onFail start...");
                        Log.v("MYTAG",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.v("MYTAG","onComplete start...");
                    }
                });
    }
}
