<%-- 
    Document   : chat
    Created on : 19-Jan-2019, 18:49:52
    Author     : Giorgio
    Helper     . Tommaso
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style><%@include file="./css/LandingPage.css" %></style>
        <title>Message</title>
    </head>
    
    <body>
        <div class="list-chat">
                <c:forEach var="chat" items="${allChats}">
                    <div>
                        <a href="<c:url value="/messageServlet">
                            <c:param name="chatID" value="${chat.getId()}"/>
                            </c:url>">
                            <c:out value="${chat.getName()}"/>
                        </a>
                    </div>
                </c:forEach>
        </div> 
        
        <div class="add-chat">
                <a href="<c:url value="/createChat"></c:url>">
                    create new chat
                </a>
        </div> 
        
        <a href="/">Back to Landing</a>
    </body>
    
</html>
