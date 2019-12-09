package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText etFn;
    EditText etEmail;
    EditText etPassword;
    Button button;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etFn =findViewById(R.id.editText);
        etEmail=findViewById(R.id.editText2);
        etPassword=findViewById(R.id.editText3);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth=FirebaseAuth.getInstance();
                String Fullname= etFn.getText().toString();
                String Email=etEmail.getText().toString();
                String Password=etPassword.getText().toString();

                 if(Fullname.isEmpty())
                 {

                     etFn.setError("Please Enter Your Full Name");
                     etFn.requestFocus();
                 }

                if(Email.isEmpty())
                {
                    etEmail.setError("Please Enter Your Email");
                    etEmail.requestFocus();

                }

                 if(Password.isEmpty())
                 {
                     etPassword.setError("Please Enter Your Password");
                     etPassword.requestFocus();
                 }

                 if(!(Fullname.isEmpty()&&Email.isEmpty()&&Password.isEmpty()))
                 {
                    User user = new User(Fullname,Email,Password);
                    user.SignUp(SignupActivity.this);
                     Toast.makeText(SignupActivity.this, "Success" , Toast.LENGTH_SHORT).show();



                 }

            }
        });


    }
}
