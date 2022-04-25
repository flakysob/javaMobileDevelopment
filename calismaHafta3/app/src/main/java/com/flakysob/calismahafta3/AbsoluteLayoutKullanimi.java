package com.flakysob.calismahafta3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;

public class AbsoluteLayoutKullanimi extends AppCompatActivity {

    private Button btnGeriDonAbsolute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absolute_layout_kullanimi);

        geriDon();
    }
    private void geriDon(){
        btnGeriDonAbsolute = findViewById(R.id.btnGeriDonAbsolute); //bunu yazmayı unutma

        btnGeriDonAbsolute.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent gec = new Intent(AbsoluteLayoutKullanimi.this, MainActivity.class);
                startActivity(gec);
            }
        });
    }
}