package com.example.yuan.imagerecognitionmanager.network.api;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Yuan on 2017/5/2.
 * class comment:post网络请求，向服务器提交表单
 * 定义登录接口
 */

public interface LoginApi {
    @FormUrlEncoded
    @POST("xxzx/a/login?__ajax=true")
    Call<HashMap<String,String>> postLoginFormFields(
            //@Field()参数为表单域名称
            @Field("username") String username,
            @Field("password") String password,
            @Field("mobileLogin") String mobileLogin
    );
}
