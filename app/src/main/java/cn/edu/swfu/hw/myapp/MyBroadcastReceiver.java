package cn.edu.swfu.hw.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("cn.edu.swfu.hw.myapp")){
            //IndexMenu.return_xh(intent.getStringExtra("stud"));
        }

    }
}
