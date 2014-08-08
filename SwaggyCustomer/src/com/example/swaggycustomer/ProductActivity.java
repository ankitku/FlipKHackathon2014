package com.example.swaggycustomer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import util.Utils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import constants.UrlConstants;
import models.Item;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductActivity extends SwaggyCustomerActivity {
	TextView tv1, tv2, tv3, tv4;
	int pos;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_item_desc);

		TextView tv1 = (TextView) findViewById(R.id.tv1);
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		TextView tv3 = (TextView) findViewById(R.id.tv3);
		TextView tv4 = (TextView) findViewById(R.id.tv4);

		pos = getIntent().getIntExtra("pid", 0);

		Item it = SwaggyCustomer.items.get(pos);
		tv1.setText(it.getAttributes().getTitle());
		tv1.setTextColor(Color.BLACK);
		tv2.setText("Rs." + it.getAttributes().getPrice());
		tv3.setText((Integer.parseInt(it.getEta())) / 60 + " mins");
		tv4.setText(it.getAttributes().getDescription());
	}

	public void track(View v) {
		
		Item it = SwaggyCustomer.items.get(pos);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		SwaggyCustomer.increasingProductId++;
		list.add(new BasicNameValuePair("order_id", "OD"
				+ SwaggyCustomer.increasingProductId));
		list.add(new BasicNameValuePair("status", "in_progress"));
		list.add(new BasicNameValuePair("device_id", it.getDelivery().getDeviceId()));
		list.add(new BasicNameValuePair("listing_id", it.getDelivery().getListingId()));
		list.add(new BasicNameValuePair("latitude", "" + SwaggyCustomer.myLatLng.latitude));
		list.add(new BasicNameValuePair("longitude", "" + SwaggyCustomer.myLatLng.longitude));

		
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
				Utils.getFilledUrl(UrlConstants.setOrderStatus, list), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						
						
						Intent intent = new Intent(ProductActivity.this, MapActivity.class);
						intent.putExtra("pid", pos);
						startActivity(intent);
					}

				}

				, new com.android.volley.Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						
						
						Intent intent = new Intent(ProductActivity.this, MapActivity.class);
						intent.putExtra("pid", pos);
						startActivity(intent);
					}

				});
		jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		SwaggyCustomer.queue.add(jsonRequest);
	}
}
