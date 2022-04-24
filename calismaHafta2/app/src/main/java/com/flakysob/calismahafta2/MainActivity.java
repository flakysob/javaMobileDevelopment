package com.flakysob.calismahafta2;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText txtGir1,txtGir2;
    private TextView txtSonuc;
    private Button btnSonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        registerEventHandlers();
    }

    public void initComponents(){
        txtGir1 = findViewById(R.id.txtGir1);
        txtGir2 = findViewById(R.id.txtGir2);
        txtSonuc = findViewById(R.id.txtSonuc);
        btnSonuc = findViewById(R.id.btnSonuc);
    }

    public void registerEventHandlers(){
        btnSonuc.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                int s1 = Integer.valueOf(txtGir1.getText().toString());
                int s2 = Integer.valueOf(txtGir2.getText().toString());
                int s = s1+s2;

                txtSonuc.setText("Sonuç : " + s);
            }
        });
    }



}