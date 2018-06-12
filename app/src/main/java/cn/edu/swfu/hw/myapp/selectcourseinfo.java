package cn.edu.swfu.hw.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class selectcourseinfo extends Activity {
    private Spinner spinner1;
    private Spinner spinner2;
    public TextView Monday12,Monday34,Monday56,Monday78,Monday910,Monday1112;
    public TextView Tuesday12,Tuesday34,Tuesday56,Tuesday78,Tuesday910,Tuesday1112;
    public TextView Wednesday12,Wednesday34,Wednesday56,Wednesday78,Wednesday910,Wednesday1112;
    public TextView Thursday12,Thursday34,Thursday56,Thursday78,Thursday910,Thursday1112;
    public TextView Friday12,Friday34,Friday56,Friday78,Friday910,Friday1112;
    private static final int FIND_XND=1;//学年度
    private static final int FIND_XQD=2;//学期
    private Button but_sumbit_xqxn;

    private ArrayAdapter<CharSequence> adapter = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 生命周期方法
        super.setContentView(R.layout.activity_selectcourseinfo); // 设置要使用的布局管理器
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        but_sumbit_xqxn=(Button) findViewById(R.id.bt_submit);
        final Myapp application = (Myapp)this.getApplication();

       //已经获得课表html页面
        Intent intentkb = this.getIntent();
        final String kbht = intentkb.getStringExtra("kbhtml");
        //在布局中显示课表
        getgrkb(kbht);

        //给spinner1绑定数据源
        adapter = new ArrayAdapter<CharSequence>(selectcourseinfo.this, android.R.layout.simple_spinner_item, HtmlTools.getXnd(kbht));// 根据position确定第二个spinner中要填充的数据
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置显示样式
        spinner1.setAdapter(adapter);

        //获得__VIEWSTATE
        final String __VIEWSTATE = HtmlTools.findViewState(kbht);

        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter = new ArrayAdapter<CharSequence>(selectcourseinfo.this, android.R.layout.simple_spinner_item, HtmlTools.getXqd(kbht));// 根据position确定第二个spinner中要填充的数据
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置显示样式
                spinner2.setAdapter(adapter);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //没被选择时干什么
            }
        });
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String string2 = parent.getItemAtPosition(position).toString();// 获取当前显示省份
               //上面一句是用来获得spinner中选中的值得
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //没被选择时干什么
            }
        });
        but_sumbit_xqxn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string1 = spinner1.getSelectedItem().toString();
                final String string2 = spinner2.getSelectedItem().toString();
                if (string1.equals("") || string2.equals("")) {
                    String title = "";
                    String mes = "请先选择你想用查询成绩的学年和学期";
                    showExitDialog(title, mes);
                } else {
                    String xh = application.getStuid();
                    //String xm= application.getStuname();
                    //这里是根据提交的学年及学期解析课表的代码
                    OkHttpUtils.post()
                            //loginUrl就是你请求解析的url
                            .url("http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xskbcx.aspx?xh=" + xh + "&xm=%B4%FA%BD%DD&gnmkdm=N121603")
                            //下面数据抓包可以得到
                            .addParams("__EVENTTARGET", "xnd")
                            .addParams("__EVENTARGUMENT", "")
                            .addParams("__VIEWSTATE", __VIEWSTATE)
                            .addParams("__VIEWSTATEGENERATOR", "55530A43")
                            .addParams("xnd", string1)
                            .addParams("xqd", string2)
                            .addHeader("Host", "202.203.132.204:8019")
                            .addHeader("Referer", "http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xskbcx.aspx?xh=" + xh + "&xm=(unable to decode value)&gnmkdm=N121603")
                            .build()
                            .connTimeOut(5000)
                            .execute(new StringCallback() {

                                @Override
                                public void onError(Call call, Exception e) {
                                    //请求失败
                                    String title = "未知故障";
                                    String mes = "连接服务器失败，建议联网重试";
                                    showExitDialog(title, mes);
                                }

                                @Override
                                public void onResponse(final String response) {
                                    //直接刷新页面，获得往年的课表
                                    getgrkb(response);
                                }
                            });
                    //这里解析课表结束了
                }
            }
        });
    }
    //得到每一节课的数据并赋值
    public void getgrkb(String html){
        ArrayList<Course> coursedata = HtmlTools.getCourseList(html);
        for(int i = 0;i < coursedata.size(); i ++){
            Course a = coursedata.get(i);
            String name = a.name;
            //String name = name1.substring(0, name1.indexOf("("));
            String classRoom = a.classRoom;
            String teacher= a.teacher;
            String weekNum = a.weekNum;
            String classTime = a.classTime;
            switch (classTime){
                case "周一第1,2节":
                    Monday12 = (TextView) findViewById(R.id.Monday12);
                    Monday12.setMovementMethod(new ScrollingMovementMethod());
                    Monday12.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周一第3,4节":
                    Monday34 = (TextView) findViewById(R.id.Monday34);
                    Monday34.setMovementMethod(new ScrollingMovementMethod());
                    Monday34.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周一第5,6节":
                    Monday56 = (TextView) findViewById(R.id.Monday56);
                    Monday56.setMovementMethod(new ScrollingMovementMethod());
                    Monday56.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周一第7,8节":
                    Monday78 = (TextView) findViewById(R.id.Monday78);
                    Monday78.setMovementMethod(new ScrollingMovementMethod());
                    Monday78.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周一第9,10节":
                    Monday910 = (TextView) findViewById(R.id.Monday910);
                    Monday910.setMovementMethod(new ScrollingMovementMethod());
                    Monday910.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周一第11,12节":
                    Monday1112 = (TextView) findViewById(R.id.Monday1112);
                    Monday1112.setMovementMethod(new ScrollingMovementMethod());
                    Monday1112.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周二第1,2节":
                    Tuesday12 = (TextView) findViewById(R.id.Tuesday12);
                    Tuesday12.setMovementMethod(new ScrollingMovementMethod());
                    Tuesday12.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周二第3,4节":
                    Tuesday34 = (TextView) findViewById(R.id.Tuesday34);
                    Tuesday34.setMovementMethod(new ScrollingMovementMethod());
                    Tuesday34.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周二第5,6节":
                    Tuesday56 = (TextView) findViewById(R.id.Tuesday56);
                    Tuesday56.setMovementMethod(new ScrollingMovementMethod());
                    Tuesday56.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周二第7,8节":
                    Tuesday78 = (TextView) findViewById(R.id.Tuesday78);
                    Tuesday78.setMovementMethod(new ScrollingMovementMethod());
                    Tuesday78.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周二第9,10节":
                    Tuesday910 = (TextView) findViewById(R.id.Tuesday910);
                    Tuesday910.setMovementMethod(new ScrollingMovementMethod());
                    Tuesday910.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周二第11,12节":
                    Tuesday1112 = (TextView) findViewById(R.id.Tuesday1112);
                    Tuesday1112.setMovementMethod(new ScrollingMovementMethod());
                    Tuesday1112.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周三第1,2节":
                    Wednesday12 = (TextView) findViewById(R.id.Wednesday12);
                    Wednesday12.setMovementMethod(new ScrollingMovementMethod());
                    Wednesday12.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周三第3,4节":
                    Wednesday34 = (TextView) findViewById(R.id.Wednesday34);
                    Wednesday34.setMovementMethod(new ScrollingMovementMethod());
                    Wednesday34.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周三第5,6节":
                    Wednesday56 = (TextView) findViewById(R.id.Wednesday56);
                    Wednesday56.setMovementMethod(new ScrollingMovementMethod());
                    Wednesday56.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周三第7,8节":
                    Wednesday78 = (TextView) findViewById(R.id.Wednesday78);
                    Wednesday78.setMovementMethod(new ScrollingMovementMethod());
                    Wednesday78.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周三第9,10节":
                    Wednesday910 = (TextView) findViewById(R.id.Wednesday910);
                    Wednesday910.setMovementMethod(new ScrollingMovementMethod());
                    Wednesday910.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周三第11,12节":
                    Wednesday1112 = (TextView) findViewById(R.id.Wednesday1112);
                    Wednesday1112.setMovementMethod(new ScrollingMovementMethod());
                    Wednesday1112.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周四第1,2节":
                    Thursday12 = (TextView) findViewById(R.id.Thursday12);
                    Thursday12.setMovementMethod(new ScrollingMovementMethod());
                    Thursday12.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周四第3,4节":
                    Thursday34 = (TextView) findViewById(R.id.Thursday34);
                    Thursday34.setMovementMethod(new ScrollingMovementMethod());
                    Thursday34.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周四第5,6节":
                    Thursday56 = (TextView) findViewById(R.id.Thursday56);
                    Thursday56.setMovementMethod(new ScrollingMovementMethod());
                    Thursday56.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周四第7,8节":
                    Thursday78 = (TextView) findViewById(R.id.Thursday78);
                    Thursday78.setMovementMethod(new ScrollingMovementMethod());
                    Thursday78.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周四第9,10节":
                    Thursday910 = (TextView) findViewById(R.id.Thursday910);
                    Thursday910.setMovementMethod(new ScrollingMovementMethod());
                    Thursday910.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周四第11,12节":
                    Thursday1112 = (TextView) findViewById(R.id.Thursday1112);
                    Thursday1112.setMovementMethod(new ScrollingMovementMethod());
                    Thursday1112.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周五第1,2节":
                    Friday12 = (TextView) findViewById(R.id.Friday12);
                    Friday12.setMovementMethod(new ScrollingMovementMethod());
                    Friday12.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周五第3,4节":
                    Friday34 = (TextView) findViewById(R.id.Friday34);
                    Friday34.setMovementMethod(new ScrollingMovementMethod());
                    Friday34.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周五第5,6节":
                    Friday56 = (TextView) findViewById(R.id.Friday56);
                    Friday56.setMovementMethod(new ScrollingMovementMethod());
                    Friday56.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周五第7,8节":
                    Friday78 = (TextView) findViewById(R.id.Friday78);
                    Friday78.setMovementMethod(new ScrollingMovementMethod());
                    Friday78.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周五第9,10节":
                    Friday910 = (TextView) findViewById(R.id.Friday910);
                    Friday910.setMovementMethod(new ScrollingMovementMethod());
                    Friday910.setText(name+classRoom+teacher+weekNum);
                    break;
                case "周五第11,12节":
                    Friday1112 = (TextView) findViewById(R.id.Friday1112);
                    Friday1112.setMovementMethod(new ScrollingMovementMethod());
                    Friday1112.setText(name+classRoom+teacher+weekNum);
                    break;
            }
        }
    }

    //弹出提示框
    private void showExitDialog(String title,String mes){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(mes)
                .setPositiveButton("确定", null)
                .show();
    }
}

