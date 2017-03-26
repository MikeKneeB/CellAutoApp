package com.cellularautomata.cellautoapp.internal;

import java.util.HashSet;

/**
 * Created by mike on 13/03/17.
 */

public class GenerationsGrid extends LifeGrid {

    private int generations;

    public GenerationsGrid(int ySize, int xSize, HashSet<Integer> surviveList, HashSet<Integer> createList, int generations) {
        super(ySize, xSize, surviveList, createList);
        this.generations = generations;
    }

    public void setGenerations(int generations) { this.generations = generations; }

    @Override
    public void evolve() {
        for (int i = 0; i != ySize; i++) {
            for (int j = 0; j != xSize; j++) {
                int neighbours = this.countNeighbours(j, i);
                int item = grid[i][j];
                if (item > 0) {
                    if (!surviveList.contains(neighbours)) {
                        tempGrid[i][j] = ((tempGrid[i][j] + 1) % generations);
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
            if (y > 0) {
                if (grid[y - 1][x - 1] == 1) {
                    total += 1;
                }
            }
            if (grid[y][x - 1] == 1) {
                total += 1;
            }
            if (y < ySize - 1) {
                if (grid[y + 1][x - 1] == 1) {
                    total += 1;
                }
            }
        }
        if (y > 0) {
            if (grid[y - 1][x] == 1) {
                total += 1;
            }
        }
        if (y < ySize - 1) {
            if (grid[y + 1][x] == 1) {
                total += 1;
            }
        }
        if (x < xSize - 1) {
            if (y > 0) {
                if (grid[y - 1][x + 1] == 1) {
                    total += 1;
                }
            }
            if (grid[y][x + 1] == 1) {
                total += 1;
            }
            if (y < ySize - 1) {
                if (grid[y + 1][x + 1] == 1) {
                    total += 1;
                }
            }
        }
        return total;
    }
}
