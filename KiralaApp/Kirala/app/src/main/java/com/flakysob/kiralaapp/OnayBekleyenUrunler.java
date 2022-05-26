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
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class OnayBekleyenUrunler extends AppCompatActivity implements IlanRecyclerView{

    RecyclerView RecyOnaylanacak;
    private AppDatabase appDatabase;
    private IUserDAO userDAO;
    private IUrunDAO urunDAO;
    private ISiparisDAO siparisDAO;
    private FirebaseAuth mAuth;
    UserEntity kullanici;
    FirebaseUser user;
    List<UrunEntity> OnayBekleyenUrunler = new LinkedList<>();
    UrunEntity urun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onay_bekleyen_urunler);
        initComponents();
        loadData();
        Ilan_RecyclerViewAdapter adapter = new Ilan_RecyclerViewAdapter(this,OnayBekleyenUrunler,this);
        RecyOnaylanacak.setAdapter(adapter);
        RecyOnaylanacak.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        List<Long> siparisID = siparisDAO.loadSiparisOnayKullanici(0,kullanici.getId());
        for (int i =0; i<siparisID.size() ;i++){
            urun  = urunDAO.loadUrunById(Long.parseLong(""+siparisID.get(i)));
            OnayBekleyenUrunler.add(urun);
            Log.w("deneme","Onay Bekleyen Urun :"+urun.getUrunBaslik());
        }
    }

    private void initComponents() {
        RecyOnaylanacak = (RecyclerView) findViewById(R.id.RecyOnaylanacak);
        mAuth = FirebaseAuth.getInstance();
        appDatabase = AppDatabase.getAppDatabase(OnayBekleyenUrunler.this);
        userDAO = appDatabase.getUserDAO();
        urunDAO = appDatabase.getUrunDAO();
        siparisDAO = appDatabase.getSiparisDAO();
        user = mAuth.getCurrentUser();
        kullanici = userDAO.loadUserByEposta(user.getEmail());
    }

    @Override
    public void onItemClick(UrunEntity urunEntity) {
        Intent intent = new Intent(OnayBekleyenUrunler.this,OnaylaReddetActivity.class);
        String ilanID = ""+urunEntity.getId();
        intent.putExtra("ilanID",ilanID);
        startActivity(intent);
    }
}