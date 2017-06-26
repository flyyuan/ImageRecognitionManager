package com.example.yuan.imagerecognitionmanager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;
import com.example.yuan.imagerecognitionmanager.adapter.MyDividerItemDecoration;
import com.example.yuan.imagerecognitionmanager.adapter.TaskListAdapter;
import com.example.yuan.imagerecognitionmanager.javaBean.TaskList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class FindTaskByDateActivity extends AppCompatActivity {
    TextView mTitle;
    CalendarDateView mCalendarDateView;
    RecyclerView taskList;
    TaskListAdapter taskListAdapter;
    private String sessionid;
    private static final String URL_base = "http://114.115.139.232:8080/xxzx/a/tpsb/";
    private static final String URL_findTaskByDate =URL_base + "task/findTaskByDate";
    private List<TaskList> taskLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_task_by_date);
        ButterKnife.bind(this);
        mCalendarDateView = (CalendarDateView) findViewById(R.id.calendarDateView);
        mTitle = (TextView) findViewById(R.id.calendar_title);
        taskList = (RecyclerView) findViewById(R.id.taskList);
        initView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        taskList.setLayoutManager(layoutManager);
        taskList.setLayoutManager(layoutManager);
        taskList.addItemDecoration(new MyDividerItemDecoration(FindTaskByDateActivity.this,LinearLayoutManager.VERTICAL));
        //在SP中获取sessionid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FindTaskByDateActivity.this);
        sessionid = prefs.getString("sessionid","");
    }

    private void findTaskByDate(String date) {
        OkGo.get(URL_findTaskByDate+ ";JSESSIONID=" +sessionid)
                .params("date",date)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("findTaskByDate------>", s);
                        parseJSONWithGSON(s);
                    }
                });
    }

    private  void parseJSONWithGSON(String s){
        Gson gson = new Gson();
        taskLists = gson.fromJson(s, new TypeToken<List<TaskList>>(){}.getType());
        taskListAdapter = new TaskListAdapter(taskLists);
        taskList.setAdapter(taskListAdapter);
    }

    private void initView() {
        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                //判断convertView为null，可以有效利用view的回收重用，左右滑动的效率高
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.calendar_item, null);
                }

                TextView chinaText = (TextView) convertView.findViewById(R.id.chinaText);
                TextView text = (TextView) convertView.findViewById(R.id.text);

                text.setText("" + bean.day);
                //mothFlag 0是当月，-1是月前，1是月后
                if (bean.mothFlag != 0) {
                    text.setTextColor(0xff9299a1);
                } else {
                    text.setTextColor(0xff444444);
                }
                chinaText.setText(bean.chinaDay);

                return convertView;
            }
        });

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                String moth = "06";
                String day = "24";
                mTitle.setText(bean.year + "/" + bean.moth + "/" + bean.day);
                if(bean.moth>=1 && bean.moth<=9){
                    moth = "0" + bean.moth;
                }
                else {
                    moth = String.valueOf(bean.moth);
                }
                if(bean.day >= 1 && bean.day<=9){
                    day = "0" + bean.day;
                }
                else {
                    day = String.valueOf(bean.day);
                }
                String date = bean.year + "-" + moth + "-" + day;
                Log.d("date------->", date);
                findTaskByDate(date);
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText("当前日期：" + data[0] + "/" + data[1] + "/" + data[2]);
    }

}

