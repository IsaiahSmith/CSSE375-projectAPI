/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lets.helloworld;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import org.bson.Document;

/**
 *
 * @author hamiltjc
 */
@WebServlet(name = "InsertUserServlet", urlPatterns = {"/InsertUserServlet"})
public class InsertUserServlet extends HttpServlet {
    
    private MangoConnection mango;
    private MongoDatabase mDB;
    private mongoDbAPI api;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            try {
                this.sha = new SHA1();
                String _id = request.getParameter("_id");
                String name = request.getParameter("name");
                String password = sha.encrypt(request.getParameter("password"));
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String zipCode = request.getParameter("zipCode");
                String gender = request.getParameter("gender");
                String dob = request.getParameter("dob");

                
                User newUser = new User(_id, password, name, street, city, state, zipCode, gender, new Date(dob));
                
                
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    public void init() throws ServletException {
        mango = new MangoConnection("lets", "hamilton",
                "Qwerty123");
        mDB = mango.getDB();
        api = new mongoDbAPI(mDB);
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
