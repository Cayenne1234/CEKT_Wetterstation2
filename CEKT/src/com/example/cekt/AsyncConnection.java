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
		
		String text ="";
		
		Element data;
	    
	    
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
		    	org.w3c.dom.Document doc = builder.parse(in);
		    	// get the first element
		         data = doc.getDocumentElement();
		   
	        	 text = text+data.getFirstChild().toString();
	        	 
		         // get all child nodes
		         NodeList nodes = data.getChildNodes();
		         
		         
		         // print the text content of each child
		         for (int i = 0; i < nodes.getLength(); i++) {
		        	 

		            text = text+nodes.item(i).getTextContent()+"\n";
		         } 
		         

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
	    		String oldTemp = "";
	    		for(int i=1; i<=data.getElementsByTagName("temp").getLength();i++){
	    			oldTemp += data.getElementsByTagName("temp").item(0).getTextContent()+"; ";
	              }
	    		ma.tempTxt.setText(text);
               ma.tempTxt.setText("Temperature: " + data.getElementsByTagName("temp").item(0).getTextContent() + " °C"+"\n"+"old Values"+oldTemp);
               String bar = data.getElementsByTagName("pressure").item(0).getTextContent();
               String oldbar = "";
               for(int i=1; i<=data.getElementsByTagName("pressure").getLength();i++){
            	   oldbar += data.getElementsByTagName("pressure").item(0).getTextContent()+"; ";
               }
               // ma.barTxt.setText("Air-Pressure: " + data.getChildNodes().item(1).getTextContent() + " hPa \n");
               ma.barTxt.setText("Air-Pressure: " + bar + " hPa \n"+
            		   	"old Values: "+oldbar);
                ma.dateTxt.setText("Date of this update:\n" + handler.getDate().toString());
              
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }

}