/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.CatProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import it.unitn.aa1718.webprogramming.geolists.database.IsFriendDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemPermissionDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
            // System.out.println("errore non ha accesso");
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU DON'T HAVE ACCESS");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);

        } else if (userID.isPresent() && !accessDAO.havePermission(userID.get(), listID)) { // se non ha i permessi di modifica
            // System.out.println("errore non ha permesso");
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU CAN'T MODIFY THIS LIST <br> ASK TO THE OWNER FOR PERMISSIONS ");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
        } else {
            switch (action) {
                case "addItem":
                    addItem(request, response, listID);
                    break;
                case "removeItem":
                    removeItem(request, response, listID);
                    break;
                case "retrieveImage":
                    retrieveImage(request, response, listID);
                    break;
                case "view":
                default:
                    viewList(request, response, listID);
            }
        }
    }

    private void retrieveImage(HttpServletRequest request, HttpServletResponse response, long id) throws IOException {
        ProductListDAO listDAO = new ProductListDAO();
        Optional<byte[]> byteArrayOpt = listDAO.getBlobImage(id);

        if (byteArrayOpt.isPresent()) {
            response.setContentType("image/gif");
            OutputStream os = response.getOutputStream();
            os.write(byteArrayOpt.get());
            os.flush();
            os.close();
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, long listID) throws ServletException, IOException {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();

        if (composeDAO.addItemToList(itemID, listID, 1, false)) {
            request.setAttribute("success", true);
        } else {
            request.setAttribute("success", false);
        }

        viewList(request, response, listID);
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response, long listID) throws ServletException, IOException {
        long itemID = Long.parseLong(request.getParameter("itemID"));
        ComposeDAO composeDAO = new ComposeDAO();

        if (composeDAO.removeItemFromList(itemID, listID)) {
            request.setAttribute("success", true);
        } else {
            request.setAttribute("success", false);
        }

        viewList(request, response, listID);
    }

    private void viewList(HttpServletRequest request, HttpServletResponse response, long listID) throws ServletException, IOException {
        // Get needed DAOs
        ComposeDAO composeDAO = new ComposeDAO();
        ItemDAO itemDAO = new ItemDAO();
        ProductListDAO plDAO = new ProductListDAO();
        AccessDAO accessDAO = new AccessDAO();

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

        //map permessi e sharing della lista
        Map<Long, Boolean> mapPermission = new HashMap<>();
        Map<Long, Boolean> mapSharing = new HashMap<>();

        // Get list details if present
        Optional<ProductList> plOpt = plDAO.get(listID);
        List<Item> items = new ArrayList<>();
        List<User> friends = new ArrayList<>();
        String name = "";
        String desc = "";
        String category = "";

        if (plOpt.isPresent()) {
            ProductList list = plOpt.get();
            category = new CatProductListDAO().get(list.getIdCat()).get().getName();

            // Get items related to the category id for adding purposes
            List<CatItem> idCategories = new ItemPermissionDAO().getItemCategories(list.getIdCat());
            for (CatItem elem : idCategories) {
                List<Item> itemOfCategory = new ItemDAO().getAllByIdCat(elem.getIdCatItem());
                items.addAll(itemOfCategory);
            }

            // get friends of user
            UserUtil uUtil = new UserUtil();
            Optional<User> userOptional = uUtil.getUserOptional(request);
            if (userOptional.isPresent()) {
                long userId = userOptional.get().getId();

                friends = new IsFriendDAO().getFriends(userId);

                for (User f : friends) {
                    mapSharing.put(f.getId(), accessDAO.canHaveAccess(f.getId(), listID));
                    mapPermission.put(f.getId(), accessDAO.havePermission(f.getId(), listID));
                }
            }

            // <editor-fold defaultstate="collapsed" desc="modifice della lista">
            //modifica della lista
            String modify = request.getParameter("modify");
            InputStream inputStream = null;
            ProductListDAO listDAO = new ProductListDAO();
            String newNote = "", newName = "";

            if ("note".equals(modify)) {
                newNote = (String) request.getParameter("newNote");
                if (!"".equals(newNote)) {
                    list.setDescription(newNote);
                    listDAO.updateWithoutImage(list.getId(), list);
                }
            }

            //cambio nome
            if ("name".equals(modify)) {
                newName = (String) request.getParameter("newName");
                if (!"".equals(newName)) {
                    list.setName(newName);
                    listDAO.updateWithoutImage(list.getId(), list);
                }
            }

            //cambio logo
            if ("logo".equals(modify)) {
                try {

                    Part filePart = request.getPart("newLogo");
                    if (filePart != null) {
                        // obtains input stream of the upload file
                        inputStream = filePart.getInputStream();
                        list.setImage(inputStream);
                        listDAO.update(list.getId(), list);
                    }
                } catch (IOException | ServletException ex) {
                    Logger.getLogger(ItemRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            } // </editor-fold>

            name = list.getName();
            desc = list.getDescription();

            //get user id from cookie
            UserUtil us = new UserUtil();
            Optional<Cookie> cookie = us.getCookie(request);

            //rimuovo dalla lista di items che posso aggiungere gli items che sono già nella lista
            items.removeAll(listItems);

            request.setAttribute("userCookie", cookie.get().getValue());
            
            // Websocket config
            String ADDRESS = request.getLocalAddr().equals("0:0:0:0:0:0:0:1") ? "localhost" : request.getLocalAddr();
            request.setAttribute("url", "wss://" + ADDRESS + ":" + String.valueOf(request.getLocalPort()) + "/friend/");

            // Return everything to List.jsp
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("listID", listID);
            request.setAttribute("listItems", listItems);
            request.setAttribute("allItems", items);
            request.setAttribute("name", name);
            request.setAttribute("desc", desc);
            request.setAttribute("category", category);
            request.setAttribute("friends", friends);

            //friend sharing and permission map
            request.setAttribute("mapPermission", mapPermission);
            request.setAttribute("mapSharing", mapSharing);

            try {
                request.getRequestDispatcher("/ROOT/lists/List.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
