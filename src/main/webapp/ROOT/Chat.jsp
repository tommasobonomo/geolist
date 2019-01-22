<%-- 
    Document   : list
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
        <%--
        <div class="chat-list">
                <c:forEach var="list" items="${allLists}">
                    <div>
                        <a href="<c:url value="/chat">
                            <c:param name="listID" value="${list.getId()}"/>
                            </c:url>">
                            <c:out value="${list.getName()}"/>
                        </a>
                    </div>
                </c:forEach>
        </div> 
        --%>
        
        <div class="show-single-messages-of-list">
                <c:if test="${not empty listID}">
                    <c:forEach var="message" items="${messages}">
                        <div>
                            <c:out value="${mapMessageUser.get(message).getName()} :"/>
                            <c:out value="${message.getText()}"/>
                            <c:out value="${message.getSendTime()}"/>
                        </div>
                    </c:forEach>
                </c:if>
        </div> 
        
        
        <div>
            <a href="/List?listID=${listID}&action=view">Back to list</a>
        </div>
    </body>
    
</html>
