package com.flakysob.kiralaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.flakysob.kiralaapp.DAO.ISiparisDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.SiparisEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IcerikActivity extends AppCompatActivity {

    private TextView icerikBaslik,icerikFiyat,icerikTelefon,icerikaciklama;
    private Button btnKirala;
    private ImageView IcerikResim;
    private AppDatabase appDatabase;
    private IUrunDAO urunDAO;
    private FirebaseAuth mAuth;
    private IUserDAO userDAO;
    private ISiparisDAO siparisDAO;
    Intent gelenIntent;
    UserEntity kullanici;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icerik);
        initComponents();
        loadData();
        registerEventHandlers();
        gelenIntent = getIntent();
        String IlanID = gelenIntent.getStringExtra("ilanID");
        Log.w("deneme", "IlanID: "+IlanID);
    }

    private void registerEventHandlers() {
        btnKirala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SiparisEntity siparis = new SiparisEntity();
                String IlanID = gelenIntent.getStringExtra("ilanID");
                siparis.setUrunId(Long.parseLong(IlanID));
                UrunEntity kiralanacakUrun = urunDAO.loadUrunById(Long.parseLong(IlanID));
                long saticiID = urunDAO.userIDbyUrun(Long.parseLong(IlanID));
                siparis.setSaticiId(saticiID);
                kullanici = userDAO.loadUserByEposta(user.getEmail());
                siparis.setMusteriId(kullanici.getId());

                try {
                    siparisDAO.insertSiparis(siparis);
                    Log.w("deneme","Siparis Kaydedildi :"+siparis.getMusteriId()+ " "+siparis.getOnaylandi_mi()+" "+siparis.getSaticiId());
                    kiralanacakUrun.setKiralandiMi(1);
                    urunDAO.updateUrun(kiralanacakUrun);
                    Log.w("deneme","Update Edildi :"+kiralanacakUrun.getKiralandiMi());
                    Toast.makeText(IcerikActivity.this,"Kiralama İsteğiniz Alındı",Toast.LENGTH_LONG);

                    Snackbar snackbar = Snackbar.make(view, "Kiralama isteği gönderildi.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Kiraladığım ürünler", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(IcerikActivity.this, KiraladigimUrunlerActivity.class);
                            startActivity(intent);
                        }
                    });
                    snackbar.show();
                }catch (Exception e)
                {
                    Log.w("deneme","Hata :",e);
                }
            }
        });

    }

    private void loadData() {
        Intent gelenIntent = getIntent();
        String IlanID = gelenIntent.getStringExtra("ilanID");
        UrunEntity icerik = urunDAO.loadUrunById(Long.parseLong(IlanID));
        icerikBaslik.setText(icerik.getUrunBaslik());
        icerikFiyat.setText(""+icerik.getFiyat());
        icerikaciklama.setText(icerik.getAciklama());
        icerikTelefon.setText(""+kullanici.getTelefon());
        String resim = icerik.getUrunResim();
        IcerikResim.setImageURI(Uri.parse(resim));
    }

    private void initComponents() {
        icerikBaslik = (TextView) findViewById(R.id.icerikBaslik);
        icerikFiyat = (TextView) findViewById(R.id.icerikFiyat);
        icerikTelefon = (TextView) findViewById(R.id.icerikTelefon);
        icerikaciklama = (TextView) findViewById(R.id.icerikaciklama);
        IcerikResim = (ImageView) findViewById(R.id.IcerikResim);
        btnKirala = (Button) findViewById(R.id.btnKirala);
        appDatabase = AppDatabase.getAppDatabase(IcerikActivity.this);
        urunDAO = appDatabase.getUrunDAO();
        userDAO = appDatabase.getUserDAO();
        mAuth = FirebaseAuth.getInstance();
        siparisDAO = appDatabase.getSiparisDAO();
        user = mAuth.getCurrentUser();
        kullanici = userDAO.loadUserByEposta(user.getEmail());
    }
}