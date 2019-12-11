package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
EditText Email,Password;
Button btnsignin;
TextView textViewSignUp;
FirebaseAuth mFirebaseAuth;
User user= new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        Email=findViewById(R.id.editText);
        Password=findViewById(R.id.editText2);
        btnsignin=findViewById(R.id.button);
        textViewSignUp=findViewById(R.id.textView);



        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setEmail(Email.getText().toString());
                user.setPassword(Password.getText().toString());

                if(user.getEmail().isEmpty())
                {
                    Email.setError("Please Enter Your Email");
                    Email.requestFocus();
                }

                else if(user.getPassword().isEmpty())
                {
                    Password.setError("Please Enter Your Password");
                    Password.requestFocus();
                }
                else if(!(user.getEmail().isEmpty()&& user.getPassword().isEmpty()))
                {
                    //test

//                    user.SignIn();
//                    user.SignIn(MainActivity.this);
                    user.SignIn(MainActivity.this);


//                            Toast.makeText(MainActivity.this,"Login Error",Toast.LENGTH_SHORT);
                }
            }
        });


    }
}
