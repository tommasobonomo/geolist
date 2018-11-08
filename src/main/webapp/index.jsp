<%-- 
    Document   : index.jsp
    Created on : 24-ott-2018, 19.26.13
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pagina di Registrazione</h1>
        <form method="POST" action="/form-actions/register">
            first name <input type="text" name="FirstName">
            <br/>
            last name <input type="text" name="LastName">
            <br/>
            username <input type="text" name="UserName">
            <br/>
            email <input type="email" name="Email">
            <br/>
            password <input type="password" name="Password">
            <br/>
            <span style="color:red;">${errMsg}</span>
            <br/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
