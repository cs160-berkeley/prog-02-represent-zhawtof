package com.zhawtof.represent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

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

        final String committeesEndpoint = "/committees";
        RequestParams comParams = new RequestParams();
        comParams.put("member_ids", rep.bioId);
        RepRESTClient.get(committeesEndpoint, comParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("JSON", "returned JSON Object");
                try {
                    JSONArray committees = response.getJSONArray("results");
                    List<String> comList = new ArrayList<>();

                    for (int i = 0; i < committees.length(); i++){
                        JSONObject com = (JSONObject) committees.get(i);
                        Log.d("Committee", com.getString("name"));
                        comList.add(com.getString("name"));

                        final ArrayAdapter<String> committeesAdapter = new ArrayAdapter<String>(
                                RepDetails.this, android.R.layout.simple_list_item_1, comList);

                        committeesList.setAdapter(committeesAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                Log.d("FAILURE: status", String.valueOf(statusCode));
                Log.d("FAILURE: responseString", responseString);
                for (Header h: headers){
                    Log.d("FAILURE: headers", h.toString());
                }
            }
        });

        partyAffiliation.setText(rep.partyAffiliation);
        if (rep.partyAffiliation.equals("D")) {
            partyAffiliation.setBackgroundColor(getResources().getColor(R.color.democrat));
        } else{
            partyAffiliation.setBackgroundColor(getResources().getColor(R.color.republican));
        }

        representativeName.setText(rep.name);
        positionInCongress.setText(rep.position.toUpperCase());
        termEnds.setText("Term Ends: " + rep.endOfTerm);

        final String recentBillEndpoint = "/bills";
        RequestParams billParams = new RequestParams();
        billParams.put("sponsor_id", rep.bioId);
        RepRESTClient.get(recentBillEndpoint, billParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("JSON", "returned JSON Object");
                try {
                    JSONArray bills = response.getJSONArray("results");
                    List<String> comList = new ArrayList<>();
                    JSONObject recBill = (JSONObject) bills.get(0);
                    Log.d("Bill", recBill.getString("bill_id"));
                    String billText = recBill.getString("bill_id") + " " + recBill.getString("official_title");
                    recentBill.setText(billText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                Log.d("FAILURE: status", String.valueOf(statusCode));
                Log.d("FAILURE: responseString", responseString);
                for (Header h: headers){
                    Log.d("FAILURE: headers", h.toString());
                }
            }
        });



    }
}
