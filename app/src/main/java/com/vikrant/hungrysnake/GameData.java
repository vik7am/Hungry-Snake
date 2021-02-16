package com.vikrant.hungrysnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;
import androidx.preference.PreferenceManager;

public class GameData {

    int snakeSize, snakeSpeed, snakeLength;
    int deviceWidth,deviceHeight;
    int difficulty, size;
    int headColor, bodyColor, backgroundColor;
    int DEFAULT_SNAKE_LENGTH=2;
    boolean nightMode;
    String highScore;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    GameData(Context context) {
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        getDisplay(context);
        nightMode=preferences.getBoolean("nightMode",false);
        difficulty=Integer.parseInt(preferences.getString("difficulty", "1"));
        headColor=Color.rgb(100,100,100);
        bodyColor=Color.parseColor(preferences.getString("color","#009688"));
        backgroundColor=(nightMode?Color.rgb(0,0,0):Color.rgb(255,255,255));
        size=Integer.parseInt(preferences.getString("size", "2"));
        snakeSize=(deviceWidth/100)*5*size;
        snakeSpeed=getSpeed(difficulty,size);
        snakeLength=DEFAULT_SNAKE_LENGTH;
        highScore=preferences.getString("highScore", "0.0.0");
        deviceWidth=deviceWidth-deviceWidth%snakeSize;
        deviceHeight=deviceHeight-deviceHeight%snakeSize;
    }

    private int getSpeed(int difficulty,int size) {
        switch(difficulty) {
            case 0:return 12*size;
            case 1:return 6*size;
            case 2:return 3*size;
            default:return 5*size;
        }
    }

    public void getDisplay(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;
    }

    public void save() {
        editor=preferences.edit();
        editor.putString("highScore", highScore);
        editor.apply();
    }
}