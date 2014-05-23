package com.example.mobilebooks;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SellingActivity extends Activity {

	String author, title, price;
	Button submit;

	EditText name, price_, contact, price_sell, price_rent, isbn, customerName,
			customerContact;
	TextView author_, title_;
	CheckBox rent_check, sell_check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selling);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					Parse.initialize(getApplicationContext(),

					"hhvNehUIwK1iY8Bmtt4FnFoHxWwo0dNgwSnqi7iz",
							"B9R0dIdKHeRHyPI0YbShuX9Kapd60yURT7H1c26Z");// this,
				} catch (NullPointerException n) {

				}
			}
		});
		t.start();
		SharedPreferences prefs = getSharedPreferences(
				"com.example.mobilebooks", MODE_WORLD_READABLE);
		author = prefs.getString(Preferences.AUTHOR, "Anonymous");
		title = prefs.getString(Preferences.TITLE, "Anonymouse");
		// price = prefs.getString(Preferences.PRICE, "$$");

		author_ = (TextView) findViewById(R.id.sell_author);
		title_ = (TextView) findViewById(R.id.sell_title);

		isbn = (EditText) findViewById(R.id.isbn);
		customerContact = (EditText) findViewById(R.id.contact);
		name = (EditText) findViewById(R.id.name);
		price_sell = (EditText) findViewById(R.id.sell_price);
		price_rent = (EditText) findViewById(R.id.rent_price);
		
		rent_check = (CheckBox) findViewById(R.id.rent_check);
		sell_check = (CheckBox) findViewById(R.id.sell_check);

		submit = (Button) findViewById(R.id.submit);
		author_.setText("Author : "+author);

		title_.setText(title);

		isbn.setText(CompleteDetails.bookIsbn_);
		customerContact.setText(RegisterActivity.emailAddress);
		name.setText(RegisterActivity.name);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				System.out.println("else Hell is here"
						+ name.getText().toString());

				ParseObject testObject = new ParseObject("BetaObject");
				testObject.put(Preferences.AUTHOR, author);
				testObject.put(Preferences.TITLE, title);
				testObject.put(Preferences.PRICE, price_sell.getText()
						.toString());
				testObject.put(Preferences.RENT, price_rent.getText()
						.toString());
				testObject.put(Preferences.ISBN, isbn.getText().toString());
				testObject.put(Preferences.SELLER_CONTACT, customerContact
						.getText().toString());
				testObject.put(Preferences.SELLER_NAME, name.getText()
						.toString());
				System.out.println("New code added here"
						+ name.getText().toString());
				// ParseInstallation.getCurrentInstallation().saveInBackground();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

					}
				});

				System.out.println(CompleteDetails.bookIsbn_);
				PushService.subscribe(SellingActivity.this,
						CompleteDetails.bookIsbn_, BuyerActivity.class);
				System.out.println(CompleteDetails.bookIsbn_);

				ParseInstallation.getCurrentInstallation().saveInBackground();

				
				if (sell_check.isChecked()) {
					testObject.put(Preferences.SELL_CHECK, "1");

				} else {
					testObject.put(Preferences.SELL_CHECK, "0");

				}
				if (rent_check.isChecked()) {
					testObject.put(Preferences.RENT_CHECK, "1");

				} else {
					testObject.put(Preferences.RENT_CHECK, "0");

				}

				testObject.saveInBackground();
				Intent entryactivity = new Intent(SellingActivity.this,
						EntryActivity.class);
				startActivity(entryactivity);

			}

		});
	}

	public String generateKeyword() {
		// TODO Auto-generated method stub
		String keyword;

		if (CompleteDetails.searchIndex.contains("-")) {
			CompleteDetails.searchIndex.replace("-", " ");
			System.out.println("Lets see what we created"
					+ CompleteDetails.searchIndex);

		}
		if (CompleteDetails.searchIndex.contains(",")) {
			CompleteDetails.searchIndex.replace(",", " ");

			System.out.println("Lets see what we created"
					+ CompleteDetails.searchIndex);
		}
		if (CompleteDetails.searchIndex.contains(",")) {
			CompleteDetails.searchIndex.replace("'", " ");

			System.out.println("Lets see what we created"
					+ CompleteDetails.searchIndex);

		}
		if (CompleteDetails.searchIndex.contains("(")) {
			CompleteDetails.searchIndex.replace("(", " ");

			System.out.println("Lets see what we created"
					+ CompleteDetails.searchIndex);
		}
		if (CompleteDetails.searchIndex.contains(")")) {
			CompleteDetails.searchIndex.replace(")", " ");

			System.out.println("Lets see what we created"
					+ CompleteDetails.searchIndex);
		}
		CompleteDetails.searchIndex.replace(" ", "");
		System.out.println("Lets see what we created"
				+ CompleteDetails.searchIndex);
		return CompleteDetails.searchIndex;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selling, menu);
		return true;
	}

}
