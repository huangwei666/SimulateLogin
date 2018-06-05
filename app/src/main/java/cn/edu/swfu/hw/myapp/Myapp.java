package cn.edu.swfu.hw.myapp;

import android.app.Application;

public class Myapp extends Application {
    private String stuid;
    private String stuname;

    public String getStuid() {

        return stuid;

    }

    public void setStuid(String stuid) {

        this.stuid = stuid;

    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }
}
