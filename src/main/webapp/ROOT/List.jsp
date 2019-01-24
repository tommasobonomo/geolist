<%-- 
    Document   : List
    Created on : 14-Nov-2018, 18:50:00
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
        <h1>${name}</h1>
        <p>${description}</p>
        
        <div class="chatoflist">
            <a href="<c:url value="/chat">
                <c:param name="listID" value="${listID}"/>
                </c:url>">
                <c:out value="chat"/>
            </a>
        </div> 
        
        <div class="list-category">
            <c:forEach var="item" items="${listItems}">
                <div class="name">
                   <a href="<c:url value="/ItemServlet">
                           <c:param name="itemID" value="${item.getId()}"/>
                           <c:param name="listID" value="${listID}"/>
                           <c:param name="action" value="viewItem"/>
                        </c:url>">
                        <c:out value="${item.getName()}" />
                    </a>
                    <form method="POST" action="<c:url value="/List">
                              <c:param name="listID" value="${listID}"/>
                              <c:param name="itemID" value="${item.getId()}"/>
                              <c:param name="action" value="removeItem"/>
                              </c:url>"
                    >
                        <input type="submit" value="Remove"/>
                    </form>
                </div>
            </c:forEach>
        </div>
        
        <div class="list-category">
            <c:forEach var="item" items="${allItems}">
                <div>
                    <a href="<c:url value="/ItemServlet">
                           <c:param name="itemID" value="${item.getId()}"/>
                           <c:param name="action" value="viewItem"/>
                        </c:url>">
                        <c:out value="${item.getName()}"/>
                    </a>
                    <form method="POST" action="<c:url value="/List">
                              <c:param name="listID" value="${listID}"/>
                              <c:param name="itemID" value="${item.getId()}"/>
                              <c:param name="action" value="addItem"/>
                              </c:url>"
                    >
                        <input type="submit" value="Add"/>
                    </form>

                </div>
            </c:forEach>
        </div> 
        
        
        
        <a href="/">Back to Landing</a>
    </body>
</html>
