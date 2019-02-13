/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mattia
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

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
        
        //mi ricavo lo user dal coockie
        UserUtil uUtil = new UserUtil();
        Optional<User> userOptional = uUtil.getUserOptional(request);
         
        
        //se non è loggato
        if(!userOptional.isPresent()){
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU ARE NOT LOGGED IN");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
        } else if(userOptional.isPresent() && !userOptional.get().isAdmin()){
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU CAN'T ACCESS THIS AREA");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
        }else{
            User user = userOptional.get();
            long newAdminID = 0, deleteItemID= 0;
            String modify = request.getParameter("modify");
            
            //funzioni di modifica
            if("admin".equals(modify)){
                newAdminID = Long.parseLong(request.getParameter("userID"));
                User newAdmin = new UserDAO().get(newAdminID).get();
                newAdmin.setIsAdmin(true);
                new UserDAO().updateWithoutImage(newAdmin.getId(), newAdmin);
            }
            
            if("deleteItem".equals(modify)){
                deleteItemID = Long.parseLong(request.getParameter("itemID"));
                new ItemDAO().delete(deleteItemID);
            }

            
            //prendo tutti gli items
            List<Item> items = new ItemDAO().getAllForAdminOrderedByName();
            Map<Long, String> mapItems = new HashMap<>();
            for(Item elem : items){
                mapItems.put(elem.getId(), elem.getName());
            }
            
            //prendo tutti gli user di sistema che non sono admin e non sono io
            List<User> usersNotAdmin = new UserDAO().getAllUsersNotAdmin(user.getId());
            Map<Long, String> mapUsers = new HashMap<>();
            for(User elem : usersNotAdmin){
                mapUsers.put(elem.getId(), elem.getName());
            }
                
            request.setAttribute("mapUsers", mapUsers);
            request.setAttribute("mapItems", mapItems);
            request.getRequestDispatcher("/ROOT/profile/AdminProfile.jsp").forward(request, response);
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

}
