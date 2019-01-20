/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.ChatDAO;
import it.unitn.aa1718.webprogramming.geolists.database.IsInDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Chat;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "ChatServlet", urlPatterns = {"/ChatServlet"})
public class ChatServlet extends HttpServlet {

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
        
        
        long userID = Long.parseLong(request.getParameter("listID"));
        String action = request.getParameter("action");
        
        
        
        IsInDAO i = new IsInDAO();
        i.getChats(userID);
        
        
        ChatDAO c = new ChatDAO();
        List<Chat> allChats = c.getAll();
        
        
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("allChats", allChats);
        
        System.out.println(allChats);
        
        try {
            request.getRequestDispatcher("/ROOT/Chat.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    private long getUserID(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        UserDAO u = new UserDAO();
        Optional<User> res = u.getUser(thisCookie);
        
        if(res.isPresent())
            return res.get().getId();
        else
            return 0;
            
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
        
        long userID = getUserID(request);
        
        if(userID==0){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
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
        }else{
        
            IsInDAO i = new IsInDAO();
            List<Chat> allChats = i.getChats(userID);
        
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("allChats", allChats);
        
        
        
            try {
                request.getRequestDispatcher("/ROOT/Chat.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
