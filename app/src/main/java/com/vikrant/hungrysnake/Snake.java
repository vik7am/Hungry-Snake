package com.vikrant.hungrysnake;

import java.util.ArrayList;

public class Snake{

    int LENGTH;
    int SIZE;
    int WIDTH,HEIGHT;
    boolean HORIZONTAL,FORWARD;
    ArrayList<Integer> X;
    ArrayList<Integer> Y;
    Egg egg;

    Snake(GameData gameData,Egg egg) {
        this.egg=egg;
        egg.setSnake(this);
        LENGTH=gameData.LENGTH;
        SIZE=gameData.SIZE;
        X=new ArrayList<>(100);
        Y=new ArrayList<>(100);
        X.add(SIZE);Y.add(0);
        X.add(0);Y.add(0);
        HORIZONTAL=true;FORWARD=true;
    }

    public void move(boolean NEW) {
        int x=0,y=0;
        if(HORIZONTAL) {
            if(FORWARD)
                x=SIZE;
            else
                x=-SIZE;
        }
        else {
            if(FORWARD)
                y=SIZE;
            else
                y=-SIZE;
        }
        X.add(0, X.get(0)+x);
        Y.add(0, Y.get(0)+y);
        if(X.get(0)==WIDTH)
            X.set(0, 0);
        if(Y.get(0)==HEIGHT)
            Y.set(0, 0);
        if(X.get(0)==-SIZE)
            X.set(0, WIDTH-SIZE);
        if(Y.get(0)==-SIZE)
            Y.set(0, HEIGHT-SIZE);
        if(egg.develop(X.get(0),Y.get(0),NEW))
            LENGTH++;
        else {
            X.remove(X.size()-1);
            Y.remove(Y.size()-1);
        }
    }

    public boolean collision() {
        if(LENGTH<4)return false;
        int x,y;
        x=X.get(0);
        y=Y.get(0);
        for(int i=4;i<LENGTH;i++)
            if(x==X.get(i)&&y==Y.get(i))
                return true;
        return false;
    }

    public void changeDirection(float x, float y) {
        boolean temp=HORIZONTAL;
        HORIZONTAL=Math.abs(x)>Math.abs(y);
        if(HORIZONTAL==temp)
            return;
        if(HORIZONTAL)
            FORWARD= x > 0;
        else
            FORWARD= y > 0;
    }

    public void setWidthHeight(int x,int y) {
        WIDTH=x;HEIGHT=y;
        egg.setWidthHeight(x, y);
    }
}