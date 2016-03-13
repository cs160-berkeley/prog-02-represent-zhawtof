package com.zhawtof.represent;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ElectionInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_info);

        try {
            InputStream in = getAssets().open("election-county-2012.json");

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONArray electionInfo = new JSONArray(responseStrBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
