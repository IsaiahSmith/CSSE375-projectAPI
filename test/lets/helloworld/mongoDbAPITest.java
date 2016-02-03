/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import com.mongodb.client.MongoDatabase;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duffytj
 */
public class mongoDbAPITest {
    
    private MangoConnection mango;
    private MongoDatabase mDB;
    private mongoDbAPI api;
    private String user_id;
    
    public mongoDbAPITest() {
        mango = new MangoConnection("test_db", "Temp",
                "Qwerty123");
        mDB = mango.getDB();
        api = new mongoDbAPI(mDB);
        user_id = "";
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insertUser method, of class mongoDbAPI.
     */
    @Test
    public void testInsertUser() {
        System.out.println("insertUser");
        
        // ensures a "unique" id for each new user added
        String _id = Long.toString(new Date().getTime()) + "@test.com";
        user_id = _id;
        String name = "sample_name";
        String password = "sample_password";
        String street = "sample_street";
        String city = "sample_city";
        String state = "sample_state";
        String zipCode = "sample_zipCode";
        String gender = "sample_gender";
        String dob = "sample_dob";
                
        User newUser = new User(_id, password, name, street, city, state, zipCode, gender, dob);
                
        int result = api.insertUser(newUser);
        
        // ensure that the document was "successfully inserted" into the database
        assertEquals(result, 1);
        
        
        // ensure that the document inserted is actually the correct one
        try {
            JSONObject json = new JSONObject(api.getUser(_id).toJson());
            JSONObject profile = json.getJSONObject("profile");
            JSONObject address = profile.getJSONObject("address");
            assertEquals(_id, json.get("_id"));
            assertEquals(name, profile.get("name"));
            assertEquals(password, json.get("password"));
            assertEquals(street, address.get("street"));
            assertEquals(city, address.get("city"));
            assertEquals(state, address.get("state"));
            assertEquals(zipCode, address.get("zipCode"));
            assertEquals(gender, profile.get("gender"));
            assertEquals(dob, profile.get("dob"));
        } catch (JSONException ex) {
            Logger.getLogger(mongoDbAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Something in the JSON access failed in insertUserTest");
        }
        
    }

    /**
     * Test of getUser method, of class mongoDbAPI.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        
        // ensures a "unique" id for each new user added
        String _id = Long.toString(new Date().getTime()) + "@test.com";
        String name = "sample_name";
        String password = "sample_password";
        String street = "sample_street";
        String city = "sample_city";
        String state = "sample_state";
        String zipCode = "sample_zipCode";
        String gender = "sample_gender";
        String dob = "sample_dob";
                
        User newUser = new User(_id, password, name, street, city, state, zipCode, gender, dob);
                
        int result = api.insertUser(newUser);
        
        // ensure that the document was "successfully inserted" into the database
        assertEquals(result, 1);
        
        
        // ensure that the document inserted is actually the correct one
        try {
            JSONObject json = new JSONObject(api.getUser(_id).toJson());
            JSONObject profile = json.getJSONObject("profile");
            JSONObject address = profile.getJSONObject("address");
            assertEquals(_id, json.get("_id"));
            assertEquals(name, profile.get("name"));
            assertEquals(password, json.get("password"));
            assertEquals(street, address.get("street"));
            assertEquals(city, address.get("city"));
            assertEquals(state, address.get("state"));
            assertEquals(zipCode, address.get("zipCode"));
            assertEquals(gender, profile.get("gender"));
            assertEquals(dob, profile.get("dob"));
        } catch (JSONException ex) {
            Logger.getLogger(mongoDbAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Something in the JSON access failed in insertUserTest");
        }
    }

    /**
     * Test of getEvent method, of class mongoDbAPI.
     */
    @Test
    public void testGetEvent() {
        System.out.println("getEvent");
        String knownEventID1 = "56719c1d44fd702acc7a0c85";
        String knownEventID2 = "56731ae9bfb2430dc03e87e1";
        String knownEventID3 = "56731d97bfb2430dc03e87e3";
        
        String knownDescription1 = "Testing Events";
        String knownDescription2 = "NoDescription";
        String knownDescription3 = "NoDescription";
        
        String knownUserID1 = "test@test.com";
        String knownUserID2 = "test@test.com";
        String knownUserID3 = "test@test.com";
        
        String knownTitle1 = "Tester";
        String knownTitle2 = "SampleTitle";
        String knownTitle3 = "SampleTitleROUNDTWO";
        
        Document eventDoc1 = api.getEvent(knownEventID1).toEventDoc();
        assertEquals(knownDescription1, eventDoc1.get("description"));
        assertEquals(knownUserID1, eventDoc1.get("host_id"));
        assertEquals(knownTitle1, eventDoc1.get("title"));
        
        Document eventDoc2 = api.getEvent(knownEventID2).toEventDoc();
        assertEquals(knownDescription2, eventDoc2.get("description"));
        assertEquals(knownUserID2, eventDoc2.get("host_id"));
        assertEquals(knownTitle2, eventDoc2.get("title"));
        
        Document eventDoc3 = api.getEvent(knownEventID3).toEventDoc();
        assertEquals(knownDescription3, eventDoc3.get("description"));
        assertEquals(knownUserID3, eventDoc3.get("host_id"));
        assertEquals(knownTitle3, eventDoc3.get("title"));
        
    }

    /**
     * Test of login method, of class mongoDbAPI.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
    }
    
}
