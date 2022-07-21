package com.example.flyingchicken;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning=false;
    private SurfaceHolder surfaceHolder;
    private GameView game;
    private double getAverageUPS;
    private double getAverageFPS;

    public GameLoop(GameView game, SurfaceHolder surfaceHolder){
        this.surfaceHolder=surfaceHolder;
        this.game=game;
    }
    public double getAverageFPS() {
        return getAverageFPS;
    }



    public double getAverageUPS() {
        return getAverageUPS;
    }

    public void startLoop() {
        isRunning=true;
        start();
    }
    @Override
    public void run() {
        super.run();

        int updateCount=0;
        int frameCount=0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        Canvas canvas=null;
        startTime=System.currentTimeMillis();
        while (isRunning){

            try{
                canvas=surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;
                    game.invalidate();
                }
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }finally {
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


            elapsedTime=System.currentTimeMillis()-startTime;
            sleepTime=(long)(updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime>0){
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while(sleepTime<0 && updateCount<MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime=System.currentTimeMillis()-startTime;
                sleepTime=(long)(updateCount*UPS_PERIOD - elapsedTime);
            }

            elapsedTime=System.currentTimeMillis()-startTime;
            if(elapsedTime>=1000){
                getAverageUPS=updateCount/(1E-3*elapsedTime);
                getAverageFPS=frameCount/(1E-3*elapsedTime);
                updateCount=0;
                frameCount=0;
                startTime=System.currentTimeMillis();
            }

        }
    }

    public void stopLoop() {
        isRunning=false;

    }
}

