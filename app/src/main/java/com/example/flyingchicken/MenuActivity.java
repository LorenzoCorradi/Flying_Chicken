package com.example.flyingchicken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity  implements View.OnClickListener {
    ArrayList<Drawable> envs;
    int index=0;
    ImageButton buttonright;
    ImageButton buttonleft;
    ImageView image;

    ArrayList<Drawable> birds;
    int index1=0;
    ImageButton buttonright1;
    ImageButton buttonleft1;
    ImageView image1;
    Button playbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonright=findViewById(R.id.imageright);
        buttonright.setOnClickListener(this);
        buttonleft=findViewById(R.id.imageleft);
        buttonleft.setOnClickListener(this);
        playbutton=findViewById(R.id.playbutton);
        playbutton.setOnClickListener(this);
        index=Constants.ENVIROMENT-1;
        image=findViewById(R.id.imageView);

        envs=new ArrayList<>();
        envs.add(getDrawable(R.drawable.vapor_background));
        envs.add(getDrawable(R.drawable.background2));

        image.setBackground(envs.get(index) );

        buttonright1=findViewById(R.id.imageright1);
        buttonright1.setOnClickListener(this);
        buttonleft1=findViewById(R.id.imageleft1);
        buttonleft1.setOnClickListener(this);
        index1=Constants.BIRD-1;
        image1=findViewById(R.id.imageView1);

        birds=new ArrayList<>();
        birds.add(getDrawable(R.drawable.plane));
        birds.add(getDrawable(R.drawable.eli));
        birds.add(getDrawable(R.drawable.chicken));
        birds.add(getDrawable(R.drawable.kingchicken));

        Glide.with(this.getBaseContext()).load(birds.get(index1)).into(new DrawableImageViewTarget(image1));







    }

    @Override
    public void onClick(View view) {
        if(view==findViewById(R.id.imageright)){
            if(index==envs.size()-1){
                index=0;
            }else{
                index++;
            }
            image.setBackground(envs.get(index) );
            Constants.ENVIROMENT=index+1;
        }
        if(view==findViewById(R.id.imageleft)){
            if(index==0){
                index= envs.size()-1;
            }else{
                index--;
            }
            image.setBackground(envs.get(index) );
            Constants.ENVIROMENT=index+1;
        }
        if(view==findViewById(R.id.imageright1)){
            if(index1==birds.size()-1){
                index1=0;
            }else{
                index1++;
            }
            Glide.with(this.getBaseContext()).load(birds.get(index1)).into(new DrawableImageViewTarget(image1));
            Constants.BIRD=index1+1;
        }
        if(view==findViewById(R.id.imageleft1)){
            if(index1==0){
                index1= birds.size()-1;
            }else{
                index1--;
            }
            Glide.with(this.getBaseContext()).load(birds.get(index1)).into(new DrawableImageViewTarget(image1));
            Constants.BIRD=index1+1;
        }
        if(view==findViewById(R.id.playbutton)){

            Intent i=new Intent(this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}