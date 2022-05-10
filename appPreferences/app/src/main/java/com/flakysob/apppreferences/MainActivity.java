package com.flakysob.apppreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    private String text;
    private boolean switchOnOff;


    private TextView textView;
    private EditText editText;
    private Button applyTextButton;
    private Switch switch1;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        editText = findViewById(R.id.edittext);
        applyTextButton = findViewById(R.id.apply_text_button);
        switch1 = findViewById(R.id.switch1);
        saveButton = findViewById(R.id.save_button);

        applyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

            }
        });

        loadData();
        updateViews();

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT,textView.getText().toString());
        editor.putBoolean(SWITCH1, switch1.isChecked());
        editor.apply();

        Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void updateViews(){
        textView.setText(text);
        switch1.setChecked(switchOnOff);
    }
}