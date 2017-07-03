package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.yuan.imagerecognitionmanager.adapter.MyDividerItemDecoration;
import com.example.yuan.imagerecognitionmanager.adapter.OnItemClickListener;
import com.example.yuan.imagerecognitionmanager.javaBean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class getUserPicByKeyWordActivity extends AppCompatActivity {
    private String sessionid;
    private static final String URL_userManage = "http://114.115.139.232:8080/xxzx/a/tpsb/userManage/getAllUserTagResult";
    private List<User> userList = new ArrayList<>();
    private SwipeMenuRecyclerView mMenuRecyclerView;
    Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private com.example.yuan.imagerecognitionmanager.adapter.MenuAdapter mMenuAdapter;
    com.example.yuan.imagerecognitionmanager.adapter.MenuAdapter.DefaultViewHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_pic_by_key_word);
        toolbar = (Toolbar) findViewById(R.id.toolbar_getusertag);
        toolbar.setTitle("查看用户历史标签");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.getUsertag);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getUserPicByKeyWordActivity.this);
        mMenuRecyclerView.setLayoutManager(layoutManager);// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new MyDividerItemDecoration(getUserPicByKeyWordActivity.this
                ,LinearLayoutManager.VERTICAL));// 添加分割线。
        initUsers();
    }

    //初始化用户列表数据
    public void initUsers() {
        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getUserPicByKeyWordActivity.this);
        sessionid = prefs.getString("sessionid","");
        //请求用户列表数据
        OkGo.get(URL_userManage+ ";JSESSIONID=" +sessionid )
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("用户列表2--------------", s);
                        parseJSONWithGSON(s);
                    }
                });
    }
    private void parseJSONWithGSON(String s) {
        Gson gson = new Gson();
        userList = gson.fromJson(s, new TypeToken<List<User>>(){}.getType());
//            adapter = new UserAdapter(userList);
//            recyclerView.setAdapter(adapter);  // ctrl + alt + F 是将一个变量提成全局变量。。。
        mMenuAdapter = new com.example.yuan.imagerecognitionmanager.adapter.MenuAdapter(userList, getUserPicByKeyWordActivity.this);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
    }

    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            User user = userList.get(position);
            String userId = user.getUserId();
            Intent intent = new Intent(getUserPicByKeyWordActivity.this,UsertagActivity.class);
            intent.putExtra("userId",userId);
            intent.putExtra("userName",user.getName());
            startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
