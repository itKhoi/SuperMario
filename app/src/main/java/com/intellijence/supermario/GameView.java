package com.intellijence.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView
        implements SurfaceHolder.Callback, GestureDetector.OnGestureListener {
    //Thread contains processes that update GUI
    private MainThread thread;
    private GestureDetectorCompat gestureDetectorCompat;

    /*** player properties ***/
    private Player player;
    private Enemy enemyOne;
    private Enemy enemyTwo;
    private Rect playerRect;

    Bitmap currMario;
    Bitmap marioIdleRight;
    Bitmap marioIdleLeft;
    Bitmap marioJumpRight;
    Bitmap marioJumpLeft;

    int playerPixelX;
    int playerPixelY;

    private float newX;
    private float newY;

    private int newCoordX;
    private int newCoordY;

    private float changeInX;
    private float changeInY;

    /*** level properties ***/
    //level 1
    private int[][] levelOneObs = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,5,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,2,0,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,2,0,2,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,2,0,0,0,0,0,0,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,3,0,0},
                                   {0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,3,0,0},
                                   {0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,3,0,0},
                                   {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0},
                                   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    private int L1Width;
    private int L1Height;

    private Point levelOneStart;
    private Point levelOneEnemy1;
    private Point levelOneEnemy2;

    private int[][] levelTwoObs = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,2,0,2,0,2,0,2,0,0,0,0,0,0,1,1,0,2,0,0,2,0,0,2,2,2,2,0,0,2,2,2,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,2,0,0,2,0,0,0,2,2,0,0,0,2,2,2,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,1,2,1,1,0,2,2,2,2,0,0,0,2,2,0,0,0,0,2,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,0,1,1,1,1,1,1,0,0,0,1,0,1,1,0,2,0,0,2,0,0,0,2,2,0,0,0,0,0,0,0,1},
                                   {1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,1,1,0,2,0,0,2,0,0,2,2,2,2,0,0,0,4,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,1,1,1,1,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
                                   {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,2,0,2,0,0,0,0,1,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,1,1,1,1,1,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1},
                                   {0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1},
                                   {0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    private int L2Width;
    private int L2Height;

    private Point levelTwoStart;

    private int[][] levelThreeObs = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,1},
                                    {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,5,2,1,0,0,0,1},
                                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
                                    {1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
                                    {1,2,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
                                    {1,2,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
                                    {1,2,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,3,1,0,1},
                                    {1,2,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,0,1},
                                    {1,2,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,0,1},
                                    {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,0,1},
                                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    private int L3Width;
    private int L3Height;

    private ArrayList<int[][]> levels = new ArrayList<>();
    private int currLevel;

    /*** display properties ***/
    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    private final int screenWidth = 1920;
    private final int screenHeight = 1080;

    private final int blockWidth = 40;
    private final int blockHeight = 40;

    private int visibleBlocksX = screenWidth / blockWidth;
    private int visibleBlocksY = screenHeight / blockHeight;

    /*** camera properties ***/
    private float cameraPosX = 0.0f;
    private float cameraPosY = 0.0f;

    private float gravity = 20f;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        gestureDetectorCompat = new GestureDetectorCompat(context, this);

        /* instantiate other variables here */
        //initialize levels
        ///level 1
        L1Width = levelOneObs[0].length;
        L1Height = levelOneObs.length;

        levelOneStart = new Point(3*blockWidth, 2*blockHeight);
        levelOneEnemy1 = new Point(24*blockWidth, 22*blockHeight);
        levelOneEnemy2 = new Point(27*blockWidth, 19*blockHeight);
        levels.add(levelOneObs);


        ///level 2
        L2Width = levelTwoObs[0].length;
        L2Height = levelTwoObs.length;

        levelTwoStart = new Point(4*blockWidth, 2*blockHeight);
        levels.add(levelTwoObs);


        ///level 3

        levels.add(levelThreeObs);
        L3Width = levelThreeObs[0].length;
        L3Height = levelThreeObs.length;
        currLevel = 0;

        //initialize player
        player = new Player(levelOneStart);
        marioIdleRight = BitmapFactory.decodeResource(this.getResources(), R.drawable.mario_idle_right);
        marioIdleLeft = BitmapFactory.decodeResource(this.getResources(), R.drawable.mario_idle_left);
        marioJumpRight = BitmapFactory.decodeResource(this.getResources(), R.drawable.mario_jump_right);
        marioJumpLeft = BitmapFactory.decodeResource(this.getResources(), R.drawable.mario_jump_left);


        currMario = marioIdleRight;

        //initialize one enemy (goomba)
        enemyOne = new Enemy(levelOneEnemy1, 0);
        enemyOne.setEnemyVelX(-2);
        enemyTwo = new Enemy(levelOneEnemy2, 1);
        enemyTwo.setEnemyVelY(-0.5f);
        enemyTwo.setInitialX(24*blockWidth);
        enemyTwo.setInitialY(levelOneEnemy2.y);

        setFocusable(true);
    }

    public void drawLevel(int ID){
        switch(ID){
            case 0: {
                player.setPlayerPosX((float) levelOneStart.x);
                player.setPlayerPosY((float) levelOneStart.y);
                enemyOne.setEnemyPosX(levelOneEnemy1.x);
                enemyOne.setEnemyPosY(levelOneEnemy1.y);
                enemyTwo.setInitialX(24*blockWidth);
                enemyTwo.setInitialY(levelOneEnemy2.y);
                enemyTwo.setInitialX(24*blockWidth);
                enemyTwo.setInitialY(levelOneEnemy2.y);
            }
            break;

            case 1: {
                player.setPlayerPosX((float) levelTwoStart.x);
                player.setPlayerPosY((float) levelTwoStart.y);
                enemyOne.setEnemyPosX((float) 4*blockWidth);
                enemyOne.setEnemyPosY((float) 25* blockHeight);
                enemyTwo.setInitialX(17*blockWidth);
                enemyTwo.setInitialY(20*blockHeight);
                enemyTwo.setEnemyPosX((float) 17* blockWidth);
                enemyTwo.setEnemyPosY((float) 20* blockHeight);
            }
            break;
            case 2:{
                player.setPlayerPosX((float) levelTwoStart.x);
                player.setPlayerPosY((float) levelTwoStart.y);
                enemyTwo.setInitialX(40*blockWidth);
                enemyTwo.setInitialY(16*blockHeight);
                enemyTwo.setEnemyPosX((float)40*blockWidth);
                enemyTwo.setEnemyPosY((float)16*blockHeight);

            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        //thread starts running when a surface is created
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            //try tries to run the code inside the first bracket
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                //if code inside try cannot be executed, while loop goes to catch
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    //flags that the screen has been touched
    public boolean onTouchEvent(MotionEvent event) {
        if (this.gestureDetectorCompat.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /*** Override all methods of GestureDetector.onGestureListener ***/
    @Override
    public boolean onDown(MotionEvent event) {
        /*/
        //right
        if ((int) event.getX() > screenWidth / 2) {
            //move player right
            changeInX = 60f;
            System.out.println("changeInX = " + changeInX);
            //player.setPlayerPosX(player.getPlayerPosX() + changeInX);
        }
        //left
        if ((int) event.getX() < screenWidth / 2) {
            //move player left
            changeInX = -60f;
            System.out.println("changeInX = " + changeInX);
            //player.setPlayerPosX(player.getPlayerPosX() + changeInX);
        }
        //*/
        return true;
    }

    @Override
    //Detects swipes
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //*/
        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                //swipe left
                //move player to the left
                changeInX = -40f;
            }else
            {
                //swipe right
                //move player to the right
                changeInX = 40f;
            }
        }

        /*/
        if ((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
            if (deltaY > 0) {
                //swipe up
                //make player jump
                //make player character jump
                playerPixelX = (int)(player.getPlayerPosX()/blockWidth);
                playerPixelY = (int)(player.getPlayerPosY()/blockWidth);

                if(levels.get(currLevel)[playerPixelY+1][playerPixelX] == 1){
                    changeInY = -60f;
                }
                return true;
            }
        }
        //*/

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        //right
        if ((int) event.getX() > screenWidth / 2) {
            //move player right
            changeInX = 60f;
            System.out.println("changeInX = " + changeInX);
            player.setPlayerPosX(player.getPlayerPosX() + changeInX);
        }
        if ((int) event.getX() < screenWidth / 2) {
            //move player left
            changeInX = -60f;
            System.out.println("changeInX = " + changeInX);
            player.setPlayerPosX(player.getPlayerPosX() + changeInX);
        }
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //*/
        //make player character jump
        playerPixelX = (int)(player.getPlayerPosX()/blockWidth);
        playerPixelY = (int)(player.getPlayerPosY()/blockWidth);

        if(levels.get(currLevel)[playerPixelY+1][playerPixelX] == 1){
            if(currMario.equals(marioIdleRight)){
                currMario = marioJumpRight;
            }
            else{
                currMario = marioJumpLeft;
            }
            changeInY = -80f;
        }
        //*/
        return true;
    }
    public boolean checkEnemyCollision(Enemy enemy){
        if(enemy.giveID()==1){
            return false;
        }
        if(enemy.getEnemyVelX()<0 && levels.get(currLevel)[(int)(enemy.getEnemyPosY()/blockHeight)][(int)(enemy.getEnemyPosX()/blockWidth )] !=0 ){
            return true;
        }
        if(enemy.getEnemyVelX()>0 && levels.get(currLevel)[(int)(enemy.getEnemyPosY()/blockHeight)][(int)(enemy.getEnemyPosX()/blockWidth + 1)] !=0 ){
            return true;
        }
        if(enemy.getEnemyVelX()<0 && levels.get(currLevel)[(int)(enemy.getEnemyPosY()/blockHeight +1)][(int)(enemy.getEnemyPosX()/blockWidth)] == 0){
            return true;
        }
        if(enemy.getEnemyVelX()>0 && levels.get(currLevel)[(int)(enemy.getEnemyPosY()/blockHeight +1)][(int)(enemy.getEnemyPosX()/blockWidth +1)] == 0){
            return true;
        }


        return false;
    }
    public boolean checkAlive(Enemy enemy){
        if((int)(enemy.getEnemyPosY()/blockHeight)==(int)(player.getPlayerPosY()/blockHeight) && (int)(enemy.getEnemyPosX()/blockWidth) == (int)(player.getPlayerPosX()/blockWidth)){
            return false;
        }
        return true;
    }
    public boolean checkCollision() {

        /*/
        if (newCoordX < levels.get(currLevel)[0].length &&
            newCoordX > 0 &&
            levels.get(currLevel)[(int) (player.getPlayerPosY() / blockHeight)][newCoordX] != 1 &&
            newCoordY < levels.get(currLevel).length &&
            newCoordY > 0 &&
            levels.get(currLevel)[newCoordY][newCoordX] != 1)
        //*/
        /*/
        if(newX >= 0 && levels.get(currLevel)[(int)(player.getPlayerPosY()/blockHeight)][newCoordX] != 1 && levels.get(currLevel)[(int)((player.getPlayerPosY()+1)/blockHeight)][newCoordX] != 1)
        {
            System.out.println("newCoordX (in checkCollision) = " + newCoordX);
            return true;
        }
        //*/
        playerPixelX = (int)(player.getPlayerPosX()/blockWidth);
        playerPixelY = (int)(player.getPlayerPosY()/blockWidth);

        if(changeInX != 0){
            //*/
            if(newX >= 0 && levels.get(currLevel)[(int)(player.getPlayerPosY()/blockHeight)][newCoordX] != 1)
            //*/
            {
                return true;
            }
        }
        if(changeInY != 0){
            //*/
            for(int i = playerPixelY; i > newCoordY-1; i--){
                if(newY >= 0 && levels.get(currLevel)[i][(int)(player.getPlayerPosX()/blockWidth)] == 1){
                    return false;
                }
            }
            return true;
            //*/
            /*/
            if(newY >= 0 && levels.get(currLevel)[newCoordY][(int)(player.getPlayerPosX()/blockWidth)] != 1)
            {
                return true;
            }
            //*/
        }

        return false;
    }

    //Updates vars involved in GUI
    public void update() {

        //update individual parts of the GUI

        /*** update player location ***/

        ///gravity effects - move player down
        ////get player pixel position based on their actual position
        playerPixelX = (int)(player.getPlayerPosX()/blockWidth);
        playerPixelY = (int)(player.getPlayerPosY()/blockWidth);

        gravity = 20f;

        if(changeInY < 0){
            newY = player.getPlayerPosY() + changeInY;
            if(newY < 0){ newY = 0; }
            if(newY > screenHeight){ newY = screenHeight; }

            newCoordY = (int)(newY/blockHeight);
            
            if(checkCollision()) {
                player.setPlayerPosY(newY);
            }
            changeInY += gravity/2;
        }
        else {
            if(playerPixelY+1 < 0){ playerPixelY = -1;}
            if(playerPixelY+1 > levels.get(currLevel).length) { playerPixelY = levels.get(currLevel).length-1; }

            if(levels.get(currLevel)[playerPixelY+1][playerPixelX] == 1){
                gravity = 0;
            }
            /*/
            if(checkCollision()){
                player.setPlayerPosY(player.getPlayerPosY() + gravity);
            }
            //*/
            player.setPlayerPosY(player.getPlayerPosY() + gravity);

        }

        /*/
        if(currMario.equals(marioJumpRight)){
            currMario = marioIdleRight;
        }
        else{
            currMario = marioIdleLeft;
        }
        //*/

        ///move player horizontally
        //*/
        if(changeInX > 0){ //trying to move right
            newX = player.getPlayerPosX() + changeInX;
            System.out.println("newX (RIGHT) = " + newX);
            newCoordX = (int)(newX/blockWidth);
            System.out.println("newCoordX (RIGHT) = " + newCoordX);
            newCoordY = (int)player.getPlayerPosY();
            /*/
            if(newX < levels.get(currLevel)[0].length*blockWidth && levels.get(currLevel)[(int)(player.getPlayerPosY()/blockHeight)][newCoordX] != 1 && levels.get(currLevel)[(int)((player.getPlayerPosY()+1)/blockHeight)][newCoordX] != 1) {
                player.setPlayerPosX(newX);
            }
            //*/
            if(checkCollision()){
                player.setPlayerPosX(newX);
                currMario = marioIdleRight;
                newX = 0;
            }
            /*/
            if(newX < levels.get(currLevel)[0].length*blockWidth) {
                //System.out.println("RIGHT");

                int rightBlockValue = levels.get(currLevel)[(int)(player.getPlayerPosY()/blockHeight)][newCoordX];
                switch(rightBlockValue) {
                    case 0:
                        player.setPlayerPosX(newX);
                        break;
                    case 1:
                        player.setPlayerPosX((newCoordX-1) * blockWidth);
                        break;

                    default:
                        break;
                }

                //player.setPlayerPosX(newX);
            }
            //*/
        }
        if(changeInX < 0){ //trying to move left
            newX = player.getPlayerPosX() + changeInX;
            System.out.println("newX (LEFT) = " + newX);
            newCoordX = (int)(newX/blockWidth);
            System.out.println("newCoordX (LEFT) = " + newCoordX);
            newCoordY = (int)player.getPlayerPosY();
            /*/
            if(newX >= 0 && levels.get(currLevel)[(int)(player.getPlayerPosY()/blockHeight)][newCoordX] != 1 && levels.get(currLevel)[(int)((player.getPlayerPosY()+1)/blockHeight)][newCoordX] != 1){
                //System.out.println("LEFT");
                player.setPlayerPosX(newX);
            }
            //*/
            //*/
            if(checkCollision()){
                player.setPlayerPosX(newX);
                currMario = marioIdleLeft;
                newX = 0;
            }
            //*/
        }
        changeInX = 0;
        //*/

        ///check for coins
        if(levels.get(currLevel)[playerPixelY][playerPixelX] == 2){
            player.incrementScore(200);
            levels.get(currLevel)[playerPixelY][playerPixelX] = 0;
        }

        ///check for flag touch
        if(levels.get(currLevel)[playerPixelY][playerPixelX] == 3){
            System.out.println("Flag touched!");
            if(currLevel+1 < levels.size()){
                System.out.println("currLevel = " + currLevel);
                currLevel++;
            }
            else{
                currLevel = 0;
            }
            drawLevel(currLevel);
        }

        //check for star powerup

        if(levels.get(currLevel)[playerPixelY][playerPixelX] == 4){
            player.gotStar();
            player.incrementScore(1000);
            levels.get(currLevel)[playerPixelY][playerPixelX] = 0;
        }

        if(levels.get(currLevel)[playerPixelY][playerPixelX] == 5){
            player.getLife();
            player.incrementScore(1000);
            levels.get(currLevel)[playerPixelY][playerPixelX] = 0;
        }

        /* if no collision
            player.setLocationX(newLocationX);
            player.setLocationY(newLocationY);
         */

        /*** update enemy locations ***/


        if(checkEnemyCollision(enemyOne)){
            enemyOne.setEnemyVelX(enemyOne.getEnemyVelX() * -1);
        }
        enemyOne.setEnemyPosX(enemyOne.getEnemyPosX() + enemyOne.getEnemyVelX());
        if(!checkAlive(enemyOne)){
            if(player.starCheck){
                System.out.println("You are star powered");
            }
            else {
                if(player.lifeUp){
                    player.getKill();
                    player.setPlayerPosX(((player.getPlayerPosX()/blockWidth) - 2)*blockWidth);
                }
                else {
                    player.setPlayerPosX((float) levelOneStart.x);
                    player.setPlayerPosY((float) levelOneStart.y);
                    player.incrementScore(-500);
                    player.loseLife();
                    if(player.getLives() == 0){
                        player.resetLives();
                        player.resetScore();
                        currLevel = 0;
                    }
                }
            }
        }
        if((enemyTwo.getEnemyPosY()/blockHeight) == enemyTwo.initialY/blockHeight-1 || (enemyTwo.getEnemyPosY()/blockHeight) > (enemyTwo.initialY/blockHeight)){
            enemyTwo.setEnemyVelY(enemyTwo.getEnemyVelY() * -1);
        }
        enemyTwo.setEnemyPosY(enemyTwo.getEnemyPosY() + enemyTwo.getEnemyVelY());
        if(!checkAlive(enemyTwo)){
            if(player.starCheck){
                System.out.println("You are star powered");
            }
            else {
                if(player.lifeUp){
                    player.getKill();
                    player.setPlayerPosX(((player.getPlayerPosX()/blockWidth) - 2)*blockWidth);
                }
                else {
                    player.setPlayerPosX((float) levelOneStart.x);
                    player.setPlayerPosY((float) levelOneStart.y);
                    player.incrementScore(-500);
                    player.incrementScore(-500);
                    player.loseLife();
                    if(player.getLives() == 0){
                        player.resetLives();
                        player.resetScore();
                        currLevel = 0;
                    }
                }
            }
        }
        if(player.starCheck){
            player.endStar();
        }
    }

    @Override
    //draws the game onto a canvas
    public void draw(Canvas canvas) {
        //draw canvas
        super.draw(canvas);
        canvas.drawColor(Color.rgb(240, 234, 214));

        /* draw each game objects */
        //camera variables
        cameraPosX = player.getPlayerPosX();
        System.out.println("Camera Pos X = " + cameraPosX);

        cameraPosY = player.getPlayerPosY();
        System.out.println("Camera Pos Y = " + cameraPosY);

        float offsetX = cameraPosX - (float) visibleBlocksX / 2.0f;
        System.out.println("Offset = " + offsetX);

        float offsetY = cameraPosY - (float) visibleBlocksY / 2.0f;
        System.out.println("Offset = " + offsetX);


        if (offsetX < 0) offsetX = 0;
        if (offsetY < 0) offsetY = 0;
        if (offsetX > L1Width - visibleBlocksX)
            offsetX = L1Width - visibleBlocksX;
        if (offsetY > L1Height - visibleBlocksY)
            offsetY = L1Height - visibleBlocksY;

        System.out.println("Offset = " + offsetX);
        System.out.println("Offset = " + offsetX);

        //block offset for rendering movement
        float blockOffsetX = (offsetX - (int) offsetX) * blockWidth;
        System.out.println("Block Offset = " + blockOffsetX);
        float blockOffsetY = (offsetY - (int) offsetY) * blockHeight;
        System.out.println("Block Offset = " + blockOffsetX);

        //draw level
        //*/
        //draw contents of level based on the char value contained in the specific coordinate

        //*/
        for(int i=0; i<levels.get(currLevel).length; i++) {
            for (int j = 0; j < levels.get(currLevel)[i].length; j++) {
        //*/
        /*/
        for(int i=-1; i< visibleBlocksY + 1; i++){
            for(int j = -1; j < visibleBlocksX + 1; j++){
        //*/
                //int currBlockID = levelOneObs[i][j];
                //int currBlockID = levelOneObs[(int)(i+offsetX)][(int)(j+offsetY)];
                int currBlockID = levels.get(currLevel)[(int)(i+offsetX)][(int)(j+offsetY)];
                switch (currBlockID) {
                    case 1: { //solid block
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(0,0,0));
                        //RectF rect = new RectF(j * blockWidth, i * blockHeight, (j + 1) * blockWidth, (i + 1) * blockHeight);
                        RectF rect = new RectF(j * blockWidth - blockOffsetX, i * blockHeight - blockOffsetY, (j + 1) * blockWidth - blockOffsetX, (i + 1) * blockHeight - blockOffsetY);
                        canvas.drawRect(rect, paint);
                    }
                    break;

                    case 2: { //coin
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(251, 208, 0));
                        //RectF rect = new RectF(j * blockWidth, i * blockHeight, (j + 1) * blockWidth, (i + 1) * blockHeight);
                        RectF rect = new RectF(j * blockWidth - blockOffsetX, i * blockHeight - blockOffsetY, (j + 1) * blockWidth - blockOffsetX, (i + 1) * blockHeight - blockOffsetY);
                        canvas.drawRect(rect, paint);
                    }
                    break;

                    case 3: { //flag
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(67, 176, 71));
                        //RectF rect = new RectF(j * blockWidth, i * blockHeight, (j + 1) * blockWidth, (i + 1) * blockHeight);
                        RectF rect = new RectF(j * blockWidth - blockOffsetX, i * blockHeight - blockOffsetY, (j + 1) * blockWidth - blockOffsetX, (i + 1) * blockHeight - blockOffsetY);
                        canvas.drawRect(rect, paint);
                    }
                    break;

                    case 4:{/* star man*/
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(255, 100, 255));
                        //RectF rect = new RectF(j * blockWidth, i * blockHeight, (j + 1) * blockWidth, (i + 1) * blockHeight);
                        RectF rect = new RectF(j * blockWidth - blockOffsetX, i * blockHeight - blockOffsetY, (j + 1) * blockWidth - blockOffsetX, (i + 1) * blockHeight - blockOffsetY);
                        canvas.drawRect(rect, paint);
                    }
                    break;

                    case 5:{/* star man*/
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(255, 255, 0));
                        //RectF rect = new RectF(j * blockWidth, i * blockHeight, (j + 1) * blockWidth, (i + 1) * blockHeight);
                        RectF rect = new RectF(j * blockWidth - blockOffsetX, i * blockHeight - blockOffsetY, (j + 1) * blockWidth - blockOffsetX, (i + 1) * blockHeight - blockOffsetY);
                        canvas.drawRect(rect, paint);
                    }
                    break;

                    default: { //sky
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(135, 206, 250));
                        //RectF rect = new RectF(j * blockWidth, i * blockHeight, (j + 1) * blockWidth, (i + 1) * blockHeight);
                        RectF rect = new RectF(j * blockWidth - blockOffsetX, i * blockHeight - blockOffsetY, (j + 1) * blockWidth - blockOffsetX, (i + 1) * blockHeight - blockOffsetY);
                        canvas.drawRect(rect, paint);
                    }
                    break;
                }
            }
        }

        //draw player

        player.drawPlayer(canvas, currMario, blockWidth, blockHeight);
        enemyOne.drawEnemy(canvas, blockWidth, blockHeight);
        enemyTwo.drawEnemy(canvas, blockWidth, blockHeight);

        Paint textPaint = new Paint();
        textPaint.setTextSize(45);
        textPaint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText("Score: " + player.getScore(), 1080f, 100f, textPaint);

        Paint live = new Paint();
        live.setTextSize(45);
        live.setColor(Color.rgb(255, 255, 255));
        canvas.drawText("Lives: " + player.getLives(), 1080f, 160f, live);

        if(player.starCheck) {
            Paint powerUp = new Paint();
            powerUp.setTextSize(45);
            powerUp.setColor(Color.rgb(255, 255, 255));
            canvas.drawText("Star Power", 500f, 100f, powerUp);
        }

        if(player.lifeUp) {
            Paint life = new Paint();
            life.setTextSize(45);
            life.setColor(Color.rgb(255, 255, 255));
            canvas.drawText("Life Up", 500f, 150f, life);
        }
    }
}