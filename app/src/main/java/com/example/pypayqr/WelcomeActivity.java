package com.example.pypayqr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;


public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{

    private ImageView imageView;
    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        context = this;


        //取消ActionBar
        getSupportActionBar().hide();
        //取消狀態欄
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = (ImageView)findViewById(R.id.imageView);

        //imageView 設定動畫元件(透明度調整)
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        imageView.setAnimation(animation);


    }
    /*實作 Animation.AnimationListener 的三種方法*/
    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(context,MainActivity.class));
        finish();
    }




    @Override
    public void onAnimationRepeat(Animation animation) {}

}

