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
    LinkedList<Pipe> queue=new LinkedList<>();
    Resources res = getContext().getResources();



    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bird=new Bird();
        bird.setWidth((100*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((100*Constants.SCREEN_HEIGHT/1900));
        bird.setX(100*Constants.SCREEN_WIDTH/1000);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));
        bird.setArrBms(arrBms);


        Pipe start=new Pipe();
        start.newLast(null,this.getResources());
        queue.add(start);

        handler=new Handler();
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    if(Constants.SCREEN_WIDTH-queue.getLast().x>400){
                        Pipe last=new Pipe();
                        last.newLast(queue.getLast(),res);
                        queue.add(last);
                    }
                }
            }
        }.start();

        r=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(queue.getFirst().getX()+queue.getFirst().getWidth()<0){
            queue.remove();
        }
        for (ListIterator<Pipe> i = queue.listIterator(); i.hasNext();){
            Pipe d=i.next();
            d.draw(canvas, Constants.PAUSED);
            if(d.getX()+d.getWidth()<0){
                i.remove();
            }
        }
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
