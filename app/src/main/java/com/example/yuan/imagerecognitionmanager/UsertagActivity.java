package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.yuan.imagerecognitionmanager.adapter.MyDividerItemDecoration;
import com.example.yuan.imagerecognitionmanager.adapter.UserTagAdapter;
import com.example.yuan.imagerecognitionmanager.javaBean.FindLabelsByUserId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class UsertagActivity extends AppCompatActivity {
    String userId;
    String userName;
    private String sessionid;
    private static final String URL_findLabelsByUserId= "http://114.115.139.232:8080/xxzx/a/tpsb/userManage/findLabelsByUserId";
    private List<FindLabelsByUserId> labList = new ArrayList<>();
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private UserTagAdapter userTagAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertag);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");
        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(UsertagActivity.this);
        sessionid = prefs.getString("sessionid","");

        toolbar = (Toolbar) findViewById(R.id.toolbar_usertag);
        toolbar.setTitle(userName+"的历史标签");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

       mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.user_tag);

        LinearLayoutManager layoutManager = new LinearLayoutManager(UsertagActivity.this);
        mMenuRecyclerView.setLayoutManager(layoutManager);// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new MyDividerItemDecoration(UsertagActivity.this
                ,LinearLayoutManager.VERTICAL));// 添加分割线。
        findLabelsByUserId();
    }
    
    private void findLabelsByUserId(){
        OkGo.get(URL_findLabelsByUserId+ ";JSESSIONID=" +sessionid )
                .params("id",userId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        parseJSONWithGSON(s);
                    }
                });
    }

    private void parseJSONWithGSON(String s) {
        Gson gson = new  Gson();
        labList = gson.fromJson(s,new TypeToken<List<FindLabelsByUserId>>(){}.getType());
        userTagAdapter = new UserTagAdapter(labList,UsertagActivity.this);
        mMenuRecyclerView.setAdapter(userTagAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
