package com.example.junhosung.robothunt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Junho Sung on 8/6/2018.
 */

public class OptionActivityFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createRadioButtons();

    }

    private void createRadioButtons() {
        RadioGroup group = (RadioGroup) getView().findViewById(R.id.radioGrp_num_mines);
        RadioGroup group2 = (RadioGroup) getView().findViewById(R.id.radioGrp_grid_size);

        int[] numMines = getResources().getIntArray(R.array.num_mines);
        int[] numRows = getResources().getIntArray(R.array.num_rows);
        int[] numCols = getResources().getIntArray(R.array.num_cols);

        for (int i = 0; i < numMines.length; i++) {
            final int numMine = numMines[i];

            RadioButton button = new RadioButton(getActivity());
            button.setText("  # Mines: " + numMine);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveNumMines(numMine);

                }
            });

            group.addView(button);


            if (numMine == getNumMineSelected(getActivity())) {
                button.setChecked(true);
            }


        }

        for (int i = 0; i < numRows.length; i++) {
            final int numRow = numRows[i];
            final int numCol = numCols[i];

            RadioButton button = new RadioButton(getContext());
            button.setText("Grid Size: (row X col) " + numRow + " X " + numCol);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveNumRows(numRow);
                    saveNumCols(numCol);
                }
            });

            group2.addView(button);

            if (numRow == getNumRowSelected(getActivity())) {
                button.setChecked(true);
            }

        }


    }

    private void saveNumMines(int numMine) {

        SharedPreferences preferences = getActivity().getSharedPreferences("AppPrefsMines",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Num mines",numMine);
        editor.apply();

    }

    static public int getNumMineSelected(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("AppPrefsMines",MODE_PRIVATE);

        return preferences.getInt("Num mines",0);

    }

    private void saveNumRows(int numRow) {

        SharedPreferences preferences = getActivity().getSharedPreferences("AppPrefsRows",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Num rows",numRow);
        editor.apply();

    }

    static public int getNumRowSelected(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("AppPrefsRows",MODE_PRIVATE);

        return preferences.getInt("Num rows", 0);
    }

    private void saveNumCols(int numCol) {

        SharedPreferences preferences = getActivity().getSharedPreferences("AppPrefsCols", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Num cols", numCol);
        editor.apply();

    }

    static public int getNumColSelected(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("AppPrefsCols", MODE_PRIVATE);

        return preferences.getInt("Num cols", 0);
    }


}
