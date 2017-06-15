package com.kunstudy.toolbardemo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kunstudy.toolbardemo.view.CustomToolbar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import static com.kunstudy.toolbardemo.R.id.toolbar;

/**
 * com.kunstudy.toolbardemo
 * <p>
 * Created by ${kun}
 * 2017/6/14
 */
public class ThridActivity extends AppCompatActivity {

    private CustomToolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);
        initSystemBar();

        initToolbar();
    }

    private void initToolbar() {
        toolbar = (CustomToolbar) findViewById(R.id.toolbar);

    }


    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            //修改window的综合属性flags
            //WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS含义为状态栏透明
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            win.setAttributes(winParams);
        }
        //调用开源库SystemBarTintManager进行状态栏着色 产生沉浸式效果
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);//使用状态栏着色可用
        tintManager.setStatusBarTintColor(Color.GREEN);//指定颜色进行着色
    }

    /**
     * 简单的实现toolbar的渐变效果
     */
    float scale = 0.5f;
    //实现点击toolbar的渐变效果
    public void change(View view) {
        //透明度渐变
        scale += 0.05;
        if (scale >= 1) {
            scale = 0.5f;
        }
        float alpha = scale * 255;
        toolbar.setBackgroundColor(Color.argb((int) alpha, 57, 174, 255));
    }




}
