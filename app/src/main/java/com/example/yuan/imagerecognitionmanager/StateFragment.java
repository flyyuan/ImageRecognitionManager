package com.example.yuan.imagerecognitionmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuan.imagerecognitionmanager.adapter.MyDividerItemDecoration;
import com.example.yuan.imagerecognitionmanager.adapter.UserAdapter;
import com.example.yuan.imagerecognitionmanager.javaBean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:
 */
public class StateFragment extends Fragment {
    private String sessionid;
    private static final String URL_userManage = "http://114.115.139.232:8080/xxzx/a/tpsb/userManage/getAllUserTagResult";
    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_state, container , false);
//        initUsers(); // 这一行代码如果写在这个地方   在网络非常好的情况下   会出现问题   当然  这个问题发生的概率很小很小
        recyclerView = (RecyclerView) view.findViewById(R.id.user_manage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        initUsers(); // 这一行代码如果写在这个地方   在网络非常好的情况下   会出现问题   当然  这个问题发生的概率很小很小
        // 这样就没毛病了       记住一点  在进行  赋值操作的时候  要保证所赋值的对象不为NULL    好了
        return view;
    }
    //初始化用户列表数据
    private void initUsers() {
        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sessionid = prefs.getString("sessionid","");
        //请求用户列表数据
        OkGo.get(URL_userManage+ ";JSESSIONID=" +sessionid )
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("用户列表2----------", s);
                        parseJSONWithGSON(s);
                    }
                });
    }

    private void parseJSONWithGSON(String s) {
        Gson gson = new Gson();
        userList = gson.fromJson(s, new TypeToken<List<User>>(){}.getType());
            adapter = new UserAdapter(userList);
            recyclerView.setAdapter(adapter);  // ctrl + alt + F 是将一个变量提成全局变量。。。
    }
}
