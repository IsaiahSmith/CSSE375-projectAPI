/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duffytj
 */
public class EventTest {
    String host_id;
    String title;
    Point2D.Double loc;
    Date startingTime;
    Date endtTime;
    String description;
    ArrayList<String> tags;
    
    public EventTest() {
        this.host_id = "unique_id";
        this.title = "title";
        this.loc = new Point2D.Double();
        this.startingTime = new Date();
        this.endtTime = new Date();
        this.description = "No Description";
        this.tags = new ArrayList<String>();
        this.tags.add("tag1");
        this.tags.add("tag2");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of toEventDoc method, of class Event.
     */
    @Test
    public void testToEventDoc() {
        Event e = new Event(host_id, title, loc, startingTime, endtTime, description, tags);
        Document doc = e.toEventDoc();
        assertEquals(doc.get("host_id"), host_id);
        assertEquals(doc.get("title"), title);
    }
}
