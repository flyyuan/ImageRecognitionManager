package com.example.yuan.imagerecognitionmanager.network.api;

import com.example.yuan.imagerecognitionmanager.javaBean.UploadResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:
 * 上传文件需要使用@Multipart关键字注解，
 * @POST表明HTTP请求方式为POST,
 * /upload为请求服务器的相对地址，
 * uploadMultipleFiles是自定义的方法名，参数为Map<String,RequestBody> files即多个文件组成的Map对象，
 * @PartMap表明这是多文件上传，如果单文件可以使用@Part MultipartBody.Part file,
 * 方法的返回类型默认为Response,由于我们已经开发了Server端，
 * 所以知道Server端的返回数据格式为Json，
 * 因此我们针对返回数据格式新建一个UploadResut类。
 */

public interface ImageUpload {
    //上传多张图片
    @Multipart
    @POST("xxzx/a/tpsb/uploadPicture")
    Call<UploadResult> uploadMultipleFiles(
            @PartMap Map<String, RequestBody> files
            );
}
