package com.example.flyingchicken;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class  Bottom extends BaseObject{
    private ArrayList<Bitmap> arrBms=new ArrayList<>();
    float speed;
    public Bottom() {
        this.speed=5;
    }
    public void move(){
        this.x=this.x-speed;
    }


    public void draw(Canvas canvas, Boolean status){
        if(!Constants.PAUSED) {
            this.move();
        }
        if(this.x+Constants.SCREEN_WIDTH<=0){
            this.x=Constants.SCREEN_WIDTH;
        }
        canvas.drawBitmap(this.getBm(),this.x,this.y,null);
    }


    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
        for(int i=0;i<arrBms.size();i++){
            this.arrBms.set(i,Bitmap.createScaledBitmap(this.arrBms.get(i),this.width,this.height,true));
        }
    }

    @Override
    public Bitmap getBm() {
        return this.arrBms.get(0);
    }
}

