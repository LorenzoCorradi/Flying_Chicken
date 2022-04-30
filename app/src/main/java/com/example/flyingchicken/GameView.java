package com.example.flyingchicken;

import android.content.Context;
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

public class GameView extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;



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
