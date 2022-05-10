package com.flakysob.apppreferences2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText txtIsim, txtSoyisim, txtYas;
    private Button btnKaydet, btnOku;
    private SharedPreferences sharedPreferences, preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        registerEventHandlers();
    }

    private void registerEventHandlers(){
        btnKaydet_onClick();
        btnOku_onClick();
    }

    private void btnKaydet_onClick(){

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("isim", txtIsim.getText().toString());
                editor.putString("soyisim", txtIsim.getText().toString());
                editor.putString("yas", txtIsim.getText().toString());
                editor.commit();
            }
        });
    }

    private void initComponents(){
        txtIsim = findViewById(R.id.txtIsim);
        txtSoyisim = findViewById(R.id.txtSoyisim);
        txtYas = findViewById(R.id.txtYas);
        btnKaydet = findViewById(R.id.btnKaydet);
        btnOku = findViewById(R.id.btnOku);

        sharedPreferences = getSharedPreferences("veriler", MODE_PRIVATE);
        preferences = getPreferences(MODE_PRIVATE);
    }

    private void btnOku_onClick(){

        btnOku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder msg = new StringBuilder();

                Map<String,?> map = preferences.getAll();
                Set<? extends Map.Entry<String, ?>> entries = map.entrySet();
                for(Map.Entry<String,?> next: entries){
                    String key = next.getKey();
                    String value = String.valueOf(next.getValue());
                    msg.append(key).append(":").append(value).append("\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Preferences İçeriği");
                builder.setMessage(msg.toString());
                builder.setIcon(android.R.drawable.ic_dialog_info);

                builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });

    }



}