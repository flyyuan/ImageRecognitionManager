package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuan.imagerecognitionmanager.image.PicassoImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.listener.UploadListener;
import com.lzy.okserver.task.ExecutorWithListener;
import com.lzy.okserver.upload.UploadInfo;
import com.lzy.okserver.upload.UploadManager;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity  implements ExecutorWithListener.OnAllTaskEndListener {
    private static final String URL_base = "http://114.115.139.232:8080/xxzx/a/tpsb/";
    private static final String URL_uploadPicture =URL_base + "uploadPicture";
    private static final String URL_getPicByKeyWord =URL_base + "queryPicByKeyWord";
    private static final String URL_exit = "http://114.115.139.232:8080/xxzx/a/logout";
    private String sessionid;
    GridView gridView;
    TextView imageinfo;
    ArrayList<ImageItem> imagesList;
    ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    private UploadManager uploadManager;
    int SuccessNum = 0;



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
        imagesList = new ArrayList<>();

        uploadManager = UploadManager.getInstance();


//        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sessionid = prefs.getString("sessionid","");

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
//        uploadManager.getThreadPool().getExecutor().removeOnAllTaskEndListener(this);
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


    
    //点击后上传图片,使用了OkGo2
    public void upload(View view){
        Toast.makeText(MainActivity.this,"正在上传",Toast.LENGTH_SHORT).show();
        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                MyUploadListener listener = new MyUploadListener();
                PostRequest postRequest = OkGo.post(URL_uploadPicture+";JSESSIONID="+sessionid)
                        .params("fileKey" + i, new File(images.get(i).path));//多文件上传
                uploadManager.addTask(images.get(i).path, postRequest, listener);
            }
        }
        else {
            Toast.makeText(MainActivity.this,"没有选择图片",Toast.LENGTH_SHORT).show();
        }
    }

    //重写onActivityResult方法,回调 ImagePicker选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==200) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                  Toast.makeText(this,"已选择"+ images.size() + "张",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //通过get请求,导出图片标签
    public void getPicByKeyWordActivity(View v){
      Intent intent = new  Intent(MainActivity.this,GetkeywordActivity.class);
        startActivity(intent);
    }

    //通过get请求,导出图片标签
    public void findTaskByDateActivity(View v){
        Intent intent = new  Intent(MainActivity.this,FindTaskByDateActivity.class);
        startActivity(intent);
    }



    @Override
    public void onAllTaskEnd() {
    }


    //上传管理器
    private class MyUploadListener extends UploadListener<String>{


        @Override
        public void onProgress(UploadInfo uploadInfo) {
            Log.e("MyUploadListener", "onProgress:" + uploadInfo.getTotalLength() + " " + uploadInfo.getUploadLength() + " " + uploadInfo.getProgress());
        }

        @Override
        public void onFinish(String s) {
            SuccessNum++;
            Toast.makeText(MainActivity.this,"第"+SuccessNum+"张,上传成功",Toast.LENGTH_LONG).show();
            if (SuccessNum == images.size()){
                Toast.makeText(MainActivity.this,"所有图片上传完成",Toast.LENGTH_LONG).show();
            }
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

    public void exitApp(View view) {
        OkGo.get(URL_exit+";JSESSIONID="+sessionid).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

}
