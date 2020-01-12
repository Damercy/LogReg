package com.example.logregapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Screen3 extends AppCompatActivity {

    Animation frombottom, fromtop;
    Button btn4,prevbtn;
    ImageView img;
    EditText user, mail, pass, mob;
    FirebaseAuth fbauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);
        btn4 = findViewById(R.id.button4);
        user = findViewById(R.id.etuser);
        mail = findViewById(R.id.etmail);
        pass = findViewById(R.id.etpass);
        mob = findViewById(R.id.etmob);
        prevbtn = findViewById(R.id.backbtn);

        //FireBaseAuth getting instance
        fbauth = FirebaseAuth.getInstance();

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        btn4.setAnimation(frombottom);
        user.setAnimation(fromtop);
        pass.setAnimation(fromtop);
        mail.setAnimation(fromtop);
        prevbtn.setAnimation(fromtop);
        mob.setAnimation(fromtop);
        img = findViewById(R.id.bgim);
        img.setVisibility(View.GONE);
        img.setAlpha(0f);
        img.setVisibility(View.VISIBLE);
        img.animate()
                .alpha(0.7f)
                .setDuration(2500)
                .setListener(null);

        //Logic for registration begins
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    // upload user data to database : email & pass
                    String umail = mail.getText().toString().trim();
                    String upass = pass.getText().toString().trim();
                    fbauth.createUserWithEmailAndPassword(umail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Screen3.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Screen3.this,Screen2.class));
                            }
                            else{
                                Toast.makeText(Screen3.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private boolean validate() {
        boolean result = false;
        String uname, upass, umail, umob;
        uname = user.getText().toString();
        upass = pass.getText().toString();
        umail = mail.getText().toString();
        umob = mob.getText().toString();
        if (uname.isEmpty() || upass.isEmpty() || umail.isEmpty() || umob.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    public void backtoAct2(View view) {
        startActivity(new Intent(Screen3.this,Screen2.class));
    }
}
