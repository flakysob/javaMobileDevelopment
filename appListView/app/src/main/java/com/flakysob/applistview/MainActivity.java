package com.flakysob.applistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewNesnesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNesnesi = findViewById(R.id.listGunler);

        ArrayList<String> gunler = new ArrayList<>();
        gunler.add("Pazartesi");
        gunler.add("Salı");
        gunler.add("Çarşamba");
        gunler.add("Perşembe");
        gunler.add("Cuma");
        gunler.add("Cumartesi");
        gunler.add("Pazar");
        gunler.add("Pazartesi");
        gunler.add("Salı");
        gunler.add("Çarşamba");
        gunler.add("Perşembe");
        gunler.add("Cuma");
        gunler.add("Cumartesi");
        gunler.add("Pazar");
        gunler.add("Pazartesi");
        gunler.add("Salı");
        gunler.add("Çarşamba");
        gunler.add("Perşembe");
        gunler.add("Cuma");
        gunler.add("Cumartesi");
        gunler.add("Pazar");
        gunler.add("Pazartesi");
        gunler.add("Salı");
        gunler.add("Çarşamba");
        gunler.add("Perşembe");
        gunler.add("Cuma");
        gunler.add("Cumartesi");
        gunler.add("Pazar");
        gunler.add("Pazartesi");
        gunler.add("Salı");
        gunler.add("Çarşamba");
        gunler.add("Perşembe");
        gunler.add("Cuma");
        gunler.add("Cumartesi");
        gunler.add("Pazar");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, gunler);
        listViewNesnesi.setAdapter(arrayAdapter);

        listViewNesnesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String secim = listViewNesnesi.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Seçim : "+ secim,Toast.LENGTH_LONG).show();
            }
        });

    }
}