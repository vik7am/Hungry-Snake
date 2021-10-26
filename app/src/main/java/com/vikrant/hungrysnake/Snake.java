package com.vikrant.hungrysnake;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class Snake{

    int length,size;
    int headColor,bodyColor;
    int deviceWidth,deviceHeight;
    boolean horizontal,forward;
    ArrayList<Point> tail;
    Point head;
    Egg egg;
    String direction;

    Snake(GameData gameData,Egg egg) {
        this.egg=egg;
        egg.setSnake(this);
        length=gameData.snakeLength-1;
        size=gameData.snakeSize;
        deviceWidth=gameData.deviceWidth;
        deviceHeight= gameData.deviceHeight;
        headColor = gameData.headColor;
        bodyColor = gameData.bodyColor;
        head = new Point(size,0);
        tail = new ArrayList<>();
        tail.add(new Point(0,0));
        direction = "RIGHT";
        horizontal=true;
        forward=true;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(headColor);
        canvas.drawRect(head.x, head.y, head.x+size,head.y+ size, paint);
        paint.setColor(bodyColor);
        for (int i = 0; i < length; i++)
            canvas.drawRect(tail.get(i).x, tail.get(i).y, tail.get(i).x+size,tail.get(i).y+ size, paint);
    }

    public void grow() {
        if(egg.collision(head))
            length++;
        else
            tail.remove(tail.size()-1);
    }

    public void checkBoundsCollision() {
        if(head.x == deviceWidth)
            head.set(0, head.y);
        else if(head.y == deviceHeight - size)
            head.set(head.x, 0);
        else if(head.x == -size)
            head.set(deviceWidth - size, head.y);
        else if(head.y == -size)
            head.set(head.x, deviceHeight-2*size);
    }

    public void move() {
        tail.add(0, new Point(head.x, head.y));
        switch(direction) {
            case "RIGHT": head.set(head.x + size, head.y); break;
            case "LEFT": head.set(head.x - size, head.y); break;
            case "UP": head.set(head.x , head.y- size); break;
            case "DOWN": head.set(head.x , head.y+ size); break;
        }
    }

    public void changeDirection(String swipe) {
        if(swipe.equals("RIGHT") ||swipe.equals("LEFT"))
            if(horizontal)
                return;
        if(swipe.equals("UP") ||swipe.equals("DOWN"))
            if(!horizontal)
                return;
        direction = swipe;
        horizontal = !horizontal;
    }

    public boolean collision() {
        if(length<3)return false;
        for(int i=3;i<length;i++)
            if(tail.get(i).equals(head))
                return  true;
        return false;
    }

}