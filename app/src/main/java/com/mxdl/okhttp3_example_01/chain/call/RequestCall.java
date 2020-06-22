package com.mxdl.okhttp3_example_01.chain.call;

import com.mxdl.okhttp3_example_01.chain.OkHttpUtil;
import com.mxdl.okhttp3_example_01.chain.base.BaseRequest;
import com.mxdl.okhttp3_example_01.http.response.OnResponse;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Description: <RequestCall><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RequestCall {
    private BaseRequest mRequest;
    private Call mCall;

    public RequestCall(BaseRequest request) {
        mRequest = request;
    }
    public <T> void execute(OnResponse<T> onResponse){
        Request request = mRequest.buidlerRequest();
        OkHttpUtil httpUtil = OkHttpUtil.getInstance();
        mCall = httpUtil.getOkHttpClient().newCall(request);
        httpUtil.execute(this,onResponse);
    }

    public Call getCall() {
        return mCall;
    }
}
