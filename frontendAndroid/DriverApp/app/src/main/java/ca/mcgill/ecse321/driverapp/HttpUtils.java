package ca.mcgill.ecse321.driverapp;
import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {

    public static final String DEFAULT_BASE_URL = "http://ecse321-project.herokuapp.com/";

    private static String baseUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(baseUrl + url, params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        System.out.println(baseUrl + url);
        client.post(context, baseUrl + url, entity, contentType, responseHandler);
    }
}