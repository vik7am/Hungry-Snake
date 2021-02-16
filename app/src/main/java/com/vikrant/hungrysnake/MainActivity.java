package com.vikrant.hungrysnake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("nightMode",false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void startGame(View view) {
        startActivity(new Intent(this, GamePanelActivity.class));
    }

    public void setting(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
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
        if (id == R.id.help) {
            showMessage("Swipe to change direction and eat eggs to make High Score");
            return true;
        }
        if (id == R.id.high_score) {
            String HIGH_SCORE= PreferenceManager.getDefaultSharedPreferences(this).getString("highScore", "0.0.0");
            String[] score=HIGH_SCORE.split("\\.");
            showMessage("Easy:          "+score[0]+"\nMedium:    "+score[1]+"\nHard:          "+score[2]);
            return true;
        }
        if (id == R.id.about) {
            showMessage("Designed and Developed by\nVikrant Kataria");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuilder.show();
    }
}