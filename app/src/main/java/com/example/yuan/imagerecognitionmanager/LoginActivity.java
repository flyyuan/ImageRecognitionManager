package com.example.yuan.imagerecognitionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuan.imagerecognitionmanager.network.api.LoginApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.MemoryCookieStore;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import butterknife.BindView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity  extends AppCompatActivity {
    private Context mContext = this;
    private static final String URL_BASE = "http://114.115.139.232:8080/";
    Gson gson = new Gson();
    private Retrofit retrofit;
    Call<HashMap<String,String>> call = null;
    private LoginApi loginApi = null;
    private EditText editText_username;
    private EditText editText_pwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_signup)
    Button btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRetrofit();
        setContentView(R.layout.activity_login);
        initView();

    }
    private void initView() {
        editText_username = (EditText) findViewById(R.id.input_username);
        editText_pwd = (EditText) findViewById(R.id.input_passwords);
    }


    //拦截器


    //初始化Retrofit，创建Retrofit实例，建立Retrofit客户端
    private void initRetrofit() {
              retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
    }



    public void login(View v){
        String username = editText_username.getText() + "";
        String password = editText_pwd.getText() + "";
        String mobileLogin = "true";
        //使用HashMap的map对象储存账号密码
        final Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                map.put("mobileLogin",mobileLogin);
        Log.d("map--->",map.toString());
        //LoginApi是interface不是class，所以我们是无法直接调用该方法，需要用Retrofit创建一个LoginApi的代理对象
        LoginApi loginapi = retrofit.create(LoginApi.class);
        //接口调用
        call=loginapi.postLoginFormFields(username , password , mobileLogin);
        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call,
                                   Response<HashMap<String,String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("Success--->", String.valueOf(response.body()));

                    //使用HashMap accountJsonMap存储请求所得的登录JSON数据
                    HashMap<String,String> accountJsonMap =  response.body();
                    if (accountJsonMap.get("id") != null){
                        Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();

                    }
                    else {
                        Toast.makeText(mContext,"账号或者密码错误",Toast.LENGTH_SHORT).show();
                    }
            }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(mContext,"网络连接不可用",Toast.LENGTH_SHORT).show();
            }

        });

    }
   public void signup(View v){
       startActivity(new Intent(LoginActivity.this,SignupActivity.class));
   }


}
