package com.example.swaggycustomer;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import map.CustomerLocationUpdater;
import models.Item;

public class SwaggyCustomer {

	public static RequestQueue queue;

	public static Gson gson = new GsonBuilder().create();

	public static boolean isGooglePlayServicesAvailable;

	public static Location customerLocation;
	
	public static CustomerLocationUpdater customerLocationUpdateHandler;
	
	public static List<Item> items; 

	
}
