package lets;

import java.net.*;
import java.awt.geom.Point2D;
import java.io.*;

import org.json.JSONObject;

public class currentLocation {
	// source:http://stackoverflow.com/questions/5015844/parsing-json-object-in-java
	/**
	 * You could get ip,
	 * country_code,country_name,region_code,region_name,city,zip_code
	 * ,time_zone,latitude,longitude,metro_code
	 * 
	 * @param requirement
	 * @return String
	 * @throws Exception
	 */
	public static String get(String requirement) throws Exception {
		URL geoLocation = new URL("http://freegeoip.net/json/");
		URLConnection l = geoLocation.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				l.getInputStream()));
		String inputLine = in.readLine();
		in.close();

//		JSONObject obj = new JSONObject(inputLine);
		return (String) new JSONObject(inputLine).get(requirement);
	}

	public static Point2D.Double getLoc() {
		try {
			URL geoLocation = new URL("http://freegeoip.net/json/");
			URLConnection l = geoLocation.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					l.getInputStream()));
			String inputLine = in.readLine();
			in.close();
			JSONObject obj = new JSONObject(inputLine);
//			double x = (double) obj.get("latitude");
//			double y = (double) obj.get("longitude");
			return new Point2D.Double((double) obj.get("latitude"), (double) obj.get("longitude"));
		} catch (Exception e) {
			System.out.println("url exception");
		}
		return null;

	}

	
	
	
	
	
	
	
	
	
	
	
}