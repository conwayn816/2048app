package com.zybooks.a2048_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {

    private Grid2048View grid2048View;

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

        return view;
    }
}
