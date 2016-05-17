package com.example.cekt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler{
	
	Date date = new Date();
	String temp, pressure;
	boolean btemp, bpressure = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
		throws SAXException {
			if(qName.equalsIgnoreCase("weatherdata")){
				String temp  = attributes.getValue("date");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					date = format.parse(temp);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//System.out.println(date);
			} else if (qName.equalsIgnoreCase("temp")) btemp = true;
			else if(qName.equalsIgnoreCase("pressure")) bpressure = true;
	}
	
	@Override
	public void endElement (String uri, String localName, String qName) throws SAXException {
		   if(qName.equalsIgnoreCase("weatherdata")){}
			 // System.out.println("End Element: "+qName);
	   }
	   
	 
	@Override
	   public void characters(char ch[], 
	      int start, int length) throws SAXException {
	      if (btemp) {
	      //   System.out.println("Temperatur: "
	        // + new String(ch, start, length));
	         temp = new String(ch, start, length);
	         btemp = false;
	      } else if (bpressure) {
	        // System.out.println("Pressure: "
	        // + new String(ch, start, length));
	         pressure =  new String(ch, start, length);
	         bpressure = false;
	      }
	   }
	
	public Date getDate(){
		return date;
	}
	

	public String getTemp() {
		return temp;
	}


	public String getPressure() {
		return pressure;
	}
}
