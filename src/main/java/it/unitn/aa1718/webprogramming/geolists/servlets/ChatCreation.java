/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.ChatDAO;
import it.unitn.aa1718.webprogramming.geolists.database.IsFriendDAO;
import it.unitn.aa1718.webprogramming.geolists.database.IsInDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Chat;
import it.unitn.aa1718.webprogramming.geolists.database.models.IsIn;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giorgio
 */
@WebServlet(name = "ChatCreation", urlPatterns = {"/createChat"})
public class ChatCreation extends HttpServlet {

    
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
        
        UserUtil util = new UserUtil();
        
        long userID = util.getUserID(request);
        
        if(userID==0){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>something go wrong, miss cookie or user</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }else{
            
            IsFriendDAO i = new IsFriendDAO();
            List<User> friends = i.getFriends(userID);
        
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("friends", friends);
        
            try {
                request.getRequestDispatcher("/ROOT/CreateChat.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserUtil util = new UserUtil();
        
        //leggo parametri richiesta
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        String[] members = request.getParameterValues("member");
        
        //inserisco la chat
        Chat newChat = new Chat(name, description, image);
        ChatDAO chatDao = new ChatDAO();
        
        Optional<Long> chatOptionalID = chatDao.createAndReturnId(newChat);
        long chatID=0;
        if(chatOptionalID.isPresent()){
            chatID = chatOptionalID.get();
            
            //inserisco i membri nella chat
            IsInDAO isInDAO = new IsInDAO();
            for (String i : members){
                isInDAO.create(new IsIn(Long.parseLong(i), chatID));
            }
            
            isInDAO.create( new IsIn( util.getUserID(request),chatID ));
        }
        else
            System.out.println("somethin go wrong :(");
        
    }

    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
