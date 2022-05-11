package com.example.flyingchicken;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;

public class GameView extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;
    ArrayList<Pipe> pipes=new ArrayList<>();
    Resources res = getContext().getResources();



    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bird=new Bird();
        bird.setWidth((2*100*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((100*Constants.SCREEN_HEIGHT/1900));
        bird.setX(100*Constants.SCREEN_WIDTH/1000);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame4));
        bird.setArrBms(arrBms);

        Pipe start=new Pipe();
        start.setimages(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe1),BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe2));
        start.init(400,5,res);
        pipes.add(start);

        for(int i=0; i<3;i++){
            Pipe last=new Pipe();
            last.setimages(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe1),BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe2));
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
        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            bird.setDrop(-15);
        }
        return true;
    }




}
