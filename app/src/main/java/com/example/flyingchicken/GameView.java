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
    boolean touchedCoin=false;
    Enviroment env;
    ArrayList<Pipe> pipes=new ArrayList<>();
    ArrayList<Coin> coins=new ArrayList<>();
    Resources res = getContext().getResources();





    //Metodo per comunicare con la MainActivity
    public interface IMyEventListener {
        public void onEventOccurred(int score, boolean started,boolean GameOverAdded,boolean touchedCoin );
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


        bird=new Bird();
        switch(Constants.BIRD){
            case 1 :
                bird.cratebird1(res);
                break;
            case 2:
                bird.cratebird2(res);
                break;
            case 3:
                bird.cratebird3(res);
                break;
            case 4:
                bird.cratebird4(res);
                break;
        }

        COLLISIONS = true;

        Constants.GAMEOVER = false;
        Constants.RESTART=false;

        score = 0;



        coin=new Coin();
        coin.setWidth((2*100*Constants.SCREEN_WIDTH/1000));
        coin.setHeight((100*Constants.SCREEN_HEIGHT/1900));
        coin.setX(1250);
        coin.setY(Constants.SCREEN_HEIGHT/2);
        ArrayList<Bitmap> coinsbit=new ArrayList<>();
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin1));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin2));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin3));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin4));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin5));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin6));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin7));
        coinsbit.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.coin8));
        coin.setArrBms(coinsbit);
        coin.setH(5);
        coins.add(coin);


        for(int i=0; i<2;i++){
            Coin last=new Coin();
            last.init(coins.get(i).getX(),Coin.newH());
            last.setArrBms(coinsbit);
            coins.add(last);
        }

        env=new Enviroment(bottom1,bottom2,0,pipes);
        switch(Constants.ENVIROMENT){
            case 1 :
                env.createEnv1(res,this,context);
                break;
            case 2:
                env.createEnv2(res,this,context);
                break;
        }




    }
    public void draw(Canvas canvas){
        super.draw(canvas);

        boolean status = Constants.PAUSED || Constants.GAMEOVER; //Se falsa le draw disegnano

        if (bird.isDead() && !GameOverAdded) {
            //Toast.makeText(context, "dead", Toast.LENGTH_SHORT).show();
            if (mEventListener != null) {
                mEventListener.onEventOccurred(score, first_touch,GameOverAdded,touchedCoin);
            }
            GameOverAdded = true;
        }
        else{
            if (mEventListener != null && !Constants.GAMEOVER) {
                mEventListener.onEventOccurred(score, first_touch,GameOverAdded,touchedCoin);
            }
            touchedCoin=false;
        }


        if(!first_touch){
            env.bottom1.draw(canvas, status);
            env.bottom2.draw(canvas, status);
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
            Pipe.draw(canvas, status,env.pipes,res);
            //for (Pipe pipe : pipes){                                    codice per vedere l'hitbox
            //    canvas.drawRect( pipe.getRectBottom(),myPaint);
            //    canvas.drawRect( pipe.getRectTop(),myPaint);
            //}

            for(Coin coin:coins){
                coin.draw(canvas,status);
            }
            //coin.draw(canvas, status);
            env.bottom1.draw(canvas, status);

            env.bottom2.draw(canvas, status);
        }
        drawFPS(canvas);
        drawUPS(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!first_touch) {
            first_touch = true;
            Constants.RESTART=false;
            mEventListener.onEventOccurred(score, first_touch, GameOverAdded,touchedCoin);
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
        int id = android.os.Process.myPid();
        //System.out.println(id);
        boolean status = Constants.PAUSED || Constants.GAMEOVER; //Se falsa le draw disegnano
        if(Constants.RESTART==true){
            restart();
        }
        if(!first_touch){
            env.bottom1.totalMove(status);
            env.bottom2.totalMove(status);
            bird.totalMove(status);

            if(bird.getY() > Constants.SCREEN_HEIGHT / 2 + 20){
                bird.setDrop(-15); //Lo faccio saltare un po meno
            }
        }
        else{
            bird.totalMove((status && !Constants.GAMEOVER) || bird.getY() > Constants.SCREEN_HEIGHT); //Si ferma quando e' caduto del tutto
            Pipe.totalMove( env.pipes,status);

            Coin.totalMove(coins,status);
           // coin.draw(canvas, status);
            env.bottom1.totalMove( status);

            env.bottom2.totalMove( status);
        }
        if(!bird.isDead() && !Constants.RESTART) {
            for (Pipe pipe : env.pipes) {
                if (bird.touchesPipe(pipe)) {
                    bird.setDead(true);
                    Constants.GAMEOVER = true;
                    Constants.SCORE = score;
 /*                   if(score>Constants.BESTSCORE){
                        Constants.BESTSCORE=score;
                    }*/

                }
                Float f1 = new Float(bird.getX());
                Float f2 = new Float((pipe.getX() + (float) (pipe.getWidth())));
                if (f1.equals(f2)) {
                    score++;
                    Constants.SCORE = score;

                }

            }
            for(Coin coin :coins){
                if(bird.touchesCoin(coin) && !touchedCoin && !coin.touched){
                    coin.touched=true;
                    Constants.COINS++;
                    touchedCoin=true;
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
        System.out.println("Ciao");
        gameLoop.stopLoop();
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
                env.pipes.get(i).init(Constants.SCREEN_WIDTH-600,5);
            }else{
                env.pipes.get(i).init(env.pipes.get(i-1).getX(),Pipe.newH(env.pipes.get(i)));
            }

        }

        for(int i=0; i<3;i++){
            coins.get(i).touched=false;
            coins.get(i).drop=0;
            coins.get(i).setY(Constants.SCREEN_HEIGHT/2);
           if(i==0){
               coins.get(i).setX(1250);
               coins.get(i).setH(5);
           }else{
               coins.get(i).init(coins.get(i-1).getX(),Coin.newH());

           }
        }

    }
}
