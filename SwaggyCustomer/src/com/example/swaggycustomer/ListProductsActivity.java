package com.example.swaggycustomer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import map.CustomerLocationUpdater;
import models.Item;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import constants.UrlConstants;

public class ListProductsActivity extends SwaggyCustomerActivity {

	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_products);
		createImageCache();
		createUIelems();
	}

	private void createUIelems() {

		listview = (ListView) findViewById(R.id.productsListView);

		if (SwaggyCustomer.customerLocationUpdateHandler == null)
			SwaggyCustomer.customerLocationUpdateHandler = new CustomerLocationUpdater();

		SwaggyCustomer.customerLocationUpdateHandler
				.start(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (SwaggyCustomer.customerLocation == null);
	//		SwaggyCustomer.customerLocation =  CustomerLocationUpdater.getLastKnownLocation(getApplicationContext());

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("latitude", ""
				+ 12.931552));
		list.add(new BasicNameValuePair("longitude", ""
				+ 77.621428));

		JsonArrayRequest jsonRequest = new JsonArrayRequest(Utils.getFilledUrl(
				UrlConstants.getListings, list),
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						try {
							SwaggyCustomer.items.clear();
							for (int i = 0; i < response.length(); i++) {
								JSONObject jsob = response.getJSONObject(i);
								
					            Iterator iter = jsob.keys();
					            while(iter.hasNext()){
					                String key = (String)iter.next();
					                String value = jsob.getString(key);
					                SwaggyCustomer.items.add(SwaggyCustomer.gson.fromJson(
											value.toString(), Item.class));
					                Log.d("Key Value","key: "+key+" Value: "+value);
					            }
					            
								
							}

							fillList(SwaggyCustomer.items);

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
		jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
		SwaggyCustomer.queue.add(jsonRequest);
	}

	private void fillList(final List<Item> items) {
		listview.setAdapter(null);
		listview.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(final int pos, View convertView, ViewGroup parent) {
				LayoutInflater mInflater = (LayoutInflater) 
			            getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = mInflater.inflate(R.layout.layout_list_item, null);
				
				TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
				TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);
				TextView tv3 = (TextView) convertView.findViewById(R.id.tv3);

				ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
				Item it = items.get(pos);
				
				tv1.setText(it.getAttributes().getTitle());
				tv1.setTextColor(Color.BLACK);
				tv2.setText("Rs." + it.getAttributes().getPrice());
				tv3.setText((Integer.parseInt(it.getEta()))/60 + " mins");
				//loadImage(it.getAttributes().getImageLink(), iv);
				
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ListProductsActivity.this, ProductActivity.class);
						intent.putExtra("pid", pos);
						startActivity(intent);
					}
				});
				
				return convertView;
		}
			
			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int pos) {
				// TODO Auto-generated method stub
				return items.get(pos);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return items.size();
			}
		});
	}
}
