package com.zhawtof.represent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RepDetails extends AppCompatActivity {

    ImageView repPicture;
    TextView partyAffiliation;
    TextView representativeName;
    TextView positionInCongress;
    ListView committeesList;
    TextView recentBill;
    TextView termEnds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_details);

        repPicture = (ImageView) findViewById(R.id.repPicture);
        partyAffiliation = (TextView) findViewById(R.id.partyAffiliation);
        representativeName = (TextView) findViewById(R.id.repName);
        positionInCongress = (TextView) findViewById(R.id.position);
        committeesList = (ListView) findViewById(R.id.committeesList);
        recentBill = (TextView) findViewById(R.id.billDescription);
        termEnds = (TextView) findViewById(R.id.endOfTerm);

        Representative rep = (Representative) getIntent().getSerializableExtra("rep");

        partyAffiliation.setText(rep.partyAffiliation);
        if (rep.partyAffiliation.equals("D")) {
            partyAffiliation.setBackgroundColor(getResources().getColor(R.color.democrat));
        } else{
            partyAffiliation.setBackgroundColor(getResources().getColor(R.color.republican));
        }

        representativeName.setText(rep.name);
        positionInCongress.setText(rep.position);
        recentBill.setText(rep.recentBill);
        termEnds.setText(rep.endOfTerm);

        final ArrayAdapter<String> committeesAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, rep.committees);

        committeesList.setAdapter(committeesAdapter);

    }
}
