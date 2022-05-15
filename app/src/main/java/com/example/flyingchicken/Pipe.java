package com.example.flyingchicken;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.security.keystore.KeyPermanentlyInvalidatedException;

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

    public void init(float lastx,int h,android.content.res.Resources res){
        this.setX(lastx+600);
        this.h=h;
        this.setWidth((100*Constants.SCREEN_WIDTH/1000));
    }
    static Pipe getlast(ArrayList<Pipe> pipes){
        float x=0;
        Pipe last=null;
        for(Pipe p:pipes){
            if(p.getX()>x){
                x=p.getX();
                last=p;
            }
        }
        return last;
    }
    static void draw(Canvas canvas, Boolean status,ArrayList<Pipe> pipes,android.content.res.Resources res){
        if(status==false){
                int count=0;
            for(Pipe p:pipes){
               if(p.x+p.width<0){
                    Pipe last=getlast(pipes);
                    int newh=newH(last);
                    p.setH(newh);
                    p.init(last.getX(),newh,res);
                }
                p.move();
                count++;
            }


        }
        for(Pipe p:pipes){
           canvas.drawBitmap(p.imageBottom,p.x,(float)(10-p.h+0.8)*10*Constants.SCREEN_HEIGHT/100,null);
           canvas.drawBitmap(p.imageTop,p.x,(float)(10-p.h-0.8)*10*Constants.SCREEN_HEIGHT/100-p.getImageTop().getHeight(),null);
        }



    }

    public void setH(int h) {
        this.h = h;
    }

    public void setimages(Bitmap imagebot, Bitmap imagetop){
        this.imageTop=imagetop;
        this.imageBottom=imagebot;
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
        float lastx;
        if(oldlast==null){
            h=5;
            lastx=0;
        }
        else {
            Random rn = new Random();
            lastx=oldlast.getX();
            int x=oldlast.getH();
            h = rn.nextInt(7) + x-3;    //l'altezza di differenza fra i tubi varia massimo del 30%
            if(h<=1){
                h=2;
            }
            else if(h>=10){
                h=9;
            }

        }
        this.setX(lastx+400);
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

    static int newH(Pipe oldlast){
        int h;

        if(oldlast==null){
            h=5;
        }
        else {
            Random rn = new Random();
            int low = oldlast.getH()-3;
            int high = oldlast.getH()+3;
            if(low<=4){
                low=4;

            }
            if(high>=9){
                high=9;

            }
            System.out.println("low: "+low);
            System.out.println("high: "+high);
            h = rn.nextInt(high - low) + low; //l'altezza di differenza fra i tubi varia massimo del 30%

            /*if (h <= 1) {
                h = 2;
            } else if (h >= 10) {
                h = 9;
            }*/
        }
        System.out.println(h);
        return h;

    }

    public Bitmap getImageTop() {
        return imageTop;
    }
}
