package com.example.junhosung.robothunt.model;

import java.util.Random;

public class GameLogic {

    private int numRow;
    private int numCol;
    private int numMines;
    private int numMinesFound;

    // Singleton Support

    private static GameLogic instance;

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }

        return instance;
    }

    private GameLogic() {
        // private to keep anyone else from instantiating
    }

    Random random = new Random();

    public GridCell[][] mineField = new GridCell[numRow][numCol];

    public void updateMineField(int row, int col) {
        mineField = new GridCell[row][col];
    }

    public void populateMineField() {

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                GridCell gridCell = new GridCell();
                mineField[i][j] = gridCell;
            }
        }

        while (numMines > 0) {
            int i = random.nextInt(numRow);
            int j = random.nextInt(numCol);

            if (mineField[i][j].isBomb != true) {
                mineField[i][j].isBomb = true;
                numMines--;
            }
        }

    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }



}
