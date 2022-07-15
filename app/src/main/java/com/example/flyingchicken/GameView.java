package com.example.flyingchicken;

import static com.example.flyingchicken.Constants.COLLISIONS;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentActivity;

import androidx.annotation.Nullable;

import com.google.android.gms.nearby.messages.Message;

import java.util.ArrayList;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {
    private Bird bird;
    private Handler handler;
    private Runnable r;
    private Runnable r2;
    private Coin coin;
    private Bottom bottom1;
    private Bottom bottom2;
    private GameLoop gameLoop;
    private Context context;
    private int score;
    boolean first_touch = false;
    boolean GameOverAdded=false;
    ArrayList<Pipe> pipes=new ArrayList<>();
    Resources res = getContext().getResources();





    //Metodo per comunicare con la MainActivity
    public interface IMyEventListener {
        public void onEventOccurred(int score, boolean started,boolean GameOverAdded );
    }

    private IMyEventListener mEventListener;

    public void setEventListener(IMyEventListener mEventListener) {
        this.mEventListener = mEventListener;
    }

        public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context=context;
        gameLoop=new GameLoop(this,surfaceHolder);
        setFocusable(true);
        this.setBackground(context.getDrawable(Constants.BACKGROUND));
        bird=new Bird();
        COLLISIONS = true;

        Constants.GAMEOVER = false;
        Constants.RESTART=false;

        score = 0;
        //Toast.makeText(context, "Score:" + score, Toast.LENGTH_SHORT).show();

        bird.setWidth((150*Constants.SCREEN_WIDTH/1000));
        bird.setHeight((120*Constants.SCREEN_HEIGHT/1900));
        bird.setX(100*Constants.SCREEN_WIDTH/1000);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        bird.setDead(false);
        ArrayList<Bitmap> arrBms=new ArrayList<>();

        /*arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame2));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame3));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.eli_frame4));*/
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.plane_v2_1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.plane_v2_2));
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
        start.init(400,5);
        pipes.add(start);

        for(int i=0; i<2;i++){
            Pipe last=new Pipe();
            last.setimages(imagepipebot,imagepipetop);
            last.init(pipes.get(i).getX(),Pipe.newH(pipes.get(i)));
            pipes.add(last);
        }



    }
    public void draw(Canvas canvas){
        super.draw(canvas);


        boolean status = Constants.PAUSED || Constants.GAMEOVER; //Se falsa le draw disegnano

        if (bird.isDead() && !GameOverAdded) {
            //Toast.makeText(context, "dead", Toast.LENGTH_SHORT).show();
            if (mEventListener != null) {
                mEventListener.onEventOccurred(score, first_touch,GameOverAdded);
            }
            GameOverAdded = true;
        }
        else{
            if (mEventListener != null) {
                mEventListener.onEventOccurred(score, first_touch,GameOverAdded);
            }
        }


        if(!first_touch){
            bottom1.draw(canvas, status);
            bottom2.draw(canvas, status);
            //Paint myPaint = new Paint();              codice per vedere l'hitbox
            //myPaint.setColor(Color.rgb(0, 0, 0));
            //canvas.drawRect(bird.getRect(),myPaint);
            bird.draw(canvas);

        }
        else{
            //Paint myPaint = new Paint();                           //codice per vedere l'hitbox
            //myPaint.setColor(Color.rgb(0, 0, 0));
            //canvas.drawRect(bird.getRect(),myPaint);
            bird.draw(canvas); //Si ferma quando e' caduto del tutto
            Pipe.draw(canvas, status,pipes,res);
            //for (Pipe pipe : pipes){                                    codice per vedere l'hitbox
            //    canvas.drawRect( pipe.getRectBottom(),myPaint);
            //    canvas.drawRect( pipe.getRectTop(),myPaint);
            //}

            coin.draw(canvas, status);
            bottom1.draw(canvas, status);

            bottom2.draw(canvas, status);
        }
        drawFPS(canvas);
        drawUPS(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!first_touch) {
            first_touch = true;
            Constants.RESTART=false;
            mEventListener.onEventOccurred(score, first_touch, GameOverAdded);
        }
        if(!bird.isDead()){
            if(event.getAction()==MotionEvent.ACTION_UP){
                if(bird.getY() + bird.getHeight() / 2 > 0)
                    bird.setDrop(-15);
            }
        }

        return true;
    }
    public void update(){

        boolean status = Constants.PAUSED || Constants.GAMEOVER; //Se falsa le draw disegnano
        if(Constants.RESTART==true){
            restart();
        }
        if(!first_touch){
            bottom1.totalMove(status);
            bottom2.totalMove(status);
            bird.totalMove(status);

            if(bird.getY() > Constants.SCREEN_HEIGHT / 2 + 20){
                bird.setDrop(-15); //Lo faccio saltare un po meno
            }
        }
        else{
            bird.totalMove((status && !Constants.GAMEOVER) || bird.getY() > Constants.SCREEN_HEIGHT); //Si ferma quando e' caduto del tutto
            Pipe.totalMove( pipes,status);

           // coin.draw(canvas, status);
            bottom1.totalMove( status);

            bottom2.totalMove( status);
        }
        if(!bird.isDead() && !Constants.RESTART) {
            for (Pipe pipe : pipes) {
                if (bird.touchesPipe(pipe)) {
                    bird.setDead(true);
                    Constants.GAMEOVER = true;
                    Constants.SCORE = score;

                }
                Float f1 = new Float(bird.getX());
                Float f2 = new Float((pipe.getX() + (float) (pipe.getWidth())));
                if (f1.equals(f2)) {
                    score++;
                    Constants.SCORE = score;

                }

            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    public void drawUPS(Canvas canvas){
        String averageUPS=Double.toString(gameLoop.getAverageUPS());
        Paint paint=new Paint();
        int color= ContextCompat.getColor(context,R.color.purple_200);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " +averageUPS,100,400,paint);

    }
    public void drawFPS(Canvas canvas){
        String averageFPS=Double.toString(gameLoop.getAverageFPS());
        System.out.println(averageFPS);
        Paint paint=new Paint();
        int color= ContextCompat.getColor(context,R.color.purple_200);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " +averageFPS,100,500,paint);

    }
    public void restart(){
        Constants.GAMEOVER = false;

        Constants.SCORE = 0;
        score=0;
        first_touch = false;
        GameOverAdded=false;
        bird.setDead(false);
        for(int i=0;i<3;i++){
            if(i==0){
                pipes.get(i).init(400,5);
            }else{
                pipes.get(i).init(pipes.get(i-1).getX(),Pipe.newH(pipes.get(i)));
            }

        }

    }
}
