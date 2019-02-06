/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.CatItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
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
 * Handles info to give to Item JSP
 * @author tommaso
 */
@WebServlet(
        name = "ItemServlet", 
        urlPatterns = "/ItemServlet"
)
public class ItemServlet extends HttpServlet {

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
            case "viewItem":
            default:
                viewItem(request,response);
                request.getRequestDispatcher("/ROOT/Item.jsp").forward(request, response);
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

    private void viewItem(HttpServletRequest request, HttpServletResponse response) {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        
        // Get needed DAOs
        ItemDAO itemDAO = new ItemDAO();
        
        // Try retrieving Item object
        Optional<Item> itemOpt = itemDAO.get(itemID);
        
        String id = "";
        String name = "";
        String note = "";
        String category = "";
        
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            id = String.valueOf(item.getId());
            name = item.getName();
            note = item.getNote();
            long categoryID = item.getIdCat();
            CatItemDAO categoryDAO = new CatItemDAO();
            Optional<CatItem> categoryOpt = categoryDAO.get(categoryID);
            if(categoryOpt.isPresent()){
                category = categoryOpt.get().getName(); 
            }
        }
        
        //mi ricavo lo user dal coockie per sapere se l'utente è admin
        UserUtil uUtil = new UserUtil();
        Optional<User> userOptional = uUtil.getUserOptional(request);
        User user = null;
        Boolean isAdmin= false;
        if(userOptional.isPresent()){
            user = userOptional.get();
            isAdmin = user.isAdmin();
        }
        
        
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("itemID", id);
        request.setAttribute("name", name);
        request.setAttribute("note", note);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("category", category);
        request.setAttribute("listID", request.getParameter("listID"));
    }

    private void retrieveImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        long id = Long.parseLong(request.getParameter("itemID"));
        
        ItemDAO itemDAO = new ItemDAO();
        Optional<byte[]> byteArrayOpt = itemDAO.getBlobImageFromItem(id);
        
        if (byteArrayOpt.isPresent()) {
            response.setContentType("image/gif");
            OutputStream os = response.getOutputStream();
            os.write(byteArrayOpt.get());
            os.flush();
            os.close();
        }
    }

}
