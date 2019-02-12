/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.EmailSender;
import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import it.unitn.aa1718.webprogramming.geolists.utility.PasswordGenerator;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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

//mi ricavo lo user dal coockie
        UserUtil uUtil = new UserUtil();
        Optional<User> userOptional = uUtil.getUserOptional(request);

        if (!userOptional.isPresent()) {
            try {
                request.setAttribute("error", false);
                request.getRequestDispatcher("/ROOT/RequestNewPassword.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU ARE ALREADY SIGNED IN");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
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

        if (userName != null) {
            Optional<User> userOptional = userDAO.get(userName);

            if (userOptional.isPresent()) {
                User thisUser = userOptional.get();

                String newPassword = PasswordGenerator.generate();

                //inivio la nuova password
                EmailSender es = new EmailSender(thisUser.getEmail(), "", "");
                es.sendEmailWithNewPassword(newPassword);

                String hashPassword = "error inside";
                try {
                    hashPassword = HashGenerator.Hash(newPassword);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(RememberPassword.class.getName()).log(Level.SEVERE, null, ex);
                }
                //setto la nuova password nel db
                thisUser.setPassword(hashPassword);
                userDAO.updateWithoutImage(thisUser.getId(), thisUser);

                request.getRequestDispatcher("/ROOT/LandingPage.jsp").forward(request, response);
            } else {
                request.setAttribute("error", true);
                request.getRequestDispatcher("/ROOT/RequestNewPassword.jsp").forward(request, response);
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "BAD REQUEST");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
        }
    }

}
