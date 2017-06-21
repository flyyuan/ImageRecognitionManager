package com.example.yuan.imagerecognitionmanager.adapter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuan.imagerecognitionmanager.GetkeywordActivity;
import com.example.yuan.imagerecognitionmanager.R;
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
                Toast.makeText(view.getContext(),user.getName(),Toast.LENGTH_LONG).show();
            }
        });
        holder.forbidWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                String userId = user.getUserId();
                String status = "3";
                OkGo.get(URL_setPermissions+ ";JSESSIONID=" +holder.sessionid)
                        .params("userId",userId)
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.forbidSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                String userId = user.getUserId();
                String status = "4";
                OkGo.get(URL_setPermissions+ ";JSESSIONID=" +holder.sessionid)
                        .params("userId",userId)
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
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
                String status = "5";
                OkGo.get(URL_setPermissions+ ";JSESSIONID=" +holder.sessionid)
                        .params("userId",userId)
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return (ViewHolder) holder;
    }


    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.userNmae.setText(user.getName());

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
