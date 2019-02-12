/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.IsFriendDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.IsFriend;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author root
 */
@WebServlet(name = "FriendServlet", urlPatterns = {"/ManageFriends"})
public class FriendServlet extends HttpServlet {

    

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
        
        viewFriend(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * and the action removeFriends and make new request
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserUtil util = new UserUtil();
        Optional<User> userOpt = util.getUserOptional(request);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            String action = request.getParameter("action");
            
            switch (action) {
                case "remove":
                    removeFriend(request, response, user.getId());
                    break;
                case "search&add":
                    searchAndAdd(request, response);
                    break;
                case "accept":
                    accept(request, response);
                    break;
                case "refuse":
                    refuse(request, response);
                    break;
                default:
                    viewFriend(request, response);
            }
            
            viewFriend(request, response);
        } else {
            String error = "YOU DON'T HAVE ACCESS";
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/error?error="+error);
        }
    }
    
    private void viewFriend(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        UserUtil util = new UserUtil();
        
        Optional<User> userOpt = util.getUserOptional(request);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            IsFriendDAO friendDAO = new IsFriendDAO();

            List<User> friends = friendDAO.getFriends(user.getId());
            
            List<User> requestFriends = friendDAO.getRequestFriends(user.getId());

            //inserisco gli elementi nella sessione
            HttpSession session = request.getSession();
            session.setAttribute("error", false);
            request.setAttribute("isAdmin", user.isAdmin());
            session.setAttribute("friends", friends);
            session.setAttribute("requestFriends", requestFriends);
            request.getRequestDispatcher("/ROOT/profile/ViewFriends.jsp").forward(request, response);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/error?error="+"YOU DON'T HAVE ACCESS");
        }
    }

    private void searchAndAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        UserDAO userDAO = new UserDAO();
        
        UserUtil util = new UserUtil();
        User thisUser = util.getUserOptional(request).get();
        Optional<User> userOptional = userDAO.get(username);

        if (userOptional.isPresent() && !username.equals(thisUser.getUsername())) { // controllo che non digiti lo stesso nome o che non sia sbaglaito l'username
            User userRequesting = userOptional.get();
            
            
            IsFriendDAO isFriendDAO = new IsFriendDAO();
            
            //faccio la richiesta di amicizia
            isFriendDAO.create(new IsFriend(thisUser.getId(),userRequesting.getId()));
            
            
            viewFriend(request, response);
        } else {
            //username inesistente o lo stesso mio
            request.setAttribute("error", true);
            request.setAttribute("isAdmin", new UserUtil().getUserOptional(request).get().isAdmin());
            request.getRequestDispatcher("/ROOT/profile/ViewFriends.jsp").forward(request, response);
        }
    }

    private void removeFriend(HttpServletRequest request, HttpServletResponse response, long userId) {
        
        //obtain id of user to delete
        long friendId = Long.valueOf(request.getParameter("friendId"));
        
        IsFriendDAO isFriendDAO = new IsFriendDAO();
        List<User> friends = isFriendDAO.getFriends(userId);
        
        //controllo siano realmente amici
        boolean isReallyFriends=false;
        for(User f : friends){
            if(f.getId()==friendId)
                isReallyFriends=true;
        }
        
        if (isReallyFriends) {
            isFriendDAO.delete(new IsFriend(friendId, userId));
            isFriendDAO.delete(new IsFriend(userId, friendId));
        }
    }

    private void accept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserUtil util = new UserUtil();
        User thisUser = util.getUserOptional(request).get();
        
        long friendId = Long.valueOf(request.getParameter("friendId"));
        UserDAO userDAO = new UserDAO();
        Optional<User> userRequestOptional = userDAO.get(friendId);
        
        if (userRequestOptional.isPresent()) {
             IsFriendDAO isFriendDAO = new IsFriendDAO();
             isFriendDAO.create(new IsFriend(thisUser.getId(),userRequestOptional.get().getId()));
             
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/error?error=" + "YOU DON'T HAVE ACCESS");
        }
    }

    private void refuse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserUtil util = new UserUtil();
        User thisUser = util.getUserOptional(request).get();
        
        long friendId = Long.valueOf(request.getParameter("friendId"));
        UserDAO userDAO = new UserDAO();
        Optional<User> userRequestOptional = userDAO.get(friendId);
        
        if (userRequestOptional.isPresent()) {
             IsFriendDAO isFriendDAO = new IsFriendDAO();
             isFriendDAO.delete(new IsFriend(userRequestOptional.get().getId(),thisUser.getId()));
             
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/error?error=" + "YOU DON'T HAVE ACCESS");
        }
    }
    

}