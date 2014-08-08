package com.example.swaggycustomer;

import models.Item;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class ProductActivity extends SwaggyCustomerActivity {
	TextView tv1,tv2,tv3,tv4;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_item_desc);
		
		
		TextView tv1 = (TextView) findViewById(R.id.tv1);
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		TextView tv3 = (TextView) findViewById(R.id.tv3);
		TextView tv4 = (TextView) findViewById(R.id.tv4);

		int pos = getIntent().getIntExtra("pid", 0);
		
		Item it = SwaggyCustomer.items.get(pos);
		tv1.setText(it.getAttributes().getTitle());
		tv1.setTextColor(Color.BLACK);
		tv2.setText("Rs." + it.getAttributes().getPrice());
		tv3.setText((Integer.parseInt(it.getEta()))/60 + " mins");
		tv4.setText(it.getAttributes().getDescription());
	}
}
