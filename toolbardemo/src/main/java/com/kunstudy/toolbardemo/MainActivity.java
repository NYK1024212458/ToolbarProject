package com.kunstudy.toolbardemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

/**
 * 主要的功能就是: 在建立单独的toolbar 的布局之外   使用按需加载的方法liatview实现toolbar
 *  一: 单独的toolbar详解
 *  二: 使用  include 和 viewstub来加载
 *  三: 沉浸式的实现
 *  四: 基于6.0权限的获取
 */

public class MainActivity extends AppCompatActivity {

    private ViewStub vb_mian_show;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoading = true;

        //  按需加载的实现

        vb_mian_show = (ViewStub) findViewById(R.id.vb_mian_show);
        //设置
        if (isLoading){
            View toolbar = vb_mian_show.inflate();
            initView(toolbar);
        }

    }

    private void initView(View toolbar) {
        //  初始toolbar设置监听
        Toolbar tb_toolbar = (Toolbar) toolbar.findViewById(R.id.tb_toolbar);
        //  设置toolbar的返回图片的监听
        tb_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Toast.makeText(MainActivity.this,"点击home",Toast.LENGTH_SHORT).show();
                        finish();

            }
        });
    }


    /**
     * 自定义Toolbar的实现  主要实现的就是搜索和tittle的写一起,展示的时候二选一
     *
     */
    public void test(View view){
        startActivity(new Intent(MainActivity.this,SecondActivity.class));

    }




}
