package com.flakysob.calismahafta3;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnGecisAbs, btnGecisRela, btnGecisFrame, btnGecisTable, btnGecisGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        absoluteLayoutKullanimi();
        relativeLayoutKullanimi();
        frameLayoutKullanimi();
        tableLayoutKullanimi();
        gridLayoutKullanimi(); //aynı şekilde yazıldı ama hata var. XML dosyasında yerleşme sıkıntı
    }


    private void absoluteLayoutKullanimi(){
        btnGecisAbs = findViewById(R.id.btnGecisAbsolute); //bunu yazmayı unutma

        btnGecisAbs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent gec = new Intent(MainActivity.this, AbsoluteLayoutKullanimi.class);
                startActivity(gec);
            }
        });
    }

    private void relativeLayoutKullanimi(){
        btnGecisRela = findViewById(R.id.btnGecisRelative); //Bunu yazmayı unutma :D

        btnGecisRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecis = new Intent(MainActivity.this, RelaviteLayoutKullanimi.class);
                startActivity(gecis);
            }
        });
    }

    private void frameLayoutKullanimi(){
        btnGecisFrame = findViewById(R.id.btnGecisFrame); //bunu yazmayı unutma

        btnGecisFrame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent gec = new Intent(MainActivity.this, FrameLayoutKullanimi.class);
                startActivity(gec);
            }
        });
    }

    private void tableLayoutKullanimi(){
        btnGecisTable = findViewById(R.id.btnGecisTable); //bunu yazmayı unutma

        btnGecisTable.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent gec = new Intent(MainActivity.this, TableLayoutKullanimi.class);
                startActivity(gec);
            }
        });
    }

    private void gridLayoutKullanimi(){
        btnGecisGrid = findViewById(R.id.btnGecisGrid); //bunu yazmayı unutma

        btnGecisGrid.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent gec = new Intent(MainActivity.this, GridLayoutKullanimi.class);
                startActivity(gec);
            }
        });
    }

}