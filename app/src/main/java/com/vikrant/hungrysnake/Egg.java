package com.vikrant.hungrysnake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

public class Egg{

    int snakeSize;
    int deviceWidth,deviceHeight;
    boolean collision;
    Point position;
    Random random;
    Snake snake;

    Egg(GameData gamedata) {
        deviceWidth=gamedata.deviceWidth;
        deviceHeight=gamedata.deviceHeight;
        snakeSize=gamedata.snakeSize;
        position = new Point(0,0);
        random=new Random();
    }

    public void setSnake(Snake snake) {
        this.snake=snake;
    }

    public boolean newEgg() {
        do{
            collision = false;
            position = new Point(random.nextInt(deviceWidth/snakeSize)*snakeSize,
                    random.nextInt((deviceHeight-snakeSize)/snakeSize)*snakeSize);
            for(int i=0;i<snake.length;i++)
                if(position.equals(snake.tail.get(i))){
                    collision = true;
                    break;
                }
            if(position.equals(snake.head))
                collision = true;
        }while(collision);
        System.out.println("Egg "+position+"ss"+snakeSize);
        return true;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(position.x, position.y,position.x + snake.size, position.y + snake.size, paint);
    }

    boolean collision(Point snakeHead) {
        if(snakeHead.equals(position))
            return newEgg();
        else
            return false;
    }
}