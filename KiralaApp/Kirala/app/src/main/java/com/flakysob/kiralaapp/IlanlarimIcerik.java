package com.flakysob.kiralaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IlanlarimIcerik extends AppCompatActivity {

    private TextView icerikBaslik,icerikFiyat,Kiralandimi,icerikaciklama;
    private ImageView IlanResim;
    private AppDatabase appDatabase;
    private IUrunDAO urunDAO;
    private FirebaseAuth mAuth;
    private IUserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlarim_icerik);
        initComponents();
        loadData();
        Intent gelenIntent = getIntent();
        String IlanID = gelenIntent.getStringExtra("ilanID");
        Log.w("deneme", "IlanID: "+IlanID);
    }

    private void loadData() {
        Intent gelenIntent = getIntent();
        String IlanID = gelenIntent.getStringExtra("ilanID");
        UrunEntity icerik = urunDAO.loadUrunById(Long.parseLong(IlanID));
        icerikBaslik.setText(icerik.getUrunBaslik());
        icerikFiyat.setText(""+icerik.getFiyat());
        icerikaciklama.setText(icerik.getAciklama());
        String resim = icerik.getUrunResim();
        IlanResim.setImageURI(Uri.parse(resim));
        int kiralandi_mi=icerik.getKiralandiMi();
        if (kiralandi_mi == 0)
        {
            Kiralandimi.setText("Kiralanmadı");
        }
        else {
            Kiralandimi.setText("Kiralandı");
        }
    }

    private void initComponents() {
        icerikBaslik = (TextView) findViewById(R.id.icerikBaslik);
        icerikFiyat = (TextView) findViewById(R.id.icerikFiyat);
        Kiralandimi = (TextView) findViewById(R.id.Kiralandimi);
        icerikaciklama = (TextView) findViewById(R.id.icerikaciklama);
        appDatabase = AppDatabase.getAppDatabase(IlanlarimIcerik.this);
        urunDAO = appDatabase.getUrunDAO();
        userDAO = appDatabase.getUserDAO();
        mAuth = FirebaseAuth.getInstance();
        IlanResim = (ImageView) findViewById(R.id.IlanResim);
    }
}