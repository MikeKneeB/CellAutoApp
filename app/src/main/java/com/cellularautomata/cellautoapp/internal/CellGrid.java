package com.cellularautomata.cellautoapp.internal;

import java.util.ArrayList;

/**
 * Created by mike on 10/03/17.
 */

public class CellGrid {


    protected int ySize;
    protected int xSize;
    protected int[][] grid;
    protected int[][] tempGrid;

    public CellGrid(int ySize, int xSize){
        this.ySize = ySize;
        this.xSize = xSize;

        this.grid = new int[ySize][xSize];
        this.tempGrid = new int[ySize][xSize];
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int valAt(int x, int y) {
        return this.grid[y][x];
    }

    public void placeValue(int x, int y, int value) {
        if (x >= xSize || y >= ySize) {
            return;
        }
        if (value == -1) {
            grid[y][x] = (1 + grid[y][x]) % 2;
            tempGrid[y][x] = (1 + tempGrid[y][x]) % 2;
        } else {
            grid[y][x] = value;
            tempGrid[y][x] = value;
        }
    }

    public void randomGrid(double threshold) {
        for(int i = 0; i != ySize; i++) {
            for(int j = 0; j != xSize; j++) {
                if(Math.random() < threshold){
                    this.grid[i][j] = 1;
                } else {
                    this.grid[i][j] = 0;
                }
            }
        }
        rebuildTemp();
    }

    public void clearGrid() {
        for(int i = 0; i != grid.length; i++) {
            for(int j = 0; j != grid[i].length; j++) {
                this.grid[i][j] = 0;
            }
        }
        rebuildTemp();
    }

    public void evolve() {

    }

    private void rebuildTemp() {
        for (int i = 0; i != ySize; i++) {
            System.arraycopy(this.grid[i], 0, this.tempGrid[i], 0, xSize);
        }
    }
}
