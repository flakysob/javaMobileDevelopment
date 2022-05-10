package com.flakysob.appcontextmenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_view);
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu,menu);
        menu.setHeaderTitle("Choose your option : ");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.option_1:
                Toast.makeText(getApplicationContext(), "Option 1 selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.option_2:
                Toast.makeText(getApplicationContext(), "Option 2 selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.option_3:
                Toast.makeText(getApplicationContext(), "Option 3 selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }
}