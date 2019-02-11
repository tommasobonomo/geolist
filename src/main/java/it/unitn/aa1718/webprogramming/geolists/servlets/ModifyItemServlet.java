package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.utility.ParametersController;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author mattia
 */
@WebServlet(urlPatterns = "/ModifyItem", name="ModifyItemServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ModifyItemServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {           
            ItemDAO dao = new ItemDAO();
            String modify =  request.getParameter("modify");  
            
            long iD = Long.parseLong(request.getParameter("items"));   
            System.out.println("\n\n\nNUMERO ID ITEM: " + iD);
            System.out.println("\n\n\n ");
            Optional items = dao.get(iD);
            Item item = (Item) items.get();
            String  newDescription = null, newName = null,
                   newNote = null;
            InputStream inputStream = null;
            ParametersController pc = new ParametersController(); 
            
            
            // <editor-fold defaultstate="collapsed" desc="tutte le modifice possibili">
            //modifico ciò che viene richiesto
            //cambio note
            
            
            if("note".equals(modify)){      
                   
                System.out.println("\n\n\nANCORA NUMERO ID " + iD);
                newNote = (String) request.getParameter("newNote");
                    item.setNote(newNote);
                    ItemDAO itemDB = new ItemDAO();
                    itemDB.update(item.getId(), item);
                    request.setAttribute("noteError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
            }else{
                request.setAttribute("noteError", false);
            }
            
            
            //cambio nome
            if("name".equals(modify)){
                newName = (String) request.getParameter("newName");
                if(pc.isInameNew(newName)){
                    item.setName(newName);
                    ItemDAO itemDB = new ItemDAO();
                    itemDB.update(item.getId(), item);
                    request.setAttribute("nameError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                }
                else{
                    request.setAttribute("nameError", true);
                }
            }else{
                request.setAttribute("nameError", false);
            }
            
            
            //cambio logo
            if("logo".equals(modify)){
                try{

                    Part filePart = request.getPart("newLogo");
                    if (filePart != null) {             
                        // obtains input stream of the upload file
                        inputStream = filePart.getInputStream();
                        item.setLogo(inputStream);
                    ItemDAO itemDB = new ItemDAO();
                    itemDB.update(item.getId(), item);
                        request.setAttribute("logoError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                    }
                    else{
                        request.setAttribute("logoError", true);
                    }
                }catch(IOException | ServletException ex){
                    Logger.getLogger(ItemRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                request.setAttribute("logoError", false);
            }// </editor-fold>
            request.setAttribute("itemID",iD);
           // request.setAttribute("items", item);            
            request.getRequestDispatcher("/ROOT/ModifyItem.jsp").forward(request, response);
        
             
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
            Logger.getLogger(ModifyAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModifyAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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

