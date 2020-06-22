package com.mxdl.okhttp3_example_01.chain.request;

import com.google.gson.Gson;
import com.mxdl.okhttp3_example_01.chain.base.BaseRequest;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description: <PostRequest><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PostRequest extends BaseRequest {
    private Object body;
    public PostRequest(String url,Object body) {
        super(url);
        this.body = body;
    }

    @Override
    public Request buidlerRequest() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(body));
        return new Request.Builder().url(url).post(requestBody).build();
    }
}
