package com.example.swaggycustomer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends SwaggyCustomerActivity implements OnItemSelectedListener {

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
//
//	private Handler handler;
//
//	private boolean fieldsVisible, isCentered, isMsgPublic;
//
//	protected GoogleMap mapFragment;
//
//	// Global constants
//	/*
//	 * Define a request code to send to Google Play services This code is
//	 * returned in Activity.onActivityResult
//	 */
//	protected final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
//
//	public static class ErrorDialogFragment extends DialogFragment {
//		protected Dialog mDialog;
//
//		public ErrorDialogFragment() {
//			super();
//			mDialog = null;
//		}
//
//		public void setDialog(Dialog dialog) {
//			mDialog = dialog;
//		}
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//			return mDialog;
//		}
//	}
//
//	protected boolean servicesConnected() {
//		int resultCode = GooglePlayServicesUtil
//				.isGooglePlayServicesAvailable(this);
//		if (ConnectionResult.SUCCESS == resultCode) {
//			Log.d("Location Updates", "Google Play services is available.");
//			return true;
//		} else {
//			int errorCode = resultCode;
//			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
//					errorCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//
//			if (errorDialog != null) {
//				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//				errorFragment.setDialog(errorDialog);
//			}
//			return false;
//		}
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.activity_map);
//		createImageCache();
//
//		SwaggyCustomer.isGooglePlayServicesAvailable = servicesConnected();
//
//		if (SwaggyCustomer.isGooglePlayServicesAvailable) { // activity specific
//															// map needs
//			setUpMapIfNeeded();
//
//			mapFragment.getUiSettings().setCompassEnabled(true);
//			mapFragment.setMyLocationEnabled(true);
//			mapFragment.setIndoorEnabled(true);
//			mapFragment.getUiSettings().setMyLocationButtonEnabled(true);
//			mapFragment.getUiSettings().setAllGesturesEnabled(true);
//
//			mapFragment.setInfoWindowAdapter(new CustomInfoWindowAdapter());
//			initAnimations();
//		}
//
//		isMsgPublic = true;
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//
//		mapFragment.clear();
//
//		drawMarkers();
// }
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mapFragment.clear();
//	}
//
//	protected void setUpMapIfNeeded() {
//		// Do a null check to confirm that we have not already instantiated the
//		// map.
//		if (mapFragment == null) {
//			// Try to obtain the map from the SupportMapFragment.
//			mapFragment = ((SupportMapFragment) getSupportFragmentManager()
//					.findFragmentById(R.id.map)).getMap();
//		}
//	}
//
//	private void drawMarkers() {
//		for (final GeoMessage gm : GeoMessenger.geoMessages.getResult()) {
//
//			LatLng p = new LatLng(gm.getLoc()[0], gm.getLoc()[1]);
//
//			loadImage(gm.getFromUserPic(), null);
//
//			final Marker m = mapFragment
//					.addMarker(new MarkerOptions()
//							.position(p)
//							.title(gm.getFromUserName())
//							.icon(BitmapDescriptorFactory.defaultMarker(gm
//									.isSeen() ? BitmapDescriptorFactory.HUE_BLUE
//									: BitmapDescriptorFactory.HUE_RED))
//							.snippet(
//									Utils.getHumanReadableTime(gm
//											.getTimestamp())));
//
//			if (!gm.isSeen())
//				animateMarker(m);
//
//			markers.put(m.getId(), gm);
//		}
//
//	}
//
//	private void animateMarker(final Marker marker) {
//		// Make the marker bounce
//		final Handler handler = new Handler();
//
//		final long startTime = SystemClock.uptimeMillis();
//		final long duration = 2000;
//
//		Projection proj = mapFragment.getProjection();
//		final LatLng markerLatLng = marker.getPosition();
//		Point startPoint = proj.toScreenLocation(markerLatLng);
//		startPoint.offset(0, -100);
//		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
//
//		final Interpolator interpolator = new BounceInterpolator();
//
//		handler.post(new Runnable() {
//			@Override
//			public void run() {
//				long elapsed = SystemClock.uptimeMillis() - startTime;
//				float t = interpolator.getInterpolation((float) elapsed
//						/ duration);
//				double lng = t * markerLatLng.longitude + (1 - t)
//						* startLatLng.longitude;
//				double lat = t * markerLatLng.latitude + (1 - t)
//						* startLatLng.latitude;
//				marker.setPosition(new LatLng(lat, lng));
//
//				if (t < 1.0) {
//					// Post again 16ms later.
//					handler.postDelayed(this, 16);
//				}
//			}
//		});
//	}
//
//
//	public void showFriendsPicker(View v) {
//		if (Session.getActiveSession() != null
//				&& Session.getActiveSession().isOpened()) {
//
//			GeoMessenger.getSelectedUsers().clear();
//			friendsPickerLayout.removeAllViews();
//
//			Intent intent = new Intent();
//			intent.setClass(MapActivity.this, PickerActivity.class);
//			startActivityForResult(intent, GET_SELECTED_FRIENDS);
//		}
//	}
//
//	public void removePic(View v) {
//		uploadImageLayout.setVisibility(View.GONE);
//		uploadPic.setImageBitmap(null);
//		selectedImageName = null;
//		selectedBitmap = null;
//	}
//
//	public void sendPic() {
//		(new UploadPicAsyncTask()).execute();
//	}
//
//	private void sendMessage() {
//		mProgress.setVisibility(View.VISIBLE);
//
//		String msg = messageText.getText().toString();
//		if(Utils.isEmpty(msg))
//		{
//			new AlertDialog.Builder(MapActivity.this).setTitle("Empty message")
//			.setMessage("Can't save empty message!")
//			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.cancel();
//				}
//			}).show();
//			return;
//		}
//
//		JSONObject jsonObjectRequest = new JSONObject();
//
//		JSONArray geoMessages = new JSONArray();
//
//		double loc[] = { GeoMessenger.customerLocation.getLatitude(),
//				GeoMessenger.customerLocation.getLongitude() };
//		try {
//			for (GraphUser g : GeoMessenger.getSelectedUsers()) {
//				JSONObject gm = new JSONObject();
//				gm.put("loc", new JSONArray(Arrays.toString(loc)));
//				gm.put("message", msg);
//				gm.put("fromUserId", GeoMessenger.userId);
//				gm.put("fromUserName", GeoMessenger.userName);
//				gm.put("toUserId", g.getId());
//				gm.put("toUserName", g.getName());
//
//				if (ratingBar.isShown())
//					gm.put("rating", ratingBar.getRating());
//				if (!Utils.isEmpty(selectedImageName))
//					gm.put("picName", selectedImageName);
//
//				geoMessages.put(gm);
//			}
//
//			JSONObject gm = new JSONObject();
//			gm.put("loc", new JSONArray(Arrays.toString(loc)));
//			gm.put("message", msg);
//			gm.put("fromUserId", GeoMessenger.userId);
//			gm.put("fromUserName", "MySelf");
//
//			if (ratingBar.isShown())
//				gm.put("rating", ratingBar.getRating());
//			if (!Utils.isEmpty(selectedImageName))
//				gm.put("picName", selectedImageName);
//
//			if (!isMsgPublic) {
//				if (selfCheckBox.isChecked()) {
//					gm.put("toUserId", GeoMessenger.userId);
//					gm.put("toUserName", "MySelf");
//					geoMessages.put(gm);
//				}
//			} else {
//				gm.put("toUserId", "public");
//				gm.put("toUserName", "public");
//				geoMessages.put(gm);
//			}
//
//			jsonObjectRequest.put("geo_messages", geoMessages);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		JsonObjectRequest jsonGeoMessagesRequest = new JsonObjectRequest(
//				Method.POST, UrlConstants.getCreateGMUrl(), jsonObjectRequest,
//				new Response.Listener<JSONObject>() {
//
//					@Override
//					public void onResponse(JSONObject response) {
//						hideFields();
//						showAlertDialog();
//						messageText.setText("");
//						mProgress.setVisibility(View.GONE);
//						fetchUpdatedMessages();
//
//						saveButton.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View arg0) {
//								showFields();
//							}
//						});
//					}
//
//				}, new Response.ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						mProgress.setVisibility(View.GONE);
//					}
//
//				});
//
//		GeoMessenger.queue.add(jsonGeoMessagesRequest);
//	}
//
//	public void picSource(View v) {
//		AlertDialog.Builder sourceSelection = new AlertDialog.Builder(this);
//
//		final CharSequence[] items = { "Take Photo", "Attach Photo" };
//
//		sourceSelection.setSingleChoiceItems(items, -1,
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int item) {
//						if (item == 1)
//							albumPic();
//						else
//							camPic();
//						dialog.dismiss();
//					}
//				});
//
//		AlertDialog alert = sourceSelection.create();
//		alert.show();
//
//	}
//
//	private void camPic() {
//		final Intent cameraIntent = new Intent(
//				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(cameraIntent, CAM_PIC);
//	}
//
//	private void albumPic() {
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.setType("image/*");
//		startActivityForResult(intent, PHOTO_SELECTED);
//	}
//
//protected void centerMap() {
//		if (GeoMessenger.customerLocation != null) {
//			LatLng p = new LatLng(GeoMessenger.customerLocation.getLatitude(),
//					GeoMessenger.customerLocation.getLongitude());
//			mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(p, 16.0f));
//			isCentered = true;
//		}
//	}
//
//
}