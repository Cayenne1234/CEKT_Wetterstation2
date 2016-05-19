package com.example.cekt;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Parser {

	public static void main(String[] args) {
		
		try{
		File xml = new File("pi.xml");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxparser = factory.newSAXParser();
		XMLHandler handler = new XMLHandler();
		saxparser.parse(xml, handler);
		
		//letzte Werte in
		System.out.println(handler.getDate());
		System.out.println(handler.getPressure());
		System.out.println(handler.getTemp());
			System.out.println(handler.getHumidity());
			System.out.println(handler.getDateList());
			System.out.println(handler.getPressureList());
			System.out.println(handler.getTempList());
			System.out.println(handler.getHumidityList());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

}
