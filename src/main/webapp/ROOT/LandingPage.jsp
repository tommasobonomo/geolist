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
        <h1>${username}</h1>
        
        <img src="<c:url value="/images/banana.png"/>" width="100"/>
        
        <div class="list-category">
            <c:forEach var="list" items="${listOfPL}">
                <div class="name">
                    <a href="<c:url value="/List">
                           <c:param name="listID" value="${list.getId()}"/>
                           <c:param name="action" value="view"/>
                        </c:url>">
                        <c:out value="${list.getName()}" />
                    </a>
                </div>
                <c:set var="listID" value="${list.getId()}" />
                <div class="items">
                    <c:forEach var="item" items="${itemsOfList.get(Long.valueOf(listID))}">
                        <div class="list">
                            <c:out value="${item.getName()}" />
                        </div>
                    </c:forEach>
                </div>
                <a href="<c:url value="/ListRegistration">
                       <c:param name="action" value="removeList"/>
                       <c:param name="listID" value="${listID}"/>
                    </c:url>">
                    Remove
                </a>
            </c:forEach>
        </div>

        <a href="<c:url value="/ListRegistration">
               <c:param name="action" value="formView"/>
           </c:url>">
            Add list
        </a>
        
        <c:if test="${logged}">
            <p>User <c:out value="${username}"/> is logged in</p>
        </c:if>
        <c:if test="${not logged}">
            <div class="login-form">
                <form method="POST" action="/form-actions/login">
                    username <input type="text" name="username">
                    <br/>
                    password <input type="password" name="password">
                    <br/>
                    <input type="checkbox"  name="remember">
                    Remember<br>
                    <input type="submit" value="Submit">
                </form>
            </div>
        </c:if>
        
    </body>

</html>
