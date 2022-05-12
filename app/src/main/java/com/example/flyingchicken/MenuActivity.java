package com.example.flyingchicken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageButton imageright=findViewById(R.id.imageright);
        imageright.setOnClickListener(this);
        Button playbutton=findViewById(R.id.playbutton);
        playbutton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view==findViewById(R.id.imageright)){
            ImageView prova=findViewById(R.id.imageView);
            prova.setBackground(getDrawable(R.drawable.background) );
            Constants.BACKGROUND=R.drawable.background;
        }
        if(view==findViewById(R.id.playbutton)){
            Intent i=new Intent(this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}