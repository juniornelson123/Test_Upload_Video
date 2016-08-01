package com.example.junior.test_token_video;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by junior on 14/07/16.
 */
public class VotaRestClient {
    private static AsyncHttpClient client=new AsyncHttpClient();

    public static final String BASE_URL="http://192.168.1.5:3000";
    public static final String IMAGE_BASE="https://sensationnel-croissant-68984.herokuapp.com";

    public void get(String url, ResponseHandlerInterface response){
        client.get(relativeUrl(url),response);
    }

    public void post(Context context, String url, HttpEntity entity,ResponseHandlerInterface response){
        client.post(context,relativeUrl(url),entity,"application/json",response);
    }
    public void patch(Context context, String url, HttpEntity entity,AsyncHttpResponseHandler responseHandler){
        client.patch(context,relativeUrl(url),entity,"application/json",responseHandler);

    }
    public void delete(String url,ResponseHandlerInterface response){
        client.delete(relativeUrl(url),response);
    }
    public String relativeUrl(String url){
        return BASE_URL+url;
    }


}
