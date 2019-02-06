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
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        UserUtil u = new UserUtil();
        Optional<Long> userID = u.getUserOptionalID(request);
        
        long listID = Long.parseLong(request.getParameter("listID"));
        String action = request.getParameter("action");
        
        
        if ( userID.isPresent() && !( new AccessDAO() ).canHaveAccess(userID.get(), listID)) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>NO ACCESS</title>");
                out.println("</head>"); 
                out.println("<body>");
                out.println("<h1>YOU DON'T HAVE ACCESS</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {

            switch (action) {
                case "plusQty":
                    plusQty(request, response, listID, userID.get());
                    break;
                case "minusQty":
                    minusQty(request, response, listID, userID.get());
                    break;
                case "addItem":
                    addItem(request, response, listID, userID.get());
                    break;
                case "removeItem":
                    removeItem(request, response, listID, userID.get());
                    break;
                case "view":
                default:
                    viewList(request, response, listID, userID.get());
            }
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, long listID, long userID) {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();

        if (composeDAO.addItemToList(itemID, listID, 1)) {
            request.setAttribute("success", true);
        } else {
            request.setAttribute("success", false);
        }

        viewList(request, response, listID, userID);
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response, long listID, long userID) {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();

        if (composeDAO.removeItemFromList(itemID, listID)) {
            request.setAttribute("success", true);
        } else {
            request.setAttribute("success", false);
        }

        viewList(request, response, listID, userID);
    }

    private void viewList(HttpServletRequest request, HttpServletResponse response, long listID, long userID) {
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

        // Return everything to List.jsp
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("listID", listID);
        request.setAttribute("listItems", listItems);
        request.setAttribute("allItems", allItems);
        request.setAttribute("name", name);
        request.setAttribute("desc", desc);
        request.setAttribute("userCookie", new UserDAO().get(userID).get().getCookie());
        request.setAttribute("url", "ws://localhost:8084/quantity/");
        
        //for retrieve quantity
        request.setAttribute("mapQuantityItem", mapQuantityItem);
        
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

    private void plusQty(HttpServletRequest request, HttpServletResponse response, long listID, long userID) throws IOException {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();
        
        Optional<Compose> composeOptional = composeDAO.getComposeObjectFromItemIdListId(itemID, listID);
        
        if(!composeOptional.isPresent()){
            viewError(request,response,"BAD REQUEST");
        } else {
            Compose composeObj = composeOptional.get();
            composeObj.setQuantity(composeObj.getQuantity()+1);
            composeDAO.updateQuantity(composeObj);
                    
            viewList(request, response, listID, userID);
        }
        
    }

    private void minusQty(HttpServletRequest request, HttpServletResponse response, long listID, long userID) throws IOException {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();
        
        Optional<Compose> composeOptional = composeDAO.getComposeObjectFromItemIdListId(itemID, listID);
        
        if(!composeOptional.isPresent()){
            viewError(request,response,"BAD REQUEST");
        } else {
            Compose composeObj = composeOptional.get();
            if(composeObj.getQuantity()>1){
                composeObj.setQuantity(composeObj.getQuantity()-1);
                composeDAO.updateQuantity(composeObj);
            }
                    
            viewList(request, response, listID, userID);
        }
    }
    
    private Map<Long,Integer> createMapQuantityItem (List<Item> items,long listID) {

        ComposeDAO composeDAO = new ComposeDAO();
        Map<Long,Integer> mapQuantityItem = new HashMap<>();
        
        
        for (Item i : items) {
            mapQuantityItem.put(i.getId(), composeDAO.getQauntityFromItemAndList(i.getId(),listID).get());
        }
        
        return mapQuantityItem;
    }
    
    private void viewError(HttpServletRequest request, HttpServletResponse response,String error) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>ERROR</title>");
                out.println("</head>"); 
                out.println("<body>");
                out.println("<h1>"+error+"</h1>");
                out.println("</body>");
                out.println("</html>");
            }
    }
}
