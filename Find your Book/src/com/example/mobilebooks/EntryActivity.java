package com.example.mobilebooks;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EntryActivity extends Activity {
	private EditText scannedText;
	public static final int SHOW_SUBACTIVITY = 1;
	public static String ScanResult;
	public static Cursor cursor;
	private Button search, help, scan;
	public static MyDatabaseHelperClass dbHelper;
	public static final int TAG = 0;
	private int flag;
	public static final int ISBN = 1;
	public static String[] price;
	public static String bookName[];
	public static String bookAuthor[];
	public static String bookImage[];
	public static String bookIsbn[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);

		search = (Button) findViewById(R.id.search);
		help = (Button) findViewById(R.id.help);

		scannedText = (EditText) findViewById(R.id.input);
		String input = scannedText.getText().toString();
		System.out.println("Scanned Input" + input);
		scan = (Button) findViewById(R.id.scan);
		scan.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startActivityForResult(new Intent(EntryActivity.this,
						ScanditSDKSampleBarcodeActivity.class),
						SHOW_SUBACTIVITY);

			}
		});

		help.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

			}
		});

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String input = scannedText.getText().toString();
				System.out.println("Scanned Input" + input);

				new FetchData().execute(input);
				// Runnable r = new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				// for(int i=0;i< EntryActivity.price.length;i++)
				// {
				// System.out.println(price[i]);
				// System.out.println("book name "+bookName[i]);
				//
				// }
				// Bundle bundle = new Bundle();
				// bundle.putStringArray("BookName", price);
				// bundle.putStringArray("BookAuthor", price);
				// bundle.putStringArray("Price", price);
				// Intent i = new Intent(EntryActivity.this, BookLists.class);
				// i.putExtras(bundle);
				// startActivity(i);
				//
				// }
				// };
				// Handler h = new Handler();
				// h.postDelayed(r, 100000);
				/*
				 * /* if (input.contentEquals("")) { Toast.makeText(
				 * EntryActivity.this,
				 * "Either Enter book Name or Scan ISBN to serch for Books " ,
				 * Toast.LENGTH_LONG).show(); } else {
				 * 
				 * if (input.contains("1") || input.contains("2") ||
				 * input.contains("3") || input.contains("4") ||
				 * input.contains("5") || input.contains("6") ||
				 * input.contains("7") || input.contains("8") ||
				 * input.contains("9") || input.contains("0")) { flag = ISBN;
				 * System.out.println("ISBN check " + flag);
				 * 
				 * } else { flag = TAG; System.out.println("TAg check " + flag);
				 * 
				 * } String DB_NAME = "Books";
				 * 
				 * dbHelper = new MyDatabaseHelperClass(EntryActivity.this,
				 * DB_NAME, null, 1); try { dbHelper.createDatabase(); } catch
				 * (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } dbHelper.openDataBase(); cursor =
				 * dbHelper.getBooksList(input, flag);
				 * 
				 * Bundle bundle = new Bundle(); String[] bookName = new
				 * String[cursor.getCount()]; String[] bookAuthor = new
				 * String[cursor.getCount()]; // String[] price = new
				 * String[cursor.getCount()]; if (cursor.getCount() == 0) {
				 * Toast.makeText( EntryActivity.this,
				 * "No Resulsts Were Found for Your search Please try a different search"
				 * , Toast.LENGTH_LONG).show(); } else { int j = 0;
				 * cursor.moveToFirst(); do {
				 * System.out.println("no of rows in cursor" +
				 * cursor.getCount()); // price[j]=
				 * dbHelper.getPrice(cursor.getInt(3)); bookName[j] =
				 * cursor.getString(3); bookAuthor[j] = cursor.getString(4);
				 * j++; } while (cursor.moveToNext());
				 * bundle.putStringArray("BookName", bookName);
				 * bundle.putStringArray("BookAuthor", bookAuthor); //
				 * bundle.putStringArray("Price", price); Intent i = new
				 * Intent(EntryActivity.this, BookLists.class);
				 * i.putExtras(bundle); startActivity(i); }
				 * 
				 * }
				 */

			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == SHOW_SUBACTIVITY) {
			if (resultCode == RESULT_OK) {

				scannedText.setText(data.getStringExtra("RESULT_STRING"),
						TextView.BufferType.EDITABLE);
				ScanResult = data.getStringExtra("ResultString");

			}

		}
	}

	private class FetchData extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			String keyword = params[0];
			System.out.println("Search word is" + keyword);
			Client.getBookFromAmazon(keyword);

			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			/*
			 * for(int i=0;i< EntryActivity.price.length;i++) {
			 * System.out.println(price[i]);
			 * System.out.println("book name "+bookName[i]);
			 * 
			 * }
			 */
			Bundle bundle = new Bundle();
			bundle.putStringArray("BookName", bookName);
			bundle.putStringArray("BookAuthor", bookAuthor);
			bundle.putStringArray("Price", price);
			bundle.putStringArray("BookImage", bookImage);
			Intent i = new Intent(EntryActivity.this, BookLists.class);
			i.putExtras(bundle);
			startActivity(i);
			super.onPostExecute(result);
		}
	}
}
