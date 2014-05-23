package com.example.mobilebooks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CompleteDetails extends Activity implements OnClickListener {
	public static String searchIndex, searchISBN;
	TextView heading, bookName, bookPrice, bookEdition, bookAuthor,
			newAvailabel, usedAvailabel;
	ImageView bookImage;
	public URL url;
	public Bitmap myBitmap;
	Button buy, sell, rent;
	private SharedPreferences prefs;
	public static Context applicationContext;
	public static String heading_, bookName_, bookAuthor_, bookPrice_,
			bookEdition_, bookIsbn_;
	public static int bookImageFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_details);
		searchIndex = getIntent().getStringExtra(BookLists.SEARCH);
		heading = (TextView) findViewById(R.id.heading);
		bookName = (TextView) findViewById(R.id.bookName);
		bookAuthor = (TextView) findViewById(R.id.bookAuthor);
		bookEdition = (TextView) findViewById(R.id.bookEdition);
		bookPrice = (TextView) findViewById(R.id.bookPrice);
		usedAvailabel = (TextView) findViewById(R.id.used_available);
		newAvailabel = (TextView) findViewById(R.id.new_available);
bookImage= (ImageView) findViewById(R.id.bookImage);
		buy = (Button) findViewById(R.id.buy);

		sell = (Button) findViewById(R.id.sell);

		rent = (Button) findViewById(R.id.rent);
		applicationContext = getApplicationContext();
		prefs = applicationContext.getSharedPreferences(
				"com.example.mobilebooks", MODE_WORLD_WRITEABLE);
		// all the variables bookname_ .... are defined in client.java
		heading.setText(bookName_);
		bookName.setText(bookName_);
		bookAuthor.setText(bookAuthor_);
		bookPrice.setText(bookPrice_);
		prefs.edit().putString(Preferences.TITLE, bookName_).commit();
		prefs.edit().putString(Preferences.AUTHOR, bookAuthor_).commit();
		prefs.edit().putString(Preferences.PRICE, bookPrice_).commit();
	

			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					HttpURLConnection connection;
					try {
						url = new URL(BookLists.image[bookImageFlag]);
						connection = (HttpURLConnection) url
								.openConnection();
						connection.setDoInput(true);
						connection.connect();
						InputStream input = connection.getInputStream();

						myBitmap = BitmapFactory.decodeStream(input);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch(NullPointerException n){
						
					}

				}
			});
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			bookImage.setImageBitmap(myBitmap);

		buy.setOnClickListener(this);
		sell.setOnClickListener(this);
		rent.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.complete_details, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent vendorsActivity;
		vendorsActivity = new Intent(CompleteDetails.this, VendorsDetails.class);
		switch (v.getId()) {
		case R.id.buy:
			Intent i = new Intent(CompleteDetails.this, AmazonActivity.class);

			System.out.println("Push Sendt");
			prefs.edit().putBoolean(Preferences.RENT, false).commit();
			prefs.edit().putBoolean(Preferences.BUY, true).commit();
			prefs.edit().putBoolean(Preferences.SELL, false).commit();
			startActivity(i);
			break;
		case R.id.sell:

			prefs.edit().putBoolean(Preferences.RENT, false).commit();
			prefs.edit().putBoolean(Preferences.BUY, false).commit();
			prefs.edit().putBoolean(Preferences.SELL, true).commit();

			Intent sell = new Intent(CompleteDetails.this,
					SellingActivity.class);
			startActivity(sell);
			break;
		case R.id.rent:
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {

					ParsePush push = new ParsePush();
					push.setChannel(CompleteDetails.bookIsbn_);

					push.clearExpiration();

					push.setMessage("A New Buyer Is looking for The book You Posted on : \n Find your books");
					push.sendInBackground();

				}
			});
			t.start();
			prefs.edit().putBoolean(Preferences.RENT, true).commit();
			prefs.edit().putBoolean(Preferences.BUY, false).commit();
			prefs.edit().putBoolean(Preferences.SELL, false).commit();

			startActivity(vendorsActivity);
			break;

		}
	}

}
