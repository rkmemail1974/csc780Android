package com.example.mobilebooks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookLists extends Activity {
	private String[] booksName, bookAuthor, price;
	public static String[] image;
	public static final String SEARCH = "SearchIndex";
	private String searchIndex;
	ListView bookList;
	CustomAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_lists);
		Bundle bundle = getIntent().getExtras();
		booksName = bundle.getStringArray("BookName");
		bookAuthor = bundle.getStringArray("BookAuthor");
		price = bundle.getStringArray("Price");
		image = bundle.getStringArray("BookImage");
		System.out.println(image.length);
		adapter = new CustomAdapter(EntryActivity.bookName,
				EntryActivity.bookAuthor, EntryActivity.price, image,
				getApplicationContext());
		bookList = (ListView) findViewById(R.id.list_of_books);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bookList.setAdapter(adapter);
			}
		});
		t.start();
		for (int i = 0; i < booksName.length; i++) {

			Log.e("checking postion " + i, EntryActivity.bookName[i]);

		}
		bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// EntryActivity.cursor.moveToPosition(position);
				CompleteDetails.bookImageFlag = position;

				searchIndex = EntryActivity.bookName[position].toString();
				new GetCompleteDetails().execute(searchIndex);

				Log.e("Position ", "item no clicked on list " + searchIndex);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_lists, menu);
		return true;
	}

	private class GetCompleteDetails extends
			AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.e("Position ", params[0]);

			Client.getCompleteDetails(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
			Intent completeDetails = new Intent(BookLists.this,
					CompleteDetails.class);
			completeDetails.putExtra(SEARCH, searchIndex);
			startActivity(completeDetails);

		}

	}

}
