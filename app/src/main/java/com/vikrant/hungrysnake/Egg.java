package com.vikrant.hungrysnake;

import java.util.Random;

public class Egg{

    int X,Y;
    int SIZE;
    int WIDTH,HEIGHT;
    Random random;
    Snake snake;

    Egg(int x) {
        SIZE=x;
        random=new Random();
    }

    public void setWidthHeight(int x,int y) {
        WIDTH=x;HEIGHT=y;
    }

    public void setSnake(Snake snake) {
        this.snake=snake;
    }

    public boolean eatEgg(int x,int y) {
        return x == X && y == Y;
    }

    public void newEgg() {
        boolean EGG;
        int x,y;
        do {
            EGG=false;
            x=random.nextInt(WIDTH/SIZE);
            y=random.nextInt(HEIGHT/SIZE);
            System.out.println("Random X:"+x+"Y:"+y);
            X=x*SIZE;
            Y=y*SIZE;
            for(int i=0;i<snake.LENGTH;i++)
                if (X == snake.X.get(i) && Y == snake.Y.get(i)) {
                    EGG = true;
                    break;
                }
        }while(EGG);
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