package com.example.yuan.imagerecognitionmanager.adapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuan.imagerecognitionmanager.GetkeywordActivity;
import com.example.yuan.imagerecognitionmanager.R;
import com.example.yuan.imagerecognitionmanager.StateFragment;
import com.example.yuan.imagerecognitionmanager.javaBean.User;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Yuan on 2017/6/20.
 * class comment:
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> mUserList;
    private String URL_setPermissions = "http://114.115.139.232:8080/xxzx/a/tpsb/userManage/setPermissions";
    StateFragment state;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View userView;
        ImageView userImage;
        TextView userNmae;
        Button forbidWriteBtn;
        Button forbidSelectBtn;
        Button forbidWriteAndSelectBtn;
        private String sessionid;
        public ViewHolder(View itemView) {
            super(itemView);
            userView = itemView;
            userImage = (ImageView) itemView.findViewById(R.id.user_image);
            userNmae = (TextView) itemView.findViewById(R.id.user_name);
            forbidWriteBtn = (Button) itemView.findViewById(R.id.forbid_write);
            forbidSelectBtn = (Button) itemView.findViewById(R.id.forbid_select);
            forbidWriteAndSelectBtn = (Button) itemView.findViewById(R.id.forbid_write_select);
            //在SP中获取sessionid
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());
            sessionid = prefs.getString("sessionid","");
        }
    }

    public UserAdapter(List<User> userList){
        mUserList = userList;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                int writeNum = user.getWriteNum();
                int selectNum = user.getSelectNum();
                if (writeNum == 0){writeNum = 1;}
                if (selectNum == 0){selectNum = 1;}
                float writeSuccRate = user.getWriteSuccNum()/writeNum;
                float selectSuccRate = user.getSelectSuccNum()/selectNum;
                Toast.makeText(view.getContext(),user.getName()+"\n填写正确率:"+writeSuccRate
                        +"\n选择正确率:"+selectSuccRate,Toast.LENGTH_SHORT).show();
            }
        });
        holder.forbidWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                String userId = user.getUserId();
                int status = 3;
                if (user.getJurisdicStatus() == status){
                    status = 1;
                    holder.forbidWriteBtn.setText("当前:优先填写");
                    holder.forbidWriteBtn.setBackgroundColor(Color.RED);
                    holder.forbidWriteAndSelectBtn.setText("禁止填写选择");
                    holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.GREEN);
                    holder.forbidSelectBtn.setText("禁止选择");
                    holder.forbidSelectBtn.setBackgroundColor(Color.GREEN);
                }else {
                    holder.forbidWriteBtn.setText("当前:禁止填写");
                    holder.forbidWriteBtn.setBackgroundColor(Color.RED);
                    holder.forbidWriteAndSelectBtn.setText("禁止填写选择");
                    holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.GREEN);
                    holder.forbidSelectBtn.setText("禁止选择");
                    holder.forbidSelectBtn.setBackgroundColor(Color.GREEN);

                }
                OkGo.get(URL_setPermissions+ ";JSESSIONID=" +holder.sessionid)
                        .params("userId",userId)
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                              }
                        });
            }
        }
        );

        holder.forbidSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                String userId = user.getUserId();
                int status = 4;
                if (user.getJurisdicStatus() == status){
                    status = 2;
                    holder.forbidSelectBtn.setText("当前:优先填写");
                    holder.forbidSelectBtn.setBackgroundColor(Color.RED);
                    holder.forbidWriteAndSelectBtn.setText("禁止填写选择");
                    holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.GREEN);
                    holder.forbidWriteBtn.setText("禁止填写");
                    holder.forbidWriteBtn.setBackgroundColor(Color.GREEN);
                }else {
                    holder.forbidSelectBtn.setText("当前:禁止选择");
                    holder.forbidSelectBtn.setBackgroundColor(Color.RED);
                    holder.forbidWriteAndSelectBtn.setText("禁止填写选择");
                    holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.GREEN);
                    holder.forbidWriteBtn.setText("禁止填写");
                    holder.forbidWriteBtn.setBackgroundColor(Color.GREEN);

                }
                OkGo.get(URL_setPermissions+ ";JSESSIONID=" +holder.sessionid)
                        .params("userId",userId)
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                            }
                        });
            }
        });

        holder.forbidWriteAndSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                String userId = user.getUserId();
                int status = 5;
                if (user.getJurisdicStatus() == status){
                    status = 6;
                    holder.forbidWriteAndSelectBtn.setText("当前:正常");
                    holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.RED);
                    holder.forbidSelectBtn.setText("禁止选择");
                    holder.forbidSelectBtn.setBackgroundColor(Color.GREEN);
                    holder.forbidWriteBtn.setText("禁止填写");
                    holder.forbidWriteBtn.setBackgroundColor(Color.GREEN);
                }else {
                    holder.forbidWriteAndSelectBtn.setText("当前:禁止填写选择");
                    holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.RED);
                    holder.forbidSelectBtn.setText("禁止选择");
                    holder.forbidSelectBtn.setBackgroundColor(Color.GREEN);
                    holder.forbidWriteBtn.setText("禁止填写");
                    holder.forbidWriteBtn.setBackgroundColor(Color.GREEN);

                }
                OkGo.get(URL_setPermissions+ ";JSESSIONID=" +holder.sessionid)
                        .params("userId",userId)
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                            }
                        }
                        );
            }
        });

        return (ViewHolder) holder;
    }


    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.userNmae.setText(user.getName());
        switch(user.getJurisdicStatus()){
            case 1:
                holder.forbidWriteBtn.setText("当前:优先填写");
                holder.forbidWriteBtn.setBackgroundColor(Color.RED);
                break;
            case 2:
                holder.forbidSelectBtn.setText("当前:优先选择");
                holder.forbidSelectBtn.setBackgroundColor(Color.RED);
                break;
            case 3:
                holder.forbidWriteBtn.setText("当前：禁止填写");
                holder.forbidWriteBtn.setBackgroundColor(Color.RED);
                break;
            case 4:
                holder.forbidSelectBtn.setText("当前：禁止选择");
                holder.forbidSelectBtn.setBackgroundColor(Color.RED);
            case 5:
                holder.forbidWriteAndSelectBtn.setText("当前：禁止填写选择");
                holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.RED);
                break;
            case 6:
                holder.forbidWriteAndSelectBtn.setText("当前:正常");
                holder.forbidWriteAndSelectBtn.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
