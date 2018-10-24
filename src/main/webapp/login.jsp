<%-- 
    Document   : login
    Created on : 24-ott-2018, 20.46.27
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>WELCOME TO MY SUPERMARKET</h1>
         <form action="ServletRegister" method="POST">
       
            <label>UserName :: </label>
            <input name="UserName" id="n1" type="text">
            <label>Password :: </label>
            <input name="Password" id="n2" type="password">
            <br>            
            <input name="Confirm" id="Submit" type="submit">
            <br> 
        </form>
    </body>
</html>
