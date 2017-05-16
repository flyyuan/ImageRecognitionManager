package com.example.yuan.imagerecognitionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.lzy.imagepicker.adapter.ImageGridAdapter;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okserver.task.ExecutorWithListener;
import com.lzy.okserver.upload.UploadManager;


import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:上传图片到服务器
 */

public class ImageUploadFragment extends Fragment{
    ArrayList<ImageItem> images = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imageupload, container, false);
        return view;
    }

}

