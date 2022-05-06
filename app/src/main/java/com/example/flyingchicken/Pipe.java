package com.example.flyingchicken;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Pipe extends BaseObject{
    float speed;
    Bitmap image;

    public Pipe() {
        this.speed = 5;
    }

    public void draw(Canvas canvas, Boolean status){
        if(status==false){
            if(this.x+this.width<0){
                this.x=Constants.SCREEN_WIDTH;
            }
            move();
        }
        canvas.drawBitmap(this.image,this.x,this.y,null);


    }
    public void move(){
        this.x=this.x-speed;
    }

    public void setImage(Bitmap image) {
        this.image = Bitmap.createScaledBitmap(image,this.width,this.height,true);

    }
}
