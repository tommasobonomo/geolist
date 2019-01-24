/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.MessageDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Message;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * write message on db
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //user
        UserUtil util = new UserUtil();
        long userID = util.getUserID(request);
        
        //chat/list id
        long listID = Long.valueOf(request.getParameter("listID"));
        
        //message text
        String text = request.getParameter("text");
        
        //add in db the message
        Timestamp sendTime = new Timestamp((new Date()).getTime());
        Message m = new Message(userID, listID, sendTime, text);
        (new MessageDAO()).create(m);
        
        viewMessageOf(request,response);
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
        viewMessageOf(request,response);
    }
    
    private void viewMessageOf(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //dichiarazioni utili
        UserUtil util = new UserUtil();
        AccessDAO a = new AccessDAO();
        
        String listID = request.getParameter("listID");
        
        long userID = util.getUserID(request);
        
        
        if(userID==0){ //don't log user
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>something go wrong, miss cookie</h1>");
                out.println("</body>");
                out.println("</html>");
            }
            
        }
        else if(listID==null){ //miss parameter of the list
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>miss parameter, bad request</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
        else if(!a.canHaveAccess(userID,Long.valueOf(listID))){ 
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>you can't have access</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
        else{
            //for refresh every few second
            //response.setIntHeader("Refresh", 5);
            
            
            
            MessageDAO msgDao = new MessageDAO();
            List<Message> messages = msgDao.getMessageFromList( Long.valueOf(listID) );
            //Collections.reverse(messages);
            UserDAO u = new UserDAO();
                
            Map<Integer,User> mapMessageUser = new HashMap<>();
            for(Message m: messages){
                mapMessageUser.put(m.hashCode(),u.get(m.getIdUser()).get());
                System.out.println(m);
            }
            
            //set attribute
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("listID", listID);
            request.setAttribute("messages", messages);
            request.setAttribute("mapMessageUser", mapMessageUser);
            
            try {
                getServletContext().getRequestDispatcher("/ROOT/Chat.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
    }
}




