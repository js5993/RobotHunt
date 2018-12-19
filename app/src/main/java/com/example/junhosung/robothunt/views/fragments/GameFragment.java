package com.example.junhosung.robothunt.views.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.robothunt.R;
import com.example.junhosung.robothunt.media.AudioPlayer;
import com.example.junhosung.robothunt.model.GameLogic;
import com.example.junhosung.robothunt.views.dialogs.VictoryDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class GameFragment extends Fragment {


    private static final int DEFAULT_NUM_ROWS = 5;
    private static final int DEFAULT_NUM_COLS = 10;
    private static final int DEFAULT_NUM_MINES = 10;
    public static final String FILE_NAME = "records.json";

    private TextView txtDisplayScans;
    private TextView txtScansUsed;
    private TextView txtDisplayMines;
    private AudioPlayer audioPlayer = new AudioPlayer();

    public JSONArray previousRecord;

    Button buttons[][];

    int numMines;
    int numRows;
    int numCols;
    int numFoundMines;
    int scansSoFar;

    final GameLogic game = GameLogic.getInstance();;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // initial default values so the game doesn't crash after the first play after installation

        // get option values from shared preferences

        numRows = OptionFragment.getNumRowSelected(getActivity());
        numCols = OptionFragment.getNumColSelected(getActivity());
        numMines = OptionFragment.getNumMineSelected(getActivity());

        //Toast.makeText(getActivity()," " + numRows + " X " + numCols + " " + numMines,Toast.LENGTH_LONG).show();

        game.setNumRow(numRows);
        game.setNumCol(numCols);
        game.setNumMines(numMines);

        // initial default values so the game doesn't crash after the first play after installation

        if (numRows == 0  || numCols == 0 || numMines == 0) {
            game.setNumRow(DEFAULT_NUM_ROWS);
            game.setNumCol(DEFAULT_NUM_COLS);
            game.setNumMines(DEFAULT_NUM_MINES);
            numMines = DEFAULT_NUM_MINES;
            numCols = DEFAULT_NUM_COLS;
            numRows = DEFAULT_NUM_ROWS;
        }

        numFoundMines = 0;
        scansSoFar = 0;

        game.updateMineField(game.getNumRow(),game.getNumCol());
        buttons = new Button[game.getNumRow()][game.getNumCol()];

        game.populateMineField();

        audioPlayer.play(getActivity());

        try {
            loadPreviousRecord();
        } catch (IOException | JSONException e) {
            Toast.makeText(getActivity(),"smth wrong with loading prev record",Toast.LENGTH_LONG).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container,false);


        Toast.makeText(getActivity(),""+ numMines,Toast.LENGTH_LONG).show();
        TableLayout table = (TableLayout) view.findViewById(R.id.tableForButton);

        for (int row = 0; row < game.getNumRow(); row++) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));

            table.addView(tableRow);

            for (int col = 0; col < game.getNumCol(); col++) {
                final int finalCol = col;
                final int finalRow = row;

                Button button = new Button(getContext());
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // make buttons not clip

                button.setPadding(0, 0, 0, 0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonClicked(finalRow, finalCol);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;

            }
        }

        txtDisplayMines = (TextView) view.findViewById(R.id.txtDisplayMines);
        txtDisplayMines.setText("found " + numFoundMines + " of " + numMines + " mines");
        txtDisplayMines.setTextColor(Color.WHITE);

        txtDisplayScans = (TextView) view.findViewById(R.id.txtDisplayScans);
        txtDisplayScans.setText(" " + scansSoFar);
        txtDisplayScans.setTextColor(Color.WHITE);

        txtScansUsed = (TextView) view.findViewById(R.id.txtScanUsed);
        txtScansUsed.setTextColor(Color.WHITE);


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        audioPlayer.stop();

    }

    private void gridButtonClicked(int row, int col) {

        if (game.mineField[row][col].isBomb == true && game.mineField[row][col].isRevealed == false ) {

            Button button = buttons[row][col];
            button.setBackgroundResource(R.drawable.robotimage);

            //scale image to button

            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robotimage);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            game.mineField[row][col].isBomb = false;
            game.mineField[row][col].isRevealed = true;

            numFoundMines++;

            txtDisplayMines.setText("found " + numFoundMines + " of " + numMines + " mines");

            checkVictory();

            reduceCount(row, col);

        }

        else if (game.mineField[row][col].isBomb == false && game.mineField[row][col].isRevealed == false) {
            game.mineField[row][col].isRevealed = true;
            //Toast.makeText(this,"No Robots Here ... Scanning",Toast.LENGTH_SHORT).show();
            scanGrid(row,col);
        }



    }


    private void reduceCount(int row, int col) {

        // after finding a mine, you look through all the buttons with a setText() in that row and column
        // and find ones that are not null or zero and subtract by 1

        //search up the column

        for (int i = row; i >= 0 ; i--) {
            if (buttons[i][col].getText().toString() != null &&
                    buttons[i][col].getText().toString() != "" &&
                    Integer.parseInt(buttons[i][col].getText().toString()) != 0) {
                int currentCount = Integer.parseInt(buttons[i][col].getText().toString());
                currentCount--;
                buttons[i][col].setText(String.valueOf(currentCount));

            }
        }

        // search down the column

        for (int i = row; i < numRows ; i++) {
            if (buttons[i][col].getText().toString() != null &&
                    buttons[i][col].getText().toString() != "" &&
                    Integer.parseInt(buttons[i][col].getText().toString()) != 0) {
                int currentCount = Integer.parseInt(buttons[i][col].getText().toString());
                currentCount--;
                buttons[i][col].setText(String.valueOf(currentCount));

            }
        }

        // search left

        for (int i = col; i >= 0 ; i--) {
            if (buttons[row][i].getText().toString() != null &&
                    buttons[row][i].getText().toString() != "" &&
                    Integer.parseInt(buttons[row][i].getText().toString()) != 0) {
                int currentCount = Integer.parseInt(buttons[row][i].getText().toString());
                currentCount--;
                buttons[row][i].setText(String.valueOf(currentCount));

            }
        }

        // search right

        for (int i = col; i < numCols ; i++) {
            if (buttons[row][i].getText().toString() != null &&
                    buttons[row][i].getText().toString() != "" &&
                    Integer.parseInt(buttons[row][i].getText().toString()) != 0) {
                int currentCount = Integer.parseInt(buttons[row][i].getText().toString());
                currentCount--;
                buttons[row][i].setText(String.valueOf(currentCount));

            }
        }


    }

    private void scanGrid(int row, int col) {

        int hiddenRobots = 0;
        Button button = buttons[row][col];

        // search up the column

        for (int i = row; i >= 0 ; i--) {
            if (game.mineField[i][col].isBomb == true) {
                hiddenRobots++;
            }
        }

        // search down the column

        for (int i = row; i < numRows; i++ ) {
            if (game.mineField[i][col].isBomb == true) {
                hiddenRobots++;
            }
        }

        // search left

        for (int i = col; i >= 0; i--) {
            if (game.mineField[row][i].isBomb == true) {
                hiddenRobots++;
            }
        }

        // search right

        for (int i = col; i < numCols; i++) {
            if (game.mineField[row][i].isBomb == true) {
                hiddenRobots++;
            }
        }

        scansSoFar++;
        txtDisplayScans.setText(" " + scansSoFar);
        button.setText(String.valueOf(hiddenRobots));

    }

    private void checkVictory() {


        if (numFoundMines == numMines) {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            VictoryDialog victoryMessageFragment = new VictoryDialog();
            victoryMessageFragment.show(fragmentManager,"Victory Dialog");
            audioPlayer.playCheering(getActivity());

            try {

                JSONObject finalRecord = saveRecordToJSON(numMines,numRows,numCols,scansSoFar);
                previousRecord.put(finalRecord);

                Writer writer = null;

                OutputStream out = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
                writer.write(previousRecord.toString());

                writer.close();

            } catch (JSONException | IOException e) {
                Toast.makeText(getActivity(),"JSON not created!",Toast.LENGTH_LONG).show();
            }

        }

    }

    public JSONObject saveRecordToJSON(int numMines, int numRows, int numCols, int scansSoFar) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("numMines", numMines);
            jsonObject.put("numRows", numRows);
            jsonObject.put("numCols", numCols);
            jsonObject.put("scansSoFar", scansSoFar);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;

    }

    public JSONArray loadPreviousRecord() throws IOException, JSONException {

        previousRecord = new JSONArray();
        BufferedReader reader = null;

        try {
            InputStream inputStream = getActivity().openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            previousRecord = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            return previousRecord;

        } catch (IOException e) {
            Toast.makeText(getActivity(),"trouble w/ loading prev record",Toast.LENGTH_LONG).show();
        }

        return previousRecord;

    }

}
