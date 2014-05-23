package com.example.mobilebooks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	String[] message, bookAuthor, price, bookImage;
	Context context;
	// URL[] bookImage;
	public int temp;
	public URL url;
	public Bitmap myBitmap;
	public static int count = 1;
	public VendorsDetails vendorDetails;
	Handler h = new Handler();
	Thread imageSet;
	int myposition;

	CustomAdapter(String[] message, String[] bookAuthor, String[] price,
			String[] bookImage, Context context) {
		this.context = context;
		this.bookAuthor = bookAuthor;
		this.message = message;
		this.price = price;
		this.bookImage = bookImage;
		vendorDetails = new VendorsDetails();
		// count = 0;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		if (bookAuthor.length < bookImage.length
//				&& bookAuthor.length < message.length) {
//
//			return bookAuthor.length;
//
//		}else if (message.length < bookImage.length
//				&& message.length < message.length) {

			return message.length;
//		}else{
//			return bookImage.length;
//		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		Handler handler = new Handler();
		v = convertView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myposition = position;
		System.out.println(" book author" +bookAuthor.length+" book Image" +bookImage.length+" message" +message.length);
		// if (v == null) {
		v = inflater.inflate(R.layout.listview, null);
		handler.book_name = (TextView) v.findViewById(R.id.book_name);
		handler.book_author = (TextView) v.findViewById(R.id.book_author);
		handler.book_image = (ImageView) v.findViewById(R.id.book_image);
		handler.price = (TextView) v.findViewById(R.id.price);
		handler.used_availabel = (TextView) v.findViewById(R.id.used_availabel);
		// if (price[position].equalsIgnoreCase("") || price[position] ==
		// null) {
		v.setTag(handler);

		// }
		try {
			// handler.used_availabel.setVisibility(View.VISIBLE);
			handler.book_author.setText(bookAuthor[position]);
			Log.e("Heell", bookAuthor[position]);
			if (position == 11) {
				position = position - 1;
			}
			handler.book_name.setText(message[position]);
			Log.e("Heell", message[position]);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					HttpURLConnection connection;
					try {
						url = new URL(bookImage[myposition]);
						// count++;
						connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
						connection.connect();
						InputStream input = connection.getInputStream();

						myBitmap = BitmapFactory.decodeStream(input);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NullPointerException n) {

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
			handler.book_image.setImageBitmap(myBitmap);

			// synchronized (Thread.currentThread()) {
			// try {
			// Thread.currentThread().wait();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

			// handler.book_image.setImageBitmap(myBitmap);

			// HttpURLConnection connection;
			// try {
			// // System.out.println(imageURL);
			// url = new URL(bookImage[count]);
			// count++;
			// connection = (HttpURLConnection) url
			// .openConnection();
			// connection.setDoInput(true);
			// connection.connect();
			// InputStream input = connection.getInputStream();
			//
			// myBitmap = BitmapFactory.decodeStream(input);
			//
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			//
			//
			//
			//
			// handler.book_image.setImageBitmap(myBitmap);
			//

			Log.d("checking price[posiotn in custom adaper", price[position]);
			handler.price.setText(price[position]);
		} catch (NullPointerException n) {
			handler.price.setText(" $$");
		}
		//
		// if (count == 1) {
		// System.out.println("he is heeeeee");
		//
		// count++;
		// // if(vendorDetails.getUsedAvailabelDetails(context))
		// // {
		// // handler.used_availabel.setVisibility(View.VISIBLE);
		// // }
		// // System.out.println("Heee is bak");
		// // Parse.initialize(context,
		// // "hhvNehUIwK1iY8Bmtt4FnFoHxWwo0dNgwSnqi7iz",
		// // "B9R0dIdKHeRHyPI0YbShuX9Kapd60yURT7H1c26Z");
		// ParseQuery<ParseObject> queryVendors = ParseQuery
		// .getQuery("BetaObject");
		// queryVendors.whereEqualTo(Preferences.SELL_CHECK, "1");
		// System.out.println("He is finding");
		//
		// queryVendors.findInBackground(new FindCallback<ParseObject>() {
		//
		// @Override
		// public void done(List<ParseObject> arg0, ParseException arg1) {
		// // TODO Auto-generated method stub
		// if (arg0.size() > 0) {
		// temp = 0;
		// } else {
		// temp = 1;
		// }
		// System.out.println("He found" + temp);
		//
		// }
		// });
		//
		// }

		// }

		// else {
		// handler = (Handler) v.getTag();
		// }
		return v;
	}

	public class Handler {
		TextView book_name, book_author, price, used_availabel;
		ImageView book_image;

	}

}
