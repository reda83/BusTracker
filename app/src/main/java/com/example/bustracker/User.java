package com.example.bustracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// la ya bibo la
public class User {

    private String FullName;
    private String Email;
    private String Password;
    private String UID;

    FirebaseAuth mFirebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    String CheckDriverOrAdmin="";

    public User()
    {
        mFirebaseAuth=FirebaseAuth.getInstance();


    }
    public User(String FullName,String Email, String Password)
    {
        mFirebaseAuth=FirebaseAuth.getInstance();
        this.FullName = FullName;
        this.Email = Email;
        this.Password = Password;
//        this.UID = UID;
    }

    public void EditAccount()
    {

    }
    public void DeleteAccount()
    {

    }

    public void Signout()
    {
        mFirebaseAuth.signOut();

    }
    public  void SignIn(final Context context)
    {

        mFirebaseAuth.signInWithEmailAndPassword(getEmail(),getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {

                }
                else if(task.isSuccessful())
                {
                    //Intent i=new Intent(context,RoutesRecyclerView.class);                            //testing
                    Intent i=new Intent(context,AdminRoutesRecyclerView.class);

                    context.startActivity(i);
//                    Toast.makeText(context,"Suceesfully Logged in",Toast.LENGTH_SHORT);

                    setUID(mFirebaseAuth.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            setFullName(dataSnapshot.child(getUID()).child("FullName").getValue(String.class));//getting fullname
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    CheckDriverOrAdmin=getFullName();
if(CheckDriverOrAdmin==null)//if there is no full name then he is admin
{
    Log.d("Admin", "onComplete: "+getFullName());
}
else {//else he is driver
    Log.d("Driver", "onComplete: "+getFullName()+" "+getUID());
}
                }
            }
        });

    }

    public  void SignUp(final Context context)
    {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(getEmail(),getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {


                }
                else

                {
                    myRef.child(mFirebaseAuth.getUid()).child("FullName").setValue(getFullName());
                    Intent i=new Intent(context,MainActivity.class);
                    context.startActivity(i);

                }

            }
        });

    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
