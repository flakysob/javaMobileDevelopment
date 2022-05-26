package com.flakysob.kiralaapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class KayitOlActivity extends AppCompatActivity {

    private TextInputLayout textAdiSoyadi,textInputEposta,textParola,textParolaKontrol,textInputTelefon;
    private MaterialButton butonKayitOl;
    private FirebaseAuth mAuth;
    private AppDatabase appDatabase;
    private IUserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        initComponents();
        registerEventHandlers();
    }

    private void initComponents(){
        textAdiSoyadi = (TextInputLayout) findViewById(R.id.textInputAdiSoyadi);
        textInputEposta = (TextInputLayout) findViewById(R.id.textInputEposta);
        textParola = (TextInputLayout) findViewById(R.id.textInputParola);
        textParolaKontrol = (TextInputLayout) findViewById(R.id.textInputParolaKontrol);
        butonKayitOl = (MaterialButton) findViewById(R.id.btnGuncelle);
        textInputTelefon = (TextInputLayout) findViewById(R.id.textInputTelefon);
        mAuth = FirebaseAuth.getInstance();
        appDatabase = AppDatabase.getAppDatabase(KayitOlActivity.this);
        userDAO = appDatabase.getUserDAO();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void registerEventHandlers(){
        butonKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eposta = textInputEposta.getEditText().getText().toString();
                String sifre = textParola.getEditText().getText().toString();
                String adsoyad = textAdiSoyadi.getEditText().getText().toString();
                String telefon = textInputTelefon.getEditText().getText().toString();
                mAuth.createUserWithEmailAndPassword(eposta, sifre)
                        .addOnCompleteListener(KayitOlActivity.this, new
                                OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            UserEntity userEntity = new UserEntity();
                                            userEntity.setEposta(eposta);
                                            userEntity.setIsimsoyisim(adsoyad);
                                            userEntity.setTelefon(telefon);
                                            try {
                                                userDAO.insertUser(userEntity);
                                                updateUI(user);
                                                Log.w("deneme", "Kayıt Başarılı");
                                            } catch (Exception e) {
                                                Log.w("deneme", "Database Kayıt başarısız: ", e);
                                                Snackbar snackbar = Snackbar.make(v, "Database Kayıt başarısız.", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                                updateUI(null);
                                            }
                                        } else {
                                            Log.w("deneme", "Kayıt başarısız: ", task.getException());
                                            Snackbar snackbar = Snackbar.make(v, "Kayıt başarısız.", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                            updateUI(null);
                                        }
                                    }
                                });
            }
        });
    }

    public void updateUI(FirebaseUser user) {
        View v = new View(KayitOlActivity.this);
        if (user != null) {
            Intent intent = new Intent(this, AnaEkranActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

}