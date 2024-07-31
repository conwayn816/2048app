package com.zybooks.a2048_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {

    private Grid2048View grid2048View;
    private TextView scoreTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        grid2048View = view.findViewById(R.id.grid2048View);
        Button newGameButton = view.findViewById(R.id.new_game_button);
        scoreTextView = view.findViewById(R.id.high_score);
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
                    grid2048View.resetGame();
                }
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

}
