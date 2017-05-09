package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {
    @BindView(R.id.btn_signup)Button signup;
    @BindView(R.id.btn_login)Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    @OnClick
    public void login(View v){
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    @OnClick
    public void signup(){

    }
}
