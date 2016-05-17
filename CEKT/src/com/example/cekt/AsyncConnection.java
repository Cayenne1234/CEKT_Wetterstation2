package com.example.cekt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncConnection extends AsyncTask<String, Void, InputStream> {
	
	    public Exception exception;
	    
	    private XMLHandler handler;

		private MainActivity ma;
	    
	    
	    public AsyncConnection(MainActivity activity){
	    	this.ma = activity;
	    }

	    protected InputStream doInBackground(String... params) {
	    	//get data from url
	        try {
	    		URL url = new URL("http://stefanmit.hopto.org/weatherxml.php");
	    		URLConnection urlConnection = url.openConnection();

		    	InputStream in = urlConnection.getInputStream();
		    	SAXParserFactory factory = SAXParserFactory.newInstance();
		    	SAXParser saxparser = factory.newSAXParser();
		    	handler = new XMLHandler();
		    	saxparser.parse(in, handler);
		    	


	        } catch (Exception e) {
	            this.exception = e;
	            
	        }
	        return null;
	    }

	    protected void onPostExecute(InputStream result) {
	    	ma.tempTxt.setText("Temperature: "+handler.getTemp()+" °C");
	    	ma.barTxt.setText("Air-Pressure: "+handler.getPressure()+" hPa \n");
	    	ma.dateTxt.setText("Date of this update:\n"+handler.getDate().toString());
	    	//ma.updateView();
	    }

}