package com.vikrant.hungrysnake;

import java.util.ArrayList;

public class Snake{

    int length,size,x,y;
    int deviceWidth,deviceHeight;
    boolean horizontal,forward,running;
    boolean tempDirection;
    ArrayList<Integer> snakeX;
    ArrayList<Integer> snakeY;
    Egg egg;

    Snake(GameData gameData,Egg egg) {
        this.egg=egg;
        egg.setSnake(this);
        length=gameData.snakeLength;
        size=gameData.snakeSize;
        deviceWidth=gameData.deviceWidth;
        deviceHeight= gameData.deviceHeight;
        snakeX=new ArrayList<>(1);
        snakeY=new ArrayList<>(1);
        snakeX.add(size);
        snakeY.add(0);
        snakeX.add(0);
        snakeY.add(0);
        horizontal=true;
        forward=true;
    }

    public void move(boolean NEW) {
        x=0;
        y=0;
        if(horizontal) {
            if(forward)
                x=size;
            else
                x=-size;
        }
        else {
            if(forward)
                y=size;
            else
                y=-size;
        }
        snakeX.add(0, snakeX.get(0)+x);
        snakeY.add(0, snakeY.get(0)+y);
        if(snakeX.get(0)==deviceWidth)
            snakeX.set(0, 0);
        if(snakeY.get(0)==deviceHeight)
            snakeY.set(0, 0);
        if(snakeX.get(0)==-size)
            snakeX.set(0, deviceWidth-size);
        if(snakeY.get(0)==-size)
            snakeY.set(0, deviceHeight-size);
        if(egg.develop(snakeX.get(0),snakeY.get(0),NEW))
            length++;
        else {
            snakeX.remove(snakeX.size()-1);
            snakeY.remove(snakeY.size()-1);
        }
    }

    public boolean collision() {
        if(length<4)return false;
        int x,y;
        x=snakeX.get(0);
        y=snakeY.get(0);
        for(int i=4;i<length;i++)
            if(x==snakeX.get(i)&&y==snakeY.get(i))
                return true;
        return false;
    }

    public void changeDirection(float x, float y) {
        tempDirection=horizontal;
        horizontal=Math.abs(x)>Math.abs(y);
        if(horizontal==tempDirection)
            return;
        if(horizontal)
            forward= x > 0;
        else
            forward= y > 0;
    }
}