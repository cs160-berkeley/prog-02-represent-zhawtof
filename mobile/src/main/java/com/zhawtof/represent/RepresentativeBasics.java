package com.zhawtof.represent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RepresentativeBasics extends AppCompatActivity {

    ArrayList<Representative> repList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_basics);
        final ListView repBasics = (ListView) findViewById(R.id.representativeList);

        ArrayList<Representative> repList = (ArrayList<Representative>) getIntent().getSerializableExtra("repList");
        String zipCode = getIntent().getStringExtra("zipCode");
        Log.d("ZIP", zipCode);

        final RepAdapter adapter = new RepAdapter(RepresentativeBasics.this, repList);
        repBasics.setAdapter(adapter);

    }
}
