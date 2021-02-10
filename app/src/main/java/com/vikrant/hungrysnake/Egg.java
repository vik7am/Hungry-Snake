package com.vikrant.hungrysnake;

import java.util.Random;

public class Egg{

    int eggX,eggY;
    int snakeSize;
    int deviceWidth,deviceHeight;
    int randomX, randomY;
    boolean egg;
    Random random;
    Snake snake;

    Egg(GameData gamedata) {
        deviceWidth=gamedata.deviceWidth;
        deviceHeight=gamedata.deviceHeight;
        snakeSize=gamedata.snakeSize;
        random=new Random();
    }

    public void setSnake(Snake snake) {
        this.snake=snake;
    }

    public boolean eatEgg(int x,int y) {
        return x == eggX && y == eggY;
    }

    public void newEgg() {

        do {
            egg=false;
            randomX=random.nextInt(deviceWidth/snakeSize);
            randomY=random.nextInt(deviceHeight/snakeSize);
            System.out.println("Random X:"+randomX+"Y:"+randomY);
            eggX=randomX*snakeSize;
            eggY=randomY*snakeSize;
            for(int i=0;i<snake.length;i++)
                if (eggX == snake.snakeX.get(i) && eggY == snake.snakeY.get(i)) {
                    egg = true;
                    break;
                }
        }while(egg);
    }

    boolean develop(int x,int y,boolean NEW) {
        if(eatEgg(x, y)) {
            if(NEW)
                newEgg();
            return true;
        }
        return false;
    }
}