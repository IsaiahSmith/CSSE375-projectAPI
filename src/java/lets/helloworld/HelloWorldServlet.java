/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;

/**
 *
 * @author hamiltjc
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/HelloWorldServlet"})
public class HelloWorldServlet extends AbstractServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("does it work?");
        try {
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            try {

                String username = request.getParameter("username");
                String pass = request.getParameter("password");
                //String username = "Stone66";

                FindIterable<Document> iterable = mDB.getCollection("test").find(new BasicDBObject("_id", username));

                Document doc = iterable.first();
                String test = doc.getString("password");
                String ans = "" + test.equals(pass);

                writer.write(ans);
                writer.flush();
                writer.close();
                System.out.println("yes!");
            } catch (Exception e) {
                try {
                    writer.write("false");
                    writer.flush();
                    writer.close();
                    System.out.println("no!");
                } catch (Exception e2) {
                    System.out.println("Super no.");
                }
            }
        } catch (Exception e3) {

        }

    }

}
