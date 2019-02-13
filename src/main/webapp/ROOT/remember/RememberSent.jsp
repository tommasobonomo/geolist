<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - register</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="../css/main.css" %></style>
    </head>

    <body>
        
        <div class="container">
            <!--titolo-->
            <div class="row text-center padding-bottom padding-top">
                <div class="col-12">
                    <img src="<c:url value="/ROOT/logos/logo-orizzontale.png"></c:url>" alt="logo orizzontale" height="60" width="180">
                    <p class="lead">Registration</p>
                    <hr class="my-4 padding-bottom">
                </div>

                <div class="col-12">
                    <p class="display-4 font-20">New password sent correctly</p>
                    <p><a href="/"><button type="button" class="btn btn-outline-Danger btn-lg">Go Home</button></a></p>
                </div>   

            </div>
        </div>          
                    
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>