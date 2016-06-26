package com.example.cekt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.provider.DocumentsContract.Document;
import android.util.Log;
import android.widget.Toast;

public class AsyncConnection extends AsyncTask<String, Void, InputStream> {
	
	    public Exception exception;
	    
	    private XMLHandler handler =null;

		private MainActivity ma;
		
		Element data;
		
		org.w3c.dom.Document doc = null;
	    
	    public AsyncConnection(MainActivity activity){
	    	this.ma = activity;
	    }

	    protected InputStream doInBackground(String... params) {
	    	InputStream in = null;
	    	//get data from url
	        try {
	    		URL url = new URL("http://stefanmit.hopto.org/weatherxml.php");
	    		URLConnection urlConnection = url.openConnection();

		    	in = urlConnection.getInputStream();
		    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder builder = factory.newDocumentBuilder();
		    	doc = builder.parse(in);
		    	// get the first element
		         data = doc.getDocumentElement();
	        	  
		         // get all child nodes
		         NodeList nodes = data.getChildNodes();

		    	SAXParserFactory factory2 = SAXParserFactory.newInstance();
		    	SAXParser saxparser = factory2.newSAXParser();
		    	handler = new XMLHandler();
		    	saxparser.parse(in, handler);

	        } catch (Exception e) {
	            this.exception = e;
	            
	        }finally{
	        	if(in!=null){
	        		try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }
	        return null;
	    }

	    protected void onPostExecute(InputStream result) {
	    	try {    
	    		
	    		// TEMPERATURE:
	    		String oldTemp = "";
	    		int tempLength = data.getElementsByTagName("temp").getLength();
	    		for(int i=tempLength-2; i>=0; i--){
	    			oldTemp += doc.getFirstChild().getChildNodes().item(i).getChildNodes().item(0).getTextContent()+"; ";
	             }
               ma.tempTxt.setText("Temperature: " + data.getElementsByTagName("temp").item(tempLength-1).getTextContent() + " °C"+"\n"+"old Values: "+oldTemp);
              
               // DRUCK:
              int barLength = data.getElementsByTagName("pressure").getLength();
               String bar = data.getElementsByTagName("pressure").item(barLength-1).getTextContent();
               String oldbar = "";
               for(int i=barLength-2; i>=0;i--){
            	   oldbar += doc.getDocumentElement().getElementsByTagName("pressure").item(i).getTextContent()+"; ";
               }
               
               // FEUCHTIGKEIT:
               int humLength = data.getElementsByTagName("humidity").getLength();
               String humid = data.getElementsByTagName("humidity").item(humLength-1).getTextContent();
               String oldHumid = "";
               for(int i=humLength-2; i>=0;i--){
            	   oldHumid += doc.getDocumentElement().getElementsByTagName("humidity").item(i).getTextContent()+"; ";
               }
               // ma.barTxt.setText("Air-Pressure: " + data.getChildNodes().item(1).getTextContent() + " hPa \n");
               ma.barTxt.setText("Air-Pressure: " + bar + " hPa \n"+"old Values: "+oldbar+
               				"\nHumidity: "+humid+" %\n"+"oldValues: "+oldHumid);
          
                ma.dateTxt.setText("Date of this update: " + handler.getDate().toString()+"\n");
 
              
			} catch (Exception e) {
				// TODO: handle exception
			}
	    
	    	//	ma.tempTxt.setText(handler.getAllStuff()); //haut keinen Error raus, löscht aber den Text
	    	//	ma.tempTxt.setTag("asdfasdf"); //macht er komischerweise nicht
 
	    }

}