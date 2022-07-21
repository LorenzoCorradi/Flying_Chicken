package com.example.flyingchicken;

import static com.example.flyingchicken.Constants.SCREEN_HEIGHT;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private int count ,vFlap,idCurrentBitmap;
    private ArrayList<Bitmap> arrBms=new ArrayList<>();
    public float drop;
    boolean dead;

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Bird(){
        this.count=0;
        this.vFlap=5;
        this.idCurrentBitmap=0;
        this.drop=0;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(this.getBm(),this.x,this.y,null);
    }
    public void totalMove(Boolean status){
        if(status==false){
            drop();
        }
    }
    private void drop() {
        if(y + drop + height / 2 < SCREEN_HEIGHT){
            this.drop+=0.6;
            this.y+=this.drop;
        }
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
        for(int i=0;i<arrBms.size();i++){
            Bitmap sup = Bitmap.createScaledBitmap(arrBms.get(i),this.width,this.height,true);
            this.arrBms.set(i,sup);
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
                this.idCurrentBitmap++;
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

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }

    public void cratebird1(Resources res){
        this.setWidth((150*Constants.SCREEN_WIDTH/1000));
        this.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        this.setX(100*Constants.SCREEN_WIDTH/1000);
        this.setY(Constants.SCREEN_HEIGHT/2-this.getHeight()/2);
        this.setDead(false);

        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.plane_v2_1));
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.plane_v2_2));
        this.setArrBms(arrBms);


    }
    public void cratebird2(Resources res){
        this.setWidth((200*Constants.SCREEN_WIDTH/1000));
        this.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        this.setX(100*Constants.SCREEN_WIDTH/1000);
        this.setY(Constants.SCREEN_HEIGHT/2-this.getHeight()/2);
        this.setDead(false);

        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.eli_frame4));
        this.setArrBms(arrBms);


    }
    public void cratebird3(Resources res){
        this.setWidth((120*Constants.SCREEN_WIDTH/1000));
        this.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        this.setX(100*Constants.SCREEN_WIDTH/1000);
        this.setY(Constants.SCREEN_HEIGHT/2-this.getHeight()/2);
        this.setDead(false);
        this.vFlap=10;

        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.chicken_frame1));
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.chiken_frame2));

        this.setArrBms(arrBms);


    }
    public void cratebird4(Resources res){
        this.setWidth((120*Constants.SCREEN_WIDTH/1000));
        this.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        this.setX(100*Constants.SCREEN_WIDTH/1000);
        this.setY(Constants.SCREEN_HEIGHT/2-this.getHeight()/2);
        this.setDead(false);
        this.vFlap=10;

        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.leochicken1));
        arrBms.add(BitmapFactory.decodeResource(res,R.drawable.leochicken2));

        this.setArrBms(arrBms);


    }
}
