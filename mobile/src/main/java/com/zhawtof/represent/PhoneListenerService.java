package com.zhawtof.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PhoneListenerService extends WearableListenerService {

private static final String REPNAME = "/repName";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if( messageEvent.getPath().equalsIgnoreCase(REPNAME) ) {
            Log.i("PHONE", "onMessageReceived" );

            String repName = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            Context context = getApplicationContext();

            ArrayList<Representative> repList = Representative.getDefaults();

            Representative rep = null;
            for (Representative r: repList) {
                if (repName.equals(r.name)) {
                    rep = r;
                }
            }
            Log.i("PHONE", "rep was found on phone from wear " + String.valueOf(rep == null));

            Intent next = new Intent(context.getApplicationContext(), RepDetails.class);
            next.putExtra("rep", rep);
            next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(next);

        } else {
            Log.d("PHONE", "if missed");
            super.onMessageReceived( messageEvent );
        }

    }
}
