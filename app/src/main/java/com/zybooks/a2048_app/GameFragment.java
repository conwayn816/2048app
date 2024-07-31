package com.zybooks.a2048_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    private TextView timerTextView;
    private long startTime = 0;
    private Handler customHandler = new Handler();
    private long timeInMilliseconds = 0;
    private Grid2048View grid2048View;
    private TextView scoreTextView;
    private GestureDetector gestureDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        timerTextView = view.findViewById(R.id.timer_text_view);
        grid2048View = view.findViewById(R.id.grid2048View);
        Button newGameButton = view.findViewById(R.id.new_game_button);
        scoreTextView = view.findViewById(R.id.high_score);
        StartTimer();
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the context of the fragment
                Context context = getActivity();
                if (context != null) {
                    SharedPreferences scoreStorage = context.getSharedPreferences("scoreStorage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = scoreStorage.edit();

                    if (scoreStorage.getInt("highScore", 0) < grid2048View.findMaxValue()) {
                        editor.putInt("highScore", grid2048View.findMaxValue());
                    }
                    // Update the TextView after resetting the score
                    updateScoreTextView();
                    editor.apply();
                    StartTimer();
                    grid2048View.resetGame();
                }
            }
        });
        gestureDetector = new GestureDetector(getContext(), new GestureListener());

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        return view;
    }

    // Method to update the score TextView
    private void updateScoreTextView() {
        Context context = getActivity();
        if (context != null) {
            SharedPreferences namedSharedPref = context.getSharedPreferences("scoreStorage", Context.MODE_PRIVATE);
            int score = namedSharedPref.getInt("highScore", 0); // Default score is 0 if not found
            scoreTextView.setText(String.valueOf(score)); // Update the TextView with the score
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (e1.getY() > e2.getY()) {
                    // Swipe Up
                    grid2048View.moveUp();
                } else {
                    // Swipe Down
                    grid2048View.moveDown();
                }
                return true;
            } else if (Math.abs(e1.getX() - e2.getX()) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (e1.getX() > e2.getX()) {
                    // Swipe Left
                    grid2048View.moveLeft();
                } else {
                    // Swipe Right
                    grid2048View.moveRight();
                }
                return true;
            }
            return false;
        }
    }
    public void StartTimer () {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (timeInMilliseconds % 1000);
            timerTextView.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };
}

