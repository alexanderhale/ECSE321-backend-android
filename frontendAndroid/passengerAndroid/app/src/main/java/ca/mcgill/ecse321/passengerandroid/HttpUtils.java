package ca.mcgill.ecse321.passengerandroid;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;

public class HttpUtils {

    public static final String DEFAULT_BASE_URL = "http://ecse321-project.herokuapp.com/";

    private static String baseUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    public static void get(String url, RequestParams params, String token, AsyncHttpResponseHandler responseHandler) {
        if (token != null) {
            String authHeader = "Bearer " + token;
            client.addHeader("Authorization", authHeader);
        }
        client.get(baseUrl + url, params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, String token, AsyncHttpResponseHandler responseHandler) {
        System.out.println(baseUrl + url);
        client.post(context, baseUrl + url, entity, contentType, responseHandler);
    }
}