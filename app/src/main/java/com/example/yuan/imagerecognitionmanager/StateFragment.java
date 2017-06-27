package com.example.yuan.imagerecognitionmanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yuan.imagerecognitionmanager.adapter.MyDividerItemDecoration;
import com.example.yuan.imagerecognitionmanager.adapter.OnItemClickListener;
import com.example.yuan.imagerecognitionmanager.adapter.UserAdapter;
import com.example.yuan.imagerecognitionmanager.javaBean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
    private Fragment mContext;
    private com.example.yuan.imagerecognitionmanager.adapter.MenuAdapter mMenuAdapter;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    Toolbar toolbar;
//    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private UserAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_state, container , false);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_state);
        //刷新用户列表
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshUsers();
//            }
//        });
//        initUsers(); // 这一行代码如果写在这个地方   在网络非常好的情况下   会出现问题   当然  这个问题发生的概率很小很小
//        recyclerView = (RecyclerView) view.findViewById(R.id.user_manage);
//        recyclerView.setLayoutManager(layoutManager);// 布局管理器。
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity()
//                ,LinearLayoutManager.VERTICAL));// 添加分割线。

        mContext = this;

        mMenuRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mMenuRecyclerView.setLayoutManager(layoutManager);// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity()
                ,LinearLayoutManager.VERTICAL));// 添加分割线。


        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        setToolbar();
        initUsers();
        mMenuAdapter = new com.example.yuan.imagerecognitionmanager.adapter.MenuAdapter(userList);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        // 这样就没毛病了       记住一点  在进行  赋值操作的时候  要保证所赋值的对象不为NULL    好了
        return view;
    }

    private void setToolbar(){
        toolbar.setTitle("用户管理");
    }

//    private void refreshUsers() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                        initUsers();
//                        swipeRefreshLayout.setRefreshing(false);
//            }
//        }).start();
//    }

    //初始化用户列表数据
    public void initUsers() {
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
//            adapter = new UserAdapter(userList);
//            recyclerView.setAdapter(adapter);  // ctrl + alt + F 是将一个变量提成全局变量。。。
        mMenuAdapter = new com.example.yuan.imagerecognitionmanager.adapter.MenuAdapter(userList);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
    }


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = 50;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
                        .setBackgroundDrawable(R.drawable.selector_green)// 点击的背景。
                        .setImage(R.mipmap.ic_action_add) // 图标。
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(getActivity())
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(closeItem); // 添加一个按钮到左侧菜单。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(getActivity())
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }
        }
    };

    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getActivity(), "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getActivity(), "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }


            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                userList.remove(adapterPosition);
                mMenuAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

}
