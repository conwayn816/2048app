package com.zybooks.a2048_app;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

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
        // Add two new random tiles
        addRandomTile();
        addRandomTile();
        // Redraw the view
        invalidate();
    }

}