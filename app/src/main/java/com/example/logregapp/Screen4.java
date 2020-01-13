package com.example.logregapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Screen4 extends AppCompatActivity {
    Animation frombottom,fromtop;
    Button btn5;
    ImageView img;
    EditText user,pass;
    FirebaseAuth fbauth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);
        btn5 = findViewById(R.id.button5);
        user = findViewById(R.id.etmaillog);
        pass = findViewById(R.id.etpasslog);
        progressDialog = new ProgressDialog(this);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        btn5.setAnimation(frombottom);
        user.setAnimation(fromtop);
        pass.setAnimation(fromtop);
        img = findViewById(R.id.bgimdia);
        img.setVisibility(View.GONE);
        img.setAlpha(0f);
        img.setVisibility(View.VISIBLE);
        img.animate()
                .alpha(1f)
                .setDuration(2500)
                .setListener(null);

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = incompleteFields();
                if(result)
                    Toast.makeText(Screen4.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                else
                    validate(user.getText().toString(),pass.getText().toString());
            }
        });

        //Load Firebase instance & progress dialog instance
        fbauth = FirebaseAuth.getInstance();
        //If logged in so check instance
        FirebaseUser fbuser = fbauth.getCurrentUser();

        //If logged in, then move to next activity from login page
        if(fbuser!=null) {
            finish();
            startActivity(new Intent(Screen4.this, Screen5.class));
        }
    }

    private boolean incompleteFields() {
        if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    private void validate(String usermail, String pass) {
        progressDialog.setMessage("Authorizing credentials. Please wait,will you?");
        progressDialog.show();
        fbauth.signInWithEmailAndPassword(usermail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Screen4.this, "Login success!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Screen4.this,onBoard.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Screen4.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
