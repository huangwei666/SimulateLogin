package cn.edu.swfu.hw.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class selectscoreinfo extends AppCompatActivity {

    private Spinner spinnerxn;
    private Spinner spinnerxq;
    private TableLayout tableLayout;
    private Button but_sumbit_xnxq;
    public TextView mxnd,mxqd,mname,mproperty,mscore,mcredit;
    //public TextView name;
    private ArrayAdapter<CharSequence> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectscoreinfo);
        //基本布局声明start
        spinnerxn = (Spinner) findViewById(R.id.spinnerxn);
        spinnerxq = (Spinner) findViewById(R.id.spinnerxq);
        but_sumbit_xnxq=(Button) findViewById(R.id.bt_submit_xnxq);
        //布局打气筒的父级元素
        tableLayout = (TableLayout) findViewById(R.id.table_risk_profile);
        //tableLayout.setStretchAllColumns(true);//设置所有的item都可伸缩扩展
        //基本布局声明end
        final Myapp application = (Myapp)this.getApplication();
        //已经获得成绩html页面
        Intent intentcj = this.getIntent();
        final String cjht = intentcj.getStringExtra("cjhtml");
        //获得__VIEWSTATE
        final String __VIEWSTATE = HtmlTools.findViewState(cjht);
        //给spinner1绑定数据源
        adapter = new ArrayAdapter<CharSequence>(selectscoreinfo.this, android.R.layout.simple_spinner_item, HtmlTools.getddlXnd(cjht));// 根据position确定第二个spinner中要填充的数据
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置显示样式
        spinnerxn.setAdapter(adapter);

        spinnerxn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter = new ArrayAdapter<CharSequence>(selectscoreinfo.this, android.R.layout.simple_spinner_item, HtmlTools.getddlXqd(cjht));// 根据position确定第二个spinner中要填充的数据
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置显示样式
                spinnerxq.setAdapter(adapter);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //没被选择时干什么
            }
        });

        spinnerxq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //没被选择时干什么
            }
        });
        but_sumbit_xnxq.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String xn = spinnerxn.getSelectedItem().toString();
                final String xq = spinnerxq.getSelectedItem().toString();
                if (xn.equals("") || xq.equals("")) {
                    String title = "";
                    String mes = "请先选择你想用查询成绩的学年和学期";
                    showExitDialog(title, mes);
                } else {
                    String xh = application.getStuid();
                    //这里是根据提交的学年及学期解析成绩的代码
                    OkHttpUtils.post()
                            .url("http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xscjcx.aspx?xh=" + xh + "&xm=%B4%FA%BD%DD&gnmkdm=N121605")
                            //下面数据抓包可以得到
                            .addParams("__EVENTTARGET", "")
                            .addParams("__EVENTARGUMENT", "")
                            .addParams("__VIEWSTATE", __VIEWSTATE)
                            .addParams("__VIEWSTATEGENERATOR", "9727EB43")
                            .addParams("hidLanguage", "")
                            .addParams("ddlXN", xn)
                            .addParams("ddlXQ", xq)
                            //.addParams("ddlXN",xq)
                            .addParams("ddl_kcxz", "")
                            .addParams("btn_xq", "(unable to decode value)")
                            .addHeader("Host", "202.203.132.204:8019")
                            .addHeader("Referer", "http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xscjcx.aspx?xh=" + xh + "&xm=(unable to decode value)&gnmkdm=N121605")
                            .build()
                            .connTimeOut(5000)
                            .execute(new StringCallback() {

                                @Override
                                public void onError(Call call, Exception e) {
                                    //请求失败
                                    //course.setText("网络不通，连接服务器失败，请联网重试");
                                    String title = "未知故障";
                                    String mes = "连接服务器失败，建议联网重试";
                                    showExitDialog(title, mes);
                                }

                                @Override
                                public void onResponse(String response) {
                                    //直接刷新页面，获得往年的课表
                                    tableLayout.removeAllViews();
                                    ScoreTable score = HtmlTools.getScoreTable(response);
                                    ArrayList<CourseScore> score1 = score.getScoreList();
                                    int length = score1.size();//根据数据，判断行数
                                    for (int i = 0; i < length; i++) {
                                        CourseScore a = score1.get(i);//获取单行数据
                                        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                                        View layout = layoutInflater.inflate(R.layout.risk_profile_table_item, null);//布局打气筒获取单行对象
                                        mxnd = (TextView) layout.findViewById(R.id.showxnd);
                                        String xn = a.getXnd();
                                        mxnd.setText(xn);
                                        mxqd = (TextView) layout.findViewById(R.id.xqd);
                                        mxqd.setText(a.getXqd());
                                        mname = (TextView) layout.findViewById(R.id.name);
                                        mname.setText(a.getName());
                                        mproperty = (TextView) layout.findViewById(R.id.property);
                                        mproperty.setText(a.getProperty());
                                        mscore = (TextView) layout.findViewById(R.id.score);
                                        mscore.setText(a.getScore());
                                        mcredit = (TextView) layout.findViewById(R.id.credit);
                                        mcredit.setText(a.getCredit());
                                        tableLayout.addView(layout);//将这一行加入表格中
                                    }
                                }
                            });
                }
            }
        });
    }
    //弹出提示框
    private void showExitDialog(String title, String mes){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(mes)
                .setPositiveButton("确定", null)
                .show();
    }
}
