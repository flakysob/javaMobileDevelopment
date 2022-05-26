package com.flakysob.kiralaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.flakysob.kiralaapp.DAO.ISiparisDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.SiparisEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class KiraladigimUrunlerActivity extends AppCompatActivity implements IlanRecyclerView{

    private AppDatabase appDatabase;
    private IUserDAO userDAO;
    private IUrunDAO urunDAO;
    private ISiparisDAO siparisDAO;
    private FirebaseAuth mAuth;
    UserEntity kullanici;
    FirebaseUser user;
    RecyclerView RecyOnaylananlar,RecyOnayBekleyenler;
    List<UrunEntity> OnayBekleyenUrunler = new LinkedList<>();
    List<UrunEntity> OnaylananUrunler = new LinkedList<>();
    UrunEntity urun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiraladigim_urunler);
        initComponents();
        Log.w("deneme","Kullanıcı ID :"+kullanici.getId());
        loadDataOnaylanan();
        loadDataOnayBekleyen();
        Ilan_RecyclerViewAdapter adapter = new Ilan_RecyclerViewAdapter(this,OnayBekleyenUrunler,this);
        RecyOnayBekleyenler.setAdapter(adapter);
        RecyOnayBekleyenler.setLayoutManager(new LinearLayoutManager(this));

        Ilan_RecyclerViewAdapter adapter2 = new Ilan_RecyclerViewAdapter(this,OnaylananUrunler,this);
        RecyOnaylananlar.setAdapter(adapter2);
        RecyOnaylananlar.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadDataOnayBekleyen() {
        List<Long> siparisID = siparisDAO.loadSiparisOnay(0,kullanici.getId());
        for (int i =0; i<siparisID.size() ;i++){
            urun  = urunDAO.loadUrunById(Long.parseLong(""+siparisID.get(i)));
            OnayBekleyenUrunler.add(urun);
            Log.w("deneme","Onay Bekleyen Urun :"+urun.getUrunBaslik());
        }
    }

    private void loadDataOnaylanan() {
        List<Long> siparisID = siparisDAO.loadSiparisOnay(1,kullanici.getId());
        for (int i =0; i<siparisID.size() ;i++){
            urun  = urunDAO.loadUrunById(Long.parseLong(""+siparisID.get(i)));
            OnaylananUrunler.add(urun);
            Log.w("deneme","Onaylanan Urun :"+urun.getUrunBaslik());
        }
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        appDatabase = AppDatabase.getAppDatabase(KiraladigimUrunlerActivity.this);
        userDAO = appDatabase.getUserDAO();
        urunDAO = appDatabase.getUrunDAO();
        siparisDAO = appDatabase.getSiparisDAO();
        user = mAuth.getCurrentUser();
        kullanici = userDAO.loadUserByEposta(user.getEmail());
        RecyOnayBekleyenler = (RecyclerView) findViewById(R.id.RecyOnayBekleyenler);
        RecyOnaylananlar = (RecyclerView) findViewById(R.id.RecyOnaylananlar);
    }

    @Override
    public void onItemClick(UrunEntity urunEntity) {
        Intent intent = new Intent(KiraladigimUrunlerActivity.this,IlanlarimIcerik.class);
        String ilanID = ""+urunEntity.getId();
        intent.putExtra("ilanID",ilanID);
        startActivity(intent);
    }
}