<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - Home</title>
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
                        <li class="nav-item active">
                            <a class="nav-link" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/">Lists</a>
                        </li>
                        <c:if test="${logged}">
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">Profile</a>
                                    </li>
                        </c:if>
                        <c:if test="${not logged}">
                            <li class="nav-item"> <!--bottone user, deve anche aprire la finestra modale-->
                                <a class="nav-link" href="#" data-toggle="modal" data-target="#loginModal">Login / Register</a>
                            </li>
                        </c:if>
                    </ul>

                </div>
            </div>
        </nav>


        <!--finestra modale -->
        <div class="modal fade show" id="loginModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">                
                    <div class="modal-body"><!--creao il form per il login dentro al body della finestra modale-->

                        <div class="center-absolute">
                            <img src="/ROOT/logos/logo.png" height="30" width="30">
                            <span class="logo-text">GEOLIST</span> 
                        </div>

                        <form method="POST" action="/form-actions/login" class="form-spacer needs-validation padding-top2">

                            <!--input "base"-->
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" name="username" id="username" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" name="password" id="password" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <!--checkbox remember me-->
                                <div class="form-check">
                                    <input type="checkbox" name="remember" id="remember" class="form-check-input">
                                    <label for="remember" class="form-check-label"> Remember me</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div><a href="/form-action/new-password"> Forgot the password? </a> </div>  
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-4 text-center">
                                        <button type="button" class="btn btn-outline-secondary btn-lg" data-dismiss="modal">Cancel</button>
                                    </div>
                                    <div class="col-4 text-center">
                                        <a href="<c:url value="/form-actions/register"><c:param name="action" value="view"></c:param></c:url>">
                                                    <button type="button" class="btn btn-outline-danger btn-lg">Register</button>
                                                </a>
                                            </div>
                                            <div class="col-4 text-center">
                                                <button type="submit" value="submit" class="btn btn-outline-danger btn-lg">Login</button>
                                            </div>
                                        </div>
                                    </div>

                                </form>
                            </div>

                        </div>
                    </div>
                </div>

                <!--Welcome back-->
                <div class="container-fluid">
                    <div class="row welcome text-center" id="titoloPrincipale">
                        <div class="col-12 padding-top">
                            <img src="/ROOT/logos/logo-orizzontale.png" height="60" width="160">
                        </div>
                        <div class="col-12">
                            <h1 class="display-4 font-20 my-2">${username}, Welcome back to Geolist</h1>
                </div>
            </div>
        </div>
        <hr>      

        <!--liste-->
        <div class="container padding-top">

            <!--lista 1-->
            <c:forEach var="list" items="${listOfPL}">
                <div class="row padding-bottom" style="width: 100%">

                    <!--titolo-->
                    <div class="col-xs-4 col-sm-6 col-md-7 col-lg-8" id="listaTitle">
                        <div class="display-4 font-20 my-2">
                            ${list.getName()}<br>
                            <small class="font-10 mr-sm-2">categoria</small>
                        </div>
                    </div>

                    <div class="col-xs-8 col-sm-6 col-md-5 col-lg-4" id="listaIcons" >
                        <div data-toggle="tooltip" data-placement="bottom" title="See description" class="d-inline">
                            <button class="btn btn-outline-secondary btn-md my-2"  data-toggle="collapse" data-target="#listWindow${list.getId()}">
                                <i class="fas fa-bars"></i>
                            </button>
                        </div>
                        <c:if test="${logged}">
                            <a href="<c:url value="/chat">
                                   <c:param name="listID" value="${list.getId()}"/>
                               </c:url>"><button class="d-inline btn btn-outline-warning btn-md my-2" data-toggle="tooltip"
                                      data-placement="bottom" title="Chat">
                                    <i class="far fa-comments"></i></button>
                            </a>
                        </c:if>
                        <a href="<c:url value="/List">
                               <c:param name="listID" value="${list.getId()}"/>
                               <c:param name="action" value="view"/>
                           </c:url>"><button class="d-inline btn btn-outline-info btn-md my-2" data-toggle="tooltip" data-placement="bottom" title="Modify">
                                <i class="fas fa-pencil-alt"></i></button>
                        </a>
                        <a href="<c:url value="/ListRegistration">
                               <c:param name="action" value="removeList"/>
                               <c:param name="listID" value="${list.getId()}"/>
                           </c:url>"><button class="d-inline btn btn-outline-danger btn-md my-2" data-toggle="tooltip" data-placement="bottom" title="Remove">
                                <i class="far fa-trash-alt"></i></button>
                        </a>
                    </div>

                    <!--collapse-->
                    <div class="collapse width-phone" id="listWindow${list.getId()}" style="width: 100%">
                        <div class="row padding-top">
                            <div class="col-xs-12 col-sm-3">
                                <div class="img-wrap-list border">
                                    <img class="mr-3" src="../images/cards/vegetariano.jpg" alt="image">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-9">
                                <h5 class="mt-0">Description</h5>
                                <p>${list.getDescription()}</p>
                                <p class="d-inline padding-top mb-0">
                                    You shared this list with :</p>
                                <div class="d-inline font-italic font-weight-bold">
                                    Mario rossi, Giorgio Segalla, Carlo Carlino.
                                </div>
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
                                    <a href="<c:url value="/ItemServlet">
                                           <c:param value="${item.getId()}" name="itemID"/>
                                           <c:param value="viewItem" name="action"/>
                                       </c:url>" id="card-trash">
                                        <button class="btn btn-success btn-md my-2 mr-2"><i class="far fa-eye"></i></button>
                                    </a>
                                    <div class="custom-control custom-checkbox" id="card-trash-2">
                                        <input type="checkbox" class="custom-control-input" id="customCheck${item.getId()}${list.getId()}" name="example1">
                                        <label class="custom-control-label" for="customCheck${item.getId()}${list.getId()}"></label>
                                    </div>
                                    <img class="card-img-top border" src="<c:url value="/ItemServlet">
                                             <c:param value="${item.getId()}" name="itemID"/>
                                             <c:param value="retrieveImage" name="action"/>
                                         </c:url>">
                                    <div class="card-body">
                                        <h4 class="card-title">${item.getName()}</h4>
                                        <p class="card-text">${item.getNote()}</p>
                                    </div>
                                    <div class="card-footer text-center">
                                        Quantity : 5
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>        
                <hr>

            </c:forEach>
        </div>



        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>