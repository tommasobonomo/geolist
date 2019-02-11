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
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author tommaso
 */
@WebServlet(
        name = "ListServlet",
        urlPatterns = "/List"
)
public class ListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        UserUtil u = new UserUtil();
        Optional<Long> userID = u.getUserOptionalID(request);
        
        long listID = Long.parseLong(request.getParameter("listID"));
        String action = request.getParameter("action");
        
        AccessDAO accessDAO = new AccessDAO();
        
        
        if (userID.isPresent() && !accessDAO.canHaveAccess(userID.get(), listID)) {
            System.out.println("errore non ha accesso");
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU DON'T HAVE ACCESS");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
            
        } else if (userID.isPresent() && !accessDAO.havePermission(userID.get(), listID) ) { // se non ha i permessi di modifica
            System.out.println("errore non ha permesso");
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU CAN'T MODIFY THIS LIST \n ASK TO THE OWNER FOR PERMISSION ");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
        }else {
            switch (action) {
                case "addItem":
                    addItem(request, response, listID);
                    break;
                case "removeItem":
                    removeItem(request, response, listID);
                    break;
                case "view":
                default:
                    viewList(request, response, listID);
            }
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, long listID) {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();

        if (composeDAO.addItemToList(itemID, listID, 1,false)) {
            request.setAttribute("success", true);
        } else {
            request.setAttribute("success", false);
        }

        viewList(request, response, listID);
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response, long listID) {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();

        if (composeDAO.removeItemFromList(itemID, listID)) {
            request.setAttribute("success", true);
        } else {
            request.setAttribute("success", false);
        }

        viewList(request, response, listID);
    }

    private void viewList(HttpServletRequest request, HttpServletResponse response, long listID) {
        // Get needed DAOs
        ComposeDAO composeDAO = new ComposeDAO();
        ItemDAO itemDAO = new ItemDAO();
        ProductListDAO plDAO = new ProductListDAO();

        // Get all ids of listItems in listID and init listItems array
        List<Compose> itemsIDs = composeDAO.getItemsID(listID);
        List<Item> listItems = new ArrayList<>();

        // For each id, get the whole item
        for (Compose itemCompose : itemsIDs) {
            long itemID = itemCompose.getIdItem();
            Optional<Item> itemOpt = itemDAO.get(itemID);
            if (itemOpt.isPresent()) {
                listItems.add(itemOpt.get());
            }
        }
        
        
        Map<Long,Integer> mapQuantityItem = new HashMap<>();
        mapQuantityItem = createMapQuantityItem(listItems,listID);
        
        Map<Long,Boolean> mapIsTakeItem = new HashMap<>();
        mapIsTakeItem = createMapIsTakeItem(listItems,listID);

        // Get list details if present
        Optional<ProductList> plOpt = plDAO.get(listID);
        String name = "";
        String desc = "";

        if (plOpt.isPresent()) {
            ProductList pl = plOpt.get();
            name = pl.getName();
            desc = pl.getDescription();
        }

        // Get all items for adding purposes
        List<Item> allItems = itemDAO.getAll();
        
        //get user id from cookie
        UserUtil u = new UserUtil();
        Optional<Cookie> cookie = u.getCookie(request);
        
        request.setAttribute("userCookie", cookie.get().getValue());

        // Return everything to List.jsp
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("listID", listID);
        request.setAttribute("listItems", listItems);
        request.setAttribute("allItems", allItems);
        request.setAttribute("name", name);
        request.setAttribute("desc", desc);
        request.setAttribute("url", "ws://localhost:8084/quantity/");
        
        //for retrieve quantity
        request.setAttribute("mapQuantityItem", mapQuantityItem);
        request.setAttribute("mapIsTakeItem", mapIsTakeItem);
        
        try {
            request.getRequestDispatcher("/ROOT/List.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
    
    private Map<Long,Integer> createMapQuantityItem (List<Item> items,long listID) {

        ComposeDAO composeDAO = new ComposeDAO();
        Map<Long,Integer> mapQuantityItem = new HashMap<>();
        
        
        for (Item i : items) {
            mapQuantityItem.put(i.getId(), composeDAO.getQuantityFromItemAndList(i.getId(),listID).get());
        }
        
        return mapQuantityItem;
    }
    
    private Map<Long,Boolean> createMapIsTakeItem (List<Item> items,long listID) {

        ComposeDAO composeDAO = new ComposeDAO();
        Map<Long,Boolean> mapIsTakeItem = new HashMap<>();
        
        
        for (Item i : items) {
            mapIsTakeItem.put(i.getId(), composeDAO.getComposeObjectFromItemIdListId(i.getId(),listID).get().isTake());
        }
        
        return mapIsTakeItem;
    }
    
}
