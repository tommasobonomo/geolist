/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.CatItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.CatProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatList;
import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import it.unitn.aa1718.webprogramming.geolists.utility.CookieManager;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author tommaso
 */
@WebServlet(
        name = "Landing Servlet",
        urlPatterns = "/"
)
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

        //setto di default valore non ha gia una lista, in modo che se è loggato non ci sono problemi
        request.setAttribute("hasAlreadyList", false);
        //Richiedo i cookie in ingresso e controllo che faccia parte di un utente loggato
        //oppure di uno anonimo

        // Valori da ritornare alla JSP
        List<ProductList> listOfPL;
        Map<Long, List<Item>> itemsOfList;
        String username;
        boolean isAnon = true;
        long id = 0;

        // User e UserAnonimous, a seconda di quale user abbiamo        
        Optional<User> userOpt = Optional.empty();
        Optional<UserAnonimous> userAnonOpt = Optional.empty();
        boolean alreadyLogged = false;

        // Gestione cookie
        if ("/".equals(request.getRequestURI())) {
            CookieManager cm = new CookieManager(request.getCookies());
            userOpt = cm.checkExistenceUser();
            userAnonOpt = cm.checkExistenceAnonimous();
            if (!userOpt.isPresent() || (userOpt.isPresent() && !userOpt.get().isActive())) { // se non è un utente registrato controllo che sia anonimo
                request.getSession().setAttribute("logged", false);
                userAnonOpt = cm.checkExistenceAnonimous();
                if (!userAnonOpt.isPresent()) {     // se non è nemmeno anonimo ne creo uno anonimo
                    userAnonOpt = cm.createAnonimous(response);
                    alreadyLogged = false; // se non è un utente, allora è il primo login
                    System.out.println("CREO UTENTE ANONIMO PER IL PRIMO LOGIN");
                } else {
                    alreadyLogged = true;
                }
                id = userAnonOpt.get().getId();
            } else {
                request.getSession().setAttribute("logged", true);
                id = userOpt.get().getId();
            }
        }

        // Setto attributi userOpt e userAnonOpt in sessione.
        HttpSession session = request.getSession();
        session.setAttribute("userOpt", userOpt);
        session.setAttribute("userAnonOpt", userAnonOpt);

        //mappa per i permessi
        Map<Long, Boolean> hasPermissionInThisList = new HashMap<>();
        
        //mappa username //TODO: da aggiornare pesa troppo
        Map<Long, List<String> > mapWhoHaveAccess = new HashMap<>();

        // Se cookie indica un normale utente
        if (userOpt.isPresent() && userOpt.get().isActive()) {
            isAnon = false;
            // DAO necessari
            AccessDAO accessDAO = new AccessDAO();
            ComposeDAO composeDAO = new ComposeDAO();
            ItemDAO itemDAO = new ItemDAO();

            User userLogged = userOpt.get();
            listOfPL = accessDAO.getAllLists(userLogged.getId());
            
            // Per ogni lista, prendi ogni suo Item
            itemsOfList = new HashMap<>();
            for (ProductList list : listOfPL) {
                long listID = list.getId();

                //controllo permessi
                boolean havePermission = accessDAO.havePermission(userLogged.getId(), listID);
                hasPermissionInThisList.put(listID, havePermission);

                //item della lista
                List<Compose> relationList = composeDAO.getItemsID(listID);
                List<Item> items = new ArrayList<>();
                for (Compose rel : relationList) {
                    Optional<Item> itemOpt = itemDAO.get(rel.getIdItem());
                    if (itemOpt.isPresent()) {
                        items.add(itemOpt.get());
                    }

                }
                itemsOfList.put(listID, items);
                List <String> listUsername = new ArrayList<>();
                
                for(User u : accessDAO.getUser(listID)){
                    if(u.getId()!=userLogged.getId())
                        listUsername.add(u.getUsername());
                
                }
                mapWhoHaveAccess.put(listID, listUsername);
            }

            username = userLogged.getUsername();
            //prendo liste di utenti con cui la lista è condivisa
            
            

        } else if (alreadyLogged) { // Utente anonimo
            ProductListDAO plistDAO = new ProductListDAO();
            ComposeDAO composeDAO = new ComposeDAO();
            ItemDAO itemDAO = new ItemDAO();

            UserAnonimous userAnon = userAnonOpt.get();

            // Se è anonimo ha solamente una lista
            Optional<ProductList> onlylistOpt = plistDAO.getListAnon(userAnon.getId());

            itemsOfList = new HashMap<>();
            listOfPL = new ArrayList<>();

            if (onlylistOpt.isPresent()) {
                ProductList onlylist = onlylistOpt.get();

                hasPermissionInThisList.put(onlylist.getId(), true);

                // Prendi tutti gli elementi 
                long listID = onlylist.getId();
                List<Compose> relationList = composeDAO.getItemsID(listID);
                List<Item> items = new ArrayList<>();
                for (Compose rel : relationList) {
                    Optional<Item> itemOpt = itemDAO.get(rel.getIdItem());
                    if (itemOpt.isPresent()) {
                        items.add(itemOpt.get());
                    }
                }
                listOfPL.add(onlylist);

                // Aggiungi all'unica entrata di itemsOfList
                itemsOfList.put(listID, items);
            }
            username = "ANONYMOUS";

            ProductListDAO plDAO = new ProductListDAO();
            UserUtil userInfo = new UserUtil();
            UserAnonimous ua = userInfo.getUserAnonymousOptional(request).get();
            request.setAttribute("hasAlreadyList", plDAO.getListAnon(ua.getId()).isPresent());
        } else {
            listOfPL = new ArrayList<>();
            itemsOfList = new HashMap<>();
            username = "ANONYMOUS";
        }

        //prendo le informazioni degli item
        List<Compose> listCompose = new ComposeDAO().getAll();
        Map<String, Compose> mapCompose = new HashMap<>();
        for (Compose elem : listCompose) {
            mapCompose.put(elem.getIdItem() + "" + elem.getIdList(), elem);
        }

        //prendo le categorie da mettere al posto dei nomi
        Map<Long, String> mapCatOfLists = new HashMap<>();
        List<CatList> listProductList = new CatProductListDAO().getAll();
        for (CatList elem : listProductList) {
            mapCatOfLists.put(elem.getIdCategory(), elem.getName());
        }

        //prendo le categorie da mettere nel form della ricerca
        CatItemDAO categoriesDao = new CatItemDAO();
        List<CatItem> listOfCat = categoriesDao.getAll();

        //get user id from cookie
        UserUtil u = new UserUtil();
        Optional<Cookie> cookie = u.getCookie(request);
        if (cookie.isPresent()) {
            request.setAttribute("userCookie", cookie.get().getValue());

            // Websocket
            String ADDRESS = request.getLocalAddr().equals("0:0:0:0:0:0:0:1") ? "localhost" : request.getLocalAddr();
            request.setAttribute("url", "wss://" + ADDRESS + ":" + String.valueOf(request.getLocalPort()) + "/quantity/");

        }
        
        request.setAttribute("mapWhoHaveAccess", mapWhoHaveAccess);
        

        //Aggiungo i parametri alla richiesta da inoltrare alla JSP
        response.setContentType("text/html;charset=UTF-8");
        session.setAttribute("listOfCat", listOfCat);
        request.setAttribute("hasPermissionInThisList", hasPermissionInThisList);
        request.setAttribute("mapCatOfLists", mapCatOfLists);
        request.setAttribute("mapCompose", mapCompose);
        request.setAttribute("listOfPL", listOfPL);
        request.setAttribute("itemsOfList", itemsOfList);
        request.setAttribute("username", username);
        request.setAttribute("isAnon", isAnon);
        request.setAttribute("id", id);
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


