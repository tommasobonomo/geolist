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
                <c:set var="listID" value="${list.getIdList()}" />
                <div class="items">
                    <c:forEach var="item" items="${itemsOfList.get(Long.valueOf(listID))}">
                        <div class="list">
                            <c:out value="${item.getName()}" />
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        
        <c:choose>
            <c:when test="${not empty logged}">
                <c:if test="${logged}">
                    <p>User <c:out value="${username}"/> is logged in</p>
                </c:if>
                <c:if test="${not logged}">
                    <p>No user for those credentials!</p>
                    <div class="login-form">
                    <form method="GET" action="/form-actions/login">
                        username <input type="text" name="username">
                        <br/>
                        password <input type="password" name="password">
                        <br/>
                        <input type="submit" value="Submit">
                    </form>
                </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <div class="login-form">
                    <form method="GET" action="/form-actions/login">
                        username <input type="text" name="username">
                        <br/>
                        password <input type="password" name="password">
                        <br/>
                        <input type="submit" value="Submit">
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
        
    </body>

</html>
