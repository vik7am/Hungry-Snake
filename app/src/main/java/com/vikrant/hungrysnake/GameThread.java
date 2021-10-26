package com.vikrant.hungrysnake;

public class GameThread extends Thread{

    GamePanel gamePanel;
    long startTime,delayTime;

    GameThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while(!gamePanel.gameOver && !gamePanel.gamePaused) {
            try{
                startTime = System.currentTimeMillis();
                gamePanel.updateState();
                gamePanel.updateDisplay();
                delayTime = System.currentTimeMillis() -startTime;
                Thread.sleep(gamePanel.sleepTime - delayTime);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gamePanel.updateDisplay();
    }
}
