package com.flakysob.kiralaapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private FirebaseAuth mAuth;
    private TextInputLayout txtInputEposta, txtInputParola;
    private MaterialButton btnGirisYap, btnKayitOl;
    private TextView sifremiUnuttum;
    private TextInputEditText txtParolaDogrulama;
    private TextInputEditText txtEpostaDogrulama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_KiralaApp);
        setContentView(R.layout.activity_main);

        initComponents();
        registerEventHandlers();

    }

    public void updateUI(FirebaseUser user) {
        View v = new View(MainActivity.this);
        if (user != null) {
            /*
            Snackbar snackbar = Snackbar.make(v, user.getEmail() + " giriş yaptı", Snackbar.LENGTH_LONG);
            snackbar.show();*/
            Intent intent = new Intent(this, AnaEkranActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);

        } /*else {

            Snackbar snackbar = Snackbar.make(v, "Henüz giriş yapılmadı", Snackbar.LENGTH_LONG);
            snackbar.show();
        }*/
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    private void initComponents(){
        mAuth = FirebaseAuth.getInstance();
        txtInputEposta = (TextInputLayout) findViewById(R.id.txtInputEposta);
        txtInputParola = (TextInputLayout) findViewById(R.id.txtInputParola);
        btnKayitOl = (MaterialButton) findViewById(R.id.btnDogrulama);
        btnGirisYap = (MaterialButton) findViewById(R.id.btnGirisYap);
        sifremiUnuttum = (TextView) findViewById(R.id.txtSifremiUnuttum);
        txtEpostaDogrulama = (TextInputEditText) findViewById(R.id.txtEpostaDogrulama);
        txtParolaDogrulama = (TextInputEditText) findViewById(R.id.txtParola);
    }

    private void registerEventHandlers(){

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,KayitOlActivity.class);
                startActivity(intent);
            }
        });


        sifremiUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SifremiUnuttumActivity.class);
                startActivity(intent);
            }
        });

        txtEpostaDogrulama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isEmail = epostaKontrolu(txtEpostaDogrulama.getText().toString());

                if(!isEmail){
                    Resources resources = MainActivity.this.getResources();
                    txtEpostaDogrulama.setError("Geçersiz e-posta adresi");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtParolaDogrulama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isPassword = parolaKontrolu(txtParolaDogrulama.getText().toString());

                if(!isPassword){


                    Resources resources = MainActivity.this.getResources();
                    txtParolaDogrulama.setError("Parola en az 8 karakter, büyük harf harf, küçük harf ve özel karakter içermelidir.");
                } else{

                    btnGirisYap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String eposta = txtInputEposta.getEditText().getText().toString();
                            String sifre = txtInputParola.getEditText().getText().toString();
                            mAuth.signInWithEmailAndPassword(eposta, sifre).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    }
                                    else
                                    {
                                        Snackbar snackbar = Snackbar.make(v, "Giriş başarısız.", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        updateUI(null);
                                    }
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    public boolean epostaKontrolu(String gelenEposta){
        String ifadeler = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(ifadeler, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(gelenEposta);
        return matcher.matches();
    }

    public boolean parolaKontrolu(String gelenParola){

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(gelenParola);
        return matcher.matches();
    }

}