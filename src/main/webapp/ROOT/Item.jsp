<%-- 
    Document   : Item
    Created on : 14-Nov-2018, 19:18:39
    Author     : tommaso
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style><%@include file="./css/LandingPage.css" %></style>
        <title><c:out value="${name}"/></title>
    </head>
    <body>
        <div class="header">Geolist</div>
        <h1><c:out value="${name}"/></h1>
        <p><c:out value="${note}"/></p>
        <p><c:out value="${logo}"/></p>
    </body>
</html>