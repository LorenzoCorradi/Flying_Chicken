package com.example.flyingchicken;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class Pipe extends BaseObject{
    float speed;
    Bitmap imageBottom;
    Bitmap imageTop;
    int h; //value from 0 to 10 representing h% of screen height

    public Pipe() {
        this.speed = 5;
    }

    public void draw(Canvas canvas, Boolean status){
        if(status==false){
            move();
        }
        canvas.drawBitmap(this.imageBottom,this.x,Constants.SCREEN_HEIGHT-((10-h)*10-8)*Constants.SCREEN_HEIGHT/100,null);
        canvas.drawBitmap(this.imageTop,this.x,0,null);


    }
    public void move(){
        this.x=this.x-speed;
    }

    public void setImageBottom(Bitmap image) {
        this.imageBottom = Bitmap.createScaledBitmap(image,this.width,this.height,true);

    }
    public void setImageTop(Bitmap image) {
        this.imageTop = Bitmap.createScaledBitmap(image,this.width,this.height,true);

    }
    public void newLast(Pipe oldlast,android.content.res.Resources res){
        if(oldlast==null){
            h=5;
        }
        else {
            Random rn = new Random();
            int x=oldlast.getH();
            h = rn.nextInt(7) + x-3;    //l'altezza di differenza fra i tubi varia massimo del 30%
            if(h<=1){
                h=2;
            }
            else if(h>=10){
                h=9;
            }

        }
        this.setX(Constants.SCREEN_WIDTH+200);
        this.setY(Constants.SCREEN_HEIGHT-600*Constants.SCREEN_HEIGHT/1900);
        this.setWidth((100*Constants.SCREEN_WIDTH/1000));

        this.setHeight((int)(h*10-8)*Constants.SCREEN_HEIGHT/100);
        this.setImageTop(BitmapFactory.decodeResource(res,R.drawable.pipe2));
        this.setHeight((int)((10-h)*10-8)*Constants.SCREEN_HEIGHT/100);
        this.setImageBottom(BitmapFactory.decodeResource(res,R.drawable.pipe1));


    }

    public int getH() {
        return h;
    }
}
