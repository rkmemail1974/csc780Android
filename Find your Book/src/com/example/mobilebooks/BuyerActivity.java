package com.example.mobilebooks;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BuyerActivity extends Activity {
	SharedPreferences prefs;
	private boolean check;
	private static String flag;
	Cursor cursor;
	TextView title, author, vendorsUsed, priceUsed, shippingUsed;
	RelativeLayout tableLayout;
	public int temp;
	public Thread getInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buyer);
		title = (TextView) findViewById(R.id.title);
		author = (TextView) findViewById(R.id.author);

//		prefs = CompleteDetails.applicationContext.getSharedPreferences(
//				"com.example.mobilebooks", MODE_WORLD_READABLE);
//		check = prefs.getBoolean(Preferences.BUY, false);
//		if (check) {
//
//			Client.getPaticularBook(CompleteDetails.searchIndex);
//			System.out.println(CompleteDetails.searchIndex);
//			flag = "buy";
//			System.out.println("Buy");
//
//		} else {
//			check = prefs.getBoolean(Preferences.SELL, false);
//			if (check) {
//
//				flag = "sell";
//				System.out.println("SELL");
//
//			} else {
//
//				System.out.println("RENT");
//				flag = "rent";
//			}
//		}
//		title.setText(prefs.getString(Preferences.TITLE,
//				CompleteDetails.searchIndex));
//		author.setText(prefs.getString(Preferences.AUTHOR, "Anonymous"));
		tableLayout = (RelativeLayout) findViewById(R.id.tableLayout);

//		boolean checkRent = prefs.getBoolean(Preferences.RENT, false);

		usedBookDetails();
	}

	private void usedBookDetails() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				System.out.println("Serching second hand books");

				ParseQuery<ParseObject> queryVendors = ParseQuery
						.getQuery("CurrentUser");
				
				
//				queryVendors.whereEqualTo(Preferences.SELL_CHECK, "1");

				queryVendors.findInBackground(new FindCallback<ParseObject>() {
					@Override
					public void done(List<ParseObject> vendorsList,
							ParseException arg1) {
						// TODO Auto-generated method stub

						System.out.println(vendorsList.size());
//						for (int i = 0; i < vendorsList.size(); i++) {
						int counter =vendorsList.size()-1;
							addUsedVenorRow(tableLayout, vendorsList, counter);

//						}
					}

				});
			}
		});
		t.start();
	}

	public void addUsedVenorRow(View v, List<ParseObject> vendorsList, int i) {
		TableLayout tl = (TableLayout) findViewById(R.id.vendors);
		TableRow tr = new TableRow(this);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		tr.setLayoutParams(lp);

		SharedPreferences prefs = getSharedPreferences(getPackageName(),
				MODE_WORLD_READABLE);
		TextView first = new TextView(this);
		first.setLayoutParams(lp);
		first.setBackgroundColor(0xff195E19);
		// first.setText(cursor.getString(4));
		first.setText(vendorsList.get(i).getString("name"));

		first.setPadding(45, 45, 45, 45);
		TextView third = new TextView(this);
		third.setLayoutParams(lp);
		first.setBackgroundColor(0xff195E19);
//		if (flag.contains("buy")) {
			third.setText(vendorsList.get(i).getString(
					"contact"));

//		} else if (flag.contains("rent")) {
			third.setText(vendorsList.get(i).getString("contact"));

//		}
		third.setPadding(45, 45, 45, 45);
		third.setBackgroundColor(0xff195E19);

		TextView second = new TextView(this);
		second.setLayoutParams(lp);
//		second.setText(vendorsList.get(i).getString("contact"));
		second.setBackgroundColor(0xff195E19);
		second.setPadding(45, 45, 45, 45);

		TextView fourth = new TextView(this);
		fourth.setLayoutParams(lp);
		fourth.setBackgroundColor(0xff195E19);
//		fourth.setText(vendorsList.get(i).getString("username"));
		fourth.setPadding(45, 45, 45, 45);

		tr.addView(first);

		tr.addView(third);
		tr.addView(second);
//		tr.addView(fourth);
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vendors_details, menu);
		return true;
	}

}
