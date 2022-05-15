package com.example.flyingchicken;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;
    private Coin coin;
    private Bottom bottom1;
    private Bottom bottom2;
    ArrayList<Pipe> pipes=new ArrayList<>();
    Resources res = getContext().getResources();




    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(context.getDrawable(Constants.BACKGROUND));
        bird=new Bird();

        bird.setWidth((100*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((100*Constants.SCREEN_HEIGHT/1900));
        bird.setX(100*Constants.SCREEN_WIDTH/1000);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms=new ArrayList<>();

        /*arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame4));*/
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.chicken_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.chiken_frame2));
        bird.setArrBms(arrBms);

        coin=new Coin();
        coin.setWidth((2*100*Constants.SCREEN_WIDTH/1000));
        coin.setHeight((100*Constants.SCREEN_HEIGHT/1900));
        coin.setX(100*Constants.SCREEN_WIDTH/1000+200);
        coin.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> coins=new ArrayList<>();
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin1));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin2));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin3));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin4));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin5));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin6));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin7));
        coins.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin8));
        coin.setArrBms(coins);


        bottom1=new Bottom();
        bottom1.setWidth(Constants.SCREEN_WIDTH);
        bottom1.setHeight(25*Constants.SCREEN_HEIGHT/100);
        bottom1.setX(0);
        bottom1.setY(Constants.SCREEN_HEIGHT-25*Constants.SCREEN_HEIGHT/100);
        ArrayList<Bitmap> bottoms=new ArrayList<>();
        bottoms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.vapor_bottom));
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
        start.init(400,5,res);
        pipes.add(start);

        for(int i=0; i<2;i++){
            Pipe last=new Pipe();
            last.setimages(imagepipebot,imagepipetop);
            last.init(pipes.get(i).getX(),Pipe.newH(pipes.get(i)),res);
            pipes.add(last);
        }

        handler=new Handler();

        r=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
    public void draw(Canvas canvas){
        super.draw(canvas);

        Pipe.draw(canvas,Constants.PAUSED,pipes,res);
        bird.draw(canvas,Constants.PAUSED);
        coin.draw(canvas,Constants.PAUSED);
        bottom1.draw(canvas,Constants.PAUSED);
        bottom2.draw(canvas,Constants.PAUSED);
        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(bird.getY() + bird.getHeight() / 2 > 0)
                bird.setDrop(-15);
        }
        return true;
    }




}
