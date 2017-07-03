package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;


public class modifyPwdActivity extends AppCompatActivity {
    private EditText et_oldpasswd,et_newpasswd,et_comfirmpasswd;
    private Button btn_submit;
    private String oldpasswd,newpasswd,comfirmpasswd;
    private String sessionid;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifypwd);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        et_oldpasswd=(EditText) findViewById(R.id.et_oldpassword);
        et_newpasswd=(EditText) findViewById(R.id.et_newpassword);
        et_comfirmpasswd=(EditText) findViewById(R.id.et_comfirmpasword);
        btn_submit=(Button)findViewById(R.id.submit_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.modifypwdtoolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(modifyPwdActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initdata();
                //在SP中获取sessionid
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(modifyPwdActivity.this);
                sessionid = prefs.getString("sessionid","");
            }
        });
    }

    private void initdata(){
        oldpasswd=et_oldpasswd.getText().toString().trim();
        newpasswd=et_newpasswd.getText().toString().trim();
        comfirmpasswd=et_comfirmpasswd.getText().toString().trim();
        if (oldpasswd.isEmpty()||newpasswd.isEmpty()||comfirmpasswd.isEmpty()){
            Toast.makeText(modifyPwdActivity.this,"请输入全部数据",Toast.LENGTH_SHORT).show();
        }else if(newpasswd.equals(comfirmpasswd)){
            OkGo.post("http://114.115.139.232:8080/xxzx/a /sys/user/modifyPwdByMobile"+ ";JSESSIONID=" +sessionid)
                    .params("oldPassword",oldpasswd)
                    .params("newPassword",newpasswd)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.d("1", "onSuccess: "+s);
                            if (s.equals("{\"modifyPwdStatus\":0}")){
                                Toast.makeText(modifyPwdActivity.this,"原密码错误，修改失败",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(modifyPwdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(modifyPwdActivity.this,LoginActivity.class));
                            }
                        }
                    });
            System.out.println("tanzhiliang  上传密码");
        }else {
            Toast.makeText(modifyPwdActivity.this,"两次输入的密码不正确",Toast.LENGTH_SHORT).show();
        }
    }
        }


