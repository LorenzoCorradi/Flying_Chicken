package com.example.flyingchicken;

import static com.example.flyingchicken.Constants.SCREEN_HEIGHT;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private int count ,vFlap,idCurrentBitmap;
    private ArrayList<Bitmap> arrBms=new ArrayList<>();
    public float drop;
    public float rotate=0;          //attributo che mi indica di quanti gradi è ruotato l'oggetto
    public Bird(){
        this.count=0;
        this.vFlap=5;
        this.idCurrentBitmap=0;
        this.drop=0;
    }
    public void draw(Canvas canvas,Boolean status){
        Matrix matrix=new Matrix();         //non chiedermi cosa sia di preciso una matrix
        if(status==false){

            if(drop<-7){            //faccio in modo che non scodi troppo verso l'alto quando tocco lo schermo per farlo salire
                matrix.postRotate((drop+rotate*4)/5);       //media pesata fra l'angolo precedente *4 e il nuovo angolo basato sulla velocità di caduta(in questo caso salita)
                rotate=(drop+rotate*4)/5;
            }else{
                matrix.postRotate((drop+rotate)/2);         //media fra angolo precedente e quello nuovo
                rotate=(drop+rotate)/2;
            }

            drop();
        }



        matrix.postTranslate(this.x,this.y);                        //setto la x e la y dell'oggetto
        canvas.drawBitmap(this.getBm(),matrix,null);
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
            this.arrBms.set(i,Bitmap.createScaledBitmap(this.arrBms.get(i),this.width,this.height,true));
        }

    }

    @Override
    public Bitmap getBm() {

        count++;
        if(this.count==this.vFlap){
           if(this.idCurrentBitmap==this.arrBms.size()-1){
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
