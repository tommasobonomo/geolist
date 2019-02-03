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
        
        
        <!--sezione di ricerca-->   
        <div>
            <br>
            <form action="/form-action/search">
                <input type="text" placeholder="Search.." name="wordSearched">
                <select name="categorySearched">
                    <option value="0" selected>all</option>
                    <c:forEach var="category" items="${listOfCat}">
                        <option value="${category.getIdCatItem()}">${category.getName()}</option>
                    </c:forEach>
                </select>
                <button type="submit"> search </button>
            </form>
        </div>
        
        
        <!--quello che ho già cercato-->
        <h1>You searched for ${wordSearched}</h1>
        
        <form action="/form-action/search">
            <button type="submit" name="orderBy" value="alfabetico"> order by name</button>
            <button type="submit" name="orderBy" value="categoria"> order by category </button>
        </form>
        
        <br>
        <h2>i risultati sono:</h2>
        
        <c:forEach var="item" items="${items}">
            <c:out value="${item.getName()}" /> <br>
        </c:forEach>
    <br>
    
    <a href="/">Back to Landing</a>
    </body>
    
    
</html>
