package com.duphungcong.twitterclient;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "rkXqdg1DcmzlwxASjw8EEyuCg";
	public static final String REST_CONSUMER_SECRET = "Un1Y7vd48ECXMGQkGDcVtql8rajV8YyOinpdsg2n0mgue3BAEW";
	// Change here and AndroidManifest.xml
	public static final String REST_CALLBACK_URL = "x-oauthflow-twitter://arbitraryname.com";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	// DEFINE METHODS for different API endpoints here
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void getHomeTimeline(String maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		if (maxId != null) {
			params.put("max_id", Long.parseLong(maxId) - 1);
		}
		client.get(apiUrl, params, handler);
	}

	public void getVerifyCredentials(AsyncHttpResponseHandler handler) {
	    String apiUrl = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();
        //params.put("format", "json");
        client.get(apiUrl, params, handler);
	}

	public void updateStatuses(String status, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", status);
		client.post(apiUrl, params, handler);
	}

	public void getMentions(String maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		if (maxId != null) {
			params.put("max_id", Long.parseLong(maxId) - 1);
		}
		client.get(apiUrl, params, handler);
	}

	public void getUserTimeline(String userId, String maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		if (maxId != null) {
			params.put("max_id", Long.parseLong(maxId) - 1);
		}
		if (userId != null) {
			params.put("user_id", Long.parseLong(userId));
		}
		client.get(apiUrl, params, handler);
	}

	/*public void getFollowers(String userId, String cursor, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("followers/list.json");
		RequestParams params = new RequestParams();
		if (cursor != null) {
			params.put("cursor", Long.parseLong(cursor));
		}
		if (userId != null) {
			params.put("user_id", Long.parseLong(userId));
		}
		client.get(apiUrl, params, handler);
	}*/

	public void getFollowers(String userId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("followers/list.json");
		RequestParams params = new RequestParams();
		if (userId != null) {
			params.put("user_id", Long.parseLong(userId));
		}
		client.get(apiUrl, params, handler);
	}
}
