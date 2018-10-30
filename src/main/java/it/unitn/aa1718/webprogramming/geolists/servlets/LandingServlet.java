/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import static it.unitn.aa1718.webprogramming.geolists.database.Database.DB_URL;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.CookiesManager;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tommaso
 */
public class LandingServlet extends HttpServlet {

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
            throws ServletException, IOException, NoSuchAlgorithmException {
        

        //Richiedo i cookie in ingresso
        CookiesManager cm = new CookiesManager(request.getCookies());
        User u = cm.checkExistenceCookie("Cookie");
        if(u != null){
            System.out.println("COOKIE TROVATO DI DEFAULT");

            //aggiorno il cookie
            Cookie c = cm.updateUser("Cookie", u);
            response.addCookie(c);
            System.out.println(c.getValue());

            ////////////////////////////////////////////
            //inviare all'utente pagina inerente a lui//
            ////////////////////////////////////////////
        }else{
            System.out.println("COOKIE NON TROVATO DI DEFAULT");
        }

        
        response.setContentType("text/html;charset=UTF-8");
        
        ProductListDAO plistDAO = new ProductListDAO();
        ItemDAO itemDAO = new ItemDAO();
        ComposeDAO composedDAO = new ComposeDAO();
        
        // Get the names of all the lists
        List<ProductList> listOfPL = plistDAO.getAll();
        request.setAttribute("listOfPL", listOfPL);
        
        // For each list save in a map list of it's items
        Map<Long,List<Item>> dict = new HashMap<>();
        for (ProductList list : listOfPL) {            
            long listID = list.getId();
            List<Compose> relationList = composedDAO.getItemsID(listID);
            List<Item> items = new ArrayList<>();
            for (Compose rel : relationList) {
                Optional<Item> res = itemDAO.get(rel.getIdItem());
                if (res.isPresent()) {
                    items.add(res.get());
                }
            }
            if (!items.isEmpty()) {
                dict.put(Long.valueOf(listID), items);
            }
        }
        request.setAttribute("itemsOfList", dict);
        
        request.getRequestDispatcher("/ROOT/LandingPage.jsp").forward(request, response);
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
            
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LandingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LandingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
