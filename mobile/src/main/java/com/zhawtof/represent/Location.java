package com.zhawtof.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Location extends AppCompatActivity {

    Button findMyRepsButton;
    TextView zipCodeField;
    ImageView locationIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final ArrayList<Representative> repList = Representative.getDefaults();

        findMyRepsButton = (Button) findViewById(R.id.findmyreps);
        zipCodeField = (TextView) findViewById(R.id.zipCode);
        locationIcon = (ImageView) findViewById(R.id.locationIcon);

        findMyRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), RepresentativeBasics.class);
                next.putExtra("repList", repList);
                startActivity(next);

                Intent watch = new Intent(getApplicationContext(), PhoneToWatchService.class);
                watch.putExtra("repList", repList);
                startService(watch);
            }
        });

        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipCodeField.setText("80121");
            }
        });
    }
}
