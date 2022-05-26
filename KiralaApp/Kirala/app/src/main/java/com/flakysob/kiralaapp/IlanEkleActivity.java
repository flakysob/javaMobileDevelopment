package com.flakysob.kiralaapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.flakysob.kiralaapp.DAO.IKategoriDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

public class IlanEkleActivity extends AppCompatActivity {

    private EditText ilanbaslik,ilanfiyat,ilanaciklama;
    private Spinner kategoriSpinner;
    private ImageView ilanresim;

    List<KategoriEntity> kategoriList;
    private AppDatabase appDatabase;
    private IUserDAO userDAO;
    private IKategoriDAO kategoriDAO;
    private IUrunDAO urunDAO;
    private FirebaseAuth mAuth;
    private Button btnIlanEkle;
    UrunEntity ilan = new UrunEntity();


    //izin sabitleri
    private static final int KAMERA_TALEP_KODU = 100;
    private static final int DEPOLAMA_TALEP_KODU = 101;

    //resim seçim sabitleri
    private static final int KAMERADAN_RESIM_SECME_KODU = 102;
    private static final int GALERIDEN_RESIM_SECME_KODU = 103;

    //izinler dizisi
    private String[] kameraIzinleri; //kamera ve depolama için
    private String[] depolamaIzinleri; //depolama için

    //resim tutucu
    private static Uri resimUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle);
        initComponents();

        //izinleri tanımlamaları
        kameraIzinleri = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        depolamaIzinleri = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        kategoriList = kategoriDAO.loadAllKategori();
        KategoriSpinnerAdapter spinnerAdapter = new KategoriSpinnerAdapter(this,R.layout.spinner_kategori,kategoriList);
        kategoriSpinner.setAdapter(spinnerAdapter);

        kategoriSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String secim = ""+(adapterView.getAdapter().getItemId(position)+1);
                Log.w("deneme", "Seçim: "+secim);
                ilan.setKategoriId(Long.parseLong(secim));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        btnIlanEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resim boş ise
                if (resimUri == null){
                    Toast.makeText(IlanEkleActivity.this,"Lütfen Resim Ekleyin",Toast.LENGTH_LONG).show();
                }
                else if(ilanaciklama.getText().toString().equals("") || ilanfiyat.getText().toString().equals("") || ilanbaslik.getText().toString().equals(""))
                {
                    Toast.makeText(IlanEkleActivity.this,"Lütfen Alanları Boş Bırakmayın",Toast.LENGTH_LONG).show();
                }
                else
                {
                    ilan.setAciklama(ilanaciklama.getText().toString());
                    ilan.setFiyat(Integer.parseInt(ilanfiyat.getText().toString()));
                    ilan.setUrunBaslik(ilanbaslik.getText().toString());
                    ilan.setKiralandiMi(0);
                    FirebaseUser user = mAuth.getCurrentUser();
                    UserEntity kullanici = new UserEntity();
                    kullanici = userDAO.loadUserByEposta(user.getEmail());
                    ilan.setUserId(kullanici.getId());
                    ilan.setUrunResim(""+resimUri);
                    try {
                        urunDAO.insertUrun(ilan);
                        Snackbar snackbar = Snackbar.make(view, "İlan yayınlandı.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("İlanlarım", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(IlanEkleActivity.this, IlanlarimActivity.class);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                        Log.w("deneme", "Urun Ekleme Başarılı: "+ilan.getUrunBaslik()+ " "+ ilan.getAciklama() + " "+ ilan.getFiyat()+" "+ilan.getKategoriId()+" "+ ilan.getUserId());
                    }catch (Exception e)
                    {
                        Snackbar snackbar = Snackbar.make(view, "İlan yayınlanamadı.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Log.w("deneme", "Urun Ekleme Başarısız: ", e);
                    }
                }


            }
        });

        ilanresim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resimSecmeDialog();
            }
        });
    }

    //depolama izni kontrolü
    private boolean depolamaIzniKontrolu(){
        boolean sonuc = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return sonuc;
    }

    //kamera izni kontrolü
    private boolean kameraIzniKontrolu(){
        boolean sonuc1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean sonuc2 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return sonuc1 && sonuc2;
    }

    //depolama izni talebi
    private void depolamaIzinTalebi(){
        ActivityCompat.requestPermissions(this,depolamaIzinleri,DEPOLAMA_TALEP_KODU);
    }

    //kamera izni talebi
    private void kameraIzinTalebi(){
        ActivityCompat.requestPermissions(this,kameraIzinleri,KAMERA_TALEP_KODU);
    }

    private void resimSecmeDialog() {
        String[] ogeler = {"Kamera","Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resim Seç");
        builder.setItems(ogeler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // kamera=0 galeri=1
                if(i==0)
                {
                    //kamera izni yoksa
                    if (!kameraIzniKontrolu()){
                        kameraIzinTalebi();
                    }
                    else{
                        //erişim izni varsa kamerayı aç
                        kameradanSec();
                    }
                }
                if (i ==1)
                {
                    //galeri izni yoksa
                    if (!depolamaIzniKontrolu()){
                        depolamaIzinTalebi();
                    }
                    else{
                        //erişim izni varsa depolamayı aç
                        galeridenSec();
                    }
                }
            }
        });
        builder.create().show();
    }
    //kamerayı açtırma
    private void kameradanSec() {
        ContentValues degerler = new ContentValues();
        degerler.put(MediaStore.Images.Media.TITLE,"Resim Başlığı");
        degerler.put(MediaStore.Images.Media.DESCRIPTION,"Resim Açıklaması");
        resimUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,degerler);

        Intent kameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(kameraIntent,KAMERADAN_RESIM_SECME_KODU);

    }

    //galeriyi açma
    private void galeridenSec() {
        Intent galeriIntent = new Intent(Intent.ACTION_PICK);
        galeriIntent.setType("image/*");
        startActivityForResult(galeriIntent,GALERIDEN_RESIM_SECME_KODU);

    }

    private void initComponents() {
        ilanbaslik = (EditText) findViewById(R.id.ilanbaslik);
        ilanfiyat = (EditText) findViewById(R.id.ilanfiyat);
        ilanaciklama = (EditText) findViewById(R.id.icerikaciklama);
        kategoriSpinner = (Spinner) findViewById(R.id.spinnerkategori);
        ilanresim = (ImageView) findViewById(R.id.ilanimg);
        appDatabase = AppDatabase.getAppDatabase(IlanEkleActivity.this);
        kategoriDAO = appDatabase.getKategoriDAO();
        userDAO = appDatabase.getUserDAO();
        urunDAO = appDatabase.getUrunDAO();
        mAuth = FirebaseAuth.getInstance();
        btnIlanEkle = (Button) findViewById(R.id.btnIlanEkle);
        ilanresim = (ImageView) findViewById(R.id.ilanimg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case KAMERA_TALEP_KODU:
            {
                if (grantResults.length>0)
                {
                    //izin alınmışsa true aksi durumda false gödner
                    boolean kameraKabul = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean depolamaKabul = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (kameraKabul && depolamaKabul){
                        kameradanSec();
                    }
                    else{
                        Toast.makeText(this,"Kamera ve depolama izni gerekli!",Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case DEPOLAMA_TALEP_KODU:
            {
                if (grantResults.length>0)
                {
                    //izin alınmışsa true aksi durumda false gödner
                    boolean depolamaKabul = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (depolamaKabul){
                        galeridenSec();
                    }
                    else{
                        Toast.makeText(this,"Depolama izni gerekli!",Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //kameradan ya da galeriden açılacak resim burada işlenip alınacak
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALERIDEN_RESIM_SECME_KODU)
            {
                try {
                    resimUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(resimUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ilanresim.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(IlanEkleActivity.this,"Bir sorun var", Toast.LENGTH_LONG).show();
                }
            }
            if(requestCode == KAMERADAN_RESIM_SECME_KODU){
                Log.w("deneme", "Get Data: "+ data.getExtras().get("data"));

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                WeakReference<Bitmap> result1 = new WeakReference<>(Bitmap.createScaledBitmap(imageBitmap,
                        imageBitmap.getHeight(),imageBitmap.getWidth(),false).copy(
                                Bitmap.Config.RGB_565,true));

                Bitmap bm = result1.get();
                resimUri = saveImage(bm,IlanEkleActivity.this);

                ilanresim.setImageURI(resimUri);
            }


        }else {
            Toast.makeText(IlanEkleActivity.this, "Bir Resim Seçmelisiniz",Toast.LENGTH_LONG).show();
        }
    }

    private Uri saveImage(Bitmap image, Context context){
        File imagesFolder = new File(context.getCacheDir(),"images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder,"captured_image_jpg");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context.getApplicationContext(),"com.flakysob.kiralaapp"+".provider",file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}