package com.example.logregapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;
//Implement this class on clicking Help
public class onBoard extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Fragment1
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.img1) // int top drawable
                .setSummary("This is summary")
                .build());

        //Fragment2
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.img2) // int top drawable
                .setSummary("This is summary")
                .build());

        //Fragment3
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.img3) // int top drawable
                .setSummary("This is summary")
                .build());
        //Complete UI using below

        //setPrevText(text); // Previous button text
        //setNextText(text); // Next button text
        //setFinishText(text); // Finish button text
        //setCancelText(text); // Cancel button text
        //setIndicatorSelected(int drawable); // Indicator drawable when selected
        //setIndicator(int drawable); // Indicator drawable
        //setGivePermissionText(String text); // Permission button text

    }
    @Override
    public void finishTutorial() {
        finish();
        startActivity(new Intent(onBoard.this,Screen5.class));
    }
    @Override
    public void currentFragmentPosition(int position) { }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }
}
