package com.example.junhosung.robothunt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Junho Sung on 8/10/2018.
 */

public class ScoreboardActivityFragment extends Fragment {

    public static final String FILE_NAME = "records.json";
    TextView txtTest;
    String testString;
    ArrayList<Record> records;
    ListView recordsList;
    ArrayAdapter<Record> arrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            records = loadRecords();
        } catch (IOException e) {

        }

        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record record, Record t1) {
                return (int) (record.scansSoFar - t1.scansSoFar);
            }
        });

        arrayAdapter = new ArrayAdapter<Record>(getActivity(),
                android.R.layout.simple_list_item_1,
                records);





    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        recordsList = (ListView) view.findViewById(R.id.list_records);
        recordsList.setAdapter(arrayAdapter);


        return view;
    }

    public ArrayList<Record> loadRecords() throws IOException {
        ArrayList<Record> records = new ArrayList<Record>();
        BufferedReader reader = null;

        try {

            InputStream in = getActivity().openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null ) {
                jsonString.append(line);

            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {
                records.add(new Record(jsonArray.getJSONObject(i)));
            }


        } catch (IOException |JSONException e) {

        } finally {
            if (reader != null) {
                reader.close();
            }
        }


        return records;

    }

}
