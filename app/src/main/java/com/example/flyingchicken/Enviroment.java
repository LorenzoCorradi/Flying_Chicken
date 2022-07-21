package com.example.flyingchicken;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Enviroment {
    Bottom bottom1;
    Bottom bottom2;
    int background;
    ArrayList<Pipe> pipes;

    public Enviroment(Bottom bottom1, Bottom bottom2, int background, ArrayList<Pipe> pipes) {
        this.bottom1 = bottom1;
        this.bottom2 = bottom2;
        this.background = background;
        this.pipes = pipes;
    }

    public void createEnv1(Resources res, GameView gameView, Context context){
        bottom1=new Bottom();
        bottom1.setWidth(Constants.SCREEN_WIDTH);
        bottom1.setHeight(25*Constants.SCREEN_HEIGHT/100);
        bottom1.setX(0);
        bottom1.setY(Constants.SCREEN_HEIGHT-25*Constants.SCREEN_HEIGHT/100);

        ArrayList<Bitmap> bottoms=new ArrayList<>();
        bottoms.add(BitmapFactory.decodeResource(res,R.drawable.vapor_bottom));
        bottom1.setArrBms(bottoms);

        bottom2=new Bottom();
        bottom2.setWidth(Constants.SCREEN_WIDTH);
        bottom2.setHeight(25*Constants.SCREEN_HEIGHT/100);
        bottom2.setX(Constants.SCREEN_WIDTH);
        bottom2.setY(Constants.SCREEN_HEIGHT-25*Constants.SCREEN_HEIGHT/100);
        bottom2.setArrBms(bottoms);

        Pipe start=new Pipe();
        Bitmap imagepipetop=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.vapor_pipe1),100*Constants.SCREEN_WIDTH/1000,Constants.SCREEN_HEIGHT,true);
        Bitmap imagepipebot=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.vapor_pipe2),100*Constants.SCREEN_WIDTH/1000,Constants.SCREEN_HEIGHT,true);
        start.setimages(imagepipebot,imagepipetop);
        start.init(Constants.SCREEN_WIDTH-600,5);
        pipes.add(start);

        for(int i=0; i<2;i++){
            Pipe last=new Pipe();
            last.setimages(imagepipebot,imagepipetop);
            last.init(pipes.get(i).getX(),Pipe.newH(pipes.get(i)));
            pipes.add(last);
        }

        gameView.setBackground(context.getDrawable(R.drawable.vapor_background));
    }
    public void createEnv2(Resources res, GameView gameView, Context context){
        bottom1=new Bottom();
        bottom1.setWidth(Constants.SCREEN_WIDTH);
        bottom1.setHeight(15*Constants.SCREEN_HEIGHT/100);
        bottom1.setX(0);
        bottom1.setY(Constants.SCREEN_HEIGHT-15*Constants.SCREEN_HEIGHT/100);

        ArrayList<Bitmap> bottomimg1=new ArrayList<>();
        bottomimg1.add(BitmapFactory.decodeResource(res,R.drawable.bottom_tree1));
        bottom1.setArrBms(bottomimg1);

        bottom2=new Bottom();
        bottom2.setWidth(Constants.SCREEN_WIDTH);
        bottom2.setHeight(15*Constants.SCREEN_HEIGHT/100);
        bottom2.setX(Constants.SCREEN_WIDTH);
        bottom2.setY(Constants.SCREEN_HEIGHT-15*Constants.SCREEN_HEIGHT/100);
        ArrayList<Bitmap> bottomimg2=new ArrayList<>();
        bottomimg2.add(BitmapFactory.decodeResource(res,R.drawable.bottom_tree2));

        bottom2.setArrBms(bottomimg2);

        Pipe start=new Pipe();
        Bitmap imagepipetop=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.pipe1_lunga_arancio),100*Constants.SCREEN_WIDTH/1000,Constants.SCREEN_HEIGHT,true);
        Bitmap imagepipebot=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.pipe2_lunga_arancio),100*Constants.SCREEN_WIDTH/1000,Constants.SCREEN_HEIGHT,true);
        start.setimages(imagepipebot,imagepipetop);
        start.init(Constants.SCREEN_WIDTH-600,5);
        pipes.add(start);

        for(int i=0; i<2;i++){
            Pipe last=new Pipe();
            last.setimages(imagepipebot,imagepipetop);
            last.init(pipes.get(i).getX(),Pipe.newH(pipes.get(i)));
            pipes.add(last);
        }

        gameView.setBackground(context.getDrawable(R.drawable.background3));
    }
}
