package com.mxdl.okhttp3_example_01.http.response;

/**
 * Description: <自定义回调><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class OnResponse<T> {
    public abstract void onStart();
    public abstract void onSucc(T t);
    public abstract void onFail(Exception e);
    public abstract void onComplete();
}
