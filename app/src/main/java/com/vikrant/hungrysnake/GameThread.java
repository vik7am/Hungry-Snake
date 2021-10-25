package com.vikrant.hungrysnake;

import android.content.Context;

public class GameThread extends Thread{

    Context context;
    GamePanel gamePanel;
    long startTime,endTime,delayTime;

    GameThread(GamePanel gamePanel, Context context) {
        this.gamePanel = gamePanel;
        this.context = context;
    }

    @Override
    public void run() {
        while(gamePanel.snake.running) {
            if(gamePanel.pause) {
                gamePanel.updateDisplay();
                break;
            }
            startTime=System.currentTimeMillis();
            gamePanel.onTimer();
            if(gamePanel.snake.collision())
                break;
            gamePanel.updateDisplay();
            endTime=System.currentTimeMillis();
            delayTime=endTime-startTime;
            try {
                if(delayTime<gamePanel.sleepTime)
                    Thread.sleep(gamePanel.sleepTime-delayTime);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(gamePanel.snake.running)
            gamePanel.showAlertDialog();
    }
}
