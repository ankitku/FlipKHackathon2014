package map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.swaggycustomer.SwaggyCustomer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class CustomerLocationUpdater implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener {

	private static LocationClient mLocationClient;
	private static final String TAG = "LocationUpdater";
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(1000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_LOW_POWER);

	public void start(Context context) {
		setUpLocationClientIfNeeded(context);
		if (!mLocationClient.isConnected()) {
			mLocationClient.connect();
			Log.d(TAG, "mLocation Client CONNECTION INITIATE, activity->"
					+ context.getApplicationContext().getClass());
		}
	}

	public void stop() {
		if (mLocationClient != null && mLocationClient.isConnected()) {
			mLocationClient.disconnect();
			Log.d(TAG, "mLocation Client DISCONNECTED");
		}
	}

	private void setUpLocationClientIfNeeded(Context context) {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(context, this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		SwaggyCustomer.customerLocation = location;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.d(TAG, "mLocation Client CONNECTION FAILED");
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.d(TAG, "mLocation Client CONNECTED");
		mLocationClient.requestLocationUpdates(REQUEST, this); // LocationListener
	}

	@Override
	public void onDisconnected() {
	}

	public static Location getLastKnownLocation(Context context) {
		Location mCurrentLocation;
	    mCurrentLocation = mLocationClient.getLastLocation();
//	    if(mCurrentLocation == null)
//	    	mCurrentLocation = new Location(l)
//			lastKnownNetworkLocation.setLatitude(12.926934);
//			lastKnownNetworkLocation.setLongitude(77.6327);

		return mCurrentLocation;
	}

	public long getAgeOfLocation(Location loc) {
		if (loc == null)
			return 2147483647;
		else
			return (long) (System.currentTimeMillis() - loc.getTime()) / 1000;
	}

	public boolean isLocationProviderEnabled(final Context context) {
		String provider = Settings.Secure.getString(
				context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		boolean locationEnabled = provider.contains("gps")
				&& provider.contains("network") ? true : false;

		if (!locationEnabled) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("No location access");
			builder.setMessage("Please allow Ola cabs to access your location. Turn it ON from Location Services");
			builder.setCancelable(false);
			builder.setPositiveButton("Settings",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int id) {
							context.startActivity(new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							dialog.cancel();
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int id) {
							dialog.cancel();
						}
					});
			final AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
		return true;
	}

	public boolean isLocationNonNull(final Context context) {

		if (SwaggyCustomer.customerLocation == null) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Sorry! We could not locate you. Please try again!");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int id) {
							dialog.cancel();
						}
					});
			final AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
		return true;
	}

}