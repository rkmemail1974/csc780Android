package com.example.mobilebooks;

import java.io.IOException;
import java.util.List;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.app.Activity;
import android.content.Context;
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

public class LoginActivity extends Activity {

	String username, password, tempUsername, tempPassword;
	Button submit, register;
	Context context = this;
	String msg = "You are not registered.  Please register or re-enter username and password!";
	public static String name;
	int duration = Toast.LENGTH_SHORT;
	EditText loginUsername, loginPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Parse.initialize(getApplicationContext(),
				"hhvNehUIwK1iY8Bmtt4FnFoHxWwo0dNgwSnqi7iz",
				"B9R0dIdKHeRHyPI0YbShuX9Kapd60yURT7H1c26Z");
		// this,
		// "hhvNehUIwK1iY8Bmtt4FnFoHxWwo0dNgwSnqi7iz",
		// "B9R0dIdKHeRHyPI0YbShuX9Kapd60yURT7H1c26Z");
		loginUsername = (EditText) findViewById(R.id.loginUsername);
		loginPassword = (EditText) findViewById(R.id.loginPassword);

		submit = (Button) findViewById(R.id.loginSubmit);
		register = (Button) findViewById(R.id.registerUser);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Hell");
				Intent RegisterActivity;
				RegisterActivity = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(RegisterActivity);

			}
		});
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				username = loginUsername.getText().toString();
				password = loginPassword.getText().toString();

				// TODO Auto-generated method stub
				// new API object , this will create a testobject class in parse
				// database

				ParseQuery<ParseObject> query = ParseQuery
						.getQuery("LoginObject");

				query.whereEqualTo("username", username);
				query.whereEqualTo("password", password);
				System.out.println(username);
				System.out.println(password);
				query.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> scoreList,
							com.parse.ParseException e) {
						if (e == null) {
							for (int i = 0; i < scoreList.size(); i++) {
								ParseObject parseObject;
								parseObject = scoreList.get(i);
								tempUsername = parseObject
										.getString("username");
								name = tempUsername;
								tempPassword = parseObject
										.getString("password");
								System.out.println(tempUsername);
								System.out.println(tempPassword);
							}

						}

						else {
System.out.println("Hell Is Here");
						}
						

						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("LoginObject");

						query.whereEqualTo("username", username);
						query.findInBackground(new FindCallback<ParseObject>() {
							
							@Override
							public void done(List<ParseObject> result, ParseException arg1) {
								// TODO Auto-generated method stub
								ParseObject parseObject;
								parseObject = result.get(0);
							RegisterActivity.emailAddress=parseObject.getString("email");
							RegisterActivity.name=parseObject.getString("name");
							ParseObject currentUser = new ParseObject("CurrentUser");
							currentUser.put("name", name);
							currentUser.put("contact", parseObject.getString("email"));
							currentUser.saveInBackground();
								
							}
						});
						if (username.equals(tempUsername)
								&& password.equals(tempPassword)) {
							Intent EntryActivity;
							EntryActivity = new Intent(
									LoginActivity.this,
									EntryActivity.class);
							startActivity(EntryActivity);
						} else {
							Toast toast = Toast.makeText(context, msg,
									duration);
							toast.show();
						}

					}
				});
				
			}

		});
	}
}
