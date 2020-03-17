package com.damage0413.homework_4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder = new MyBinder();
    // 通过继承Binder来实现IBinder类
    public class MyBinder extends Binder {
        public int getCount(){
            // 获取Service的运行状态：count
            return count;
        }
    }
    public MyService() {
    }
    // 必须实现的onBind(方法
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService", "onBind 绑定");
        return binder;
    }
    // Service被创建时回调该方法。
    @Override
    public void onCreate(){
        super.onCreate();
        System.out.println("Service is Created");
        // 启动一条线程、动态地修改count状态值
        new Thread(){
            @Override
            public void run(){
                while (!quit){
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){}
                    count++;
                }
            }
        }.start();
    }
    // Service被断开连接时回调该方法
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyService", "onUnbind 解绑");
        return true;
    }
    // Service被关闭之前回调。
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        Log.d("MyService", "onDestroy 销毁");
    }
}