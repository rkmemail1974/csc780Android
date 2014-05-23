package com.example.mobilebooks;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

import android.widget.VideoView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Thread startApp = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(i);

				finish();

			}
		});
		Handler handler = new Handler();
		handler.postDelayed(startApp, 2000);

	}

}
