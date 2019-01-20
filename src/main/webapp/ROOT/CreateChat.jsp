<%-- 
    Document   : CreateChat
    Created on : 14-Nov-2018, 17:50:02
    Author     : Giorgio
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Chat</title>
    </head>
    <body>
        <h1>Create Chat</h1>
        <form method="POST" action="<c:url value="/chatServlet">
                  <c:param name="action" />
              </c:url>">
            
            <label>Name: </label>
            <input name="name" id="name" type="text"/><br/>
            
            <label>Description: </label>
            <input name="description" id="description" type="text"/><br/>
            
            <label>Image: </label>
            <input name="image" id="image" type="text"/><br/>
            
            <select name="member" multiple>
                    <c:forEach items="${allFriends}" var="f">
                        <option value="${f.getId()}"> ${f.getName()} </option>
                    </c:forEach>
            </select>
            
            <input value="Sumbit" type="submit"/>
        </form>
    </body>
</html>
