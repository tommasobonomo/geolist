/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.geolocation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet that handles all location related queries
 * @author tommaso
 */
@WebServlet(name = "Geolocation", urlPatterns = {"/Geolocation"})
public class Geolocation extends HttpServlet {

    private String app_id = "R6mhWpHMAL1WKFiqlNVn";
    private String app_code = "fjkiwApcOJdDm38A_tvw_Q";
    private double MAX_DISTANCE = 1000; // in metri, fino a quanto lontano includere i posti 
    
    private HttpResponse<JsonNode> makeRequest(String location, String shop) {
        HttpResponse<JsonNode> res = null;
        try {
            res = Unirest.get("https://places.cit.api.here.com/places/v1/autosuggest")
                    .queryString("at", location)
                    .queryString("q", shop)
                    .queryString("app_id", app_id)
                    .queryString("app_code", app_code)
                    .asJson();
        } catch (UnirestException ex) {
            Logger.getLogger(Geolocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
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
        
        String latStr = request.getParameter("latitude");
        String lngStr = request.getParameter("longitude");
        
        double latitude = Double.parseDouble(latStr);
        double longitude = Double.parseDouble(lngStr);
       
        String location = latStr.concat(",").concat(lngStr);
        
        HttpResponse<JsonNode> responseJson = makeRequest(location, "supermarket");
        
        JSONArray results = responseJson.getBody().getObject().getJSONArray("results");
        
        List<Place> places = new ArrayList<>();
        for (int i = 1; i < results.length() - 1; i++) {
            JSONObject tmp = results.getJSONObject(i);
            if (tmp.getDouble("distance") < MAX_DISTANCE) {
                places.add(new Place(tmp));   
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
