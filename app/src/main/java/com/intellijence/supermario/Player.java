package com.intellijence.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player{
    private Point point;
    private Rect frameToDraw;
    private Rect playerRect;

    float playerPosX = 1.0f;
    float playerPosY = 1.0f;
    float playerVelX = 0.0f;
    float playerVelY = 0.0f;

    boolean starCheck = false;
    private int starCounter = 0;
    private int lives = 3;

    boolean lifeUp = false;


    //Use a colored block for now

    private int score;

    //variables for displaying mario's sprites

    private int dir;
    private int moveFrameIndex;

    public Player(Point point){
        this.point = point;
        playerPosX = (float)point.x;
        playerPosY = (float)point.y;
    }

    public int getLives(){return this.lives;}

    public void loseLife(){
        this.lives -= 1;

    }

    public void resetScore(){this.score = 0;}

    public void resetLives(){this.lives=3;}

    public void getLife(){
        lifeUp = true;
    }

    public void getKill(){
        lifeUp = false;
    }

    public void gotStar(){
        starCheck = true;
    }

    public void endStar(){
        if(starCounter == 600){
            this.starCheck = false;
        }
        else{
            this.starCounter += 1;
        }
    }

    /*** getters and setters for position ***/
    //public float getPlayerX(){ return point.x; }

    public float getPlayerPosX(){return playerPosX;}

    public float getPlayerPosY(){return playerPosY;}

    public void setPlayerPosX(float x){ this.playerPosX = x; }

    public void setPlayerPosY(float y){ this.playerPosY = y; }

    /*** getters and setters for velocity ***/
    public float getPlayerVelX(){return playerVelX;}
    
    public float getPlayerVelY(){return playerVelY;}

    public void setPlayerVelX(float x){ this.playerVelX = x; }

    public void setPlayerVelY(float y){ this.playerVelY = y; }

    public void incrementScore(int add){
        this.score += add;
        if(this.score<0){
            this.score = 0;
        }
    }

    public int getScore(){ return score; }

    public void setDir(int newDir){
        /*
        0 - right
        1 - left
         */
        this.dir = newDir;
    }

    public void drawPlayer(Canvas canvas, Bitmap currBitMap, int blockWidth, int blockHeight){
        playerRect = new Rect((int)(this.playerPosX), (int)(this.playerPosY), (int)(this.playerPosX+blockWidth), (int)(this.playerPosY+blockHeight));
        frameToDraw = new Rect(0, 0, blockWidth, blockHeight);
        Paint playerPaint = new Paint();
        playerPaint.setStyle(Paint.Style.FILL);
        playerPaint.setColor(Color.rgb(255, 255, 255));
        //canvas.drawRect(playerRect, playerPaint);
        canvas.drawBitmap(currBitMap,
                frameToDraw,
                playerRect, playerPaint);
    }
}