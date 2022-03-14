package com.cqc.servicelearning;

import androidx.appcompat.app.AppCompatActivity;

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
    private final String TAG = "MainActivity";

    private Button start;
    private Button stop;
    private Button btnbind;
    private Button btncancel;
    private Button btnstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate() enter");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testStartService();

        testBindService();
    }

    //保持所启动的Service的IBinder对象,同时定义一个ServiceConnection对象
    TestService2.MyBinder binder; //客户端对Service操作的一个对象（规定好的数据结构？）
    private ServiceConnection serviceConnection = new ServiceConnection() {

        //Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"------Service DisConnected-------");
            System.out.println("------Service DisConnected-------");
        }

        //Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"------Service Connected-------");
            System.out.println("------Service Connected-------");
            binder = (TestService2.MyBinder) service; //初始化binder对象,service是onBinder返回的
        }
    };



    private void testBindService() {

        btnbind = findViewById(R.id.button_bind);
        btncancel =  findViewById(R.id.button_cance);
        btnstatus  = findViewById(R.id.button_getstatus);
        final Intent intent = new Intent();
        intent.setAction("com.jay.example.service.TEST_SERVICE2");
        intent.setPackage(getPackageName());

        btnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定service
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解除service绑定
                unbindService(serviceConnection);
            }
        });

        btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Service的count的值为:"
                        + binder.getCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void testStartService() {
        start = findViewById(R.id.button_start);
        stop = findViewById(R.id.button_stop);

        Intent intent = new Intent();
        intent.setAction("com.jay.example.service.TEST_SERVICE1");
        intent.setPackage(getPackageName());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
    }


}