/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.MessageDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Message;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    request.setAttribute("error", "you don't have access");
                    getServletContext().getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            
            //set attribute
            User u = new UserDAO().get(userID).get();
            ProductList pl = new ProductListDAO().get(Long.valueOf(listID)).get();
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("listID", Long.valueOf(listID));
            request.setAttribute("listName", pl.getName());
            request.setAttribute("myUsername", u.getUsername());
            request.setAttribute("userCookie", u.getCookie());
            request.setAttribute("url", "wss://localhost:" + String.valueOf(request.getLocalPort()) + "/chat/");

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
        }

    }

    public Map<Integer, User> getMapMessageUser() {
        return mapMessageUser;
    }
}
