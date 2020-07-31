package com.vikrant.hungrysnake;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GamePanelActivity extends Activity{

    GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gamePanel=new GamePanel(this);
        setContentView(gamePanel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamePanel.resumeGame();
        gamePanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        gamePanel.RUN=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        gamePanel.setVisibility(View.GONE);
    }


    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.task.cancel(false);

    }
}