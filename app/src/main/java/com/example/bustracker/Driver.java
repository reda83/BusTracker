package com.example.bustracker;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Driver {
    private String FullName;
    private String Email;
    private String Password;
    private String UID;
    private Double lat;
    private Double lon;

    boolean success=false;
    boolean success2=false;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");

    public Driver()
    {

    }
    public Driver(String FullName,String Email, String Password)
    {
        this.FullName = FullName;
        this.Email = Email;
        this.Password = Password;
    }
    public void Signout()
    {
        mFirebaseAuth.signOut();

    }
    public  boolean SignIn()
    {

        mFirebaseAuth=FirebaseAuth.getInstance();

        mFirebaseAuth.signInWithEmailAndPassword(getEmail(),getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    success=false;
                }
                else
                {
                    success=true;
                    setUID(mFirebaseAuth.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            setFullName(dataSnapshot.child(getUID()).child("FullName").getValue(String.class));
                            setLat(dataSnapshot.child(getUID()).child("lat").getValue(Double.class));
                            setLon(dataSnapshot.child(getUID()).child("lon").getValue(Double.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        if(success)
        {
            return true;

        }
        else {return false;}
    }

    public  boolean SignUp()
    {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(getEmail(),getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    success2=false;
                }
                else
                {
                    success2=true;
                    myRef.child(mFirebaseAuth.getUid()).child("FullName").setValue(getFullName());
                    myRef.child(mFirebaseAuth.getUid()).child("lat").setValue("");
                    myRef.child(mFirebaseAuth.getUid()).child("lon").setValue("");

                }

            }
        });

        if(success2)
        {
            return true;
        }
        else return false;

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
