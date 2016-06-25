package com.example.cekt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener  {
	
	LinearLayout ll;
	Button refreshBtn;
	 TextView tempTxt;
	 TextView barTxt;
	 TextView dateTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tempTxt = new TextView(this);
		tempTxt.setText("update Temperature");
		barTxt = new TextView(this);
		barTxt.setText("update Preasure");
		dateTxt = new TextView(this);
		dateTxt.setText("date of last update");
		
		// get the View's from main.xml
		ll = (LinearLayout)findViewById(R.id.linLayout); //gets the Layout from res/menu/main.xml
		ll.addView(tempTxt);
		ll.addView(barTxt);
		ll.addView(dateTxt);

		refreshBtn = (Button)findViewById(R.id.refreshBtn);
		refreshBtn.setText("refresh data");
		refreshBtn.setOnClickListener(this);
		
		refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * is called when the ActionListener is called, e.g. the refresh button has been pressed
	 */
	@Override
	public void onClick(View v) {
		refresh();
	}
	
	/**
	 * gets the new values and updates the TextViews
	 */
	private void refresh(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new AsyncConnection(this).execute("http://stefanmit.hopto.org/weatherxml.php");
			Toast.makeText(getApplicationContext(), "The data is being fetched, be patient ;)", Toast.LENGTH_SHORT).show();
		}else showStuff("Get access to the internet you moron ;)");
	} 


	public void showStuff(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
}
