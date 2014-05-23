package com.example.mobilebooks;

import java.io.IOException;

import com.parse.CountCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

public class RegisterActivity extends Activity {

	public static String name, username, emailAddress, password;
	public int countNumber = 1;
	Button submit;

	EditText registerName, registerUsername, registerPassword, registerEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Parse.initialize(this, "fjASsIzZ2fMgGbd2DPrhrUInl0UbJqUn4F82AaVq",
		// "LelxOUQIyqtgoqxaCnoyY7XEEUnbBtg8NW7QzpYK");//this,
		// "hhvNehUIwK1iY8Bmtt4FnFoHxWwo0dNgwSnqi7iz",
		// "B9R0dIdKHeRHyPI0YbShuX9Kapd60yURT7H1c26Z");
		Parse.initialize(getApplicationContext(),
				"hhvNehUIwK1iY8Bmtt4FnFoHxWwo0dNgwSnqi7iz",
				"B9R0dIdKHeRHyPI0YbShuX9Kapd60yURT7H1c26Z");
		
		registerName = (EditText) findViewById(R.id.registerName);
		registerUsername = (EditText) findViewById(R.id.registerUsername);
		registerPassword = (EditText) findViewById(R.id.registerPassword);
		registerEmail = (EditText) findViewById(R.id.registerEmail);

		submit = (Button) findViewById(R.id.registerSubmit);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				name = registerName.getText().toString();
				username = registerUsername.getText().toString();
				emailAddress = registerEmail.getText().toString();
				password = registerPassword.getText().toString();

				// TODO Auto-generated method stub
				// new API object , this will create a testobject class in parse
				// database

				ParseObject testObject = new ParseObject("LoginObject");

				// ParseQuery<ParseObject> query =
				// ParseQuery.getQuery("LoginObject");
				//
				// try {
				// countNumber = query.count();
				// } catch(com.parse.ParseException e) {}

				/*
				 * query.countInBackground(new CountCallback() { public void
				 * done(int count, com.parse.ParseException e) { if (e == null)
				 * { // The count request succeeded. Log the count countNumber =
				 * count; } else { // The request failed } } });
				 */

				// adding items in the file of parse.com
				testObject.put("UserId", countNumber + 1);
				testObject.put("name", name);
				testObject.put("username", username);
				testObject.put("password", password);
				testObject.put("email", emailAddress);

				// new We have to write this to save the data .
				testObject.saveInBackground();
				Intent LoginActivity;
				LoginActivity = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(LoginActivity);

			}
		});

	}

}
