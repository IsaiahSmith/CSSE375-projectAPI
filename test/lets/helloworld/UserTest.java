/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.geom.Point2D;

/**
 *
 * @author duffytj
 */
public class UserTest {
    String _id;
    String password;
    String name;
    String street;
    String city;
    String state;
    String zipCode;
    String gender;
    String dob;
    private MangoConnection mango;
    private MongoDatabase mDB;
    
    public UserTest() {
    this._id = "unique_id";
    this.password = "password";
    this.name = "John Smith";
    this.street = "Street";
    this.city = "City";
    this.state = "State";
    this.zipCode = "12345";
    this.gender = "Male";
    this.dob = "1/1/2010";
    this.mango = new MangoConnection("test_db", "Temp",
                "Qwerty123");
    this.mDB = mango.getDB();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of toDoc method, of class User.
     */
    @Test
    public void testToDoc() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        Document doc = user.toDoc();
        assertEquals(_id, doc.get("_id"));
        assertEquals(password, doc.get("password"));
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        assertEquals(user.getId(), _id);
    }

    /**
     * Test of getInterests method, of class User.
     */
    @Test
    public void testGetInterests() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        user.addInterests("Interest", mDB);
        assertEquals("Interest", user.getInterests().get(0));
    }

    /**
     * Test of getFollowing method, of class User.
     */
    @Test
    public void testGetFollowing() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        user.follow("test@test.com", mDB);
        ArrayList<String> following = user.getFollowing();
        assertEquals("test@test.com", following.get(0));
    }

    /**
     * Test of follow method, of class User.
     */
    @Test
    public void testFollow() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        user.follow("test@test.com", mDB);
        ArrayList<String> following = user.getFollowing();
        assertEquals("test@test.com", following.get(0));
    }

    /**
     * Test of addInterests method, of class User.
     */
    @Test
    public void testAddInterests() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        user.addInterests("Interest", mDB);
        assertEquals("Interest", user.getInterests().get(0));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEquals() {
        User user = new User(_id, password, name, street, city, state, zipCode, gender, dob);
        assertTrue(user.equals(user));
    }
}
