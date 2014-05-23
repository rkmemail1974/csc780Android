/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file 
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License. 
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */
package com.example.mobilebooks;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

/*
 * This class shows how to make a simple authenticated ItemLookup call to the
 * Amazon Product Advertising API.
 *
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class Client {
	/*
	 * Your AWS Access Key ID, as taken from the AWS Your Account page.
	 */
	private static final String AWS_ACCESS_KEY_ID = "AKIAJVTU6YAIEJI26SGA";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "CbLiKWY8ycHdNMWQ2eoVi12oE6ISxiJcFbFK6R0O";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 */
	private static final String ENDPOINT = "ecs.amazonaws.com";

	/*
	 * The Item ID to lookup. The value below was selected for the US locale.
	 * You can choose a different value if this value does not work in the
	 * locale of your choice.
	 */
	public static void getCompleteDetails(String keyword) {
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String requestUrl = null;
		String title = null;

		/* The helper can sign requests in two forms - map form and string form */

		/*
		 * Here is an example in map form, where the request parameters are
		 * stored in a map.
		 */
		System.out.println("Map form example:");
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		// params.put("Version", "2009-03-31");
		params.put("Operation", "ItemSearch");
		// params.put("BrowserNode", "Harry Potter Books,");
		// refer amazon site to understand searChIndex
		params.put("SearchIndex", "Books");
		// to get the result of the specific page no. ie page 1 or page 2 the do
		// as follow
		// params.put("ItemPage","2");
		// params.put("ASIN" ,"0743273567");
		params.put("Keywords", keyword);
		params.put("ResponseGroup", "Large");
		params.put("AssociateTag", "book");
		// params.put("MerchantId", "All");
		params.put("Condition", "New");
		requestUrl = helper.sign(params);
		System.out.println("Signed Request is \"" + requestUrl + "\"");
		fetchDetailedList(requestUrl);

	}

	private static void fetchDetailedList(String requestUrl) {
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> price = new ArrayList<String>();
		ArrayList<String> bookAuthor = new ArrayList<String>();
		ArrayList<String> bookIsbn = new ArrayList<String>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Document doc = null;
			try {
				doc = db.parse(requestUrl);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			NodeList priceNode = doc.getElementsByTagName("FormattedPrice");
			CompleteDetails.bookPrice_ = priceNode.item(0).getTextContent();
			// why isbn is store in database as AXXXXX
			NodeList isbnNode = doc.getElementsByTagName("ISBN");
			CompleteDetails.bookIsbn_ = "A" + isbnNode.item(0).getTextContent();
			System.out.println("Haleluie" + CompleteDetails.bookIsbn_);

			NodeList authorNode = doc.getElementsByTagName("Author");
			CompleteDetails.bookAuthor_ = authorNode.item(0).getTextContent();

			NodeList titleNode = doc.getElementsByTagName("Title");
			// ResponseGroup - OfferSummary ,getElementByTag - FormattedPrice
			CompleteDetails.bookName_ = titleNode.item(0).getTextContent();

			// ResponseGroup - small , getElementBYTag - Title
		} catch (NullPointerException n) {
		}

	}

	public static void getBookImages(String keyword) {
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String requestUrl = null;
		String title = null;

		/* The helper can sign requests in two forms - map form and string form */

		/*
		 * Here is an example in map form, where the request parameters are
		 * stored in a map.
		 */
		System.out.println("Map form example:");
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		// params.put("Version", "2009-03-31");
		params.put("Operation", "ItemSearch");
		// params.put("BrowserNode", "Harry Potter Books,");
		// refer amazon site to understand searChIndex
		params.put("SearchIndex", "Books");
		// to get the result of the specific page no. ie page 1 or page 2 the do
		// as follow
		// params.put("ItemPage","2");
		// params.put("ASIN" ,"0743273567");
		params.put("Keywords", keyword);
		params.put("ResponseGroup", "Images");
		params.put("AssociateTag", "book");
		// params.put("MerchantId", "All");
		params.put("Condition", "New");
		requestUrl = helper.sign(params);
		System.out.println("Signed Request is \"" + requestUrl + "\"");
		ArrayList<String> titles = new ArrayList<String>();

		// ArrayList<String> price = new ArrayList<String>();
		try {
			System.out.println("1.helloe 122");

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			System.out.println("1.2.helloe 122");
			DocumentBuilder db = dbf.newDocumentBuilder();

			// NodeList titleNode = doc.getElementsByTagName("Name");
			// ResponseGroup - OfferSummary ,getElementByTag - FormattedPrice

			// ResponseGroup - small , getElementBYTag - Title
			Document doc = db.parse(requestUrl);
			NodeList priceNode = doc.getElementsByTagName("URL");

			// System.out.println(titleNode.getLength());
			// EntryActivity.bookImage[0] = priceNode.item(1).getTextContent();
			// System.out.println("Heeloooool" + EntryActivity.bookImage[0]);
			for (int i = 0; i < priceNode.getLength(); i++) {
				titles.add(priceNode.item(i).getTextContent());
				System.out.println("heell @ " + titles.get(i).toString());
				// price.add(priceNode.item(i).getTextContent());

			}
		} catch (RuntimeException e) {

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("tHERS SOME WOHOOO");
		EntryActivity.bookImage = new String[titles.size()];
		// EntryActivity.bookImage[0] = priceNode.item(1).getTextContent();
		// System.out.println("Heeloooool" + EntryActivity.bookImage[0]);
		for (int i = 0, j = 0; i < titles.size(); i = i + 8) {
			System.out.println(titles.get(i).toString());
			EntryActivity.bookImage[j] = titles.get(i).toString();
			j++;
			System.out.println("Heedfdsfaloooool" + EntryActivity.bookImage[j]);
		}

	}

	public static void getBookFromAmazon(String keyword) {
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String requestUrl = null;
		String title = null;

		/* The helper can sign requests in two forms - map form and string form */

		/*
		 * Here is an example in map form, where the request parameters are
		 * stored in a map.
		 */
		System.out.println("Map form example:");
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		// params.put("Version", "2009-03-31");
		params.put("Operation", "ItemSearch");
		// params.put("BrowserNode", "Harry Potter Books,");
		// refer amazon site to understand searChIndex
		params.put("SearchIndex", "Books");
		// to get the result of the specific page no. ie page 1 or page 2 the do
		// as follow
		// params.put("ItemPage","2");
		// params.put("ASIN" ,"0743273567");
		params.put("Keywords", keyword);
		params.put("ResponseGroup", "Large");
		params.put("AssociateTag", "book");
		// params.put("MerchantId", "All");
		params.put("Condition", "New");
		requestUrl = helper.sign(params);
		System.out.println("Signed Request is \"" + requestUrl + "\"");
		ArrayList<String> titles = fetchTitles(requestUrl);
		// getBookImages(keyword);
	}

	/*
	 * Utility function to fetch the response from the service and extract the
	 * title from the XML.
	 */
	private static ArrayList<String> fetchTitles(String requestUrl) {
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> price = new ArrayList<String>();
		ArrayList<String> bookAuthor = new ArrayList<String>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(requestUrl);
			NodeList priceNode = doc.getElementsByTagName("FormattedPrice");
			NodeList imageNode = doc.getElementsByTagName("ThumbnailImage");
			NodeList authorNode = doc.getElementsByTagName("Author");
			NodeList titleNode = doc.getElementsByTagName("Title");
			// ResponseGroup - OfferSummary ,getElementByTag - FormattedPrice

			// ResponseGroup - small , getElementBYTag - Title
			System.out.println(titleNode.getLength());
			System.out.println(authorNode.getLength());
			System.out.println(priceNode.getLength());

			for (int i = 0; i < priceNode.getLength(); i++) {
				price.add(priceNode.item(i).getTextContent());
				// bookAuthor.add(authorNode.item(i).getTextContent());
				System.out.println(priceNode.item(i).getTextContent());

			}
			EntryActivity.price = new String[price.size()];
			EntryActivity.bookImage = new String[imageNode.getLength()];

			for (int i = 0; i < imageNode.getLength(); i++) {
				// price.add(imageNode.item(i).getTextContent());
				// bookAuthor.add(authorNode.item(i).getTextContent());
				String image = imageNode.item(i).getTextContent().toString();
				image = image.replaceAll("jpg(.*)", "jpg");

				System.out.println("M khay \n" + image);

				// EntryActivity.bookImage[0] =
				// priceNode.item(1).getTextContent();
				// System.out.println(titles.get(i).toString());
				EntryActivity.bookImage[i] = image;

				System.out.println("Heedfdsfaloooool"
						+ EntryActivity.bookImage[i]);
			}
			for (int i = 0; i < authorNode.getLength(); i++) {
				bookAuthor.add(authorNode.item(i).getTextContent());
				System.out.println(authorNode.toString());

			}

			EntryActivity.bookAuthor = new String[bookAuthor.size()];

			for (int i = 0; i < titleNode.getLength()+1; i=i+6) {
			if(i>titleNode.getLength()){
				
			}else{
				try{
				titles.add(titleNode.item(i).getTextContent());
				System.out.println(titleNode.toString());
			}catch (NullPointerException n)
			{
				
			}
			}
			}

			EntryActivity.bookName = new String[titles.size()];

			for (int i = 0; i < priceNode.getLength(); i++) {
				// price.add(priceNode.item(i).getTextContent() );
				// bookAuthor.add(authorNode.item(i).getTextContent());
				// System.out.println(price.get(i).toString());
				EntryActivity.price[i] = price.get(i).toString();
				Log.e("checking postion " + i, EntryActivity.price[i]);

			}
			for (int i = 0; i < authorNode.getLength(); i++) {
				// price.add(priceNode.item(i).getTextContent() );
				// bookAuthor.add(authorNode.item(i).getTextContent());
				System.out.println(bookAuthor.get(i).toString());
				EntryActivity.bookAuthor[i] = bookAuthor.get(i).toString();

				Log.e("checking postion " + i, EntryActivity.bookAuthor[i]);

			}
			for (int i = 0; i < titles.size(); i++) {
				// price.add(priceNode.item(i).getTextContent() );
				// bookAuthor.add(authorNode.item(i).getTextContent());
				System.out.println(titles.get(i).toString());
				EntryActivity.bookName[i] = titles.get(i).toString();

				Log.e("checking postion " + i, EntryActivity.bookName[i]);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return titles;

	}

	public static void getPaticularBook(String keyword) {
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String requestUrl = null;
		String title = null;

		/* The helper can sign requests in two forms - map form and string form */

		/*
		 * Here is an example in map form, where the request parameters are
		 * stored in a map.
		 */
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		// params.put("Version", "2009-03-31");
		params.put("Operation", "ItemSearch");
		// params.put("BrowserNode", "Harry Potter Books,");
		// refer amazon site to understand searChIndex
		params.put("SearchIndex", "Books");
		// to get the result of the specific page no. ie page 1 or page 2 the do
		// as follow
		// params.put("ItemPage","2");
		// params.put("ASIN" ,"0743273567");
		params.put("Keywords", keyword);
		params.put("ResponseGroup", "Large");
		params.put("AssociateTag", "book");
		// params.put("MerchantId", "All");
		params.put("Condition", "New");
		requestUrl = helper.sign(params);
		System.out.println("Signed Request is \"" + requestUrl + "\"");

		getPaticularBookDetails();

	}

	private static void getPaticularBookDetails() {
		// TODO Auto-generated method stub

	}

	/*
	 * public static URL getBookImage{
	 * 
	 * SignedRequestsHelper helper; try { helper =
	 * SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID,
	 * AWS_SECRET_KEY); } catch (Exception e) { e.printStackTrace(); return; }
	 * 
	 * String requestUrl = null; String title = null;
	 * 
	 * The helper can sign requests in two forms - map form and string form
	 * 
	 * 
	 * Here is an example in map form, where the request parameters are stored
	 * in a map.
	 * 
	 * System.out.println("Map form example:"); Map<String, String> params = new
	 * HashMap<String, String>(); params.put("Service", "AWSECommerceService");
	 * // params.put("Version", "2009-03-31"); params.put("Operation",
	 * "ItemSearch"); // params.put("BrowserNode", "Harry Potter Books,"); //
	 * refer amazon site to understand searChIndex params.put("SearchIndex",
	 * "Books"); // to get the result of the specific page no. ie page 1 or page
	 * 2 the do // as follow // params.put("ItemPage","2"); // params.put("ASIN"
	 * ,"0743273567"); params.put("Keywords", "java");
	 * params.put("ResponseGroup", "Images"); params.put("AssociateTag",
	 * "book"); // params.put("MerchantId", "All"); params.put("Condition",
	 * "New"); requestUrl = helper.sign(params);
	 * System.out.println("Signed Request is \"" + requestUrl + "\"");
	 */// }
}
