/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duffytj
 */
public class InsertUserServletTest {
    
    URL url;
    URLConnection connection;
    String id;
    String zipCode;
    
    public InsertUserServletTest() {
        try {
            // new user to be inserted with "unique" id
            this.id = Long.toString(new Date().getTime()) + "@test.com";
            
            // random zip code between 10000 and 12500 to check later when comparing to make sure newly added user is the right one
            this.zipCode = Integer.toString(new Random().nextInt(2500) + 10000);
            
            // add new user
            this.url = new URL("http://localhost:8080/CSSE375-projectAPI/InsertUserServlet?_id=" + this.id + "&name=SampleUserName&password=insecurepassword&street=SampleStreet&city=SampleCity&state=IN&zipCode=" + this.zipCode + "&gender=male&dob=01/01/1990");
            this.connection = url.openConnection();
            this.url.getContent();
        } catch (Exception e) {
            this.url = null;
            this.connection = null;
            System.out.println("url exception");
        }
    }

    /**
     * Test of processRequest method, of class InsertUserServlet.
     */
    @Test
    public void testInsertUser() throws Exception {
        this.url = new URL("http://localhost:8080/CSSE375-projectAPI/GetUserServlet?_id=" + this.id);
        this.connection = this.url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        String inputLine = in.readLine();
        in.close();
        
        // grab json object
        JSONObject obj = new JSONObject(inputLine);
        
        // check to make sure user was added and exists in database by checking that the randomly generated zipCodes match
        String actual = obj.getJSONObject("profile").getJSONObject("address").get("zipCode").toString();
        assertEquals(actual, this.zipCode);
        if (actual.equals(this.zipCode)) {
            System.out.println("User Insert Success!");
        }
        
    }
    
}
