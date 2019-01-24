<%-- 
    Document   : index.jsp
    Created on : 24-ott-2018, 19.26.13
    Author     : Lorenzo
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="it.unitn.aa1718.webprogramming.geolists.database.Database"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>

<%
        String ids = request.getParameter("id");
        
        String query = "SELECT * FROM Item AS I WHERE I.id = ?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps =c.prepareStatement(query);
            ps.setString(1,ids);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Blob blob = rs.getBlob("logo");
                byte byteArray[] = blob.getBytes(1, (int) blob.length());
                response.setContentType("image/gif");
                OutputStream os = response.getOutputStream();
                os.write(byteArray);
                os.flush();
                os.close();
            }else{
                System.out.println("no image to be found");
            }
        }catch(Exception e){
             System.out.println(e);
        }
%>