package com.flakysob.kiralaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SifremiUnuttumActivity extends AppCompatActivity {

    private TextInputEditText txtEpostaDogrulama;
    private MaterialButton btnDogrulama;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        initComponents();
        registerEventHandlers();
    }

    private void initComponents(){
        mAuth = FirebaseAuth.getInstance();
        txtEpostaDogrulama = (TextInputEditText) findViewById(R.id.txtEpostaDogrulama);
        btnDogrulama = (MaterialButton) findViewById(R.id.btnDogrulama);
    }

    private void registerEventHandlers(){
        btnDogrulama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEpostaDogrulama.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(SifremiUnuttumActivity.this,"Doğrulama E-Postası alanı boş bırakılamaz.", Toast.LENGTH_LONG).show();
                    txtEpostaDogrulama.requestFocus();
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(SifremiUnuttumActivity.this,"Geçerli bir E-Posta adresi girin.", Toast.LENGTH_LONG).show();
                    txtEpostaDogrulama.requestFocus();
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SifremiUnuttumActivity.this,"Doğrulama E-Postası gönderildi.", Toast.LENGTH_LONG).show();
                        }else if(!task.isSuccessful()){
                            Toast.makeText(SifremiUnuttumActivity.this,"Doğrulama E-Postası gönderimi başarısız oldu.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}