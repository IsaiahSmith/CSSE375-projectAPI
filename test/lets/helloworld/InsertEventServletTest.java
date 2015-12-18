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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duffytj
 */
public class InsertEventServletTest {
    
    URL url;
    URLConnection connection;
    String title;
    
    
    public InsertEventServletTest() {
        try {
            // "unique" title for event
            this.title = Long.toString(new Date().getTime());
            
            // add event
            this.url = new URL("http://localhost:8080/CSSE375-projectAPI/InsertEventServlet?owner_id=test@test.com&title=" + this.title + "&street=SampleStreet&city=SampleCity&state=IN&zipCode=47803&description=NoDescription&tags=SampleTag");
            this.connection = url.openConnection();
            this.url.getContent();
        } catch (Exception e) {
            this.url = null;
            this.connection = null;
            System.out.println("url exception");
        }
    }
    
    public void setUpClass() {
    }
    
    public static void tearDownClass() {
    }

    /**
     * Test inserting a new event
     */
    @Test
    public void testInsertEvent() throws Exception {            
        this.url = new URL("http://localhost:8080/CSSE375-projectAPI/GetAllEventsServlet");
        this.connection = this.url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        String inputLine = in.readLine();
        in.close();
        
        // break up the returned content into an array of json strings
        String[] jsonList = inputLine.split("@@@@@");
        
        // grab most recently inserted event as a json object
        JSONObject obj = new JSONObject(jsonList[jsonList.length - 1]);
        
        String actual = obj.get("title").toString();
        
        // check to make sure event was added and exists in database in last spot
        assertEquals(actual, this.title);
        
        if (actual.equals(this.title)) {
            System.out.println("Event Insert Success!");
        }
    }
    
}
