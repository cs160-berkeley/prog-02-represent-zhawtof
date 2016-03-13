package com.zhawtof.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchListenerService extends WearableListenerService {
    private static final String REP_LIST = "/repList";
    private static final String COUNTY = "/county";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("WATCH", "in WatchListenerService, got: " + messageEvent.getPath());

        Intent intent = new Intent(this, RepresentativeInfo.class );

        if( messageEvent.getPath().equalsIgnoreCase( REP_LIST ) ) {

            Log.d("WATCH", "message received and in replist");
            String repList = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] repsArray = repList.split(",");
            intent.putExtra("repList", repsArray);


        } else if (messageEvent.getPath().equalsIgnoreCase( COUNTY )) {

            Log.d("WATCH", "message received and county");
            String county = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.d("COUNTY", county);
            intent.putExtra("county", county);

        } else {
            Log.d("WATCH", "message received but not replist");
            super.onMessageReceived( messageEvent );
        }

        Log.d("WATCH", "about to start watch RepresentativeInfo");
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);

    }
}