package com.example.cekt;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler{
	
	Date date = new Date();
	String temp, pressure, humidity;
	boolean btemp, bpressure, bhumidity = false;
	LinkedList<String> tempList, pressureList, humidityList = new LinkedList<String>();
	LinkedList<Date> dateList = new LinkedList<Date>();
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
		throws SAXException {
			if(qName.equalsIgnoreCase("weatherdata")){
				String temp  = attributes.getValue("date");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					date = format.parse(temp);
					dateList.add(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//System.out.println(date);
			} else if (qName.equalsIgnoreCase("temp")) btemp = true;
			else if(qName.equalsIgnoreCase("pressure")) bpressure = true;
        else if (qName.equalsIgnoreCase("humidity")) bhumidity = true;
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
			  tempList.add(temp);
              Log.d("asdf", temp);
	         btemp = false;
	      } else if (bpressure) {
	        // System.out.println("Pressure: "
	        // + new String(ch, start, length));
	         pressure =  new String(ch, start, length);
			  pressureList.add(pressure);
              Log.d("asdfasf", pressure);
	         bpressure = false;
	      }

        else if (bhumidity){
              humidity = new String(ch, start, length);
              humidityList.add(humidity);
              bhumidity = false;
			  Log.d("asasdfasfsfdf", humidity);

          }
	   }
	
	public Date getDate(){
		return date;
	}
	

	public String getTemp() {
		return temp;
	}

    public String getHumidity() {return humidity;}

	public String getPressure() {
		if(pressure==null)pressure = "null";
		return pressure;
	}

	public LinkedList<Date> getDateList() {return dateList;}

	public LinkedList<String> getTempList() {return tempList;}

	public LinkedList<String> getPressureList() {return pressureList;}

    public LinkedList<String> getHumidityList(){return humidityList;}
}
