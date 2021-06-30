package com.intellijence.supermario;

public class Level{
    private int[][] blocks;
    private int levelWidth;
    private int levelHeight;

    public Level(int[][] blocks, int levelWidth, int levelHeight){
        this.blocks = blocks;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    //*/
    // 2d int array version
    public int getBlockID(int x, int y){
        if(x >= 0 && x < levelWidth && y >= 0 && y < levelHeight){
            return blocks[y][x];
        }
        //if not return a char to represent error
        else{
            return -1;
        }
    }
    //*/

    public int getLevelWidth(){
        return levelWidth;
    }

    public int  getLevelHeight(){
        return levelHeight;
    }

}

