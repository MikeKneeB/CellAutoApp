package com.cellularautomata.cellautoapp.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by mike on 10/03/17.
 */

public class LifeGrid extends CellGrid {

    protected HashSet<Integer> surviveList;
    protected HashSet<Integer> createList;

    public LifeGrid(int xSize, int ySize, HashSet<Integer> surviveList, HashSet<Integer> createList) {
        super(ySize, xSize);

        this.surviveList = surviveList;
        this.createList = createList;
    }

    public void setSurviveList(int[] surviveList) {
        this.surviveList = new HashSet<Integer>();
        for (int i = 0; i != surviveList.length; i++) {
            this.surviveList.add((Integer) surviveList[i]);
        }
    }

    public void setSurviveList(HashSet<Integer> surviveList) {
        this.surviveList = surviveList;
    }

    public void setCreateList(int[] createList) {
        this.surviveList = new HashSet<Integer>();
        for (int i = 0; i != createList.length; i++) {
            this.surviveList.add((Integer) createList[i]);
        }
    }

    public void setCreateList(HashSet<Integer> createList) {
        this.createList = createList;
    }

    @Override
    public void evolve() {
        for (int i = 0; i != ySize; i++) {
            for (int j = 0; j != xSize; j++) {
                int neighbours = this.countNeighbours(j, i);
                int item = grid[i][j];
                if (item == 1) {
                    if (!surviveList.contains(neighbours)) {
                        tempGrid[i][j] = 0;
                    }
                } else {
                    if (createList.contains(neighbours)) {
                        tempGrid[i][j] = 1;
                    }
                }
            }
            if (i > 1) {
                System.arraycopy(this.tempGrid[i - 2], 0, this.grid[i - 2], 0, xSize);
            }
        }
        System.arraycopy(this.tempGrid[ySize - 3], 0, this.grid[ySize - 3], 0, xSize);
        System.arraycopy(this.tempGrid[ySize - 2], 0, this.grid[ySize - 2], 0, xSize);
        System.arraycopy(this.tempGrid[ySize - 1], 0, this.grid[ySize - 1], 0, xSize);
    }

    private int countNeighbours(int x, int y) {
        int total = 0;
        if (x > 0) {
            if (y > 0){
                total += grid[y - 1][x - 1];
            }
            total += grid[y][x - 1];
            if (y < ySize - 1) {
                total += grid[y + 1][x - 1];
            }
        }
        if (y > 0) {
            total += grid[y - 1][x];
        }
        if (y < ySize - 1) {
            total += grid[y + 1][x];
        }
        if (x < xSize - 1) {
            if (y > 0) {
                total += grid[y - 1][x + 1];
            }
            total += grid[y][x + 1];
            if (y < ySize - 1) {
                total += grid[y + 1][x + 1];
            }
        }
        return total;
    }

    public void printLists() {
        String outp = "";
        Iterator<Integer> it = surviveList.iterator();
        while (it.hasNext()) {
            outp += it.next() + " ";
        }

        outp += ": ";

        it = createList.iterator();
        while (it.hasNext()) {
            outp += it.next() + " ";
        }

        System.out.println(outp);
    }
}
