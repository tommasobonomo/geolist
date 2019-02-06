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
        
        <!-- 
            <object> is the same as <img> but it doesn't display a broken icon
            if there is no image.
            Can add a default image in object if needed, with a simple <img> tag
        -->
        <p>
            <object data="
                <c:url value="/ItemServlet">
                    <c:param name="action" value="retrieveImage"/>
                    <c:param name="itemID" value="${itemID}"/>
                </c:url>
            " type="image/jpg"> 
            </object>
        </p>
        
        <c:if test="${listID != null}">
        <a href="<c:url value="/List">
                           <c:param name="listID" value="${listID}"/>
                           <c:param name="action" value="view"/>
                        </c:url>">Back to List</a>
        </c:if>
        <a href="/">Back to Landing</a>
        
        
    </body>
</html>
