<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - ${name}</title>
        <link rel="icon" href="../images/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="/ROOT/css/main.css" %></style>
    </head>

    <body>

        <!--navbar-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
            <div class="container-fluid">
                
                <!--logo e titolo del sito-->
                <a class="navbar-brand" href="/">
                    <img src="<c:url value="/ROOT/logos/logo-orizzontale.png"/>" height="40" width="120" alt="logo">
                </a>
                
                <!--bottone che serve per la navabar quando collassa, viene visualizzato solamente quando la finestra raggiunge
                la dimensione specificata nel target-->
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#collapse-target" >
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!--tutto quello che voglio fare collassare lo metto all'interno di questo div-->
                <div class="collapse navbar-collapse" id="collapse-target">
                    
                    <!--lista degli elementi cliccabili-->
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/">Lists</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/ViewAccount">Profile</a>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>
              
                
        <div class="container padding-top2">
    
            <div class="row padding-top">
                <aside class="col-sm-5 col-5">
                    <article class="gallery-wrap"> 
                        <div class="img-big-wrap border">
                            <object data="  <c:url value="/friend/profile">
                                        <c:param name="action" value="retrieveImage"/>
                                        <c:param name="friendId" value="${friend.getId()}"/>
                                    </c:url>
                                    " type="image/jpg"> 
                            </object>
                        </div> <!-- slider-product.// -->
                    </article> <!-- gallery-wrap .end// -->
                </aside>
                
                <aside class="col-sm-1 col-1"></aside>
                
                <aside class="col-sm-6 col-6">
                    <h3 class="display-4 font-25 padding-bottom">${friend.getUsername()}</h3>
                    <div class="padding-bottom">
                        <div class="display-4 font-15">Name</div>
                        <div class="display-4 font-10"><p>${friend.getName()}</p></div>
                    </div>
                    <div class="padding-bottom">
                        <div class="display-4 font-15">LastName</div>
                        <div class="display-4 font-10">${friend.getLastname()}</div>
                    </div>  
                    <div class="padding-bottom">
                        <div class="display-4 font-15">Email</div>
                        <div class="display-4 font-10">${friend.getEmail()}</div>
                    </div> <!-- item-property-hor .// -->
                    <hr>
                </aside> <!-- col.// -->
            </div> <!-- row.// -->
        </div>
        
                
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>