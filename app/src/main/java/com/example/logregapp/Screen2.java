package com.example.logregapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Screen2 extends AppCompatActivity {

    ConstraintLayout myLay;
    AnimationDrawable anim;
    Animation frombottom;
    Button btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        myLay = findViewById(R.id.myLayout1);
        anim=(AnimationDrawable)myLay.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(2000);
        anim.start();
        btn2 = findViewById(R.id.button2);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        btn2.setAnimation(frombottom);
        btn3 = findViewById(R.id.button3);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        btn3.setAnimation(frombottom);
    }

    public void toAct3(View view) {
        Intent i = new Intent(this,Screen3.class);
        startActivity(i);
    }

    public void toAct4(View view) {
        Intent i = new Intent(this,Screen4.class);
        startActivity(i);
    }
}
