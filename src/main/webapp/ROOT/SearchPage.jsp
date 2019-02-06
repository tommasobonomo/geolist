<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - profile</title>
        <link rel="icon" href="../images/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="./css/main.css" %></style>
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

                <div class="collapse navbar-collapse" id="collapse-target">
                    <!--pulsante ricerca prodotto-->
                    <form method="POST" action="/form-action/search" class="form-inline my-2 search-form padding-left2" id="navbarSearchForm">
                        <input class="form-control mr-sm-2" type="search" placeholder="Search.." aria-label="Search" name="wordSearched">
                        <select class="form-control mr-sm-2" id="selezione" name="categorySearched">
                            <option value="0" selected>all</option>
                            <c:forEach var="category" items="${listOfCat}">
                                <option value="${category.getIdCatItem()}">${category.getName()}</option>
                            </c:forEach>
                        </select>
                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </div>

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
                
                        <!--vero body della pagina-->
        <div class="container padding-top padding-bottom">
            <div class="row">

                <!--menu laterale-->
                <div class="col-md-3 padding-top menu">
                    <p class="display-4 padding-top menu-title">Categories</p>
                    <hr>
                    <a href="<c:url value="/form-action/search">
                           <c:param name="orderBy" value="alfabetico"></c:param>
                        </c:url>" 
                        class="menu-link"><p class="menu-link">Order by name</p>
                    </a>
                    <a href="<c:url value="/form-action/search">
                           <c:param name="orderBy" value="categoria"></c:param>
                        </c:url>" 
                        class="menu-link"><p class="menu-link">Order by category</p>
                    </a>
                    <hr>
                </div>
                <div class="col-md-1"></div>

                <!--body a destra-->
                <div class="col-md-8 padding-top2 padding-bottom" id="remove-padding-phone">
                    <div class="row text-center">
                        <div class="col-12">
                            <div class="display-4 font-15">you searched for "${wordSearched}"</div>
                        </div>
                    </div>

                    <hr>

                    <div class="container-fluid">
                        <div class="row">

                            <c:forEach var="item" items="${items}">
                            <!--carta-->
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-4">
                                <div class="card">
                                    <img class="card-img-top border-bottom" src="<c:url value="/ItemServlet">
                                           <c:param value="${item.getId()}" name="itemID"/>
                                           <c:param value="retrieveImage" name="action"/>
                                           </c:url>">
                                    <%--<img class="card-img-top" src="<c:url value="/ROOT/logos/durango.png"></c:url>">--%>
                                    <div class="card-body">
                                        <h4 class="card-title">${item.getName()}</h4>
                                        <p class="card-text">${item.getNote()}</p>
                                    </div>
                                    <div class="card-footer form-inline">
                                        <a href="<c:url value="/ItemServlet">
                                           <c:param value="${item.getId()}" name="itemID"/>
                                           <c:param value="viewItem" name="action"/>
                                           </c:url>
                                           " class="btn btn-danger ml-2 mr-sm-2">see</a>
                                    
                                        <select class="form-control mr-sm-2" name="list" id="selectList">
                                            <option disabled selected hidden>add to</option>                            
                                            <option>list 1</option>
                                            <option>list 2</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            </c:forEach>

                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
                
                
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>