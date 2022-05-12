package com.example.flyingchicken;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class  Coin extends BaseObject{
    private int count ,vFlap,idCurrentBitmap;
    private ArrayList<Bitmap> arrBms=new ArrayList<>();
    public float drop;
    public Coin(){
        this.count=0;
        this.vFlap=5;
        this.idCurrentBitmap=0;
        this.drop=0;
    }
    public void draw(Canvas canvas,Boolean status){
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

        count++;
        if(this.count==this.vFlap){
            if(this.idCurrentBitmap==7){
                this.idCurrentBitmap=0;
            }
            else{
                this.idCurrentBitmap++;
            }
            count=0;

        }
        return this.arrBms.get(idCurrentBitmap);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}

