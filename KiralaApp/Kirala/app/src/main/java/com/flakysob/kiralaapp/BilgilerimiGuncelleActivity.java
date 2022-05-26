package com.flakysob.kiralaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BilgilerimiGuncelleActivity extends AppCompatActivity {

    private TextInputLayout textAdiSoyadi,textInputEposta,textInputTelefon;
    private MaterialButton butonGuncelle;
    private IUserDAO userDAO;
    private AppDatabase appDatabase;
    private FirebaseAuth mAuth;
    private UserEntity kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgilerimi_guncelle);
        initComponents();
        registerEventHandlers();
        loadData();
    }

    private void loadData() {
        FirebaseUser user = mAuth.getCurrentUser();
        kullanici = userDAO.loadUserByEposta(user.getEmail());
        textInputEposta.getEditText().setText(kullanici.getEposta());
        textInputTelefon.getEditText().setText(kullanici.getTelefon());
        textAdiSoyadi.getEditText().setText(kullanici.getIsimsoyisim());
    }

    private void registerEventHandlers() {
        butonGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity gunceluser = new UserEntity();
                gunceluser.setEposta(textInputEposta.getEditText().getText().toString());
                gunceluser.setTelefon(textInputTelefon.getEditText().getText().toString());
                gunceluser.setIsimsoyisim(textAdiSoyadi.getEditText().getText().toString());
                gunceluser.setId(kullanici.getId());

                try {
                    userDAO.updateUser(gunceluser);
                    Log.w("deneme", "User: "+gunceluser.getEposta()+" "+ gunceluser.getTelefon() + " "+ gunceluser.getIsimsoyisim());
                    Intent intent = new Intent(BilgilerimiGuncelleActivity.this, ProfileActivity.class);
                    Toast.makeText(BilgilerimiGuncelleActivity.this, "Başarıyla güncellendi.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }catch (Exception e) {
                    Log.w("deneme", "Database Güncelleme başarısız: ", e);
                    Snackbar snackbar = Snackbar.make(view, "Güncelleme başarısız.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });
    }

    private void initComponents() {
        textAdiSoyadi = (TextInputLayout) findViewById(R.id.textInputAdiSoyadi);
        textInputEposta = (TextInputLayout) findViewById(R.id.textInputEposta);
        butonGuncelle = (MaterialButton) findViewById(R.id.btnGuncelle);
        textInputTelefon = (TextInputLayout) findViewById(R.id.textInputTelefon);
        appDatabase = AppDatabase.getAppDatabase(BilgilerimiGuncelleActivity.this);
        userDAO = appDatabase.getUserDAO();
        mAuth = FirebaseAuth.getInstance();
    }
}