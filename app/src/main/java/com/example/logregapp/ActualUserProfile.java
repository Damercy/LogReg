package com.example.logregapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

// TODO: Fit image perfectly in center of imageview & align buttons : DONE
// TODO: Retrieve data from storage & set in profile picture
public class ActualUserProfile extends AppCompatActivity {
    ConstraintLayout myLay;
    AnimationDrawable anim;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView name,mail,mob;
    ImageView profPic;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageRef,dpRef;
    UserProfile prof;
    static int PICK_IMAGE = 123; // Each actvity (audio/image/video) requires a unique identifier like this
    Uri imgPath;
    @Override
    protected void onActivityResult(int reqCode,int resCode, Intent data){
        if( reqCode == PICK_IMAGE && resCode == RESULT_OK && data.getData()!=null){
            imgPath = data.getData(); //The very real image path inside user's phone
            Log.e("Path",imgPath.getPath());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgPath); //Converts image path to bitmap aka actual pixels
                profPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(reqCode,resCode,data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_user_profile);
        profPic = findViewById(R.id.profile_image);
        name = findViewById(R.id.usrname);
        mail = findViewById(R.id.usrmail);
        mob = findViewById(R.id.usrmob);
        progressDialog = new ProgressDialog(this);
        //ALways two steps: Get instance, then get reference
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference(); // pointing to root object (directory) in storage
        dpRef = storage.getReference();
        myLay = findViewById(R.id.profileLayout);
        anim=(AnimationDrawable)myLay.getBackground();
        anim.setEnterFadeDuration(2500);
        anim.setExitFadeDuration(2500);
        anim.start();
        dpRef.child(firebaseAuth.getUid()).child("profile_pic").child("DP").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).placeholder(R.drawable.bg_diamond).error(R.drawable.ic_error).fit().centerCrop().into(profPic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActualUserProfile.this, "Failed retrieving", Toast.LENGTH_SHORT).show();
            }
        });
        //Get dB reference of corresponding User
        //setDetailsofUser();
        initializeProf();
        profPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Choose profile picture"),PICK_IMAGE);
            }
        });
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

    public void updateDP(View view) {
        progressDialog.setMessage("I hope your internet's fast");
        progressDialog.show();
        //create a tree structure in storage as: root/uid(random nos.)/profile_pic/DP.jpg
        // thus, .child("xyz") == /xyz
        StorageReference imgRef = storageRef.child(firebaseAuth.getUid()).child("profile_pic").child("DP");
        UploadTask uploadTask = imgRef.putFile(imgPath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActualUserProfile.this, "Image failed to upload", Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(ActualUserProfile.this, "Image updated!", Toast.LENGTH_SHORT).show();
            }
        });
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



//Bitmap Rotate Code
//            ExifInterface exifInterface = null;
//                try {
//                    exifInterface = new ExifInterface(imgPath.getPath());
//                }
//               catch (IOException e){
//                    e.printStackTrace();
//               }
//                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
//                Matrix matrix = new Matrix();
//                switch (orientation){
//                    case ExifInterface.ORIENTATION_ROTATE_90: matrix.setRotate(90);
//                            break;
//                            case ExifInterface.ORIENTATION_ROTATE_180: matrix.setRotate(180);
//                            break;
//                    default:
//                }
//                Bitmap rotatedbitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);