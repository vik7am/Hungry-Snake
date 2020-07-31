package com.vikrant.hungrysnake;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    Paint paint;
    Snake snake;
    GameData gameData;
    MyAsyncTask task;
    boolean RUN;
    float SX1,SX2,SX3,SY1,SY2,SY3;
    int DEVICE_WIDTH,DEVICE_HEIGHT,WIDTH,HEIGHT,SLEEP_TIME;
    Egg egg;

    public GamePanel(Context context)
    {
        super(context);
        gameData = new GameData(getContext());
        SLEEP_TIME=gameData.SPEED*10;
        paint=new Paint();
        egg=new Egg(gameData.SIZE);
        snake=new Snake(gameData,egg);
        getHolder().addCallback(this);
    }

    public void resumeGame()
    {
        RUN=true;
        task=new MyAsyncTask(getContext());
        System.out.println("Game resumed");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int DEVICE_WIDTH, int DEVICE_HEIGHT){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        RUN=false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        System.out.println("Surface Created");
        DEVICE_WIDTH=gameData.DEVICE_WIDTH; DEVICE_HEIGHT=gameData.DEVICE_HEIGHT;
        WIDTH=DEVICE_WIDTH-DEVICE_WIDTH%snake.SIZE;HEIGHT=DEVICE_HEIGHT-DEVICE_HEIGHT%snake.SIZE;
        snake.setWidthHeight(WIDTH, HEIGHT);
        RUN=true;
        task.execute();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(gameData.BRGB);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(gameData.HRGB);
        canvas.drawRect(snake.X.get(0), snake.Y.get(0), snake.X.get(0) + snake.SIZE, snake.Y.get(0) + snake.SIZE, paint);
        paint.setColor(gameData.TRGB);
        for (int i = 1; i < snake.LENGTH; i++)
            canvas.drawRect(snake.X.get(i), snake.Y.get(i), snake.X.get(i) + snake.SIZE, snake.Y.get(i) + snake.SIZE, paint);
        paint.setColor(gameData.TRGB);
        canvas.drawRect(egg.X, egg.Y, egg.X + snake.SIZE, egg.Y + snake.SIZE, paint);
        paint.setColor(Color.BLACK);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            SX1 = event.getX();
            SY1 = event.getY();
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            SX2 = event.getX();
            SY2 = event.getY();
            SX3=SX2-SX1;
            SY3=SY2-SY1;
            snake.changeDirection(SX3, SY3);
        }
        return super.onTouchEvent(event);
    }



    class MyAsyncTask extends AsyncTask<Void,Void,Void> implements android.content.DialogInterface.OnClickListener
    {
        Context context;
        public MyAsyncTask(Context context)
        {
            this.context=context;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            long st,et,d;
            while(RUN)
            {
                st=System.currentTimeMillis();
                snake.move(true);
                if(snake.collision())
                    break;
                Canvas canvas = getHolder().lockCanvas();
                if(canvas!=null)
                {
                    draw(canvas);
                    getHolder().unlockCanvasAndPost(canvas);
                }
                et=System.currentTimeMillis();
                d=et-st;
                //System.out.println("Time: "+d);
                try
                {
                    if(d<SLEEP_TIME)
                        Thread.sleep(SLEEP_TIME-d);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCancelled())
                    break;
            }
            System.out.println("Thread is Exiting");
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            if(getHighScore(snake.LENGTH-2))
                alertDialogBuilder.setMessage("High Score: "+(snake.LENGTH-2));
            else
                alertDialogBuilder.setMessage("Your Score: "+(snake.LENGTH-2));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", this);
            alertDialogBuilder.show();
        }
        public boolean getHighScore(int x)
        {
            String[] score=gameData.HIGH_SCORE.split("\\.");
            int[] high=new int[score.length];
            for(int i=0;i<score.length;i++)
            {
                //System.out.println(score[i]);
                high[i]=Integer.parseInt(score[i]);
            }

            if(x>high[gameData.MODE])
            {
                high[gameData.MODE]=x;
                gameData.HIGH_SCORE=""+high[0]+"."+high[1]+"."+high[2]+"."+high[3]+"."+high[4];
                gameData.save(context);
                return true;
            }
            else
                return false;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            ((Activity)context).finish();
        }
    }
}