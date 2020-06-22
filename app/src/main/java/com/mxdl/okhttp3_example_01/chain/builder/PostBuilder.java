package com.mxdl.okhttp3_example_01.chain.builder;

import com.mxdl.okhttp3_example_01.chain.base.BaseBuilder;
import com.mxdl.okhttp3_example_01.chain.base.BaseRequest;
import com.mxdl.okhttp3_example_01.chain.request.PostRequest;

/**
 * Description: <PostBuilder><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PostBuilder extends BaseBuilder<PostBuilder> {
    private Object body;
    @Override
    public BaseRequest builer() {
        return new PostRequest(url,body);
    }
    public PostBuilder body(Object body){
        this.body = body;
        return this;
    }
}
