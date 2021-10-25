package com.vikrant.hungrysnake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
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
    Handler handler;
    Context context;
    AlertDialog.Builder alertDialogBuilder;

    public GamePanel(Context context, Handler handler) {
        super(context);
        this.context = context;
        this.handler = handler;
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
    public void surfaceDestroyed(SurfaceHolder holder){}

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        snake.running=true;
        gameThread= new GameThread(this, context);
        gameThread.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(gameData.backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        snake.draw(canvas, paint);
        egg.draw(canvas, paint);
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
        alertDialogBuilder = new AlertDialog.Builder(context);
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("nightMode",false))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
                alertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        alertDialogBuilder.setTitle("Resume");
        alertDialogBuilder.setMessage("Do you want to resume the game");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameThread= new GameThread(GamePanel.this, context);
                pause=false;
                snake.running=true;
                gameThread.start();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.sendEmptyMessage(0);
            }
        });
        handler.sendEmptyMessage(1);
    }

    public void exitGame() {
        alertDialogBuilder = new AlertDialog.Builder(context);
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("nightMode",false))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1)
                alertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        if(getHighScore(snake.length-2))
            alertDialogBuilder.setMessage("High Score: "+(snake.length-2));
        else
            alertDialogBuilder.setMessage("Your Score: "+(snake.length-2));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.sendEmptyMessage(0);
            }
        });
        handler.sendEmptyMessage(1);
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

    void updateDisplay() {
        Canvas canvas = getHolder().lockCanvas();
        if(canvas!=null) {
            draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    void showAlertDialog() {
        if(pause)
            pauseGame();
        else
            exitGame();
    }

    public void onTimer() {
        snake.move();
        snake.checkBoundsCollision();
        snake.grow();
    }
}