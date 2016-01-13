/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import java.io.IOException;
import java.io.OutputStreamWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hamiltjc
 */
@WebServlet(name = "InsertUserServlet", urlPatterns = {"/InsertUserServlet"})
public class InsertUserServlet extends AbstractServlet {
    
    private SHA1 sha;
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
                //this.sha = new SHA1();
                String _id = request.getParameter("_id");
                String name = request.getParameter("name");
                String password = request.getParameter("password");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String zipCode = request.getParameter("zipCode");
                String gender = request.getParameter("gender");
                String dob = request.getParameter("dob");
                
                User newUser = new User(_id, password, name, street, city, state, zipCode, gender, dob);
                
                int result = api.insertUser(newUser);
                String ans = "false";
                if(result == 1) {
                    ans = "true";
                }
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
}
