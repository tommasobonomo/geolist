/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author mattia
 */
@WebServlet(name = "SearchItem", urlPatterns = {"/form-action/search"})
public class SearchItem extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        //parametri di ricerca
        HttpSession session = request.getSession();
        String wordSearched = null;
        Integer categorySearched = null;
        String orderBy = null;
        orderBy = (String) request.getParameter("orderBy");
        

        //recupero da dove di dovere
        if(orderBy == null){
            wordSearched = (String) request.getParameter("wordSearched");
            wordSearched = wordSearched.toLowerCase();
            categorySearched = Integer.parseInt(request.getParameter("categorySearched"));
        } else{
            wordSearched = (String) session.getAttribute("wordSearched");
            categorySearched = (Integer) session.getAttribute("categorySearched");
        }

        
        //effettuo la ricerca
        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = null;
        if(categorySearched == 0){
            items = itemDAO.getFromPattern(wordSearched);
        }
        else{
            items = itemDAO.getFromPatternAndCategory(wordSearched, categorySearched);
        }
        
           
        //ordino se c'è bisogno di ordinare qualcosa
        if("alfabetico".equals(orderBy)){
           Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item i1, Item i2) {
                    return i1.getName().compareTo(i2.getName());
                }
            });
        }
        if("categoria".equals(orderBy)){
           Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item i1, Item i2) {
                    return Long.valueOf(i1.getIdCat()).compareTo(Long.valueOf(i2.getIdCat()));
                }
            });
        }
        
         
        //inserisco gli elementi nella sessione
        session.setAttribute("items", items);
        session.setAttribute("wordSearched", wordSearched);
        session.setAttribute("categorySearched", categorySearched);
        request.getRequestDispatcher("/ROOT/SearchPage.jsp").forward(request, response);
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
