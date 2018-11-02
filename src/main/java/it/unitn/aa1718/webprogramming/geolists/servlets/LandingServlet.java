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
        
        // Prepare data to send to the JSP page        
        Optional<User> optU = cm.checkExistenceCookie("Cookie");
        Optional<List<ProductList>> listOfPL;
        Optional<Map<Long,List<Item>>> itemsOfList;
        
        if(optU.isPresent()){
            System.out.println("COOKIE TROVATO DI DEFAULT");
            User u = optU.get();
            
            //aggiorno il cookie
            Cookie c = cm.updateUser("Cookie", u);
            response.addCookie(c);
            System.out.println(c.getValue());

            // Based on the user u, retrieve his lists and items of those lists 
            
            ProductListDAO plistDAO = new ProductListDAO();
            ItemDAO itemDAO = new ItemDAO();
            ComposeDAO composedDAO = new ComposeDAO();
            
            // Get lists of user u
            listOfPL = Optional.of(plistDAO.getListOfUser(u));
            
            // For each list save in a map list of it's items
            Map<Long,List<Item>> dict = new HashMap<>();

            for (ProductList list : listOfPL.get()) {            
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
            itemsOfList = Optional.of(dict);
        } else {
            // No cookie --> no user, so we don't have any lists or items            
            System.out.println("COOKIE NON TROVATO DI DEFAULT");
            listOfPL = Optional.empty();
            itemsOfList = Optional.empty();
        }

        request.setAttribute("user", optU);
        request.setAttribute("lists", listOfPL);
        request.setAttribute("itemsPerList", itemsOfList);
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
