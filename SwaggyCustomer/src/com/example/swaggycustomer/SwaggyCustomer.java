package com.example.swaggycustomer;

import java.util.List;

import map.CustomerLocationUpdater;
import models.Item;
import android.location.Location;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SwaggyCustomer {

	public static RequestQueue queue;

	public static Gson gson = new GsonBuilder().setFieldNamingPolicy(
			FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	public static boolean isGooglePlayServicesAvailable;

	public static Location customerLocation;
	
	public static CustomerLocationUpdater customerLocationUpdateHandler;
	
	public static List<Item> items; 

	public static LatLng myLatLng = new LatLng(12.928036, 77.633037);
	
	public static int increasingProductId = 3848;
}
