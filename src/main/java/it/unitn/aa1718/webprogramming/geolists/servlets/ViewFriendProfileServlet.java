/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.IsFriendDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.io.OutputStream;
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
@WebServlet(name = "ViewFriendProfileServlet", urlPatterns = {"/friend/profile"})
public class ViewFriendProfileServlet extends HttpServlet {

    

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
        
        String action = request.getParameter("action");
        switch (action) {
            case "retrieveImage":
                retrieveImage(request,response);
                break;
            case "viewFriend":
            default:
                viewFriend(request,response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    private void viewFriend(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long friendID = Long.parseLong(request.getParameter("friendId"));
        
        // Get needed DAOs
        UserDAO userDAO = new UserDAO();
        IsFriendDAO isFriendDAO = new IsFriendDAO();
        
        UserUtil userUtil = new UserUtil();
        Optional<User> thisUserOpt = userUtil.getUserOptional(request);
        
        // Try retrieving friend
        Optional<User> friendOpt = userDAO.get(friendID);
        
        
        // se l'utente è loggato, l'amico esiste e sono realmente amici
        if (friendOpt.isPresent() && thisUserOpt.isPresent() && isFriendDAO.isFriend(thisUserOpt.get().getId(), friendOpt.get().getId())) {
            User friend = friendOpt.get();

            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("friend", friend);
            request.getRequestDispatcher("/ROOT/profile/FriendProfile.jsp").forward(request, response);
        }else{
            String error= "you don't have access";
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/error?error="+error);
        }
        
        
        
        
        
    }

    private void retrieveImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        long id = Long.parseLong(request.getParameter("friendId"));
        
        UserDAO userDAO = new UserDAO();
        Optional<byte[]> byteArrayOpt = userDAO.getBlobImageFromItem(id);
        
        if (byteArrayOpt.isPresent()) {
            response.setContentType("image/gif");
            OutputStream os = response.getOutputStream();
            os.write(byteArrayOpt.get());
            os.flush();
            os.close();
        }
    }

}
