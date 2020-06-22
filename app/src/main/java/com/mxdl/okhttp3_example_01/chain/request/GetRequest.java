package com.mxdl.okhttp3_example_01.chain.request;

import com.mxdl.okhttp3_example_01.chain.base.BaseRequest;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Description: <GetRequest><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class GetRequest extends BaseRequest {
    private HashMap<String,String> params;
    public GetRequest(String url,HashMap<String,String> params) {
        super(url);
        this.params = params;
    }

    @Override
    public Request buidlerRequest() {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if(params != null && !params.isEmpty()){
            Iterator<String> iterator = params.keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                builder.addQueryParameter(key,params.get(key));
            }
        }
        return new Request.Builder().url(builder.build()).build();
    }
}
