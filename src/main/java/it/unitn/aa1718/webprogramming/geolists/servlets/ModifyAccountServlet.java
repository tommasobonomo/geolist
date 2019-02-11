package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import it.unitn.aa1718.webprogramming.geolists.utility.ParametersController;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
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
@WebServlet(urlPatterns = "/ModifyServlet", name="ModifyAccountServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ModifyAccountServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        
        //mi ricavo lo user dal coockie
        UserUtil uUtil = new UserUtil();
        Optional<User> userOptional = uUtil.getUserOptional(request);
         
        String modify =  request.getParameter("modify");
        
        //se non è loggato
        if(!userOptional.isPresent()){
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU ARE NOT LOGGED IN");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
        }else{
            User user = userOptional.get();
            //variabili
            String  newUsername = null, newEmail = null, newName = null,
                   newSurname = null, newPassword = null, oldPassword = null;
            InputStream inputStream = null;
            ParametersController pc = new ParametersController(); 
            
            
            // <editor-fold defaultstate="collapsed" desc="tutte le modifice possibili">
            //modifico ciò che viene richiesto
            //cambio username
            if("username".equals(modify)){ 
                newUsername = (String) request.getParameter("newUsername");
                if(pc.usernameCtrl(newUsername)){
                    user.setUsername(newUsername);
                    UserDAO userDB = new UserDAO();
                    userDB.update(user.getId(), user);
                    request.setAttribute("usernameError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                }
                else{
                    request.setAttribute("usernameError", true);
                }
            }else{
                request.setAttribute("usernameError", false);
            }
            
            //cambio email
            if("email".equals(modify)){
                newEmail = (String) request.getParameter("newEmail");
                if(pc.emailCtrl(newEmail)){
                    user.setEmail(newEmail);
                    UserDAO userDB = new UserDAO();
                    userDB.update(user.getId(), user);
                    request.setAttribute("emailError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                }
                else{
                    request.setAttribute("emailError", true);
                }
            }else{
                request.setAttribute("emailError", false);
            }
            
            //cambio nome
            if("name".equals(modify)){
                newName = (String) request.getParameter("newName");
                if(pc.nameCtrl(newName)){
                    user.setName(newName);
                    UserDAO userDB = new UserDAO();
                    userDB.update(user.getId(), user);
                    request.setAttribute("nameError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                }
                else{
                    request.setAttribute("nameError", true);
                }
            }else{
                request.setAttribute("nameError", false);
            }
            
            //cambio cognome
            if("surname".equals(modify)){
                newSurname = (String) request.getParameter("newSurname");
                if(pc.surnameCtrl(newSurname)){
                    user.setLastname(newSurname);
                    UserDAO userDB = new UserDAO();
                    userDB.update(user.getId(), user);
                    request.setAttribute("surnameError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                }
                else{
                    request.setAttribute("surnameError", true);
                }
            }else{
                request.setAttribute("surnameError", false);
            }
            
            //cambio password controllando prima che la vecchia coincida
            if("password".equals(modify)){
                oldPassword = (String) request.getParameter("oldPassword");
                newPassword = (String) request.getParameter("newPassword");
                if(user.getPassword().equals(HashGenerator.Hash(oldPassword)) && pc.passwordCtrl(newPassword)){
                    user.setPassword(HashGenerator.Hash(newPassword));
                    UserDAO userDB = new UserDAO();
                    userDB.update(user.getId(), user);
                    request.setAttribute("passwordError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                }
                else{
                    request.setAttribute("passwordError", true);
                }
            }else{
                request.setAttribute("passwordError", false);
            }
            
            //cambio avatar
            if("avatar".equals(modify)){
                try{
                    Part filePart = request.getPart("newAvatar");
                    if (filePart != null) {             
                        // obtains input stream of the upload file
                        inputStream = filePart.getInputStream();
                        user.setImage(inputStream);
                        UserDAO userDB = new UserDAO();
                        userDB.update(user.getId(), user);
                        request.setAttribute("avatarError", false); //questi errori mi servono per comunicare che sia andato tutto bene oppure no
                    }
                    else{
                        request.setAttribute("avatarError", true);
                    }
                }catch(IOException | ServletException ex){
                    Logger.getLogger(ItemRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                request.setAttribute("avatarError", false);
            }// </editor-fold>
            
            
            request.setAttribute("user", user);
            request.setAttribute("isAdmin", user.isAdmin());
            request.getRequestDispatcher("/ROOT/profile/ModifyAccount.jsp").forward(request, response);
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
