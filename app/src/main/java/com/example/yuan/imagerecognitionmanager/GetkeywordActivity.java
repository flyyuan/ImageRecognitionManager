package com.example.yuan.imagerecognitionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yuan.imagerecognitionmanager.adapter.GetPicByKeyWordAdapter;
import com.example.yuan.imagerecognitionmanager.adapter.MyDividerItemDecoration;
import com.example.yuan.imagerecognitionmanager.javaBean.PicByWord;
import com.example.yuan.imagerecognitionmanager.javaBean.PictureTab;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetkeywordActivity extends AppCompatActivity {
    private String sessionid;
    private static final String URL_base = "http://114.115.139.232:8080/xxzx/a/tpsb/";
    private static final String URL_getPicByKeyWord =URL_base + "queryPicByKeyWord";
    EditText queryPicByKeyWordEdt;
//    TextView getPicByKeyWordText;
//    ImageView getPicByKeyWordImage;
    private String labsJSON = null;
    private String baseImageUrl = "http://114.115.139.232:8080/pic/image/";
    private String imageUrl;
    private RecyclerView getWordRecycerView;
    private GetPicByKeyWordAdapter getPicByKeyWordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getkeyword);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_getkeyword);
        toolbar.setTitle("搜索和导出图片标签");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        queryPicByKeyWordEdt = (EditText) findViewById(R.id.queryPicByKeyWordEdt);
        getWordRecycerView = (RecyclerView) findViewById(R.id.rv_getPicByKeyWord);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        getWordRecycerView.setLayoutManager(layoutManager);
        getWordRecycerView.addItemDecoration(new MyDividerItemDecoration(GetkeywordActivity.this,LinearLayoutManager.VERTICAL));
//        getPicByKeyWordText = (TextView)findViewById(R.id.getPicByKeyWord) ;
//        getPicByKeyWordImage = (ImageView) findViewById(R.id.getPicByKeyWordImage);
        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(GetkeywordActivity.this);
        sessionid = prefs.getString("sessionid","");

    }


    //导出图片标签
    public void get(View v){
        Log.d("----------", queryPicByKeyWordEdt.getText().toString());
        String picByKeyWord = queryPicByKeyWordEdt.getText().toString();
        OkGo.post(URL_getPicByKeyWord  + ";JSESSIONID=" +sessionid )
                .tag(this)
                .params("keyWord",picByKeyWord)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("导出图片标签----------", s);
                        labsJSON = s;
                        Log.d("导出图片标签----------", String.valueOf(labsJSON.length()));
                        parseJSONWithGSON(s);
                    }
                });
    }

//    private void parseJSONWithGSON(String s) {
//        String pictureName;
//        String finishTime ;
//        List<String> labs = new ArrayList<>();
//        Gson gson = new Gson();
//        List<PictureTab>tabList = gson.fromJson(s, new TypeToken<List<PictureTab>>(){}.getType());
//        for (PictureTab labels : tabList){
//                labs.addAll(labels.getLabels());
//                pictureName = labels.getPicture_name();
//                finishTime = labels.getFinish_time();
//                Log.d("---", "parseJSONWithGSON: "+ labs);
//                Log.d("---", "parseJSONWithGSON: "+ pictureName);
//                Log.d("---", "parseJSONWithGSON: "+ finishTime);
//            labs.add(finishTime +"\n");
//            imageUrl = baseImageUrl + pictureName;
////            setTextLabsAndTime(labs);
////            setgetPicByKeyWordImage(imageUrl);
//        }
//    }

    private void parseJSONWithGSON(String s){
        Gson gson = new Gson();
        List<PicByWord> picByWordsList = gson.fromJson(s,new TypeToken<List<PicByWord>>()
        {}.getType());
        getPicByKeyWordAdapter = new GetPicByKeyWordAdapter(picByWordsList,GetkeywordActivity.this);
        getWordRecycerView.setAdapter(getPicByKeyWordAdapter);
    }

//    //设置标签
//    private void setTextLabsAndTime(List<String> labs) {
//        if (labsJSON.length() != 2) {
//            getPicByKeyWordText.setText(labs.toString());
//        }
//        else {
//            getPicByKeyWordText.setText("暂无此标签数据");
//        }
//    }

//    //加载标签图片
//    private void setgetPicByKeyWordImage(String url){
//        Glide.with(this).load(url).into(getPicByKeyWordImage);
//    }

    //导出标签生成TXT文件
    public void exportTagTotxt(View view){
        if(labsJSON != null){
//        byte[] buffer = labsJSON.getBytes();
        BufferedWriter bufferedWriter = null;
        try {
            //获取当前时间
            SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy.MM.dd--HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            String   newTime  =   formatter.format(curDate);

            File file = new File(getExternalFilesDir(null),newTime +".txt");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] labsTXT = labsJSON.getBytes();
            outputStream.write(labsTXT);
            outputStream.close();
            Toast.makeText(GetkeywordActivity.this,"导出成功,目录："+getExternalFilesDir(null).toString(),
                    Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(GetkeywordActivity.this, "导出失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    else {
            Toast.makeText(GetkeywordActivity.this,"请获取标签后再导出",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}