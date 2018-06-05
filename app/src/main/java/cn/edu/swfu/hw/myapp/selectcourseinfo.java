package cn.edu.swfu.hw.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class selectcourseinfo extends Activity {
    private Spinner spinner1;
    private Spinner spinner2;
    private TextView course;
    private static final int FIND_XND=1;//学年度
    private static final int FIND_XQD=2;//学期

    private Button but_sumbit_xqxn;

    private String[][] dataStrings = { { "1", "2", "3", }, { "1", "2", "3", }, { "1", "2", "3", }, };
    private ArrayAdapter<CharSequence> adapter = null;
    //private String __VIEWSTATE = "dDwtMTY3ODA2Njg2OTt0PDtsPGk8MT47PjtsPHQ8O2w8aTwxPjtpPDI+O2k8ND47aTw3PjtpPDk+O2k8MTE+O2k8MTM+O2k8MTU+O2k8MjE+O2k8MjM+O2k8MjU+O2k8Mjc+O2k8Mjk+O2k8MzE+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPDIwMDctMjAwODE7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8eG47eG47Pj47Pjt0PGk8Mz47QDwyMDE3LTIwMTg7MjAxNi0yMDE3OzIwMTUtMjAxNjs+O0A8MjAxNy0yMDE4OzIwMTYtMjAxNzsyMDE1LTIwMTY7Pj47bDxpPDE+Oz4+Ozs+O3Q8dDw7O2w8aTwxPjs+Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWtpuWPt++8mjIwMTUxMTUyMDM4Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlp5PlkI3vvJrpu4TkvJ87Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWtpumZou+8muWkp+aVsOaNruS4juaZuuiDveW3peeoi+WtpumZojs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85LiT5Lia77ya6K6h566X5py656eR5a2m5LiO5oqA5pyvOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJrorqHnrpfmnLrnp5HlrabkuI7mioDmnK8yMDE1KDIp54+tOz4+Oz47Oz47dDw7bDxpPDE+Oz47bDx0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+O3Q8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47bDxpPDE+Oz47bDx0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+O3Q8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDI+O2k8Mj47bDw+Oz4+Oz47Ozs7Ozs7Ozs7PjtsPGk8MD47PjtsPHQ8O2w8aTwxPjtpPDI+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDwxMjE3Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxMjE3Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzosIMwMzc3Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwoMjAxNi0yMDE3LTIpLTExMTAwMTE2LTEyMTctMUE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiuoeeul+acuuWbvuW9ouWtpjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85ZGoM+esrDPoioLov57nu60y6IqCe+esrDgtOOWRqH0v56ys5LiJ5pWZ5a2m5qW8MzAz6Zi25qKv5pWZ5a6kL+W8uuaMr+W5szs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85ZGoM+esrDPoioLov57nu60y6IqCe+esrDgtOOWRqH0v57uP566h5qW8MjE25py65oi/L+W8uuaMr+W5szs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNy0wNC0xMS0xNy0yMzs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2PjtpPDc+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPDIxMDQ7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIxMDQ7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiwgzA3Mjc7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCgyMDE2LTIwMTctMiktMzExMDAwNjctMjEwNC0xQTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8572R57uc6Lev55Sx5oqA5pyvOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlkagy56ysMTHoioLov57nu60y6IqCe+esrDE2LTE25ZGofS/nrKzkuInmlZnlrabmpbwyMDflpJrlqpLkvZPmlZnlrqQv54aK6aOeOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlkagy56ysM+iKgui/nue7rTLoioJ756ysMTYtMTblkah9L0QxMzborqHnrpfmnLrmiL/vvIg077yJL+eGiumjnjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNy0wNi0wOS0xNC0zMjs+Pjs+Ozs+Oz4+Oz4+Oz4+O3Q8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDI+O2k8Mj47bDw+Oz4+Oz47Ozs7Ozs7Ozs7PjtsPGk8MD47PjtsPHQ8O2w8aTwxPjtpPDI+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8572R57uc6Lev55Sx5oqA5pyv5a6e5LmgOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDznhorpo547Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDAuNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTctMTk7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47PjtsPHQ8cDxwPGw8VGV4dDs+O2w86Z2i5ZCR5a+56LGh56iL5bqP6K6+6K6h77yISmF2Ye+8ieWunuS5oDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85L2V6ZGrOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxLjA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDE3LTE5Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47Pj47Pj47dDxAMDxwPHA8bDxQYWdlQ291bnQ7XyFJdGVtQ291bnQ7XyFEYXRhU291cmNlSXRlbUNvdW50O0RhdGFLZXlzOz47bDxpPDE+O2k8MD47aTwwPjtsPD47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8UGFnZUNvdW50O18hSXRlbUNvdW50O18hRGF0YVNvdXJjZUl0ZW1Db3VudDtEYXRhS2V5czs+O2w8aTwxPjtpPDI+O2k8Mj47bDw+Oz4+Oz47Ozs7Ozs7Ozs7PjtsPGk8MD47PjtsPHQ8O2w8aTwxPjtpPDI+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDwyMDE2LTIwMTc7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOmdouWQkeWvueixoeeoi+W6j+iuvuiuoe+8iEphdmHvvInlrp7kuaA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS9lemRqzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MS4wOz4+Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8MjAxNi0yMDE3Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDznvZHnu5zot6/nlLHmioDmnK/lrp7kuaA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOeGiumjnjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MC41Oz4+Oz47Oz47Pj47Pj47Pj47Pj47Pj47Pt63P1RaBZgdJN1ui4MlfNRqhgNV";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 生命周期方法
        super.setContentView(R.layout.activity_selectcourseinfo); // 设置要使用的布局管理器
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //course = (TextView) findViewById(R.id.course);
        course = (TextView)this.findViewById(R.id.course);
        course.setMovementMethod(ScrollingMovementMethod.getInstance());
        but_sumbit_xqxn=(Button) findViewById(R.id.bt_submit);
        final Myapp application = (Myapp)this.getApplication();

       //这里已经获得课表html页面
        Intent intentkb = this.getIntent();
        final String kbht = intentkb.getStringExtra("kbhtml");
        String grkbcx = findCourseTableHtml(kbht);
        course.setText(grkbcx);
        adapter = new ArrayAdapter<CharSequence>(selectcourseinfo.this, android.R.layout.simple_spinner_item, getXnd(kbht));// 根据position确定第二个spinner中要填充的数据
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置显示样式
        spinner1.setAdapter(adapter);
        final String __VIEWSTATE = findViewState(kbht);

        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter = new ArrayAdapter<CharSequence>(selectcourseinfo.this, android.R.layout.simple_spinner_item, getXqd(kbht));// 根据position确定第二个spinner中要填充的数据
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置显示样式
                spinner2.setAdapter(adapter);
               // String string1 = parent.getItemAtPosition(position).toString();// 获取当前显示省份

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
               String string1 = spinner1.getSelectedItem().toString();
               String string2 = spinner2.getSelectedItem().toString();
               String xh= application.getStuid();
               //String xm= application.getStuname();
                //这里是根据提交的学年及学期解析课表的代码
                OkHttpUtils.post()
                        //loginUrl就是你请求解析的url
                        .url("http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xskbcx.aspx?xh="+xh+"&xm=%B4%FA%BD%DD&gnmkdm=N121603")
                        //下面数据抓包可以得到
                        .addParams("__EVENTTARGET", "xnd")
                        .addParams("__EVENTARGUMENT", "")
                        .addParams("__VIEWSTATE",__VIEWSTATE)
                        .addParams("__VIEWSTATEGENERATOR","55530A43")
                        .addParams("xnd",string1)
                        .addParams("xqd",string2)
                        .addHeader("Host", "202.203.132.204:8019")
                        .addHeader("Referer", "http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xskbcx.aspx?xh="+xh+"&xm=(unable to decode value)&gnmkdm=N121603")
                        .build()
                        .connTimeOut(5000)
                        .execute(new StringCallback() {

                            @Override
                            public void onError(Call call, Exception e) {
                                //请求失败
                                course.setText("网络不通，连接服务器失败，请联网重试");
                            }

                            @Override
                            public void onResponse(String response) {
                                course.setText(findCourseTableHtml(response));
                                //请求成功，response就是得到的html文件（网页源代码）
                            }
                        });
                //这里解析课表结束了
            }
        });


    }
    /*解析个人课表*/
    private static String findCourseTableHtml(String html){
        String res="";
        String tar="<table id=\"Table1\" class=\"blacktab\" bordercolor=\"Black\" border=\"0\" width=\"100%\">";
        String pattern="<table id=\"Table1\" class=\"blacktab\" bordercolor=\"Black\" border=\"0\" width=\"100%\">([\\S\\s]+?)</table>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(0);
            res=res.substring(res.indexOf(tar)+tar.length(),res.lastIndexOf("</table>")).trim();
        }else{
            System.out.println(html);
            System.out.println("什么都没有");
        }
        return res;
    }
    /**
     * 在HTML中提取学年度或者学期的选项的HTML记录串
     * @param html HTML文档
     * @param x 代表学期或者学年度的参数
     * @return  返回HTML表示的学年度或者学期选项
     */
    private static String findXndOrXqdHtml(String html,int x){
        String res="";
        String pattern=null;
        switch(x){
            case FIND_XND:
                pattern="<select name=\"xnd\" onchange=\"__doPostBack\\('xnd',''\\)\" language=\"javascript\" id=\"xnd\">([\\s\\S]+?)</select>";
                break;
            case FIND_XQD:
                pattern="<select name=\"xqd\" onchange=\"__doPostBack\\('xqd',''\\)\" language=\"javascript\" id=\"xqd\">([\\s\\S]+?)</select>";
                break;
        }
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(1);
        }
        return res.trim();
    }

    /**
     * 在HTML中提取学年度或者学期的选项列表
     * @param html HTML文档
     * @param x 代表学年度或者学期
     * @return 返回学年度或者学期的选项数组
     */
    private static String[] getOptions(String html,int x){

        String[] ops=null;
        String res="";
        String tar=findXndOrXqdHtml(html, x);
        ArrayList<String> arr=new ArrayList<String>();
        String pattern="<option([\\s\\S]*?)>(.*?)</option>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(tar);
        while(m.find()){
            res=m.group(2);
            arr.add(res);
        }

        ops=new String[arr.size()];
        for(int i=0;i<ops.length;i++){
            ops[i]=arr.get(i);
        }

        return ops;
    }

    /**
     * 获取学年度选项
     * @param html HTML文档
     * @return 返回学年度选项数组
     */
    public static String[] getXnd(String html){
        return getOptions(html, FIND_XND);
    }

    /**
     * 获取学期选项数组
     * @param html HTML文档
     * @return 学期选项数组
     */
    public static String[] getXqd(String html){
        return getOptions(html, FIND_XQD);
    }
    /**
     * 查找__VIEWSTATE参数的值
     * @param html HTML文档
     * @return 返回
     */
    public static String findViewState(String html) {
        String res="";
        String pattern="<input type=\"hidden\" name=\"__VIEWSTATE\" value=\"(.*?)\" />";

        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);

        if(m.find()){
            res=m.group();
            res=res.substring(res.indexOf("value=\"")+7,res.lastIndexOf("\""));
        }
        return res;
    }
}

