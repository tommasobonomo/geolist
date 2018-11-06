/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import it.unitn.aa1718.webprogramming.geolists.utility.CookieManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            throws ServletException, IOException {
        

        //Richiedo i cookie in ingresso e controllo che faccia parte di un utente loggato
        //oppure di uno anonimo
        User u = null; UserAnonimous ua = null;
        if("/".equals(request.getRequestURI())){
            CookieManager cm = new CookieManager(request.getCookies());
            u = cm.checkExistenceUser();
            if (u == null){     // se non è un utente registrato controllo che sia anonimo
                request.getSession().setAttribute("logged", false);
                ua = cm.checkExistenceAnonimous();
                if(ua == null){     // se non è nemmeno anonimo ne creo uno anonimo
                    Cookie c = cm.createAnonimous(response);
                    response.addCookie(c);
                    System.out.println("CREO UTENTE ANONIMO PER IL PRIMO LOGIN");
                }
            }
        }
      
        response.setContentType("text/html;charset=UTF-8");
        
        
        // variabili varie
        ProductListDAO plistDAO = new ProductListDAO();
        ItemDAO itemDAO = new ItemDAO();
        ComposeDAO composedDAO = new ComposeDAO();
        AccessDAO accessDAO = new AccessDAO();
        
        
        // Get the names of all the lists
        List<ProductList> listOfPL = null;
        if(u != null)
            listOfPL = plistDAO.getListUser(u.getId());
        else if (ua != null){
            listOfPL = plistDAO.getListAnon(ua.getId());
            System.out.println("ID USER ANONIMO " +ua.getId());
            System.out.println("COOKIE ANONIMO ATTUALE " + ua.getCookie());
        }
        else
            listOfPL = plistDAO.getAll();
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
