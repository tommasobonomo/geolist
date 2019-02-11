package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.CatProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatList;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(
        name = "ItemRegister",
        urlPatterns = "/ItemRegistration"
)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB

public class ItemRegister extends HttpServlet {

    Random rand = new Random();
    String note, name;
    String ids;
    int id;
    long idCat;
    InputStream inputStream = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        
        switch (action) {
            case "addItem":
                Pair<Boolean,String> success = addItem(request);
                if (success.getKey()) {
                    response.sendRedirect("/");
                } else {
                    response.setContentType("text/html");
                    request.setAttribute("formError", success.getValue());
                    request.setAttribute("action", "viewForm");
                    request.getRequestDispatcher("/ROOT/AddItem.jsp").forward(request, response);
                }
                break;
            case "viewForm":
            default:
                viewForm(request);
                request.getRequestDispatcher("/ROOT/AddItem.jsp").forward(request, response);
        }
    }

    /**
     * Try to add an Item to the DB, checking for errors in parameters
     * @param request
     * @return a pair object containing a boolean to check if there was an error
     * and a string to describe it if there was one
     */
    private Pair<Boolean,String> addItem(HttpServletRequest request) {

        boolean success_bool = false;
        String success_string = "";

        //prendo valori variabili dalla richiesta
        this.name = request.getParameter("Name");
        this.note = request.getParameter("Note");
        this.idCat = Long.parseLong(request.getParameter("category"));

        if (this.name.equals("") || this.note.equals("")) {
            success_bool = false;
            success_string = this.name.equals("") ? "Name is missing" : "Note is missing";
        } else {
            success_bool = true;
            try {
                Part filePart = request.getPart("File");
                if (filePart != null) {
                    // prints out some information for debugging
                    // System.out.println(filePart.getName());
                    // System.out.println(filePart.getSize());
                    // System.out.println(filePart.getContentType());

                    // obtains input stream of the upload file
                    this.inputStream = filePart.getInputStream();
                }
            } catch (IOException | ServletException ex) {
                Logger.getLogger(ItemRegister.class.getName()).log(Level.SEVERE, null, ex);
            }

            //logo="c:\\docs\\DB_photos\\"+name+(".png");
            Item u = new Item(this.idCat, this.name, this.inputStream, this.note);

            ItemDAO ID = new ItemDAO();
            ID.create(u);
        }
        
        return new Pair<>(success_bool,success_string);
    }

    /**
     * Add all categories to the session
     * @param request 
     */
    private void viewForm(HttpServletRequest request) {
        CatProductListDAO clDAO = new CatProductListDAO();
        List<CatList> categories = clDAO.getAll();
        request.getSession().setAttribute("categories", categories);
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
