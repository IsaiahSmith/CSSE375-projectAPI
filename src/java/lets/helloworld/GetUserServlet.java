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
@WebServlet(name = "GetUserServlet", urlPatterns = {"/GetUserServlet"})
public class GetUserServlet extends AbstractServlet {
    
//    private MangoConnection mango;
//    private MongoDatabase mDB;
//    private mongoDbAPI api;

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

                String _id = request.getParameter("_id");
                
                User user = new User(api.getUser(_id));
                String ans = user.toString();
                
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
