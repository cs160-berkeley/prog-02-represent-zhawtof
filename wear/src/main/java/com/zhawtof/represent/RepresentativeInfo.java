package com.zhawtof.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RepresentativeInfo extends Activity {


    TextView repName;
    TextView partyAffiliation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.round_activity_representative_info);

        Button electionButton = (Button) findViewById(R.id.twelveElectionButton);

        electionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElectionInfo.class);
                startActivity(intent);
            }
        });

        repName = (TextView) findViewById(R.id.repName);
        partyAffiliation = (TextView) findViewById(R.id.partyAffiliation);

        String[] reps = getIntent().getStringArrayExtra("repList");

        int position = getIntent().getIntExtra("position", 0);

        if (reps != null){
            String[] r = reps[position].split("\\.");

            final String nameOfRep = r[0];
            String party = r[1];

            repName.setText(nameOfRep);
            partyAffiliation.setText(party);

            repName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("repName", nameOfRep);
                    startService(sendIntent);
                }
            });

        } else {
            repName.setText("Use the Phone");
        }
    }
}
