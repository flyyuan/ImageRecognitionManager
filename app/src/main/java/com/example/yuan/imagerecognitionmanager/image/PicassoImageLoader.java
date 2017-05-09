package com.example.yuan.imagerecognitionmanager.image;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:使用ImagePicker，首先你需要继承 com.lzy.imagepicker.loader.ImageLoader 这个接口
 * ,实现其中的方法,
 * 比如以下代码是使用 Picasso 三方加载库实现的
 */

public class PicassoImageLoader implements ImageLoader{
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)
                .load(Uri.fromFile(new File(path)))//
                .placeholder(com.lzy.imagepicker.R.mipmap.default_image)//
                .error(com.lzy.imagepicker.R.mipmap.default_image)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
