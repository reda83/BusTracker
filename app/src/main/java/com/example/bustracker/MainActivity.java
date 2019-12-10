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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
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

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signin;
    int RC_SIGN_IN = 0;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
 //       updateUI(account);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        Email=findViewById(R.id.editText);
        Password=findViewById(R.id.editText2);
        btnsignin=findViewById(R.id.button);
        textViewSignUp=findViewById(R.id.textView);

        signin = findViewById(R.id.google_sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_sign_in_button:
                        signIn();
                        break;
                }}
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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
                    user.SignIn(MainActivity.this);


//                            Toast.makeText(MainActivity.this,"Login Error",Toast.LENGTH_SHORT);
                }
            }
        });


    }

    public void signIn(View view) {
        Intent i = new Intent(this,RoutesRecyclerView.class);
        startActivity(i);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent i = new Intent(this,RoutesRecyclerView.class);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
}
