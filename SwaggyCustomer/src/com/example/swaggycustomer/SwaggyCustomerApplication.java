package com.example.swaggycustomer;

import com.android.volley.toolbox.Volley;

import android.app.Application;

public class SwaggyCustomerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SwaggyCustomer.queue = Volley.newRequestQueue(getApplicationContext());
	}
	
}
