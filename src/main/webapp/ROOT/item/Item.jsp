<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - ${name}</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
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
                        <button name="page" value="1" class="btn btn-outline-danger my-2 my-sm-0" type="submit">Search</button>
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
                            <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">Profile</a>
                                </li>
                            </ul>

                        </div>
                    </div>
                </nav>


                <div class="container padding-top2">

                    <div class="row padding-top">
                        <aside class="col-sm-12 col-md-5">
                            <article class="gallery-wrap"> 
                                <div class="img-big-wrap border">
                                    <object data="  <c:url value="/ItemServlet">
                                        <c:param name="action" value="retrieveImage"/>
                                        <c:param name="itemID" value="${itemID}"/>
                                    </c:url>
                                    " type="image/jpg"> 
                            </object>
                        </div> <!-- slider-product.// -->
                    </article> <!-- gallery-wrap .end// -->
                </aside>

                <aside class=" col-md-1"></aside>

                <aside class=" col-md-6">
                    <h3 class="display-4 font-25 padding-bottom">${name}</h3>
                    <div class="padding-bottom">
                        <div class="display-4 font-15">Description</div>
                        <div class="display-4 font-10"><p>${note}</p></div>
                    </div>
                    <div class="padding-bottom">
                        <div class="display-4 font-15">Categoria</div>
                        <div class="display-4 font-10">${category}</div>
                    </div>  <!-- item-property-hor .// -->
                    <hr>


                    <c:if test="${isAdmin}">
                        <a class="menu-link" href="<c:url value="/ModifyItem" > 
                               <c:param name="items" value="${itemID}"/>   
                           </c:url>">
                            <button class="btn btn-outline-info btn-md my-2"><i class="fas fa-pencil-alt"></i>Modify</button></a>
                        </c:if>


                    <c:if test="${listID != null}">
                        <a href="<c:url value="/List"> 
                               <c:param name="listID" value="${listID}"/>
                               <c:param name="action" value="view"/>
                           </c:url>">
                            <button class="btn btn-outline-danger btn-md my-2"><i class="fas fa-chevron-left"></i> Back to List</button></a>
                        </c:if>




                </aside> <!-- col.// -->
            </div> <!-- row.// -->
        </div>


        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>