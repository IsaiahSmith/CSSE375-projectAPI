/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author duffytj
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({lets.helloworld.mongoDbAPITest.class, lets.helloworld.InsertEventServletTest.class, lets.helloworld.InsertUserServletTest.class, lets.helloworld.GetUserServletTest.class, lets.helloworld.GetAllEventsServletTest.class, lets.helloworld.UserTest.class, lets.helloworld.EventTest.class,})
public class HelloworldSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}
