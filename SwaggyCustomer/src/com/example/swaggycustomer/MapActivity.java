package com.example.swaggycustomer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import util.Utils;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import constants.UrlConstants;

public class MapActivity extends SwaggyCustomerActivity {

	private Handler handler = new Handler();

	int pos;
	TextView etaTV;
	private boolean fieldsVisible, isCentered, isMsgPublic;
	String eta;
	LatLng DBlatlng;
	protected GoogleMap mapFragment;

	// Global constants
	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	protected final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	public static class ErrorDialogFragment extends DialogFragment {
		protected Dialog mDialog;

		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	Runnable r = new Runnable() {

		@Override
		public void run() {

			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("order_id", "OD"
					+ SwaggyCustomer.increasingProductId));

			JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
					Utils.getFilledUrl(UrlConstants.getEta, list), null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							try {
								etaTV.setText("" + response.optInt("eta") / 60
										+ " mins");

								DBlatlng = new LatLng(response.getJSONObject(
										"delivery_location").getDouble("x"),
										response.getJSONObject(
												"delivery_location").getDouble(
												"y"));

								mapFragment.clear();
								drawMarkers();
								
								handler.postDelayed(r, 4000);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

					, new com.android.volley.Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
						}

					});
			jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			SwaggyCustomer.queue.add(jsonRequest);

		}
	};

	protected boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			int errorCode = resultCode;
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					errorCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			if (errorDialog != null) {
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
			}
			return false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map);
		handler = new Handler();
		etaTV = (TextView) findViewById(R.id.etaTV);

		SwaggyCustomer.isGooglePlayServicesAvailable = servicesConnected();

		if (SwaggyCustomer.isGooglePlayServicesAvailable) { // activity specific
															// map needs
			setUpMapIfNeeded();

			mapFragment.getUiSettings().setCompassEnabled(true);
			mapFragment.setMyLocationEnabled(true);
			mapFragment.setIndoorEnabled(true);
			mapFragment.getUiSettings().setMyLocationButtonEnabled(true);
			mapFragment.getUiSettings().setAllGesturesEnabled(true);
		}
		pos = getIntent().getIntExtra("pid", 0);
		handler.post(r);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mapFragment.clear();

	}

	@Override
	protected void onPause() {
		super.onPause();
		mapFragment.clear();
	}

	protected void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mapFragment == null) {
			// Try to obtain the map from the SupportMapFragment.
			mapFragment = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
		}
	}

	private void drawMarkers() {

		LatLng productLatLng = new LatLng(
				Double.parseDouble(SwaggyCustomer.items.get(pos)
						.getAttributes().getLatitude()),
				Double.parseDouble(SwaggyCustomer.items.get(pos)
						.getAttributes().getLongitude()));

		final Marker a = mapFragment.addMarker(new MarkerOptions()
				.position(productLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

		final Marker b = mapFragment.addMarker(new MarkerOptions()
				.position(SwaggyCustomer.myLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

		final Marker c = mapFragment.addMarker(new MarkerOptions()
				.position(DBlatlng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

		LatLngBounds bounds = new LatLngBounds.Builder().include(productLatLng)
				.include(SwaggyCustomer.myLatLng).include(DBlatlng).build();

		Point displaySize = new Point();
		getWindowManager().getDefaultDisplay().getSize(displaySize);

		mapFragment.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,
				displaySize.x, 250, 30));

	}
}