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
import com.example.yuan.imagerecognitionmanager.javaBean.FindLabelsByUserId;
import com.example.yuan.imagerecognitionmanager.javaBean.User;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by Yuan on 2017/7/2.
 * class comment:
 */

public class UserTagAdapter extends SwipeMenuAdapter<UserTagAdapter.DefaultViewHolder> {
    Context mContext;

    private List<FindLabelsByUserId> mLabelsList;

    private OnItemClickListener mOnItemClickListener;

    public UserTagAdapter(List<FindLabelsByUserId> labelsList, Context context) {
        mContext = context;
        mLabelsList = labelsList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usertag, parent, false);
    }

    @Override
    public UserTagAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        UserTagAdapter.DefaultViewHolder viewHolder = new UserTagAdapter.DefaultViewHolder(realContentView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(UserTagAdapter.DefaultViewHolder holder, int position) {
        FindLabelsByUserId labelsByUserId= mLabelsList.get(position);
        holder.tvPicName.setText("图片名：" + labelsByUserId.getPicName());
        holder.tagExpTv1.setText("标注成功："+labelsByUserId.getSuccLabels() + "\n"
                + "标注失败："+labelsByUserId.getErrorLabels());


        //加载标签图片
        Glide.with(mContext)
                .load("http://114.115.139.232:8080/pic/image/"+labelsByUserId.getUrl())
                .placeholder(R.drawable.loading)
                .into(holder.ivTagPhoto);
    }


    @Override
    public int getItemCount() {
        return mLabelsList.size();
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivTagPhoto;
        TextView tvPicName;
//        TextView tag;
//        TextView createDate;
//        TextView update;
        ExpandableTextView tagExpTv1;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivTagPhoto = (ImageView) itemView.findViewById(R.id.tag_image);
            tvPicName = (TextView) itemView.findViewById(R.id.pic_name);
//            tag = (TextView) itemView.findViewById(R.id.tv_tag);
//            createDate= (TextView) itemView.findViewById(R.id.tv_createtagDate);
//            update = (TextView) itemView.findViewById(R.id.tv_upDateTag);
            tagExpTv1 = (ExpandableTextView) itemView.findViewById(R.id.tag_text_view);

        }

        @Override
        public void onClick(View view) {

        }
    }
}



