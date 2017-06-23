package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:
 */

public class PresonalFragment extends Fragment {
    private String name;
    private String loginName;
    private TextView name_tv;
    private TextView input_name;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_presonal, container , false);
        name_tv = (TextView) view.findViewById(R.id.name_tv);
        input_name = (TextView) view.findViewById(R.id.input_name);
        setPresonal();
        return view;
    }

    private void setPresonal() {
        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        name = prefs.getString("name","");
        loginName = prefs.getString("loginName","");
        Log.d("--------->",name);
        Log.d("--------->",loginName);
        name_tv.setText(name);
        input_name.setText(loginName);
    }


    }
