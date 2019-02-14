<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - Home</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:if test="${logged}">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/clientLanding.js"></script>
        <script> connect('${url}', '${userCookie}')</script>
    </c:if>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
    <style><%@include file="/ROOT/css/main.css" %></style>
    <!-- Geolocation scripts in JS -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/geolocation.js"></script>
</head>

<body>
    <!-- Geolocation -->
    <p id="listcategories" style="display: none"><c:forEach var="list" items="${listOfPL}">${list.getIdCat()},</c:forEach></p>

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
                        <input class="form-control mr-sm-2" id="searchline" type="search" placeholder="Search.." aria-label="Search" name="wordSearched">
                        <select class="form-control mr-sm-2" id="selezione" name="categorySearched">
                            <option value="0" selected>all</option>
                            <c:forEach var="cat" items="${listOfCat}">
                                <option value="${cat.getIdCatItem()}">${cat.getName()}</option>
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
                            <a href="" class="nav-link" data-toggle="modal" data-target="#geoModal">
                                <i class="fas fa-globe-europe" id="globe"></i>
                                Geo
                            </a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="/">
                                <i class="fas fa-home"></i>
                                Home
                            </a>
                        </li>
                        <c:if test="${logged}">
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">
                                            <i class="fas fa-user"></i>
                                            Profile
                                        </a>
                                    </li>
                        </c:if>
                        <c:if test="${not logged}">
                            <li class="nav-item"> <!--bottone user, deve anche aprire la finestra modale-->
                                <a class="nav-link" href="#" data-toggle="modal" data-target="#loginModal">
                                    <i class="fas fa-sign-in-alt"></i>
                                    Login
                                </a>
                            </li>
                        </c:if>
                    </ul>

            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row welcome text-center" id="titoloPrincipale">
            <div class="col-12 padding-top">
                <img src="/ROOT/logos/logo-orizzontale.png" height="60" width="160">
            </div>
            <div class="col-12">
                <h1 class="display-4 font-20 my-2">These are our proposed lists</h1>
            </div>
        </div>
    </div>
    <hr>
    
    <!--liste-->
        <div class="container padding-top">

            <c:forEach var="list" items="${lists}">
                <div class="row padding-bottom" style="width: 100%">

                    <!--titolo-->
                    <div class="col-xs-4 col-sm-6 col-md-7 col-lg-8" id="listaTitle">
                        <div class="display-4 font-20 my-2">
                            ${list.getName()}<br>
                            <small class="font-10 mr-sm-2">${mapCatOfLists.get(list.getIdCat())}</small>
                        </div>
                    </div>

                    <div class="col-xs-8 col-sm-6 col-md-5 col-lg-4" id="listaIcons" >
                        <div data-toggle="tooltip" data-placement="bottom" title="See description" class="d-inline">
                            <button class="btn btn-outline-secondary btn-md my-2"  data-toggle="collapse" data-target="#listWindow${list.getId()}">
                                <i class="fas fa-bars"></i>
                            </button>
                        </div>
                        <a href="<c:url value="/PremadeLists">
                                   <c:param name="listID" value="${list.getId()}"/>
                                   <c:param name="action" value="add"/>
                               </c:url>"><button class="d-inline btn btn-outline-success btn-md my-2" data-toggle="tooltip" data-placement="bottom" title="add">
                                <i class="fas fa-plus"></i></button>
                        </a>
                        <c:if test="${isAdmin}">      
                            <a href="<c:url value="/List">
                                   <c:param name="listID" value="${list.getId()}"/>
                                   <c:param name="action" value="view"/>
                               </c:url>"><button class="d-inline btn btn-outline-info btn-md my-2" data-toggle="tooltip" data-placement="bottom" title="Modify">
                                    <i class="fas fa-pencil-alt"></i></button>
                            </a>
                            <a href="<c:url value="/ListRegistration">
                                   <c:param name="action" value="removeList"/>
                                   <c:param name="listID" value="${list.getId()}"/>
                                   <c:param name="from" value="premade"/>
                               </c:url>"><button class="d-inline btn btn-outline-danger btn-md my-2" data-toggle="tooltip" data-placement="bottom" title="Remove">
                                    <i class="far fa-trash-alt"></i></button>
                            </a>
                        </c:if>

                    </div>

                    <!--collapse-->
                    <div class="collapse width-phone" id="listWindow${list.getId()}" style="width: 100%">
                        <div class="row padding-top">
                            <div class="col-xs-12 col-sm-3">
                                <div class="img-wrap-list border">
                                    <img class="mr-3" src="<c:url value="/List">
                                             <c:param name="action" value="retrieveImage" />
                                             <c:param name="listID" value="${list.getId()}" />
                                         </c:url>" alt="image">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-9">
                                <h5 class="mt-0">Description</h5>
                                <p>${list.getDescription()}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!--lista di carte-->
                <div class="container testimonial-group">
                    <div class="row flex-nowrap flex-row card-deck">

                        <!--elenco di carte-->
                        <c:forEach var="item" items="${itemsOfList.get(list.getId())}">
                            <div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
                                <div class="card m-0">
                                    <img class="card-img-top border" src="<c:url value="/ItemServlet">
                                             <c:param value="${item.getId()}" name="itemID"/>
                                             <c:param value="retrieveImage" name="action"/>
                                         </c:url>">
                                    <div class="card-body">
                                        <h4 class="card-title">${item.getName()}</h4>
                                        <p class="card-text">${item.getNote()}</p>
                                    </div>         
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>        
                <hr class="padding-bottom">
            </c:forEach>
        </div>
    
    <hr>

    <div class="row text-center padding-bottom">
        <div class="col-12">
            <a href="/"><button type="button" class="btn btn-outline-Danger btn-lg">Go Home</button></a>
        </div>
    </div>

    
    
    
    <!--finestra modale geolocation-->
    <div class="modal fade show" id="geoModal">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="center-absolute">
                        <span class="display-4 font-15">Geolocation</span>
                    </div><br><br>
                    <p id="geoerror" class="padding-top" style="display: none">Location not available!</p>
                    <div class="georesults"></div>
                    <div class="row">
                        <div class="col-12 text-center">
                            <button type="button" class="btn btn-outline-secondary btn-md" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>