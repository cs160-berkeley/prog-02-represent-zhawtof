package com.zhawtof.represent;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by zhawtof on 3/9/16.
 */
public class RepRESTClient {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String BASE_URL = "http://congress.api.sunlightfoundation.com";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.put("apikey", "e914b8e6fbd04495a95ea2c977c8c232");
        Log.d("URL", getAbsoluteUrl(url));
        Log.d("PARAMS", params.toString());
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
