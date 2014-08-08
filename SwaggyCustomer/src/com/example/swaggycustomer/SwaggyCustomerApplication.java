package com.example.swaggycustomer;

import java.util.ArrayList;

import models.Item;
import android.app.Application;

import com.android.volley.toolbox.Volley;

public class SwaggyCustomerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SwaggyCustomer.queue = Volley.newRequestQueue(getApplicationContext());
		SwaggyCustomer.items = new ArrayList<Item>();
	}
	
}
