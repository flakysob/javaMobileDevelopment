package com.flakysob.calismahafta3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RelaviteLayoutKullanimi extends AppCompatActivity {

    private Button btnGeriDonRelative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relavite_layout_kullanimi);

        geriMainActivity();
    }

    public void geriMainActivity(){
        btnGeriDonRelative = findViewById(R.id.btnGeriDon); //bunu yazmayı unutma

        btnGeriDonRelative.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent gecistir = new Intent(RelaviteLayoutKullanimi.this, MainActivity.class);
                startActivity(gecistir);
            }
        });
    }
}