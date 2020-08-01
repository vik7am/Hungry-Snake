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
        if(x==X&&y==Y)
        {
            return true;
        }
        else
            return false;

    }

    public void newEgg(int SX,int SY) {
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
                if(X==snake.X.get(i)&&Y==snake.Y.get(i))
                    EGG=true;
        }while(EGG);
    }

    boolean develop(int x,int y,boolean NEW) {
        if(eatEgg(x, y)) {
            if(NEW)
                newEgg(x,y);
            return true;
        }
        return false;
    }
}