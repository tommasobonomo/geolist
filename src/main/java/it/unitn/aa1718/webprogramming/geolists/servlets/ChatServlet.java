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
     * Handles the HTTP <code>POST</code> method. write message on db
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
        String listID = request.getParameter("listID");

        
        
        
        //visualize message
        if (listID == null) {
            try {

                getServletContext().getRequestDispatcher("/").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            //message text
            String text = request.getParameter("text");
        
            //add in db the message
            Timestamp sendTime = new Timestamp((new Date()).getTime());
            Message m = new Message(userID, Long.valueOf(listID), sendTime, text);
            (new MessageDAO()).create(m);
            
            VisualizeMessage v = viewMessageOf(userID, Long.valueOf(listID));
            
            //controll if have access
            if (v.isError()) {
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>NO ACCESS</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>YOU DON'T HAVE ACCESS</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }

            //set attribute
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("listID", Long.valueOf(listID));
            request.setAttribute("messages", v.getMessages());
            request.setAttribute("mapMessageUser", v.getMapMessageUser());

            try {
                getServletContext().getRequestDispatcher("/ROOT/Chat.jsp").forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserUtil util = new UserUtil();

        long userID = util.getUserID(request);
        String listID = request.getParameter("listID");

        if (listID == null) {
            try {

                getServletContext().getRequestDispatcher("/").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            VisualizeMessage v = viewMessageOf(userID, Long.valueOf(listID));
            
            //controll if have access
            if (v.isError()) {
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>YOU DON'T HAVE ACCESS</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>DON'T</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }

            //set attribute
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("listID", Long.valueOf(listID));
            request.setAttribute("messages", v.getMessages());
            request.setAttribute("mapMessageUser", v.getMapMessageUser());

            try {
                getServletContext().getRequestDispatcher("/ROOT/Chat.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private VisualizeMessage viewMessageOf(long userID, long listID) throws IOException {
        //dichiarazioni utili

        AccessDAO a = new AccessDAO();
        VisualizeMessage res = new VisualizeMessage();

        if (userID == 0) { //don't log user
            res.setError(ErrorView.NOUSER);
        } else if (!a.canHaveAccess(userID, listID)) {
            res.setError(ErrorView.NOTACCESS);
        } else {
            //for refresh every few second
            //response.setIntHeader("Refresh", 5);

            MessageDAO msgDao = new MessageDAO();
            List<Message> messages = msgDao.getMessageFromList(listID);
            //Collections.reverse(messages);

            res.setMessages(messages);
            res.createMapMessagesUser(messages);

        }

        return res;
    }
}

enum ErrorView {
    NOTACCESS, NOUSER, MISSPARAMETER, NOERROR
}

class VisualizeMessage {

    private ErrorView error = ErrorView.NOERROR;
    private List<Message> messages;
    private Map<Integer, User> mapMessageUser = new HashMap<>();

    ;
    
    public void setError(ErrorView error) {
        this.error = error;
    }

    public ErrorView getError(ErrorView error) {
        return this.error;
    }

    public boolean isError() {
        return !(error == ErrorView.NOERROR);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void createMapMessagesUser(List<Message> messages) {

        UserDAO userDAO = new UserDAO();

        for (Message m : messages) {
            mapMessageUser.put(m.hashCode(), userDAO.get(m.getIdUser()).get());
            System.out.println(m);
        }
    }

    public Map<Integer, User> getMapMessageUser() {
        return mapMessageUser;
    }
}
