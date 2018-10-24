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
        <form action="ServletRegister" method="POST">
            <label>First Name :: </label>
            <input name="FirstName" id="n1" type="text">
            <br>         
            <label>Last Name :: </label>
            <input name="LastName" id="n2" type="text">
            <br>            
            <label>UserName :: </label>
            <input name="UserName" id="n3" type="text">
            <br>            
            <label>Email :: </label>
            <input name="Email" id="n4" type="text">
            <br>            
            <label>Password :: </label>
            <input name="Password" id="n5" type="password">
            <br>            
            <input name="Confirm" id="Submit" type="submit">
            <br> 
        </form>
    </body>
</html>
