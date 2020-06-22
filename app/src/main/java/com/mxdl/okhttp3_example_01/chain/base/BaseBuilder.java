package com.mxdl.okhttp3_example_01.chain.base;

/**
 * Description: <BaseBuilder><br>
 * Author:      mxdl<br>
 * Date:        2020/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseBuilder<T> {
    protected String url;
    public T url(String url){
        this.url = url;
        return (T) this;
    }
    public abstract BaseRequest builer();
}
