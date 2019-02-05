<%-- 
    Document   : chat2
    Created on : 04-Feb-2019, 21:56:45
    Author     : Giorgio
--%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script><%@include file="./websocketclient.js" %></script>
    </head>
    
    <input name="text" id="textMessage" type="text" placeholder="write message ..."/>
    <button onclick="writeMessage(document.getElementById('textMessage').value)">Click me</button>


</body>
</html>
