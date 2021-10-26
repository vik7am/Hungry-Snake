package com.vikrant.hungrysnake;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.annotation.SuppressLint;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    boolean gameOver, gamePaused;
    int sleepTime;
    int high[];
    float startX, startY, endX, endY;
    String score[];
    Paint paint;
    GameData gameData;
    Egg egg;
    Snake snake;
    GameThread gameThread;
    Handler handler;
    Context context;
    AlertDialog.Builder alertDialogBuilder;
    String swipe;
    Point point;

    public GamePanel(Context context, Handler handler) {
        super(context);
        this.context = context;
        this.handler = handler;
        gameData = new GameData(getContext());
        egg = new Egg(gameData);
        snake = new Snake(gameData, egg);
        sleepTime = gameData.snakeSpeed * 10;
        paint = new Paint();
        paint.setAntiAlias(true);
        swipe = "RIGHT";
        point = new Point();
        getHighScore(0);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int DEVICE_WIDTH, int DEVICE_HEIGHT) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(this);
        gameThread.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(gameData.backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        snake.draw(canvas, paint);
        egg.draw(canvas, paint);
        updateScore(canvas);
        if (gamePaused)
            pauseGameScreen(canvas);
        else if (gameOver)
            gameOverScreen(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            endX = event.getX() - startX;
            endY = event.getY() - startY;
            swipe = getSwipeDirection(endX, endY);
            if (gamePaused)
                pauseGame();
            else if (gameOver)
                gameEnd();
            else
                snake.changeDirection(swipe);
        }
        return super.onTouchEvent(event);
    }

    public void pauseGame() {
        if (swipe.equals("RIGHT"))
            resumeGame();
        else if (swipe.equals("LEFT"))
            exitGame();
    }

    public void saveHighScore() {
        if (snake.length-1 > high[gameData.difficulty]) {
            high[gameData.difficulty] = snake.length - 1;
            gameData.highScore = "" + high[0] + "." + high[1] + "." + high[2];
            gameData.save();
        }
    }

    public void gameEnd() {
        saveHighScore();
        if (swipe.equals("RIGHT")) {
            egg = new Egg(gameData);
            snake = new Snake(gameData, egg);
            resumeGame();
        } else if (swipe.equals("LEFT"))
            exitGame();
    }

    public String getSwipeDirection(float x, float y) {
        if (Math.abs(x) > Math.abs(y))
            if (x > 0)
                return "RIGHT";
            else
                return "LEFT";
        else
            if (y > 0)
                return "DOWN";
            else
                return "UP";
    }

    public void exitGame() {
        handler.sendEmptyMessage(0);
    }

    public void resumeGame() {
        gameThread = new GameThread(GamePanel.this);
        gamePaused = false;
        gameOver = false;
        gameThread.start();
    }

    public void pauseGameScreen(Canvas canvas) {
        paint.setColor(gameData.headColor);
        canvas.drawRect(0, gameData.deviceHeight / 4f,
                gameData.deviceWidth + gameData.snakeSize, gameData.deviceHeight * 3 / 4f, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(gameData.backgroundColor);
        paint.setTextSize(gameData.deviceWidth / 20f);
        canvas.drawText("Game Paused", gameData.deviceWidth / 2f,
                gameData.deviceHeight / 2f - gameData.deviceWidth / 5f, paint);
        canvas.drawText("Resume->", gameData.deviceWidth / 2f, gameData.deviceHeight / 2f, paint);
        canvas.drawText("<-Exit", gameData.deviceWidth / 2f,
                gameData.deviceHeight / 2f + gameData.deviceWidth / 5f, paint);
    }

    public void gameOverScreen(Canvas canvas) {
        paint.setColor(gameData.headColor);
        canvas.drawRect(0, gameData.deviceHeight / 4f,
                gameData.deviceWidth + gameData.snakeSize, gameData.deviceHeight * 3 / 4f, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(gameData.backgroundColor);
        paint.setTextSize(gameData.deviceWidth / 20f);
        if(getHighScore(snake.length-1))
            canvas.drawText("High Score "+(snake.length -1), gameData.deviceWidth / 2f,
                gameData.deviceHeight / 2f - gameData.deviceWidth / 5f, paint);
        else
            canvas.drawText("Your Score "+(snake.length -1), gameData.deviceWidth / 2f,
                    gameData.deviceHeight / 2f - gameData.deviceWidth / 5f, paint);
        canvas.drawText("Restart->", gameData.deviceWidth / 2f, gameData.deviceHeight / 2f, paint);
        canvas.drawText("<-Exit", gameData.deviceWidth / 2f,
                gameData.deviceHeight / 2f + gameData.deviceWidth / 5f, paint);
    }


    public boolean getHighScore(int x) {
        score = gameData.highScore.split("\\.");
        high = new int[score.length];
        for (int i = 0; i < score.length; i++)
            high[i] = Integer.parseInt(score[i]);
        if (x > high[gameData.difficulty]) {
            return true;
        } else
            return false;
    }

    void updateDisplay() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void updateState() {
        if (snake.collision()) {
            gameOver = true;
            getHighScore(snake.length-1);
            return;
        }
        if (gamePaused)
            return;
        snake.move();
        snake.checkBoundsCollision();
        snake.grow();
    }

    public void updateScore(Canvas canvas) {
        paint.setColor(gameData.bodyColor);
        paint.setTextSize(gameData.snakeSize / 2f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Score " + (snake.length - 1), gameData.deviceWidth / 20f,
                gameData.deviceHeight, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("High Score " +high[gameData.difficulty], gameData.deviceWidth ,
                gameData.deviceHeight, paint);
    }
}
