package com.example.yuan.imagerecognitionmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuan.imagerecognitionmanager.R;
import com.example.yuan.imagerecognitionmanager.javaBean.PicByWord;

import java.util.List;

/**
 * Created by Yuan on 2017/6/29.
 * class comment:
 */

public class GetPicByKeyWordAdapter  extends RecyclerView.Adapter<GetPicByKeyWordAdapter.ViewHolder>{
    Context mContext;
    private List<PicByWord> mPicByWordList;
    private String baseImageUrl = "http://114.115.139.232:8080/pic/image/";
    private String imageUrl;

    public GetPicByKeyWordAdapter(List<PicByWord> picByWordList , Context context){
        mContext = context;
        mPicByWordList = picByWordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_pkeyword,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String lab = null;

        PicByWord picByWord = mPicByWordList.get(position);
        lab = picByWord.getLabels().toString();
        holder.keywordText.setText(lab);

        imageUrl = baseImageUrl + picByWord.getPicture_name();

        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.nothing)
                .into(holder.picImage);
    }

    @Override
    public int getItemCount() {
        return mPicByWordList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView picImage;
        TextView keywordText;
        public ViewHolder(View itemView) {
            super(itemView);
            picImage = (ImageView) itemView.findViewById(R.id.getPicByKeyWordImage);
            keywordText = (TextView) itemView.findViewById(R.id.getPicByKeyWord);
        }
    }

}
