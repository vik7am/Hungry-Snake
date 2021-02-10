package com.vikrant.hungrysnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.preference.PreferenceManager;

public class GameData {

    int snakeSize, snakeSpeed, snakeLength;
    int deviceWidth,deviceHeight;
    int difficulty, size;
    int headColor, bodyColor, backgroundColor;
    boolean nightMode;
    String highScore;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    GameData(Context context) {
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        getDisplay(context);
        nightMode=preferences.getBoolean("nightMode",false);
        difficulty=getDifficulty(preferences.getString("difficulty", "Normal"));
        //difficulty=Integer.parseInt(preferences.getString("difficulty", "1"));
        //headColor=getColor(preferences.getString("color","Green"),"HEAD");
        headColor=Color.rgb(100,100,100);
        bodyColor=getColor(preferences.getString("color","Green"),"BODY");
        //backgroundColor=getColor(preferences.getString("color","Green"),"BACKGROUND");
        backgroundColor=(nightMode?Color.rgb(0,0,0):Color.rgb(255,255,255));
        size=getSize(preferences.getString("size", "Medium"));
        snakeSize=(deviceWidth/100)*5*size;
        snakeSpeed=getSpeed(difficulty,size);
        snakeLength=preferences.getInt("length", 2);
        highScore=preferences.getString("highScore", "0.0.0");
        deviceWidth=deviceWidth-deviceWidth%snakeSize;
        deviceHeight=deviceHeight-deviceHeight%snakeSize;
        //Log.d("speed",""+speed);
        //Log.d("Size",""+size);
    }

    private int getDifficulty(String difficulty) {
        switch(difficulty) {
            case "Easy": return 0;
            case "Normal": return 1;
            case "Hard": return 2;
        }
        return 0;
    }

    private int getSize(String size) {
        switch(size) {
            case "Small": return 1;
            case "Medium": return 2;
            case "Large": return 3;
        }
        return 0;
    }

    private int getSpeed(int difficulty,int size) {
        switch(difficulty) {
            case 0:return 12*size;
            case 1:return 6*size;
            case 2:return 3*size;
        }
        return 12;
    }

    private int getColor(String color, String type){
                switch(color) {
                    case "Red": return Color.rgb(244,67,54);
                    case "Green": return Color.rgb(0,150,136);
                    case "Blue": return Color.rgb(63,81,181);
                }
        return 0;
    }

    public void getDisplay(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;
        //System.out.println("H:"+deviceHeight+"W:"+deviceWidth);
    }

    public void save() {
        editor=preferences.edit();
        editor.putInt("size", snakeSize);
        editor.putInt("speed", snakeSpeed);
        editor.putInt("length", snakeLength);
        editor.putString("highScore", highScore);
        editor.apply();
    }
}