package com.example.logregapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;


public class MainActivity extends AppCompatActivity {

    ConstraintLayout myLay;
    AnimationDrawable anim;
    Animation frombottom;
    SparkButton btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLay = findViewById(R.id.myLayout);
        btn1 = findViewById(R.id.button1);
        anim=(AnimationDrawable)myLay.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(2000);
        anim.start();
        btn1 = findViewById(R.id.button1);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        btn1.setAnimation(frombottom);
        btn1.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                finish();
                startActivity(new Intent(MainActivity.this,Screen2.class));
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
            }
        });
    }
}

