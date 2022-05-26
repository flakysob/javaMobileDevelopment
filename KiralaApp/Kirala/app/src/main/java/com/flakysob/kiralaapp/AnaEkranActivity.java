package com.flakysob.kiralaapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.flakysob.kiralaapp.DAO.IKategoriDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.github.clans.fab.FloatingActionButton;
import android.view.Menu;
import java.util.List;


public class AnaEkranActivity extends AppCompatActivity implements RecyclerViewInterface{

    private AppDatabase appDatabase;
    private IUserDAO userDAO;
    private IKategoriDAO kategoriDAO;
    private IUrunDAO urunDAO;
    private RecyclerView recyclerView;
    List<KategoriEntity> kategoriList;
    UserEntity kayitliUser;

    private FirebaseAuth mAuth;

    private FloatingActionButton fabProfile,fabAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_KiralaApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);

        Intent gelenIntent = getIntent();
        FirebaseUser user = gelenIntent.getParcelableExtra("user");
        initComponents();
        loadData();

        Kategori_RecyclerViewAdapter adapter = new Kategori_RecyclerViewAdapter(this,kategoriList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kayitliUser = userDAO.loadUserByEposta(user.getEmail());


        Toast.makeText(AnaEkranActivity.this,"Hoş geldin "+kayitliUser.getIsimsoyisim(),Toast.LENGTH_LONG).show();
        registerEventHandlers();

    }

    private  void initComponents(){
        fabProfile = (FloatingActionButton) findViewById(R.id.fabProfile);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.myRecyclerView);
        appDatabase = AppDatabase.getAppDatabase(AnaEkranActivity.this);
        kategoriDAO = appDatabase.getKategoriDAO();
        userDAO = appDatabase.getUserDAO();
        urunDAO = appDatabase.getUrunDAO();
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void loadData(){
        kategoriList = kategoriDAO.loadAllKategori();
    }

    private void registerEventHandlers(){

        fabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AnaEkranActivity.this, ProfileActivity.class);
                startActivity(intent2);
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AnaEkranActivity.this, IlanEkleActivity.class);
                startActivity(intent2);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mAuth.signOut();
        Intent intent = new Intent(AnaEkranActivity.this,MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(KategoriEntity kategoriEntity) {
        Intent intent = new Intent(AnaEkranActivity.this,Ilanlar.class);
        String kategoriID = ""+kategoriEntity.getId();
        intent.putExtra("kategoriID",kategoriID);
        startActivity(intent);
    }
}