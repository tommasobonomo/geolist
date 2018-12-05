<%-- 
    Document   : index.jsp
    Created on : 24-ott-2018, 19.26.13
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
@MultipartConfig(maxFileSize = 16177216)
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pagina di Registrazione di Items'</h1>
        <form method="POST" action="/form-actions/item" enctype="multipart/form-data">
            Name <input type="text" name="Name">
            <br/>
            Logo <input tag="multipart" type="file" name="File">
            <br/>
            Note <input type="text" name="Note">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
