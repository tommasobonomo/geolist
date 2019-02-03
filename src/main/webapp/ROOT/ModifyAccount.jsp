<%-- 
    Document   : SearchPage
    Created on : 02-Feb-2019, 18:56:12
    Author     : mattia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style><%@include file="./css/LandingPage.css" %></style>
        <title>Modify account Page</title>
    </head>
    <body>        
        
        <h1>l'utente loggato è ${user.getUsername()}</h1>
        
        modifica username : 
        <form action="/ModifyServlet">
            <input type="text" name="newUsername">
            <button type="submit" name="modify" value="username">invia</button>
            <c:if test="${usernameError}"><small style="color:red">errore nel caricamento</small></c:if>
        </form>
        <br/>
        
        modifica email : 
        <form action="/ModifyServlet">
            <input type="text" name="newEmail">
            <button type="submit" name="modify" value="email">invia</button>
            <c:if test="${emailError}"><small style="color:red">errore nel caricamento</small></c:if>
        </form>
        <br/>
        
        modifica nome : 
        <form action="/ModifyServlet">
            <input type="text" name="newName">
            <button type="submit" name="modify" value="name">invia</button>
            <c:if test="${nameError}"><small style="color:red">errore nel caricamento</small></c:if>
        </form>
        <br/>

        modifica cognome : 
        <form action="/ModifyServlet">
            <input type="text" name="newSurname">
            <button type="submit" name="modify" value="surname">invia</button>
            <c:if test="${surnameError}"><small style="color:red">errore nel caricamento</small></c:if>
        </form>
        <br/>
        
        modifica password : 
        <form action="/ModifyServlet">
            <input type="text" name="oldPassword">
            <input type="text" name="newPassword">
            <button type="submit" name="modify" value="password">invia</button>
            <c:if test="${passwordError}"><small style="color:red">errore nel caricamento</small></c:if>
        </form>
        <br/>
        
        <a href="/">Back to Landing</a>
    </body>
    
    
=======
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
    </body>
>>>>>>> pusho per molon
</html>
