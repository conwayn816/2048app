package com.zybooks.a2048_app;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
//initialize the grid
public class Grid2048View extends View {
    private int[][] grid;
    private final int SIZE = 4;
    private Paint cellPaint;
    private Paint textPaint;
    private Random random;

    public Grid2048View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
// Grid info
    private void init() {
        grid = new int[SIZE][SIZE];
        random = new Random();
        cellPaint = new Paint();
        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        initializeGrid();
    }
// The beginning of the grid
    private void initializeGrid() {
        addRandomTile();
        addRandomTile();
    }
// randomised tile generation
    private void addRandomTile() {
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (grid[row][col] != 0);

        grid[row][col] = random.nextFloat() < 0.9f ? 2 : 4;
    }
//Draw The Box
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int cellSize = Math.min(width, height) / SIZE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int left = j * cellSize;
                int top = i * cellSize;
                int right = left + cellSize;
                int bottom = top + cellSize;

                cellPaint.setColor(getCellColor(grid[i][j]));
                canvas.drawRect(left, top, right, bottom, cellPaint);

                if (grid[i][j] != 0) {
                    textPaint.setColor(grid[i][j] <= 4 ? Color.BLACK : Color.WHITE);
                    textPaint.setTextSize(cellSize / 3f);
                    String text = String.valueOf(grid[i][j]);
                    float x = left + cellSize / 2f;
                    float y = top + cellSize / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f);
                    canvas.drawText(text, x, y, textPaint);
                }
            }
        }
    }
//Color for the numbers
    private int getCellColor(int value) {
        switch (value) {
            case 2: return Color.rgb(250, 250, 250);
            case 4: return Color.rgb(0, 255, 0);
            case 8: return Color.rgb(20, 0, 255);
            case 16: return Color.rgb(160, 32, 240);
            case 32: return Color.rgb(246, 124, 95);
            case 64: return Color.rgb(255, 0, 0);
            case 128: return Color.rgb(255, 255, 0);
            default: return Color.rgb(205, 193, 180);
        }
    }

    public void resetGame() {

        // Clear the grid

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = 0;
            }
        }
        // Add two new random tiles and regenerate the view
        addRandomTile();
        addRandomTile();
        invalidate();
        

    }
    // This is to visually move the block left
    public void moveLeft() {

        boolean moved = false;
        //Checks for the non zero values and makes a compression
        for (int i = 0; i < SIZE; i++) {
            int[] newRow = new int[SIZE];
            int index = 0;
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] != 0) {
                    newRow[index++] = grid[i][j];
                }
            }
            //Merge Adjacent Tiles from the left
            for (int j = 0; j < SIZE - 1; j++) {
                if (newRow[j] != 0 && newRow[j] == newRow[j + 1]) {
                    newRow[j] *= 2;
                    newRow[j + 1] = 0;
                    moved = true;
                }
            }
            //Recompress the Row After Merging
            index = 0;
            for (int j = 0; j < SIZE; j++) {
                if (newRow[j] != 0) {
                    grid[i][index++] = newRow[j];
                }
            }
            while (index < SIZE) {
                grid[i][index++] = 0;
            }
        }


        // Add two new random tiles and regenerate the view
        addRandomTile();

        invalidate();


    }
    //This will visually move the block to the right
    public void moveRight() {
        boolean moved = false;

        for (int i = 0; i < SIZE; i++) {
            int[] newRow = new int[SIZE];
            int index = SIZE - 1;

            // Copy non-zero values into newRow, starting from the right
            for (int j = SIZE - 1; j >= 0; j--) {
                if (grid[i][j] != 0) {
                    newRow[index--] = grid[i][j];
                }
            }

            // Merge adjacent tiles
            for (int j = SIZE - 1; j > 0; j--) {
                if (newRow[j] != 0 && newRow[j] == newRow[j - 1]) {
                    newRow[j] *= 2;
                    newRow[j - 1] = 0;
                    moved = true;
                }
            }

            // Recompress the row to the right after merging
            index = SIZE - 1;
            for (int j = SIZE - 1; j >= 0; j--) {
                if (newRow[j] != 0) {
                    grid[i][index--] = newRow[j];
                }
            }
            while (index >= 0) {
                grid[i][index--] = 0;
            }
        }


        // Add two new random tiles and regenerate the view
        addRandomTile();

        invalidate();

    }
    public void moveUp() {
        boolean moved = false;

        // Iterate over each column
        for (int col = 0; col < SIZE; col++) {
            // Create a temporary array to store the current column's values
            int[] temp = new int[SIZE];
            int tempIndex = 0;

            // Extract non-zero values into the temp array
            for (int row = 0; row < SIZE; row++) {
                if (grid[row][col] != 0) {
                    temp[tempIndex] = grid[row][col];
                    tempIndex++;
                }
            }

            // Perform the merge operation in the temp array
            for (int i = 0; i < tempIndex - 1; i++) {
                if (temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    temp[i + 1] = 0;
                    moved = true;
                }
            }

            // Shift the merged values to the top
            int finalIndex = 0;
            for (int i = 0; i < tempIndex; i++) {
                if (temp[i] != 0) {
                    grid[finalIndex][col] = temp[i];
                    if (finalIndex != i) {
                        moved = true;
                    }
                    finalIndex++;
                }
            }

            // Fill the rest with zeros
            for (int i = finalIndex; i < SIZE; i++) {
                grid[i][col] = 0;
            }
        }

        // Add two new random tiles and regenerate the view
        addRandomTile();

        invalidate();

    }
    public void moveDown() {
        boolean moved = false;

        // Iterate over each column
        for (int col = 0; col < SIZE; col++) {
            // Create a temporary array to store the current column's values
            int[] temp = new int[SIZE];
            int tempIndex = SIZE - 1;

            // Extract non-zero values into the temp array, but from bottom to top
            for (int row = SIZE - 1; row >= 0; row--) {
                if (grid[row][col] != 0) {
                    temp[tempIndex] = grid[row][col];
                    tempIndex--;
                }
            }

            // Perform the merge operation in the temp array
            for (int i = SIZE - 1; i > tempIndex + 1; i--) {
                if (temp[i] == temp[i - 1]) {
                    temp[i] *= 2;
                    temp[i - 1] = 0;
                    moved = true;
                }
            }

            // Shift the merged values to the bottom
            int finalIndex = SIZE - 1;
            for (int i = SIZE - 1; i > tempIndex; i--) {
                if (temp[i] != 0) {
                    grid[finalIndex][col] = temp[i];
                    if (finalIndex != i) {
                        moved = true;
                    }
                    finalIndex--;
                }
            }

            // Fill the rest with zeros
            for (int i = finalIndex; i >= 0; i--) {
                grid[i][col] = 0;
            }
        }


        // Add two new random tiles and regenerate the view
        addRandomTile();

        invalidate();
    }
    }