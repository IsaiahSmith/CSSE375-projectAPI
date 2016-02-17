/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.Date;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hamiltjc
 */
@WebServlet(name = "InsertEventServlet", urlPatterns = {"/InsertEventServlet"})
public class InsertEventServlet extends AbstractServlet {

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
        try {
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            try {
                String owner_id = request.getParameter("owner_id");
                User u = new User(api.getUser(owner_id));
                
                String loc = "";
                String title = request.getParameter("title");
                String startTime = "1/1/1900"; //request.getParameter("startTime");
                String endTime = "1/2/1900"; //request.getParameter("endTime");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String zipCode = request.getParameter("zipCode");
                String description = request.getParameter("description");
                String tagsString = request.getParameter("tags");
                
                ArrayList<String> tags = parseTags(tagsString);
                
                int[] locValues = parseLoc(loc);
                
                u.addEvent(new Event(owner_id, title, new Point2D.Double(locValues[0], locValues[1]), new Date(startTime), new Date(endTime), description, tags), mDB);
                
                String ans = "true";
                writer.write(ans);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                try {
                    writer.write("false");
                    writer.flush();
                    writer.close();
                } catch (Exception e2) {
                    
                }
            }
        } catch (Exception e3) {

        }

    }
    
    private int[] parseLoc(String loc) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int[] ans = {0, 0};
        return ans;
    }

    private ArrayList<String> parseTags(String tagsString) {
        String[] strSplit = tagsString.split(", ");
        ArrayList<String> ans = new ArrayList<>();
        ans.addAll(Arrays.asList(strSplit));
        return ans;
    }

}
