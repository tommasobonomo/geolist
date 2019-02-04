<%-- 
    Document   : requestNewPassword
    Created on : 04-Feb-2019, 12:05:28
    Author     : Giorgio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Write your username, we send you an email with new password
        
        <c:if test="${error}">
            <div class="chatoflist">
                <font color="red">username non esistente<font>
            </div> 
        </c:if>
        
        <div>
            <br>
            <form action="/form-action/new-password" method="POST">
                <input type="text" placeholder="username" name="userName">
                <button type="submit"> submit </button>
            </form>
        </div>
    </body>
</html>
