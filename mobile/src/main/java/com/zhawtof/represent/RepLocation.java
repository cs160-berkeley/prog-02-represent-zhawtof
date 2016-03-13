package com.zhawtof.represent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class RepLocation extends AppCompatActivity {

    Button findMyRepsButton;
    TextView zipCodeField;
    ImageView locationIcon;
    Location currentLocation;

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.d("GPS", "IS ON!!!");
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                    100, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
        } else {
            Log.d("GPS", "IS OFF!!!");
        }

        findMyRepsButton = (Button) findViewById(R.id.findmyreps);
        zipCodeField = (TextView) findViewById(R.id.zipCode);
        locationIcon = (ImageView) findViewById(R.id.locationIcon);

        findMyRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zipCodeField.getText() != null) {

                    final String endpoint = "/legislators/locate";
                    RequestParams params = new RequestParams();
                    params.put("zip", zipCodeField.getText());
                    RepRESTClient.get(endpoint, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("JSON", "returned JSON Object");
                            try {
                                JSONArray candidates = response.getJSONArray("results");
                                Log.d("JSON", candidates.toString());
                                ArrayList<Representative> repList = new ArrayList<>();

                                for (int i = 0; i < candidates.length(); i++) {
                                    JSONObject can = (JSONObject) candidates.get(i);
                                    Log.d("NEW REP", can.getString("first_name") + " " + can.getString("last_name"));
                                    repList.add(new Representative(
                                            can.getString("bioguide_id"), //id
                                            can.getString("first_name") + " " + can.getString("last_name"), //name
                                            can.getString("chamber"), //position
                                            can.getString("party"), //party
                                            can.getString("term_end"), //end of term
                                            can.getString("website"), //website
                                            can.getString("oc_email") //email
                                    ));
                                }

                                Intent next = new Intent(getApplicationContext(), RepresentativeBasics.class);
                                next.putExtra("repList", repList);
                                next.putExtra("zipCode", zipCodeField.getText().toString());
                                startActivity(next);

                                Intent watch = new Intent(getApplicationContext(), PhoneToWatchService.class);
                                watch.putExtra("repList", repList);
//                              watch.putExtra("county", county);
                                startService(watch);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("FAILURE: status", String.valueOf(statusCode));
                            Log.d("FAILURE: responseString", responseString);
                            for (Header h : headers) {
                                Log.d("FAILURE: headers", h.toString());
                            }
                        }
                    });
                } else {
                    Toast.makeText(RepLocation.this, "Need Zip Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zipCode = "Where are you?";

                if (ActivityCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (currentLocation != null){
                    Log.d("Latitude", String.valueOf(currentLocation.getLatitude()));
                    Log.d("Longitude", String.valueOf(currentLocation.getLongitude()));
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(
                                currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    zipCode = addresses.get(0).getPostalCode();
                    Log.d("ADDRESS", addresses.get(0).toString());

                    zipCodeField.setText(zipCode);

                } else {
                    Log.d("currentLocation", "NULL");
                }

            }
        });
    }
}
