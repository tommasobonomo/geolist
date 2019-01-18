package it.unitn.aa1718.webprogramming.geolists.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.io.DataInputStream;
import java.io.File;
import java.util.Random;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;


 
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



@WebServlet(
        name = "ItemRegister",
        urlPatterns = "/form-actions/item"
)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB

public class ItemRegister extends HttpServlet {
     
    Random rand = new Random();
    String note, name, logo,foto;
    int  id = rand.nextInt(5000000) + 1;
    int  idCat = 2;//rand.nextInt(5000000) + 1;
    InputStream inputStream = null;
     
    
    
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        //prendo valori variabili dalla richiesta
        this.name = request.getParameter("Name");
        this.note = request.getParameter("Note");
        
        Part filePart = request.getPart("File");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            this.inputStream = filePart.getInputStream();
        }
         
        //logo="c:\\docs\\DB_photos\\"+name+(".png");
            Item u = new Item(this.id, this.idCat, this.name, this.inputStream, this.note); 
           
            ItemDAO ID = new ItemDAO();
            ID.create(u);
            
            
            //mando l'utente nella pagina di corretto invio della mail
            request.getRequestDispatcher("/ROOT/email/verify.jsp").forward(request, response);
        }
       
    
    }