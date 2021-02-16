package com.vikrant.hungrysnake;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.preference.PreferenceManager;
import android.annotation.SuppressLint;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    boolean pause;
    int sleepTime;
    int high[];
    float SX1,SX2,SX3,SY1,SY2,SY3;
    String score[];
    Paint paint;
    GameData gameData;
    Egg egg;
    Snake snake;
    GameThread gameThread;
    GamePanelActivity activity;
    AlertDialog.Builder alertDialogBuilder;

    public GamePanel(GamePanelActivity activity) {
        super(activity.getApplicationContext());
        this.activity=activity;
        gameData = new GameData(getContext());
        egg=new Egg(gameData);
        snake=new Snake(gameData,egg);
        sleepTime=gameData.snakeSpeed*10;
        paint=new Paint();
        pause=false;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int DEVICE_WIDTH, int DEVICE_HEIGHT){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        snake.running=true;
        gameThread= new GameThread(activity);
        gameThread.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(gameData.backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(gameData.headColor);
        canvas.drawRect(snake.snakeX.get(0), snake.snakeY.get(0), snake.snakeX.get(0) + snake.size, snake.snakeY.get(0) + snake.size, paint);
        paint.setColor(gameData.bodyColor);
        for (int i = 1; i < snake.length; i++)
            canvas.drawRect(snake.snakeX.get(i), snake.snakeY.get(i), snake.snakeX.get(i) + snake.size, snake.snakeY.get(i) + snake.size, paint);
        paint.setColor(gameData.bodyColor);
        canvas.drawRect(egg.eggX, egg.eggY, egg.eggX + snake.size, egg.eggY + snake.size, paint);
        paint.setColor(Color.BLACK);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            SX1 = event.getX();
            SY1 = event.getY();
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            SX2 = event.getX();
            SY2 = event.getY();
            SX3=SX2-SX1;
            SY3=SY2-SY1;
            snake.changeDirection(SX3, SY3);
        }
        return super.onTouchEvent(event);
    }

    public void pauseGame() {
        alertDialogBuilder = new AlertDialog.Builder(activity);
        if(PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("nightMode",false))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
                alertDialogBuilder = new AlertDialog.Builder(activity, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        alertDialogBuilder.setTitle("Resume");
        alertDialogBuilder.setMessage("Do you want to resume the game");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameThread= new GameThread(activity);
                pause=false;
                snake.running=true;
                gameThread.start();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        alertDialogBuilder.show();
    }

    public void exitGame() {
        alertDialogBuilder = new AlertDialog.Builder(activity);
        if(PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("nightMode",false))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
                alertDialogBuilder = new AlertDialog.Builder(activity, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        if(getHighScore(snake.length-2))
            alertDialogBuilder.setMessage("High Score: "+(snake.length-2));
        else
            alertDialogBuilder.setMessage("Your Score: "+(snake.length-2));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        alertDialogBuilder.show();
    }

    public boolean getHighScore(int x) {
        score=gameData.highScore.split("\\.");
        high=new int[score.length];
        for(int i=0;i<score.length;i++)
            high[i]=Integer.parseInt(score[i]);
        if(x>high[gameData.difficulty]) {
            high[gameData.difficulty]=x;
            gameData.highScore=""+high[0]+"."+high[1]+"."+high[2];
            gameData.save();
            return true;
        }
        else
            return false;
    }
}