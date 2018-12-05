package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.utility.EmailSender;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.io.DataInputStream;
import java.io.File;

import java.sql.Timestamp;
import java.util.Random;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

@WebServlet(
        name = "ItemRegister",
        urlPatterns = "/form-actions/item"
)
public class ItemRegister extends HttpServlet {
     
    Random rand = new Random();
    String note, name, logo,foto;
    int  id = rand.nextInt(5000000) + 1;
    int  idCat = rand.nextInt(5000000) + 1;
    
      
    
    
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

        
        Part part = request.getPart("file");
                
                
        //logo="c:\\docs\\DB_photos\\"+name+(".png");
        
        String contentType =request.getContentType();
        if((contentType !=null) && (contentType.indexOf("multipart/form-data") >=0));
        DataInputStream in =new DataInputStream(request.getInputStream());
        int formDataLength =request.getContentLength();
        byte dataBytes[] = new byte[formDataLength];
        String file =new String(dataBytes);
        
        int byteRead=0;
        int totalBytesRead =0;
        while(totalBytesRead<formDataLength){
            byteRead =in.read(dataBytes,totalBytesRead,formDataLength);
            totalBytesRead +=byteRead;
        }
        
        logo=file.substring(file.indexOf("filename=\"")+40);
        logo =logo.substring(0, logo.indexOf("\n"));
        logo=logo.substring(logo.lastIndexOf("\\")+1,logo.indexOf("\""));
        
        int LastIndex = contentType.lastIndexOf("=");
        String bordo =contentType.substring(LastIndex +1,contentType.length());
        int pos;
        pos = file.indexOf("filename=\"");
        pos = file.indexOf("\n",pos) +1;
        pos = file.indexOf("\n",pos) +1;
        pos = file.indexOf("\n",pos) +1;
        int bordoLocation = file.indexOf(bordo,pos)-4;
        int startpos =((file.substring(0,pos)).getBytes()).length;
        int endpos =((file.substring(0,bordoLocation)).getBytes()).length;
        File ff =new File("c:/docs/DB_photos/"+logo);
        this.foto=("c:/docs/DB_photos/"+logo);
        
            Item u = new Item(this.id, this.idCat, this.name, this.foto, this.note); 
           
            ItemDAO ID = new ItemDAO();
            ID.create(u);
            
            //mando l'utente nella pagina di corretto invio della mail
            request.getRequestDispatcher("/ROOT/email/verify.jsp").forward(request, response);
        }
        
        
    }

