package com.vikrant.hungrysnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;

public class GameData {

    int SIZE;
    int SPEED;
    int LENGTH;
    int HRGB,BRGB,TRGB;
    int DEVICE_WIDTH,DEVICE_HEIGHT;
    int MODE,DEFAULT_COLOR;
    boolean NIGHT_MODE;
    String HIGH_SCORE;
    SharedPreferences sp;
    SharedPreferences.Editor e;

    GameData(Context context) {
        sp=context.getSharedPreferences("GameData", Context.MODE_PRIVATE);
        getDisplay(context);
        SIZE=sp.getInt("SIZE", (DEVICE_WIDTH/100)*10);
        SPEED=sp.getInt("SPEED", 12);
        LENGTH=sp.getInt("LENGTH", 2);
        HRGB=sp.getInt("HRGB", Color.rgb(100,100,100));
        TRGB=sp.getInt("TRGB", Color.rgb(63,81,181));
        BRGB=sp.getInt("BRGB", Color.rgb(255,255,255));
        MODE=sp.getInt("MODE", 1);
        DEFAULT_COLOR=sp.getInt("DEFAULT_COLOR", 2);
        HIGH_SCORE=sp.getString("HIGH_SCORE", "0.0.0.0.0");
        NIGHT_MODE=sp.getBoolean("NIGHT_MODE",false);

    }
    public void getDisplay(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        DEVICE_WIDTH = displayMetrics.widthPixels;
        DEVICE_HEIGHT = displayMetrics.heightPixels;
    }

    public void save() {
        e=sp.edit();
        e.putInt("SIZE",SIZE);
        e.putInt("SPEED",SPEED);
        e.putInt("LENGTH", LENGTH);
        e.putInt("HRGB", HRGB);
        e.putInt("TRGB", TRGB);
        e.putInt("BRGB", BRGB);
        e.putInt("MODE", MODE);
        e.putInt("DEFAULT_COLOR", DEFAULT_COLOR);
        e.putString("HIGH_SCORE", HIGH_SCORE);
        e.putBoolean("NIGHT_MODE",NIGHT_MODE);
        e.apply();
    }
}