package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddLineActivity extends AppCompatActivity {

EditText editText;
EditText editText2;
TimePicker timePicker;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);
        editText=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        timePicker=findViewById(R.id.timePicker1);


    }

    public void next(View view) {
         String Location=editText.getText().toString();
         String Line=editText2.getText().toString();
        int Hour=timePicker.getHour();
        int Min=timePicker.getMinute();
        Log.d("", "next: "+Location+Line);
        String AM_PM ;
        if(Hour < 12) {
            AM_PM = "AM";
        } else {
            Hour -= 12;
            AM_PM = "PM";
        }
            String Time = Hour + ":" + Min + " " + AM_PM;



            if(Location.isEmpty())
        {
            editText.setError("Please Input Location");
            editText.requestFocus();

        }
        else if(Line.isEmpty())
        {
            editText2.setError("Please Input Line");
            editText2.requestFocus();

        }
        else if(!(Location.isEmpty()&&Line.isEmpty()))
            {
                myRef.child("Bus Lines").child("Going").child(Location).child("Start From").setValue(Line);
                myRef.child("Bus Lines").child("Going").child(Location).child("Start Time").setValue(Time);
                myRef.child("Bus Lines").child("Going").child(Location).child("isStarted").setValue("false");
                Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                i.putExtra("Location", Location);
                startActivity(i);


            }

    }
}
