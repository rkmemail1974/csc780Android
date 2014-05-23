package com.example.mobilebooks;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TABLE_NAME = "CompleteList";
	private static final String TABLE_NAME1 = "AllBooks";
	private static final String TABLE_NAME3 = "Dealers";
	private boolean check;

	private static String DB_PATH;
	private static String DB_NAME = "Books";
	private SQLiteDatabase myDatabase;
	private final Context context;

	public DatabaseHelper(Context context, String dbname,
			CursorFactory factory, int version) {

		// TODO Auto-generated constructor stub
		super(context, dbname, null, 1);
		this.context = context;
		// this is the path given to store db on phone ,as learnt from
		// stackoverflow
		DB_PATH = context.getApplicationInfo().dataDir + "/databases/";

		// the database is being created at the following path in phone
		// /data/data/com.example.mobilebooks/files/databases/
		// DB_PATH = context.getFilesDir().getPath() + "/databases/";

		System.out.println(DB_PATH);

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDatabase = this.getReadableDatabase();
		myDatabase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	public void createDatabase() throws IOException {
		boolean dbExits = checkDataBase();
		if (dbExits) {
			// do nothing
		} else {
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");

			}
		}
	}

	Cursor getBooksList(String keyword, int flag) {
		String sql;
		try {
			if (flag == EntryActivity.ISBN) {
				System.out.println("DBHELPER isbn" + flag);

				sql = "select * from " + TABLE_NAME1 + " Where ISBN = "
						+ keyword;
			} else {
				System.out.println("DBHELPER tag" + flag);

				String word = "'" + keyword + "'";
				sql = "select * from " + TABLE_NAME1 + " Where Tag = " + word;

			}
			Cursor mCur = myDatabase.rawQuery(sql, null);
			// if (mCur != null) {
			// mCur.moveToNext();
			// }

			return mCur;
		} catch (SQLException mSQLException) {
			Log.e("Data", "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	/*
	 * public String getPrice(int key) { String query = "select * from "+
	 * TABLE_NAME+" where ISBN = "+ key; Cursor cur = myDatabase.rawQuery(query,
	 * null); System.out.println("no. of rows in cursor"+cur.getColumnCount()+
	 * "" + cur.getCount());
	 * 
	 * System.out.println("column index of price"+cur.getColumnIndex("Price"));
	 * cur.moveToFirst(); String result= cur.getString(5).toString(); return
	 * result; }
	 */
	private boolean checkDataBase() {
		// TODO Auto-generated method stub
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

			// database does't exist yet.

		}
		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		// Open the db stored in assests folder ,so that we can copy it to the
		// phone
		InputStream myInput = context.getAssets().open(DB_NAME);

		// custom path given by us to creatte a db inside phone
		String outFileName = DB_PATH + DB_NAME;

		// FileOutputStream will create a file at the given path(outFileName) if
		// it does not exist
		// and if it exists it will be truncated
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		// reomve the above comment also after understanding , ill tell you
		// reason why later on phone
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor getSpecifiedBook(int searchIndex) {
		// TODO Auto-generated method stub
		String bookQuery = "Select * from " + TABLE_NAME1 + " where ISBN = "
				+ searchIndex;
		Cursor cur = myDatabase.rawQuery(bookQuery, null);
		Log.e("Seartch index. in ALLBOOKS is", "  " + searchIndex + TABLE_NAME1
				+ " cursor rows" + cur.getCount());

		return cur;
	}

	public Cursor getCompleteDetails(int searchIndex) {
		// TODO Auto-generated method stub
		String completeDetails = "Select * From " + TABLE_NAME
				+ " Where  ISBN = " + searchIndex;
		Cursor cur = myDatabase.rawQuery(completeDetails, null);
		Log.e("No of rows returned is ", " :: " + cur.getCount()
				+ "no of coulms" + cur.getColumnCount());
		return cur;
	}

	public Cursor getVendorsList(int searchIndex, String vendor) {
		// TODO Auto-generated method stub

		String query = "Select * From " + TABLE_NAME3 + " where ISBN = "
				+ searchIndex + " AND " + vendor + " = 1";
		Cursor cur = myDatabase.rawQuery(query, null);
		System.out.println(" vendor name " + vendor);
		Log.e("No of rows returned is ", " :: " + cur.getCount());

		return cur;
	}

}
