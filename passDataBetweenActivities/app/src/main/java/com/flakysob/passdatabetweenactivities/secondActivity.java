package com.flakysob.passdatabetweenactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txtView = findViewById(R.id.textView1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Second Activity");

        Intent intent = getIntent();
        String Name = intent.getStringExtra("NAME");
        String Surname = intent.getStringExtra("SURNAME");
        String Email = intent.getStringExtra("EMAIL");

        txtView.setText("Name : " + Name +"\nSurname : "+Surname+ "\nEmail : "+ Email);


    }
}