package com.zhawtof.represent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class RepresentativeBasics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_basics);
        final ListView repBasics = (ListView) findViewById(R.id.representativeList);

//        ArrayList<Representative> repList = (ArrayList) getIntent().getSerializableExtra("repList");
        ArrayList<Representative> repList = Representative.getDefaults();

        final RepAdapter adapter = new RepAdapter(this, repList);
        repBasics.setAdapter(adapter);
    }
}
