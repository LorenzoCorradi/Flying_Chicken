package com.example.flyingchicken;

import static com.example.flyingchicken.Constants.COLLISIONS;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView2 extends View {
    private Bird bird;
    private Handler handler = new Handler();
    private Runnable r=new Runnable() {
        @Override
        public void run() {
            if(bird.getY() > Constants.SCREEN_HEIGHT / 2f + 100){
                bird.setDrop(-10);
            }

            invalidate();
        }
    };;

    public GameView2(Context context) {
        super(context);

        this.setBackground(context.getDrawable(Constants.BACKGROUND));


        bird = new Bird();

        bird.setWidth((150*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        bird.setX(Constants.SCREEN_WIDTH / 2f);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        bird.setDead(false);
        ArrayList<Bitmap> arrBms=new ArrayList<>();

        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame4));
        bird.setArrBms(arrBms);

    }

    public GameView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        bird = new Bird();

        bird.setWidth((150*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        bird.setX(100 * Constants.SCREEN_WIDTH / 1000f);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        bird.setDead(false);
        ArrayList<Bitmap> arrBms=new ArrayList<>();

        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame4));
        bird.setArrBms(arrBms);

    }


    public GameView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        bird = new Bird();

        bird.setWidth((150*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        bird.setX(100 * Constants.SCREEN_WIDTH / 1000f);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        bird.setDead(false);
        ArrayList<Bitmap> arrBms=new ArrayList<>();

        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame4));
        bird.setArrBms(arrBms);

    }

    @Override
    public void onDraw(Canvas canvas){
      super.onDraw(canvas);
      bird.draw(canvas, false);
      handler.postDelayed(r, 10);
    }

}
