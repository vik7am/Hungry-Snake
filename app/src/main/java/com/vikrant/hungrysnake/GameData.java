package com.vikrant.hungrysnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;

public class GameData {

    int SIZE;
    int SPEED;
    int LENGTH;
    int HR,HG,HB;
    int TR,TG,TB;
    int BR,BG,BB;
    int HRGB,BRGB,TRGB;
    int DEVICE_WIDTH,DEVICE_HEIGHT;
    int MODE,DEFAULT_COLOR;
    String HIGH_SCORE;
    boolean SOUND,DEVICE;
    SharedPreferences sp;

    GameData(Context context)
    {
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
        SOUND=sp.getBoolean("SOUND", true);
        DEVICE=sp.getBoolean("DEVICE", true);

    }
    public void getDisplay(Context context)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        DEVICE_WIDTH = displayMetrics.widthPixels;
        DEVICE_HEIGHT = displayMetrics.heightPixels;
    }

    public void save(Context context)
    {
        SharedPreferences.Editor e=sp.edit();
        e.putInt("SIZE",SIZE);
        e.putInt("SPEED",SPEED);
        e.putInt("LENGTH", LENGTH);
        e.putBoolean("SOUND", SOUND);
        e.putInt("HRGB", HRGB);
        e.putInt("TRGB", TRGB);
        e.putInt("BRGB", BRGB);
        e.putInt("MODE", MODE);
        e.putInt("DEFAULT_COLOR", DEFAULT_COLOR);
        e.putString("HIGH_SCORE", HIGH_SCORE);
        e.commit();
    }
}
