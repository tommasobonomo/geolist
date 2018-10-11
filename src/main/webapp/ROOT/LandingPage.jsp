<%-- 
    Document   : LandingPage
    Created on : 11-Oct-2018, 17:06:36
    Author     : tommaso
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Landing Page</title>
    </head>

    <body>
        <form action="login" method="GET">
            username <input type="text" name="username">
            <br>
            password <input type="password" name="password">
            <br>
            <input type="submit" value="Submit">
        </form>
    </body>

</html>
