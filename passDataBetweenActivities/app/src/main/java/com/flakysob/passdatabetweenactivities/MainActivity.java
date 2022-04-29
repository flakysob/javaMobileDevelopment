package com.flakysob.passdatabetweenactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edt1,edt2,edt3;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("First Activity");

        initComponents();

        btn1Click();

    }

    private void initComponents(){
        edt1 = findViewById(R.id.txtName);
        edt2 = findViewById(R.id.txtSurname);
        edt3 = findViewById(R.id.txtMail);
        btn1 = findViewById(R.id.btnSend);
    }



    private void btn1Click(){
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String tName = edt1.getText().toString();
                String tSurname = edt2.getText().toString();
                String tMail = edt3.getText().toString();


                Intent intent = new Intent(MainActivity.this,secondActivity.class);
                intent.putExtra("NAME",tName);
                intent.putExtra("SURNAME",tSurname);
                intent.putExtra("EMAIL",tMail);
                startActivity(intent);


            }
        });
    }
}