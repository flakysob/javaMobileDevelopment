package com.flakysob.calismahafta5;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String permiss = Manifest.permission.SEND_SMS;
        boolean isGranted = ContextCompat.checkSelfPermission(ImplicitIntentActivity.this,permiss) == PackageManager.PERMISSION_GRANTED;
    }
}