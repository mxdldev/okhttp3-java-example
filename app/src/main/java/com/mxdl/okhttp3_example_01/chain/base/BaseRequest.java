package com.mxdl.okhttp3_example_01.chain.base;

import android.view.textclassifier.TextLinks;

import com.mxdl.okhttp3_example_01.chain.call.RequestCall;

import okhttp3.Request;

/**
 * Description: <BaseRequest><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRequest {
    protected String url;

    public BaseRequest(String url) {
        this.url = url;
    }
    public abstract Request buidlerRequest();
    public RequestCall builderCall(){
        return new RequestCall(this);
    }
}
