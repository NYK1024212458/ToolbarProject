package com.kunstudy.toolbardemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.kunstudy.toolbardemo.view.CustomToolbar;

/**
 * com.kunstudy.toolbardemo
 * <p>
 * Created by ${kun}
 * 2017/6/14
 */
public class SecondActivity extends AppCompatActivity{
    private static final String TAG = SecondActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2562;
    private Context mContext;
    private CustomToolbar mToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContext = SecondActivity.this;

        initView();
        checkPerssion();


        boolean b = ActivityCompat.shouldShowRequestPermissionRationale(SecondActivity.this, Manifest.permission.SEND_SMS);
        Log.d(TAG, "onCreate: 是否展示权限说明"+"==========="+b);

        //  最后就是整合到一起获取权限的方法
        //checkTrue();
    }

    private void checkTrue() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(SecondActivity.this,  // 检查是否获取到权限
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SecondActivity.this,  //是否应该给客户展示权限的说明

                    Manifest.permission.SEND_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(SecondActivity.this, //  开始动态的申请权限
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    private void initView() {
        //绑定这个控件就行了哈
        mToolBar = (CustomToolbar) findViewById(R.id.toolbar);

        //当然也可以调用里面的各种方法，来实现你想要的搜索框还是按钮和标题，当然也可以在布局文件中直接实现，是不是很简单呢
        /* mToolBar.hideTitleView();
        mToolBar.showSearchView();*/
    }
    public void off(View view){
        mToolBar.showToolbar(true);
    }
    public void on(View view){
        mToolBar.showToolbar(false);
    }
    public void enter3(View view){
        startActivity(new Intent(SecondActivity.this,ThridActivity.class));
    }

    /**
     * 基于6.0的权限获取
     * 我们动态获取 发送短信的权限来说明
     * 一: 申明
     * 二: 检查是否获取到权限
     */
    private void checkPerssion() {
        //  检查是否获取 发送短信的权限
        int i = ContextCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS);

        Log.d(TAG, "checkPerssion: 检查是否获取权限的返回值"+"=====================" +i);
        int permissionDenied = PackageManager.PERMISSION_DENIED;    // denied 否认. 拒绝 不承认

        Log.d(TAG, "checkPerssion: permissionDenied"+"=====================" +permissionDenied);
        int permissionGranted = PackageManager.PERMISSION_GRANTED; //  granted  授予同意
        Log.d(TAG, "checkPerssion: permissionGranted"+"=====================" +permissionGranted);

        //  没有获取权限我们获取权限

        if (PackageManager.PERMISSION_DENIED==i){
            // 此时要动态的获取权限  注意防范名 可以同时提取多个权限
            ActivityCompat.requestPermissions(SecondActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.  如果请求关闭了,那这个数据是空的
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG, "checkPerssion: 检查是否获取权限的返回值"+"=====================" +ContextCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS));
                } else {

                    // permission denied, boo! Disable the     没有获取到权限
                    // functionality that depends on this permission.


                }
                return;
            }
        }
    }
}
