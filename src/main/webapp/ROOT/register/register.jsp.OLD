<%-- 
    Document   : index.jsp
    Created on : 24-ott-2018, 19.26.13
    Author     : Lorenzo
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pagina di Registrazione</h1>
        <form method="POST" action="<c:url value="/form-actions/register">
                                        <c:param name="action" value="send"></c:param>
                                    </c:url>" enctype="multipart/form-data">
            first name <input type="text" name="FirstName">
            <c:if test="${nameError}"><small style="color:red">Name not correct (can't be empty)</small></c:if>
            <br/>
            last name <input type="text" name="LastName">
            <c:if test="${surnameError}"><small style="color:red">Surname not correct (can't be empty)</small></c:if>
            <br/>
            username <input type="text" name="UserName">
            <c:if test="${usernameError}"><small style="color:red">User already present or not correct (can't have spaces or be empty)</small></c:if>
            <br/>
            email <input type="text" name="Email">
            <c:if test="${emailError}"><small style="color:red">Email already present or not correct</small></c:if>
            <br/>
            password <input type="password" name="Password">
            <c:if test="${passwordError}"><small style="color:red">Password not correct (needs a number, a special character and a numeric one)</small></c:if>
            <br/>
            image <input type="file" name="File"/>
            <br/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
