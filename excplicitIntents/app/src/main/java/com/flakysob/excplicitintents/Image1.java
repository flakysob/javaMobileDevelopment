package com.flakysob.excplicitintents;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Image1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image1);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String imgIndex = bundle.getString("ImageIndex");
            setImage(imgIndex);
        }


    }

    private void setImage(String index){
        ImageView imgView = (ImageView) findViewById(R.id.imageViewM1);
        switch(index){
            case "1":
                imgView.setImageResource(R.drawable.m1);
                break;
            case "2":
                imgView.setImageResource(R.drawable.m2);
                break;
            case "3":
                imgView.setImageResource(R.drawable.m3);
                break;
            case "4":
                imgView.setImageResource(R.drawable.m4);
                break;
            case "5":
                imgView.setImageResource(R.drawable.m5);
                break;
            default:
                imgView.setImageResource(R.drawable.m1);
                break;

        }
    }
}