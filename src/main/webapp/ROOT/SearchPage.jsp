<%-- 
    Document   : SearchPage
    Created on : 02-Feb-2019, 18:56:12
    Author     : mattia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style><%@include file="./css/LandingPage.css" %></style>
        <title>Landing Page</title>
    </head>
    <body>
        
        <h1>You searched for ${wordSearched}</h1>
        <h2>i risultati sono:</h2>
        
        <c:forEach var="item" items="${items}">
            <c:out value="${item.getName()}" /> <br>
        </c:forEach>
    <br>
    
    <a href="/">Back to Landing</a>
    </body>
    
    
</html>
