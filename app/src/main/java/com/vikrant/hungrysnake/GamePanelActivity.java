package com.vikrant.hungrysnake;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import androidx.annotation.NonNull;

public class GamePanelActivity extends Activity {

    GamePanel gamePanel;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHandler();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gamePanel=new GamePanel(this, handler);
        setContentView(gamePanel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamePanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        gamePanel.pause=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        gamePanel.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.pause=true;
        gamePanel.snake.running=false;
    }

    public void setHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 0)
                    finish();
                else if(msg.what == 1)
                    gamePanel.alertDialogBuilder.show();
            }
        };
    }
}