/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.CatProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemPermissionDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * servlet that manage the research on the items in the database
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
        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = null;
        int nResults = 0, pageTot;
        String orderBy = (String) request.getParameter("orderBy");
        if (orderBy == null) {
            orderBy = "noOrder";
        }

        //calcolo page e offset
        int start = 1;
        try{start = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){}
        int total = 12;
        start = start - 1;
        if (start != 0) {
            start = start * total + 1;
        }

        //COSE DI GIORGIO
        UserUtil u = new UserUtil();
        Optional<User> userOpt = u.getUserOptional(request);
        Optional<UserAnonimous> userAnoOpt = u.getUserAnonymousOptional(request);
        
        //recupero da dove di dovere
        if ("noOrder".equals(orderBy)) {
            wordSearched = (String) request.getParameter("wordSearched");
            wordSearched = wordSearched.toLowerCase();
            categorySearched = Integer.parseInt(request.getParameter("categorySearched"));
            //faccio ricerca normale
            if (categorySearched == 0) {
                items = itemDAO.getFromPattern(wordSearched, start, total, userOpt);
                nResults = itemDAO.getNResultsFromPattern(wordSearched,userOpt);
            } else {
                items = itemDAO.getFromPatternAndCategory(wordSearched, categorySearched, start, total, userOpt);
                nResults = itemDAO.getNResultsFromPatternAndCategory(wordSearched, categorySearched, userOpt);
            }
        } else {
            wordSearched = (String) session.getAttribute("wordSearched");
            wordSearched = wordSearched.toLowerCase();
            categorySearched = (Integer) session.getAttribute("categorySearched");
            //faccio ricerca ordinata
            if ("alfabetico".equals(orderBy)) {
                if (categorySearched == 0) {
                    items = itemDAO.getFromPatternOrderedByAlfabetico(wordSearched, start, total, userOpt);
                    nResults = itemDAO.getNResultsFromPattern(wordSearched,userOpt);
                } else {
                    items = itemDAO.getFromPatternAndCategoryOrderedByAlfabetico(wordSearched, categorySearched, start, total, userOpt);
                    nResults = itemDAO.getNResultsFromPatternAndCategory(wordSearched, categorySearched, userOpt);
                }
            }
            if ("categoria".equals(orderBy)) {
                if (categorySearched == 0) {
                    items = itemDAO.getFromPatternOrderedByCategory(wordSearched, start, total, userOpt);
                    nResults = itemDAO.getNResultsFromPattern(wordSearched,userOpt);
                } else {
                    items = itemDAO.getFromPatternAndCategory(wordSearched, categorySearched, start, total, userOpt);
                    nResults = itemDAO.getNResultsFromPatternAndCategory(wordSearched, categorySearched, userOpt);
                }
            }
        }

        //conto le pagine di item totali
        pageTot = nResults / 12;
        if (nResults % 12 != 0) {
            pageTot++;
        }

        //attributi della sessione
        Map<Long, List<Long>> mapListAddPermissionByItem = new HashMap<>();
        Map<Long, ProductList> mapListOfUser = new HashMap<>();
        boolean isLogged = false;

        //se è loggato
        if (userOpt.isPresent()) {
            ItemPermissionDAO itemPermissionDAO = new ItemPermissionDAO();

            for (Item i : items) {
                long userId = userOpt.get().getId();
                List<Long> list = itemPermissionDAO.getPossibleMyListToAddItem(userId, i.getId());
                mapListAddPermissionByItem.put(i.getId(), list);
            }

            ProductListDAO plDAO = new ProductListDAO();
            List<ProductList> listOfProductListUser = plDAO.getListUser(userOpt.get().getId());

            for (ProductList list : listOfProductListUser) {
                mapListOfUser.put(list.getId(), list);
            }

            isLogged = true;

        } else if (userAnoOpt.isPresent()) { //se è anonimo
            ProductListDAO plDAO = new ProductListDAO();

            Optional<ProductList> listAnonymousOpt = plDAO.getListAnon(userAnoOpt.get().getId());

            if (listAnonymousOpt.isPresent()) {
                //aggiungo l'unica lista dell'utente anonimo alla mappa delle liste
                ProductList listAnonymous = listAnonymousOpt.get();
                mapListOfUser.put(listAnonymous.getId(), listAnonymous);
                long categoryOfListId = listAnonymous.getIdCat();

                ItemPermissionDAO itemPermissionDAO = new ItemPermissionDAO();

                //se ha il permesso di essere aggiunto agli item la metto
                for (Item i : items) {
                    if (itemPermissionDAO.catogoryItemIsUnderCategoryList(categoryOfListId, i.getIdCat())) {
                        List<Long> list = new ArrayList<>();
                        list.add(listAnonymous.getId());
                        mapListAddPermissionByItem.put(i.getId(), list);
                    }
                }
            }
            isLogged = false;

        } else {
            response.sendRedirect("/");
        }

        //genero la mappa che al suo interno ha gli id delle categorie e il nome della categoria relativo
        Map<Long, String> mapIdCat = new CatProductListDAO().getAllNamesOfCat();

        //inserisco gli elementi nella sessione
        request.setAttribute("page", request.getParameter("page"));
        request.setAttribute("pageTot", pageTot);
        request.setAttribute("orderBy", orderBy);
        session.setAttribute("mapIdCat", mapIdCat);
        session.setAttribute("items", items);
        session.setAttribute("wordSearched", wordSearched);
        session.setAttribute("categorySearched", categorySearched);
        session.setAttribute("mapListAddPermissionByItem", mapListAddPermissionByItem);
        session.setAttribute("listOfUser", mapListOfUser);
        session.setAttribute("logged", isLogged);
        request.getRequestDispatcher("/ROOT/item/SearchPage.jsp").forward(request, response);
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
