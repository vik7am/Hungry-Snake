package com.vikrant.hungrysnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import androidx.preference.PreferenceManager;

public class GameData {

    int SIZE;
    int SPEED;
    int LENGTH;
    //int HRGB,BRGB,TRGB;
    int DEVICE_WIDTH,DEVICE_HEIGHT;
    int DEFAULT_COLOR;
    int difficulty;
    boolean NIGHT_MODE;
    String HIGH_SCORE;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    int snake_head_color, snake_body_color, snake_background_color;

    GameData(Context context) {
        sp= PreferenceManager.getDefaultSharedPreferences(context);
        getDisplay(context);
        NIGHT_MODE=sp.getBoolean("NIGHT_MODE",false);
        difficulty=Integer.parseInt(sp.getString("difficulty", "1"));
        snake_head_color=getColor(sp.getString("color","Green"),"HEAD");
        snake_body_color=getColor(sp.getString("color","Green"),"BODY");
        snake_background_color=getColor(sp.getString("color","Green"),"BACKGROUND");
        SIZE=(DEVICE_WIDTH/100)*5*getSize(sp.getString("size", "Medium"));
        //SIZE=getSize(sp.getString("size", (DEVICE_WIDTH/100)*10));
        SPEED=getSpeed(sp.getString("difficulty", "1"));
        LENGTH=sp.getInt("LENGTH", 2);
        //HRGB=sp.getInt("HRGB", Color.rgb(100,100,100));
        //TRGB=sp.getInt("TRGB", Color.rgb(63,81,181));
        //BRGB=sp.getInt("BRGB", Color.rgb(255,255,255));
        //DEFAULT_COLOR=sp.getInt("DEFAULT_COLOR", 2);
        HIGH_SCORE=sp.getString("HIGH_SCORE", "0.0.0");
        Log.d("speed",""+SPEED);
        Log.d("Size",""+SIZE);
    }

    private int getSize(String size) {
        switch(size) {
            case "Small": return 1;
            case "Medium": return 2;
            case "Large": return 3;
        }
        return 0;
    }

    private int getSpeed(String difficulty) {
        switch(difficulty) {
            case "0":return 12*getSize(sp.getString("size", "Medium"));
            case "1":return 6*getSize(sp.getString("size", "Medium"));
            case "2":return 3*getSize(sp.getString("size", "Medium"));
        }
        return 12;
    }

    private int getColor(String color, String type){
        switch(type) {
            case "HEAD":
                return Color.rgb(100,100,100);
            case "BODY":
                switch(color) {
                    case "Red": return Color.rgb(244,67,54);
                    case "Green": return Color.rgb(0,150,136);
                    case "Blue": return Color.rgb(63,81,181);
                }
            case "BACKGROUND":
                if(sp.getBoolean("NIGHT_MODE",false))
                    return Color.rgb(0,0,0);
                else
                    return Color.rgb(255,255,255);
        }
        return 0;
    }

    public void getDisplay(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        DEVICE_WIDTH = displayMetrics.widthPixels;
        DEVICE_HEIGHT = displayMetrics.heightPixels;
    }

    public void save() {
        e=sp.edit();
        //e.putBoolean("night_mode", night_mode);
        e.putInt("SIZE",SIZE);
        e.putInt("SPEED",SPEED);
        e.putInt("LENGTH", LENGTH);
        //e.putInt("DEFAULT_COLOR", DEFAULT_COLOR);
        e.putString("HIGH_SCORE", HIGH_SCORE);
        //e.putBoolean("NIGHT_MODE",NIGHT_MODE);
        e.apply();
    }
}