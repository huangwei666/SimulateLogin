package cn.edu.swfu.hw.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class NewAisle extends AppCompatActivity {

    public EditText etstuId;
    private EditText etstuPsw;
    private CheckBox checkBox, checkBox2;
    //声明一个SharedPreferences对象和一个Editor对象
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //登录url
    private String loginUrl = "http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/default5.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_aisle);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        //获取preferences和editor对象
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = preferences.edit();
        etstuId = (EditText) findViewById(R.id.et_stuid);
        etstuPsw = (EditText) findViewById(R.id.et_stupsw);
        /*
    启动程序时首先检查sharedPreferences中是否储存有用户名和密码
    若无，则将checkbox状态显示为未选中
    若有，则直接中sharedPreferences中读取用户名和密码，并将checkbox状态显示为已选中
    这里getString()方法需要两个参数，第一个是键，第二个是值。
    启动程序时我们传入需要读取的键，值填null即可。若有值则会自动显示，没有则为空。
     */
        String name = preferences.getString("userName",null);
        if (name == null) {
            checkBox.setChecked(false);
        } else {
            etstuId.setText(name);
            checkBox.setChecked(true);
        }
        String password = preferences.getString("userPassword", null);
        if (password == null) {
            checkBox2.setChecked(false);
        } else {
            etstuPsw.setText(password);
            checkBox2.setChecked(true);
        }
    }





    /**
     * 向服务器登录
     *
     * @param view
     */
    public void login(View view) {
        //用户输入的值
        final String stuId = etstuId.getText().toString();
        final String stuPsw = etstuPsw.getText().toString();
        //这里应该做一些空值判断
        if (stuId.equals("") || stuPsw.equals("")) {
            String title = "";
            String mes = "用户名密码不能为空，请重新输入";
            showExitDialog00(title, mes);
        } else {
            if (checkBox.isChecked()) {
                //如果用户选择了记住用户名
                //将用户输入的用户名存入储存中，键为userName
                editor.putString("userName", stuId);
                editor.commit();
            } else {
                //否则将用户名清除
                editor.remove("userName");
                editor.commit();
            }
            if (checkBox2.isChecked()) {
                //如果用户选择了记住密码
                //将用户输入的密码存入储存中，键为userName
                editor.putString("userPassword", stuPsw);
                editor.commit();
            } else {
                //否则将密码清除
                editor.remove("userPassword");
                editor.commit();
            }
            //请求登录
            OkHttpUtils.post()
                    //loginUrl就是你请求登录的url
                    .url(loginUrl)
                    //下面数据抓包可以得到
                    .addParams("__VIEWSTATE", "dDwtNTgxODgzNDk1O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDQ+Oz47bDx0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjs+Pjs+Pjs+ma9CuqoFzgztlig1T9/UCOZm3iE=")
                    .addParams("__VIEWSTATEGENERATOR", "994E02A8")
                    .addParams("TextBox1", stuId) //学号，
                    .addParams("TextBox2", stuPsw)//密码
                    .addParams("RadioButtonList1", "(unable to decode value)")
                    .addParams("Button1", "")
                    .addHeader("Host", "202.203.132.204:8019")
                    .addHeader("Referer", "http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/default5.aspx")
                    .build()
                    .connTimeOut(5000)
                    .execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e) {
                            //请求失败
                            //tvContent.setText("网络不通，连接服务器失败，请联网重试");
                            String title = "获取网页失败";
                            String mes = "网络不通，连接服务器失败，请稍后重试";
                            showExitDialog00(title, mes);
                            //若登陆不成功，则将错误的用户名和密码清除，并提示登陆失败
                            editor.remove("userName");
                            editor.remove("userPassword");
                            editor.commit();
                        }

                        @Override
                        public void onResponse(String response) {
                            //System.out.println("onResponse");
                            //请求成功，response就是得到的html文件（网页源代码）
                            if (response.contains("密码错误")) {
                                //如果源代码包含“密码错误”
                                String title = "";
                                String mes = "验证码不正确";
                                showExitDialog00(title, mes);
                            } else if (response.contains("用户名不存在")) {
                                //如果源代码包含“用户名不存在”
                                String title = "";
                                String mes = "用户名不存在,请检查重新输入";
                                showExitDialog00(title, mes);
                            } else {
                                Map name = getNameOrFailedInfo(response);
                                String name1 = (String) name.get("0");
                                Intent intent = new Intent();
                                intent.setClass(NewAisle.this, IndexMenu.class);
                                intent.putExtra("name", name1);
                                intent.putExtra("stud", etstuId.getText().toString());
                                startActivity(intent);
                            }
                        }
                    });
        }
    }
    public void cancel(View view) {
        //取消登录则结束
        finish();
    }
    public void oldaisle(View view){
        Intent newaisle = new Intent(NewAisle.this,MainActivity.class);
        startActivity(newaisle);
    }
    //用jsoup解析网页获得学号+姓名
    private Map<String, String> getNameOrFailedInfo(String html) {
        Map<String, String> returnInfo = new LinkedHashMap<>();
        if (null != html) {
            Document document = Jsoup.parse(html);
            Element nameElement = document.getElementById("xhxm");
            if (null != nameElement) {
                String studentName = nameElement.html();
                Pattern p = Pattern.compile("(.+)[^同学]");
                Matcher m = p.matcher(studentName);
                if(m.find()) {
                    returnInfo.put("0", m.group());
                }
            } else {
                // 找不到学生姓名，说明登录失败，跳转回了登录界面。这里取得登录失败的原因后返回
                Element infoElement = document.select("script[defer]").last();
                if (null != infoElement) {
                    String login_failed_info = infoElement.html();
                    Pattern p = Pattern.compile("([\\u4E00-\\u9FA5]+)");
                    Matcher m = p.matcher(login_failed_info);
                    if(m.find()) {
                        returnInfo.put("1", m.group());
                    }
                }
            }
        }
        return returnInfo;
    }


    //弹出提示框
    private void showExitDialog00(String title,String mes){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(mes)
                .setPositiveButton("确定", null)
                .show();
    }
}
