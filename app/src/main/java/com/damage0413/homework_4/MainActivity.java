package com.damage0413.homework_4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBtnbind;
    private Button mBtnunbind;
    private Button mBtngetServiceStatus;
    MyService.MyBinder mBinder;
    private ServiceConnection conn = new ServiceConnection(){
        // 当该Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("MainActivity", "onServiceConnected 连接成功");
            mBinder = (MyService.MyBinder) service;
        }
        // 当该Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "onServiceDisconnected 断开连接");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取程序界面中的start、stop、getServiceStatus按钮
        mBtnbind = (Button)findViewById(R.id.bind);
        mBtnunbind = (Button)findViewById(R.id.unbind);
        mBtnunbind.setEnabled(false);
        mBtngetServiceStatus = (Button)findViewById(R.id.getServiceStatus);
        //创建启动Service的Intent
        final Intent intent = new Intent();
        //为Intent设置Action属性
        intent.setAction("com.damage0413.homework_4.MyService");
        intent.setPackage(this.getPackageName());
        mBtnbind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //绑定指定Serivce
                bindService(intent,conn, Service.BIND_AUTO_CREATE);
                mBtnunbind.setEnabled(true);
            }
        });
        mBtnunbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解除绑定Serivce
                unbindService(conn);
                mBtnunbind.setEnabled(false);
            }
        });
        mBtngetServiceStatus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant", "ShowToast"})
            @Override
            public void onClick(View v) {
                // 获取、并显示Service的count值
                Toast.makeText(MainActivity.this
                        , "Serivce的count值为：" + mBinder.getCount()
                        , 4000).show();

            }
        });
    }
}
