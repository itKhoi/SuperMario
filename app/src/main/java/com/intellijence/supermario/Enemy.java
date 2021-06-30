package com.intellijence.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy {
    private Point point;
    private Rect enemyRect;

    private int eID;

    float enemyPosX = 1.0f;
    float enemyPosY = 1.0f;
    float enemyVelX = 0.0f;
    float enemyVelY = 0.0f;
    float initialX;
    float initialY;

    public Enemy(Point point, int eID){
        this.point = point;
        enemyPosX = (float)point.x;
        enemyPosY = (float)point.y;
        this.eID = eID;
    }

    public void setInitialX(float yes){
        this.initialX = yes;
    }

    public void setInitialY(float yes){
        this.initialY = yes;
    }

    public float getEnemyPosX(){return enemyPosX;}

    public float getEnemyPosY(){return enemyPosY;}

    public void setEnemyPosX(float x){ this.enemyPosX = x; }

    public void setEnemyPosY(float y){ this.enemyPosY = y; }

    public float getEnemyVelX(){return enemyVelX;}

    public float getEnemyVelY(){return enemyVelY;}

    public void setEnemyVelX(float x){ this.enemyVelX = x; }

    public void setEnemyVelY(float y){ this.enemyVelY = y; }

    public void drawEnemy(Canvas canvas, int blockWidth, int blockHeight){
        enemyRect = new Rect((int)(this.enemyPosX), (int)(this.enemyPosY), (int)(this.enemyPosX+blockWidth), (int)(this.enemyPosY+blockHeight));
        Paint enemyPaint = new Paint();
        enemyPaint.setStyle(Paint.Style.FILL);
        enemyPaint.setColor(Color.rgb(255, 0, 0));
        canvas.drawRect(enemyRect, enemyPaint);
    }

    public int giveID(){
        return this.eID;
    }
}
