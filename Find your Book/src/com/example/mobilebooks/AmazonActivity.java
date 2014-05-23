package com.example.mobilebooks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class AmazonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amazon);
		WebView amazon = (WebView)findViewById(R.id.amazon);
		amazon.getSettings().setJavaScriptEnabled(true);
		String keyword = CompleteDetails.bookName_;
		System.out.println(CompleteDetails.bookName_);
		String newKeyword=keyword.replace(' ', '+');
		System.out.println(newKeyword);
		
		amazon.loadUrl("http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dstripbooks&field-keywords="+newKeyword);
		

	}
}
