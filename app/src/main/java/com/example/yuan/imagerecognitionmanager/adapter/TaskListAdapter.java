package com.example.yuan.imagerecognitionmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuan.imagerecognitionmanager.R;
import com.example.yuan.imagerecognitionmanager.javaBean.TaskList;

import java.util.List;

/**
 * Created by Yuan on 2017/6/25.
 * class comment:
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private List<TaskList> mTaskList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView isNewRecord;
        TextView id;
        TextView createDate;
        TextView updateDate;
        TextView taskSum;
        TextView finishNum;
        TextView finishSpeed;


        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.name);
            isNewRecord = (TextView) itemView.findViewById(R.id.is_new_record);
            id = (TextView) itemView.findViewById(R.id.id);
            createDate = (TextView) itemView.findViewById(R.id.create_date);
            updateDate = (TextView) itemView.findViewById(R.id.update_date);
            taskSum = (TextView) itemView.findViewById(R.id.task_sum);
            finishNum = (TextView) itemView.findViewById(R.id.task_sum);
            finishSpeed = (TextView) itemView.findViewById(R.id.finish_speed);
        }
    }

    public TaskListAdapter(List<TaskList> taskLists){
        mTaskList = taskLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tasklist_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskList taskList = mTaskList.get(position);
        String newRecord;
        if (taskList.isIsNewRecord() == false){
            newRecord = "不是新记录";
        }else {
            newRecord = "新记录";
        }
        holder.username.setText("用户名："+ taskList.getName());
        holder.id.setText("用户id：" + taskList.getId());
        holder.isNewRecord.setText("是否新记录：" + newRecord);
        holder.createDate.setText("创建时间：" + taskList.getCreateDate());
        holder.updateDate.setText("更新时间：" + taskList.getUpdateDate());
        holder.taskSum.setText("任务总数：" + taskList.getTaskSum());
        holder.finishNum.setText("已完成任务数：" + taskList.getFinishNum());
        holder.finishSpeed.setText("完成速度：" + taskList.getFinishSpeed());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
