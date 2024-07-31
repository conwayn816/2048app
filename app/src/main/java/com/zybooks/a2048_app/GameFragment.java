package com.zybooks.a2048_app;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {

    private Grid2048View grid2048View;
    private GestureDetector gestureDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        grid2048View = view.findViewById(R.id.grid2048View);
        Button newGameButton = view.findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid2048View.resetGame();
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

    }
}

