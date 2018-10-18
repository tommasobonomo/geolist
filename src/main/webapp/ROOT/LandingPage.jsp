<%-- 
    Document   : LandingPage
    Created on : 11-Oct-2018, 17:06:36
    Author     : tommaso
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style><%@include file="./css/LandingPage.css" %></style>
        <title>Landing Page</title>
    </head>

    <body>
        
        <div class="header">Geolist</div>
        
        <div class="list-category">
            <c:forEach var="list" items="${listOfPL}">
                <div class="name">
                    <c:out value="${list.getName()}" />
                </div>
                <div class="items">
                    <c:forEach items="${[0,1,2,3,4,5,6]}">
                        <div class="list"></div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        
    </body>

</html>
