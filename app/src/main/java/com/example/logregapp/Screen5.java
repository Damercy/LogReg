package com.example.logregapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class Screen5 extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void openProfile(View view) {
        //Intent to go to profile activity
        startActivity(new Intent(Screen5.this,ActualUserProfile.class));
    }

    public void LogOut(MenuItem item) {
        confirmation();
    }

    //TODO:Complete dashboard button
    public void openDashb(MenuItem item) {
    }

    public void openOnBoardHelp(MenuItem item) {
        startActivity(new Intent(Screen5.this,onBoard.class));
    }
    //TODO:Complete About button
    public void openAbt(MenuItem item) {
    }
    private void confirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Screen5.this);
        builder.setMessage("Won't you stay?")
                .setPositiveButton("Goodbye friend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        finish();
                        Toast.makeText(Screen5.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Screen5.this,Screen2.class));
                    }
                })
                .setNegativeButton("Alright I'll stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}
