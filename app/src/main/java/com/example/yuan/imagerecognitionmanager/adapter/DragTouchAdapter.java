package com.example.yuan.imagerecognitionmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuan.imagerecognitionmanager.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

/**
 * Created by Yuan on 2017/6/27.
 * class comment:
 */

public class DragTouchAdapter extends SwipeMenuAdapter<DragTouchAdapter.DefaultViewHolder> {

    private SwipeMenuRecyclerView mMenuRecyclerView;

    private List<String> titles;

    private OnItemClickListener mOnItemClickListener;

    public DragTouchAdapter(SwipeMenuRecyclerView menuRecyclerView, List<String> titles) {
        this.mMenuRecyclerView = menuRecyclerView;
        this.titles = titles;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public DragTouchAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        viewHolder.mMenuRecyclerView = mMenuRecyclerView;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DragTouchAdapter.DefaultViewHolder holder, int position) {
        holder.setData(titles.get(position));
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {

        TextView tvTitle;
        OnItemClickListener mOnItemClickListener;
        SwipeMenuRecyclerView mMenuRecyclerView;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mMenuRecyclerView.startDrag(this);
                    break;
                }
            }
            return false;
        }
    }

}
