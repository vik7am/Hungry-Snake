package com.vikrant.hungrysnake;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread{

    GamePanelActivity activity;
    GamePanel gamePanel;

    GameThread(GamePanelActivity activity) {
        gamePanel=activity.gamePanel;
        this.activity=activity;
    }

    @Override
    public void run() {
        long st,et,d;
        while(gamePanel.RUN) {
            //Log.e("GameThread",""+gamePanel.snake.X.get(0));
            st=System.currentTimeMillis();
            gamePanel.snake.move(true);
            if(gamePanel.snake.collision())
                break;
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Canvas canvas = gamePanel.getHolder().lockCanvas();
                    if(canvas!=null) {
                        gamePanel.draw(canvas);
                        gamePanel.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            });
            et=System.currentTimeMillis();
            d=et-st;
            try {
                if(gamePanel.PAUSE)
                    break;
                if(d<gamePanel.SLEEP_TIME)
                    Thread.sleep(gamePanel.SLEEP_TIME-d);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if(gamePanel.RUN)
            activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(gamePanel.PAUSE)
                    gamePanel.pauseGame();
                else
                    gamePanel.exitGame();
            }
            });
        //System.out.println("Thread is Exiting");
    }
}
