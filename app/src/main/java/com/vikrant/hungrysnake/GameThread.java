package com.vikrant.hungrysnake;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread{

    GamePanelActivity activity;
    GamePanel gamePanel;
    long startTime,endTime,delayTime;

    GameThread(GamePanelActivity activity) {
        gamePanel=activity.gamePanel;
        this.activity=activity;
    }

    @Override
    public void run() {

        while(gamePanel.snake.running) {
            startTime=System.currentTimeMillis();
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
            endTime=System.currentTimeMillis();
            delayTime=endTime-startTime;
            try {
                if(gamePanel.pause)
                    break;
                if(delayTime<gamePanel.sleepTime)
                    Thread.sleep(gamePanel.sleepTime-delayTime);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if(gamePanel.snake.running)
            activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(gamePanel.pause)
                    gamePanel.pauseGame();
                else
                    gamePanel.exitGame();
            }
            });
    }
}
