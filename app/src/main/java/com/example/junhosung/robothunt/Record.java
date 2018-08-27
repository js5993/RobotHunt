package com.example.junhosung.robothunt;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Junho Sung on 8/25/2018.
 */

public class Record {

    int numRows;
    int numCols;

    int numMines;
    int scansSoFar;

    public Record(JSONObject jsonObject) throws JSONException {
        numRows = jsonObject.getInt("numRows");
        numCols = jsonObject.getInt("numCols");
        numMines = jsonObject.getInt("numMines");
        scansSoFar = jsonObject.getInt("scansSoFar");

    }

    @Override
    public String toString() {
        return
                " " + numRows +
                " X " + numCols +
                " grid || " + numMines +
                " mines || scans used: " + scansSoFar;
    }
}
