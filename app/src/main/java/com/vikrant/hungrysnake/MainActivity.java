package com.vikrant.hungrysnake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSharedPreferences("GameData", Context.MODE_PRIVATE).getBoolean("NIGHT_MODE",true))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        startActivity(new Intent(this, GamePanelActivity.class));
    }

    public void setting(View view) {
        startActivity(new Intent(this, Setting.class));
    }

    public void exitGame(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (id == R.id.help) {
            alertDialogBuilder.setMessage(R.string.help);
            alertDialogBuilder.setPositiveButton("OK", this);
            alertDialogBuilder.show();
            return true;
        }
        if (id == R.id.high_score) {
            String HIGH_SCORE=getSharedPreferences("GameData", Context.MODE_PRIVATE).getString("HIGH_SCORE", "0.0.0.0.0");
            String[] score=HIGH_SCORE.split("\\.");
            String message="Easy:          "+score[0]+"\nMedium:    "+score[1]+"\nHard:          "+score[2]+"\nVery Hard: "+score[3]+"\nCustom:     "+score[4];
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("OK", this);
            alertDialogBuilder.show();
            return true;
        }
        if (id == R.id.about) {
            alertDialogBuilder.setMessage(R.string.about);
            alertDialogBuilder.setPositiveButton("OK", this);
            alertDialogBuilder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
}