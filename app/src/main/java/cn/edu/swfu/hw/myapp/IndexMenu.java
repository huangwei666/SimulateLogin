package cn.edu.swfu.hw.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndexMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView xhxm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //这里是获得姓名展示的start
        Intent intent = this.getIntent();
        String xh_xm = intent.getStringExtra("name");
        //这里是将学号姓名截取出来，并使之持久化start
        String xh=xh_xm.substring(0,11);
        String xm=xh_xm.substring(12);
        Myapp application = (Myapp)this.getApplication();
        application.setStuid(xh);
        //application.setStuname(xm);
        //这里是将学号姓名截取出来，并使之持久化end
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        xhxm = (TextView) headerView.findViewById(R.id.welcome);
        xhxm.setText(xh_xm);
        //这里是获得姓名展示的end
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Myapp application = (Myapp)this.getApplication();
        String xh= application.getStuid();
        if (id == R.id.nav_camera) {
            //个人成绩查询
        }
        else if (id == R.id.nav_gallery) {
            //个人课表查询
            //这里是获得默认课表html
            OkHttpClient okHttpClient = new OkHttpClient();
            //2构造Request,
            URL url = null;
            try {
                url = new URL("http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xskbcx.aspx?xh="+xh+"&xm=(unable to decode value)&gnmkdm=N121603");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Request.Builder builder = new Request.Builder();
            //重定向的地址
            builder.addHeader("Referer","http://202.203.132.204:8019/(c5itei55ffluveaafibfulev)/xs_main.aspx?xh="+xh);
            builder.url(url);
            Request request = builder.build();

            //3将Request封装成call
            Call call = okHttpClient.newCall(request);

            //4，执行call，这个方法是异步请求数据
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //失败调用
                    //Log.e("MainActivity", "onFailure: " );
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String string = response.body().string();
                    Intent intentcb = new Intent();
                    intentcb.putExtra("kbhtml",string);
                    intentcb.setClass(IndexMenu.this,selectcourseinfo.class);
                    startActivity(intentcb);
                }
            });
        }
        else if (id == R.id.nav_slideshow) {
            //跳转到学校校历
            Intent intentxx = new Intent();
            intentxx.setClass(IndexMenu.this,schoolcalendar.class);
            startActivity(intentxx);
        }
        else if (id == R.id.nav_manage) {
            //一键评教

        }
        else if (id == R.id.nav_share) {
            //

        }
        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
