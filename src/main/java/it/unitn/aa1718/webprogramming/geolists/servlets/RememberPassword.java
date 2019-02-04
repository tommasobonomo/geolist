/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.EmailSender;
import it.unitn.aa1718.webprogramming.geolists.utility.PasswordGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
@WebServlet(name = "RememberPassword", urlPatterns = {"/form-action/new-password"})
public class RememberPassword extends HttpServlet {

    /**
     * send jsp page for request new password
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("error", false);
            request.getRequestDispatcher("/ROOT/RequestNewPassword.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * send mail with new password
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        UserDAO userDAO = new UserDAO();

        Optional<User> userOptional = userDAO.get(userName);

        if (userOptional.isPresent()) {
            User thisUser = userOptional.get();
            
            String newPassword = PasswordGenerator.generate();
            
            //inivio la nuova password
            EmailSender es = new EmailSender(thisUser.getEmail(), "", "");
            es.sendEmailWithNewPassword(newPassword);
            
            //setto la nuova password nel db
            thisUser.setPassword(newPassword);
            userDAO.update(thisUser.getId(), thisUser);
            
            request.getRequestDispatcher("/ROOT/LandingPage.jsp").forward(request, response);
        } else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/ROOT/RequestNewPassword.jsp").forward(request, response);
        }
    }

}
