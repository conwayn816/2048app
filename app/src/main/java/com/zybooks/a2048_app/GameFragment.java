package com.zybooks.a2048_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zybooks.a2048_app.R;
import android.view.Gravity;

public class GameFragment extends Fragment {

    private GridLayout gameGrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        gameGrid = view.findViewById(R.id.game_grid);

        // Create the 4x4 grid of TextViews
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                TextView textView = new TextView(requireContext());
                textView.setText("Grid Cell"); // Initially empty
                textView.setTextSize(24); // Example text size
                textView.setGravity(Gravity.CENTER); // Center text
                textView.setBackgroundResource(R.drawable.grid_item_background); // Set background drawable

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                params.width = GridLayout.LayoutParams.MATCH_PARENT;
                params.height = GridLayout.LayoutParams.MATCH_PARENT;
                gameGrid.addView(textView, params);
            }
        }
        return view;
    }
}
