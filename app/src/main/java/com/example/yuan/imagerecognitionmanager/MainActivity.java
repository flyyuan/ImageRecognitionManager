package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuan.imagerecognitionmanager.image.PicassoImageLoader;
import com.example.yuan.imagerecognitionmanager.javaBean.UploadResult;
import com.example.yuan.imagerecognitionmanager.network.ServiceGenerator;
import com.example.yuan.imagerecognitionmanager.network.api.ImageUpload;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.listener.UploadListener;
import com.lzy.okserver.task.ExecutorWithListener;
import com.lzy.okserver.upload.UploadInfo;
import com.lzy.okserver.upload.UploadManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  implements ExecutorWithListener.OnAllTaskEndListener {

    GridView gridView;
    TextView imageinfo;
    ArrayList<ImageItem> imagesList;
    ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    private UploadManager uploadManager;



    //底部Tab菜单栏
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_state:
                    replaceFragment(new StateFragment());
                    return true;
                case R.id.navigation_imageUpload:
                    replaceFragment(new ImageUploadFragment());
                    return true;
                case R.id.navigation_presonal:
                    replaceFragment(new PresonalFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(new StateFragment());

        //初始化gridView和imageinfo和imageList
        gridView = bindView(R.id.gridView);
        imageinfo = bindView(R.id.imageinfo);
        imagesList = new ArrayList<>();

        uploadManager = UploadManager.getInstance();

        //使用ImagePicker，仿微信的图片选择
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(50);
        
    }
    protected void onDestroy() {
        super.onDestroy();
        //记得移除
        uploadManager.getThreadPool().getExecutor().removeOnAllTaskEndListener(this);
    }


    //替换Fragment
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.commit();
    }

    //bindView 方法
    public <T extends View> T bindView(int id) {
        return (T) this.findViewById(id);
    }


    //点击ImagePicker，选择图片
    public void selectImage(View view){
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 200);
    }


    
    //点击后上传图片,使用了
    public void upload(View view){
        Toast.makeText(MainActivity.this,"正在上传",Toast.LENGTH_SHORT).show();
        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                MyUploadListener listener = new MyUploadListener();
//                listener.setUserTag(gridView.getChildAt(i));
                PostRequest postRequest = OkGo.post("http://114.115.139.232:8080/xxzx/a/tpsb/uploadPicture;JSESSIONID=")
                        .headers("headerKey1", "headerValue1")//
                        .headers("headerKey2", "headerValue2")//
                        .params("paramKey1", "paramValue1")//
                        .params("paramKey2", "paramValue2")//
                        .params("fileKey" + i, new File(images.get(i).path));
                uploadManager.addTask(images.get(i).path, postRequest, listener);
            }
        }
    }

    //重写onActivityResult方法,回调 ImagePicker选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==200) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                MyAdapter adapter = new MyAdapter(images);
//                gridView.setAdapter(adapter);
                  Toast.makeText(this,"已选择"+ images.size() + "张",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAllTaskEnd() {

    }

    //GridView 适配器
    private class MyAdapter extends BaseAdapter {

        private List<ImageItem> items;

        public MyAdapter(List<ImageItem> items) {
            this.items = items;
        }

        public void setData(List<ImageItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public ImageItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int size = gridView.getWidth() / 3;
            if (convertView == null) {
                imageView = new ImageView(MainActivity.this);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.parseColor("#88888888"));
            } else {
                imageView = (ImageView) convertView;
            }
            imagePicker.getImageLoader().displayImage(MainActivity.this, getItem(position).path, imageView, size, size);
            return imageView;
        }
    }

    //上传管理器
    private class MyUploadListener extends UploadListener<String>{

        @Override
        public void onProgress(UploadInfo uploadInfo) {

        }

        @Override
        public void onFinish(String s) {
            Toast.makeText(MainActivity.this,"上传成功:"+s,Toast.LENGTH_LONG).show();
            Log.e("MyUploadListener", "finish:" + s);
        }

        @Override
        public void onError(UploadInfo uploadInfo, String errorMsg, Exception e) {
            Toast.makeText(MainActivity.this,"上传失败:"+errorMsg, Toast.LENGTH_LONG).show();
            Log.e("MyUploadListener", "onError:" + errorMsg);
        }

        @Override
        public String parseNetworkResponse(okhttp3.Response response) throws Exception {
            Log.e("MyUploadListener", "convertSuccess");
            return response.body().string();
        }
    }




    //双击退出
    private static boolean isExit = false;  // 标识是否退出
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onBackPressed(){
        exit();
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);  // 利用handler延迟发送更改状态信息
        } else {
            this.finish();
        }
    }

}
