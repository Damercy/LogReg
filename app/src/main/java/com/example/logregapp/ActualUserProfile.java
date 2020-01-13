package com.example.logregapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Implement on screen navigator to update profile picture and other details on clicking update button
public class ActualUserProfile extends AppCompatActivity {
    ConstraintLayout myLay;
    AnimationDrawable anim;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView name,mail,mob;
    DatabaseReference databaseReference;
    UserProfile prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_user_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        myLay = findViewById(R.id.profileLayout);
        anim=(AnimationDrawable)myLay.getBackground();
        anim.setEnterFadeDuration(2500);
        anim.setExitFadeDuration(2500);
        anim.start();
        name = findViewById(R.id.usrname);
        mail = findViewById(R.id.usrmail);
        mob = findViewById(R.id.usrmob);
        //Get dB reference of corresponding User
        //setDetailsofUser();
        initializeProf();
    }

    private void initializeProf() {
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
                    Toast.makeText(ActualUserProfile.this, "Error fetching data. Please login again.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActualUserProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProf(View view) {
        startActivity(new Intent(this,PopUpUpdateProf.class));
    }

    public void GoPrevAct(View view) {
        startActivity(new Intent(this,Screen5.class));
    }
}


// private void setDetailsofUser() {
//
//        ValueEventListener myListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                prof = dataSnapshot.getValue(UserProfile.class);
//                if(prof!=null){
//                    //set User Data
//                    name.setText(prof.userName);
//                    mail.setText(prof.userEmail);
//                    mob.setText(prof.userMob);
//                }
//                else
//                    Toast.makeText(ActualUserProfile.this, "Error fetching your data. Please login again!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ActualUserProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
//            }
//        };
//        databaseReference.addValueEventListener(myListener);
//    }