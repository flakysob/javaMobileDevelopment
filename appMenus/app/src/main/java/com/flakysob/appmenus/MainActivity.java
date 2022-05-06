package com.flakysob.appmenus;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings){
            Toast.makeText(getApplicationContext(),"Bak oğlum Mernuş!",Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.help){
            Toast.makeText(getApplicationContext(),"Sen ne o, ne bu, ne de şu'sun!",Toast.LENGTH_SHORT).show();
            //return super.onOptionsItemSelected(item); (MESAJ VERİLMEYECEĞİ ZAMAN BUNU BULLANIRSIN)
        }
        return true;
    }
}