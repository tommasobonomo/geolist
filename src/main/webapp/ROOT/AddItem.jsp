<%-- 
    Document   : AddItem
    Created on : 24-Jan-2019, 18:51:02
    Author     : tommaso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Item</title>
    </head>
    <body>
        <h1>Pagina di Registrazione di Items</h1>
        <form method="POST" action="<c:url value="/ItemRegistration">
                  <c:param name="action" value="addItem"/>
              </c:url>" enctype="multipart/form-data">
            <p> Name <input type="text" name="Name"/> </p>
            <p> Logo <input type="file" name="File"/> </p>
            <p> Note <input type="text" name="Note"/> </p>
            <p>
                <select name="category">
                    <c:forEach items="${categories}" var="cat">
                        <option value="${cat.getIdCategory()}">${cat.getName()}</option>
                    </c:forEach>
                </select>
            </p>
            <p> <input type="submit" value="Submit"> </p>
        </form>
        <p>${formError}</p>
    </body>
</html>
