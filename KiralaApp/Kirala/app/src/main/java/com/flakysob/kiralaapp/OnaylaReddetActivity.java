package com.flakysob.kiralaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flakysob.kiralaapp.DAO.ISiparisDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.SiparisEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OnaylaReddetActivity extends AppCompatActivity {

    private TextView icerikBaslik,icerikFiyat,Kiralandimi,icerikaciklama;
    private Button btnKabulet,btnReddet;
    private AppDatabase appDatabase;
    private IUrunDAO urunDAO;
    private FirebaseAuth mAuth;
    private IUserDAO userDAO;
    private ImageView OnaylaResim;
    private ISiparisDAO siparisDAO;
    UserEntity kullanici;
    FirebaseUser user;
    UrunEntity icerik;
    Intent gelenIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onayla_reddet);
        initComponents();
        loadData();
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        btnKabulet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IlanID = gelenIntent.getStringExtra("ilanID");
                SiparisEntity siparis = siparisDAO.loadByIlanID(Long.parseLong(IlanID));
                siparis.setOnaylandi_mi(1);
                siparisDAO.updateSiparis(siparis);
                Log.w("deneme","Onay Bekleyen Urun :"+siparis.getOnaylandi_mi()+" "+siparis.getSaticiId());
                Toast.makeText(OnaylaReddetActivity.this,"Onaylandı",Toast.LENGTH_LONG).show();
            }
        });
        btnReddet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IlanID = gelenIntent.getStringExtra("ilanID");
                SiparisEntity siparis = siparisDAO.loadByIlanID(Long.parseLong(IlanID));
                siparisDAO.deleteSiparis(siparis);
                UrunEntity ilan = urunDAO.loadUrunById(Long.parseLong(IlanID));
                ilan.setKiralandiMi(0);
                urunDAO.updateUrun(ilan);
                Toast.makeText(OnaylaReddetActivity.this,"Reddedildi",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadData() {
        gelenIntent = getIntent();
        String IlanID = gelenIntent.getStringExtra("ilanID");
        icerik = urunDAO.loadUrunById(Long.parseLong(IlanID));
        icerikBaslik.setText(icerik.getUrunBaslik());
        icerikFiyat.setText(""+icerik.getFiyat());
        icerikaciklama.setText(icerik.getAciklama());
        String resim = icerik.getUrunResim();
        OnaylaResim.setImageURI(Uri.parse(resim));
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
        icerikaciklama = (TextView) findViewById(R.id.icerikaciklama);
        Kiralandimi = (TextView) findViewById(R.id.Kiralandimi);
        btnKabulet = (Button) findViewById(R.id.btnKabulet);
        btnReddet = (Button) findViewById(R.id.btnReddet);
        OnaylaResim = (ImageView) findViewById(R.id.OnaylaResim);
        mAuth = FirebaseAuth.getInstance();
        appDatabase = AppDatabase.getAppDatabase(OnaylaReddetActivity.this);
        userDAO = appDatabase.getUserDAO();
        urunDAO = appDatabase.getUrunDAO();
        siparisDAO = appDatabase.getSiparisDAO();
        user = mAuth.getCurrentUser();
        kullanici = userDAO.loadUserByEposta(user.getEmail());
    }
}