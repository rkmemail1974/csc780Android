package com.example.mobilebooks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelperClass extends SQLiteOpenHelper {
/*	public static final String DB_NAME = "Books";
	public static String DB_PATH;
	private static Context context;
	private static SQLiteDatabase database;*/
	private static final String TABLE_NAME = "CompleteList";
	private static final String TABLE_NAME1 = "AllBooks";
	private static final String TABLE_NAME3 = "Dealers";
	private boolean check;

	private static String DB_PATH;
	private static String DB_NAME = "Books";
	private SQLiteDatabase database;
	private final Context context;

	public MyDatabaseHelperClass(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
//		DB_PATH = context.getFilesDir().getPath() + "/databases/";
		 DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
	}

	public boolean createDatabase()throws IOException {
		boolean checkDB = checkDatabase();
		if (checkDB == true) {
			// do NOTHING
		} else {
			try {
				copyDatabase();
			} catch (IOException e) {

			}
		}
		return true;
	}

	private void copyDatabase() throws IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = context.getAssets().open(DB_NAME);
		String file = DB_PATH + DB_NAME;
		OutputStream stream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int length = 0;
		// Copying files from inputstream (pointing to DB in assest folder ) to
		// outputstream (pointing out to file in local Mobile

		while ((length = inputStream.read(buffer)) > 0) {
			stream.write(buffer);

		}
		stream.flush();
		inputStream.close();
		stream.close();
	}

	private boolean checkDatabase() {
		// TODO Auto-generated method stub
		boolean dbExists;
		String myPath = DB_PATH + DB_NAME;

		try{
		SQLiteDatabase database = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);} catch (SQLiteException e) {

					// database does't exist yet.

				}
		if (database == null) {
			return false;
		}
		database.close();
		return true;
	}

	public void openDataBase() throws SQLException{
		String path = DB_PATH + DB_NAME;
		database = this.getReadableDatabase();
		database = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		if (database == null) {
			Log.e("Some problem in opening DB ",
					"Line no 82 openDatabase Function()");

		} else {
			Log.e("opening DB ",
					" Datanase open at Line no 82 openDatabase Function()");

		}
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
		Cursor cur = database.rawQuery(bookQuery, null);
		Log.e("Seartch index. in ALLBOOKS is", "  " + searchIndex + TABLE_NAME1
				+ " cursor rows" + cur.getCount());

		return cur;
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
			Cursor mCur = database.rawQuery(sql, null);
			// if (mCur != null) {
			// mCur.moveToNext();
			// }

			return mCur;
		} catch (SQLException mSQLException) {
			Log.e("Data", "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getCompleteDetails(int searchIndex) {
		// TODO Auto-generated method stub
		String completeDetails = "Select * From " + TABLE_NAME
				+ " Where  ISBN = " + searchIndex;
		Cursor cur = database.rawQuery(completeDetails, null);
		Log.e("No of rows returned is ", " :: " + cur.getCount()
				+ "no of coulms" + cur.getColumnCount());
		return cur;
	}

	public Cursor getVendorsList(int searchIndex, String vendor) {
		// TODO Auto-generated method stub

		String query = "Select * From " + TABLE_NAME3 + " where ISBN = "
				+ searchIndex + " AND " + vendor + " = 1";
		Cursor cur = database.rawQuery(query, null);
		System.out.println(" vendor name " + vendor);
		Log.e("No of rows returned is ", " :: " + cur.getCount());

		return cur;
	}
}
