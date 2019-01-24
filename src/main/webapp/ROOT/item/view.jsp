<%-- 
    Document   : view
    Created on : 23-gen-2019, 20.54.05
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Immagine caricata con Successo</h1>
        <b> <a href="registeritem.jsp">Upload </a></b> <br/><br/>
        
        <form method="POST" action="showItem.jsp">
    
            <input type="text" name="id"  placeholder="insert an ID"/><br/><br/>
            <input type="submit" value="Submit">
        </form>
        
    </body>
</html>
