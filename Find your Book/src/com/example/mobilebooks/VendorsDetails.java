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

public class VendorsDetails extends Activity {
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
		setContentView(R.layout.activity_vendors_details);
		title = (TextView) findViewById(R.id.title);
		author = (TextView) findViewById(R.id.author);

		prefs = CompleteDetails.applicationContext.getSharedPreferences(
				"com.example.mobilebooks", MODE_WORLD_WRITEABLE);
		check = prefs.getBoolean(Preferences.BUY, false);
		if (check) {

			Client.getPaticularBook(CompleteDetails.searchIndex);
			System.out.println(CompleteDetails.searchIndex);
			flag = "buy";
			System.out.println("Buy");

		} else {
			check = prefs.getBoolean(Preferences.SELL, false);
			if (check) {

				flag = "sell";
				System.out.println("SELL");

			} else {

				System.out.println("RENT");
				flag = "rent";
			}
		}
		title.setText(prefs.getString(Preferences.TITLE,
				CompleteDetails.searchIndex));
		author.setText(prefs.getString(Preferences.AUTHOR, "Anonymous"));
		tableLayout = (RelativeLayout) findViewById(R.id.tableLayout);

		boolean checkRent = prefs.getBoolean(Preferences.RENT, false);
		// if (!checkRent) {
		// addTableRow(tableLayout);
		// } else {

		usedBookDetails();
		// }
	}

	/*
	 * public void addTableRow(View v) { TableLayout tl = (TableLayout)
	 * findViewById(R.id.vendors); TableRow tr = new TableRow(this);
	 * LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
	 * LayoutParams.WRAP_CONTENT); tr.setLayoutParams(lp);
	 * 
	 * SharedPreferences prefs = getSharedPreferences(getPackageName(),
	 * MODE_WORLD_READABLE); TextView first = new TextView(this);
	 * first.setLayoutParams(lp); first.setBackgroundColor(Color.WHITE);
	 * first.setText("Amazon");
	 * 
	 * first.setPadding(45, 45, 45, 45); TextView third = new TextView(this);
	 * third.setLayoutParams(lp); third.setBackgroundColor(Color.WHITE); if
	 * (flag.contains("buy")) { third.setText(prefs.getString(Preferences.PRICE,
	 * "$$"));
	 * 
	 * } else if (flag.contains("sell")) { third.setText(cursor.getString(7));
	 * 
	 * } else if (flag.contains("rent")) { third.setText(cursor.getString(8));
	 * 
	 * } third.setPadding(45, 45, 45, 45);
	 * 
	 * TextView second = new TextView(this); second.setLayoutParams(lp);
	 * second.setBackgroundColor(Color.WHITE); second.setPadding(45, 45, 45,
	 * 45); second.setText("Amazon");
	 * 
	 * TextView fourth = new TextView(this); fourth.setLayoutParams(lp);
	 * fourth.setBackgroundColor(Color.WHITE);
	 * fourth.setText("Amazon.com/support"); fourth.setPadding(45, 45, 45, 45);
	 * 
	 * tr.addView(first);
	 * 
	 * tr.addView(third); tr.addView(second); tr.addView(fourth); tl.addView(tr,
	 * new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
	 * LayoutParams.WRAP_CONTENT)); }
	 */
	private void usedBookDetails() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				System.out.println("Serching second hand books");

				ParseQuery<ParseObject> queryVendors = ParseQuery
						.getQuery("BetaObject");
				queryVendors.whereEqualTo(Preferences.TITLE,
						CompleteDetails.searchIndex);
				List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
				queries.add(queryVendors);
				ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
				mainQuery.whereEqualTo(Preferences.SELL_CHECK, "1");

				mainQuery.findInBackground(new FindCallback<ParseObject>() {
					@Override
					public void done(List<ParseObject> vendorsList,
							ParseException arg1) {
						// TODO Auto-generated method stub

						System.out.println(vendorsList.size());
						for (int i = 0; i < vendorsList.size(); i++) {
							addUsedVenorRow(tableLayout, vendorsList, i);

						}
					}

				});
			}
		});
		t.start();
	}

	/*
	 * public boolean getUsedAvailabelDetails(final Context context) { getInfo =
	 * new Thread(new Runnable() {
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * System.out.println("He is finding"); temp = 1;
	 * 
	 * ParseQuery<ParseObject> queryVendors = ParseQuery
	 * .getQuery("BetaObject");
	 * queryVendors.whereEqualTo(Preferences.SELL_CHECK, "1");
	 * System.out.println("He is finding");
	 * 
	 * queryVendors.findInBackground(new FindCallback<ParseObject>() {
	 * 
	 * @Override public void done(List<ParseObject> arg0, ParseException arg1) {
	 * // TODO Auto-generated method stub if (arg0.size() > 0) { temp = 0; }
	 * else { temp = 1; } System.out.println("He found");
	 * 
	 * getInfo.notifyAll(); } });
	 * 
	 * } });
	 * 
	 * getInfo.start();
	 * 
	 * synchronized (Thread.currentThread()) {
	 * 
	 * try {
	 * 
	 * Thread.currentThread().wait(); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace();
	 * 
	 * // getInfo.notify(); } if (temp == 0) { return true; } else { return
	 * false; } } }
	 */
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
		first.setText(vendorsList.get(i).getString(Preferences.SELLER_NAME));

		first.setPadding(45, 45, 45, 45);
		TextView third = new TextView(this);
		third.setLayoutParams(lp);
		first.setBackgroundColor(0xff195E19);
		if (flag.contains("buy")) {
			third.setText(vendorsList.get(i).getString(
					Preferences.SELLER_CONTACT));

		} else if (flag.contains("rent")) {
			third.setText(vendorsList.get(i).getString(
					Preferences.SELLER_CONTACT));

		}
		third.setPadding(45, 45, 45, 45);
		third.setBackgroundColor(0xff195E19);

		TextView second = new TextView(this);
		second.setLayoutParams(lp);
		second.setText(vendorsList.get(i).getString("TITLE"));
		second.setBackgroundColor(0xff195E19);
		second.setPadding(45, 45, 45, 45);

		TextView fourth = new TextView(this);
		fourth.setLayoutParams(lp);
		fourth.setBackgroundColor(0xff195E19);
		fourth.setText(vendorsList.get(i).getString(Preferences.PRICE));
		fourth.setPadding(45, 45, 45, 45);

		tr.addView(first);

		tr.addView(third);
		tr.addView(second);
		tr.addView(fourth);
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
