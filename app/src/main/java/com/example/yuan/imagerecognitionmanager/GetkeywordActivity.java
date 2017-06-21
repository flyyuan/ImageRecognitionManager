package com.example.yuan.imagerecognitionmanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuan.imagerecognitionmanager.javaBean.PictureTab;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetkeywordActivity extends AppCompatActivity {
    private String sessionid;
    private static final String URL_base = "http://114.115.139.232:8080/xxzx/a/tpsb/";
    private static final String URL_getPicByKeyWord =URL_base + "queryPicByKeyWord";
    EditText queryPicByKeyWordEdt;
    TextView getPicByKeyWordText;
    ImageView getPicByKeyWordImage;
    private String labsJSON;
    private String baseImageUrl = "http://114.115.139.232:8080/pic/image/";
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getkeyword);
        queryPicByKeyWordEdt = (EditText) findViewById(R.id.queryPicByKeyWordEdt);
        getPicByKeyWordText = (TextView)findViewById(R.id.getPicByKeyWord) ;
        getPicByKeyWordImage = (ImageView) findViewById(R.id.getPicByKeyWordImage);
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

    private void parseJSONWithGSON(String s) {
        String pictureName;
        String finishTime ;
        List<String> labs = new ArrayList<>();
        Gson gson = new Gson();
        List<PictureTab>tabList = gson.fromJson(s, new TypeToken<List<PictureTab>>(){}.getType());
        for (PictureTab labels : tabList){
            labs.addAll(labels.getLabels());
            pictureName = labels.getPicture_name();
            finishTime = labels.getFinish_time();
            Log.d("---", "parseJSONWithGSON: "+ labs);
            Log.d("---", "parseJSONWithGSON: "+ pictureName);
            Log.d("---", "parseJSONWithGSON: "+ finishTime);
            labs.add(finishTime +"\n");
            imageUrl = baseImageUrl + pictureName;
            setTextLabsAndTime(labs);
            setgetPicByKeyWordImage(imageUrl);
        }
    }

    //设置标签
    private void setTextLabsAndTime(List<String> labs) {
        if (labsJSON.length() != 2) {
            getPicByKeyWordText.setText(labs.toString());
        }
        else {
            getPicByKeyWordText.setText("暂无此标签数据");
        }
    }

    //加载标签图片
    private void setgetPicByKeyWordImage(String url){
        Glide.with(this).load(url).into(getPicByKeyWordImage);
    }
}