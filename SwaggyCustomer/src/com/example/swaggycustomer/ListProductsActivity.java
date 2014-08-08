package com.example.swaggycustomer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import map.CustomerLocationUpdater;

import constants.UrlConstants;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListProductsActivity extends SwaggyCustomerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_products);
		
		createUIelems();
	}
	
	private void createUIelems() {
		if (SwaggyCustomer.customerLocationUpdateHandler == null)
			SwaggyCustomer.customerLocationUpdateHandler = new CustomerLocationUpdater();

		SwaggyCustomer.customerLocationUpdateHandler.start(getApplicationContext());		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		while(SwaggyCustomer.customerLocation == null)
			continue;

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("user_id", "5197112"));

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
				Utils.getFilledUrl(UrlConstants.getEvents, list), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						String jsonrep;
						try {
							jsonrep = response.getJSONArray("events")
									.toString();

							tabs = new ArrayList<ListItemWithImageUrl>();
							JSONArray jarr;
							jarr = new JSONArray(jsonrep);

							for (int i = 0; i < jarr.length(); i++) {
								JSONObject jb = jarr.getJSONObject(i);
								Event e = Event.getObjectFromJson(
										jb.toString(), Event.class);
								tabs.add(new ListItemWithImageUrl(e.image_url,
										e.position + "at" + e.company, e.name));
							}
							fillist();
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}, new com.android.volley.Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}

				});

		SwaggyCustomer.queue.add(jsonRequest);
	}

}
