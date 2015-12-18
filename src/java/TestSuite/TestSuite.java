/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestSuite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

/**
 *
 * @author duffytj
 */
public class TestSuite {
    
    public void test() {
        //
        try {
            URL geoLocation = new URL("http://localhost:8080/CSSE375-projectAPI/GetAllEventsServlet");
            URLConnection l = geoLocation.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(l.getInputStream()));
            String inputLine = in.readLine();
            in.close();
            JSONObject obj = new JSONObject(inputLine);
            System.out.println(obj.get("_id"));
        } catch (Exception e) {
            System.out.println("url exception");
        }
    }
}