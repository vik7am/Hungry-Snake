package com.vikrant.hungrysnake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

public class Egg{

    int snakeSize;
    int deviceWidth,deviceHeight;
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

    public boolean newEgg(boolean collision) {
        do{
            position = new Point(random.nextInt(deviceWidth/snakeSize)*snakeSize,
                    random.nextInt(deviceHeight/snakeSize)*snakeSize);
            for(int i=0;i<snake.length;i++)
                if(position.equals(snake.head)){
                    collision =true;
                    break;
                }
            //implement head collision check with the egg here
        }while(collision);
        return true;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(position.x, position.y,position.x + snake.size, position.y + snake.size, paint);
    }

    boolean collision(Point snakeHead) {
        if(snakeHead.equals(position))
            return newEgg(false);
        else
            return false;
    }
}