package com.flakysob.kiralaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.PrimitiveIterator;

public class ProfileActivity extends AppCompatActivity {

    private TextView kullaniciadi,kullanicieposta,kullanicitelefon;
    private AppDatabase appDatabase;
    private FirebaseAuth mAuth;
    private IUserDAO userDAO;
    private Button btnbilgilerimidegistir,btnilanlarim,btnkiraladigimurunler,btnOnayBekleyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponents();
        registerEventHandlers();
        FirebaseUser user = mAuth.getCurrentUser();
        UserEntity kullanici = new UserEntity();
        kullanici = userDAO.loadUserByEposta(user.getEmail());
        kullaniciadi.setText(kullanici.getIsimsoyisim());
        kullanicieposta.setText(kullanici.getEposta());
        kullanicitelefon.setText(kullanici.getTelefon());
    }

    private void registerEventHandlers() {
        btnbilgilerimidegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, BilgilerimiGuncelleActivity.class);
                startActivity(intent);
            }
        });
        btnilanlarim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,IlanlarimActivity.class);
                startActivity(intent);
            }
        });
        btnkiraladigimurunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,KiraladigimUrunlerActivity.class);
                startActivity(intent);
            }
        });
        btnOnayBekleyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,OnayBekleyenUrunler.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        kullaniciadi = (TextView) findViewById(R.id.kullaniciadi);
        kullanicieposta = (TextView) findViewById(R.id.kullanicieposta);
        kullanicitelefon = (TextView) findViewById(R.id.kullanicitelefon);
        mAuth = FirebaseAuth.getInstance();
        appDatabase = AppDatabase.getAppDatabase(ProfileActivity.this);
        userDAO = appDatabase.getUserDAO();
        btnbilgilerimidegistir = (Button) findViewById(R.id.btnbilgilerimidegistir);
        btnilanlarim = (Button) findViewById(R.id.btnilanlarim);
        btnkiraladigimurunler = (Button) findViewById(R.id.btnkiraladigimurunler);
        btnOnayBekleyen = (Button) findViewById(R.id.btnOnayBekleyen);

    }
}