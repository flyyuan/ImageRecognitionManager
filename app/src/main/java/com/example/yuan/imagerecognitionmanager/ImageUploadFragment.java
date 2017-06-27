package com.example.yuan.imagerecognitionmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.imagepicker.bean.ImageItem;


import java.util.ArrayList;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:上传图片到服务器
 */

public class ImageUploadFragment extends Fragment{
    ArrayList<ImageItem> images = null;
    Toolbar toolbar ;
    Menu menu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imageupload, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_image);
        toolbar.setTitle("图像管理");
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
}

