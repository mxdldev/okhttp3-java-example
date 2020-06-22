package com.mxdl.okhttp3_example_01.chain.builder;

import com.mxdl.okhttp3_example_01.chain.base.BaseBuilder;
import com.mxdl.okhttp3_example_01.chain.base.BaseRequest;
import com.mxdl.okhttp3_example_01.chain.request.GetRequest;

import java.util.HashMap;

/**
 * Description: <GetBuilder><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class GetBuilder extends BaseBuilder<GetBuilder> {
    private HashMap<String,String> params = new HashMap<>();
    @Override
    public BaseRequest builer() {
        return new GetRequest(url,params);
    }
    public GetBuilder addParams(String key,String value){
        params.put(key,value);
        return this;
    }
}
