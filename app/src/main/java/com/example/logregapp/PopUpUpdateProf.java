package com.example.logregapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopUpUpdateProf extends Activity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView name,mail,mob;
    DatabaseReference databaseReference;
    UserProfile prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_update_prof);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        name = findViewById(R.id.new_name);
        mail = findViewById(R.id.new_mail);
        mob = findViewById(R.id.new_phone);
        //LoC reqd. for Pop Ups, I guess
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //Set width & height of pop up
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));

        //Now orienting layout in bottom right, near update button
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prof = dataSnapshot.getValue(UserProfile.class);
                if(prof!=null){
                    name.setText(prof.getuserName());
                    mail.setText(prof.getuserEmail());
                    mob.setText(prof.getuserMob());
                }
                else
                    Toast.makeText(PopUpUpdateProf.this, "Error fetching data. Please refresh.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PopUpUpdateProf.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUserDetails(View view) {
        String nName,nMail,nMob;
        nName = name.getText().toString();
        nMail = mail.getText().toString();
        nMob = mob.getText().toString();
        UserProfile userUpdated = new UserProfile(nName,nMail,nMob);
        databaseReference.setValue(userUpdated);
        finish();
    }
}
