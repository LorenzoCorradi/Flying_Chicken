package com.example.flyingchicken;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private int count ,vFlap,idCurrentBitmap;
    private ArrayList<Bitmap> arrBms=new ArrayList<>();
    public Bird(){
        this.count=0;
        this.vFlap=10;
        this.idCurrentBitmap=0;
    }
    public void draw(Canvas canvas){
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
            if(this.idCurrentBitmap==1){
                this.idCurrentBitmap=0;
            }
            else{
                this.idCurrentBitmap=1;
            }
           /* for(int i=0;i<arrBms.size();i++){
                if(1==arrBms.size()-1){

                    this.idCurrentBitmap=0;
                    break;

                }else if(this.idCurrentBitmap==i){

                    idCurrentBitmap=i+1;
                    break;
                }

            }*/
            count=0;

        }
        return this.arrBms.get(idCurrentBitmap);
    }
}
