package com.example.flyingchicken;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class  Coin extends BaseObject{
    private int count ,vFlap,idCurrentBitmap;
    private ArrayList<Bitmap> arrBms=new ArrayList<>();
    public float drop;
    int h;
    float speed;
    public Coin(){
        this.speed=5;
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

    public void init(float lastx,int h){
        this.setX(lastx+600);
        this.h=h;
        this.setWidth((2*100*Constants.SCREEN_WIDTH/1000));
        this.setHeight((100*Constants.SCREEN_HEIGHT/1900));
        this.setY(Constants.SCREEN_HEIGHT*h*10/100);
    }
    static Coin getlast(ArrayList<Coin> coins){
        float x=0;
        Coin last=null;
        for(Coin c:coins){
            if(c.getX()>x){
                x=c.getX();
                last=c;
            }
        }
        return last;
    }
    static void totalMove(ArrayList<Coin> coins,Boolean status){
        if(status==false){
            int count=0;
            for(Coin c:coins){
                if(c.x+c.width<0){
                    Coin last=getlast(coins);
                    int newh=newH();
                    c.setH(newh);
                    c.init(last.getX(),newh);
                }
                c.move();
                count++;
            }


        }
    }
    public void move(){
        this.x=this.x-speed;
    }
    public int getH() {
        return h;
    }
    static int newH(){
        int high=7;
        int low=4;
        Random rn = new Random();

        int newh = rn.nextInt(high - low) + low; //l'altezza di differenza fra i tubi varia massimo del 30%
        return newh;
    }
    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
    public void setH(int h) {
        this.h = h;
    }
}

