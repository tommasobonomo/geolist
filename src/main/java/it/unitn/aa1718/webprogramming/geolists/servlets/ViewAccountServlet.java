package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.UserUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mattia
 */
@WebServlet(urlPatterns = "/ViewAccount", name="ViewAccountServlet")
public class ViewAccountServlet extends HttpServlet {

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
        
        //mi ricavo lo user dal coockie
        UserUtil uUtil = new UserUtil();
        Optional<User> userOptional = uUtil.getUserOptional(request);
        String action = request.getParameter("action");
        
        if (!userOptional.isPresent()){
            
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "YOU DON'T HAVE ACCESS");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
            
        } else if(action==null){
            
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("error", "BAD REQUEST");
            request.getRequestDispatcher("/ROOT/error/Error.jsp").forward(request, response);
            
        } else {
            User user = userOptional.get();

            
            switch (action) {
                case "retrieveImage":
                    retrieveImage(request, response, user.getId());
                    break;
                case "viewAccount":
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/ROOT/profile/ViewAccount.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        }
        
    }
    
        private void retrieveImage(HttpServletRequest request, HttpServletResponse response, long id) throws IOException {

        UserDAO userDAO = new UserDAO();
        Optional<byte[]> byteArrayOpt = userDAO.getBlobImage(id);
        
        if (byteArrayOpt.isPresent()) {
            response.setContentType("image/gif");
            OutputStream os = response.getOutputStream();
            os.write(byteArrayOpt.get());
            os.flush();
            os.close();
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
    };

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
    
// </editor-fold>

}
