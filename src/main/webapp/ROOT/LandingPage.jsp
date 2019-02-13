<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - Home</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:if test="${logged}">
            <script><%@include file="./javascript/clientLanding.js" %></script>
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
                        <input class="form-control mr-sm-2" type="search" placeholder="Search.." aria-label="Search" name="wordSearched">
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
                        <li class="nav-item active">
                            <a class="nav-link" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a href="" class="nav-link" data-toggle="modal" data-target="#geoModal">Geo</a>
                        </li>
                        <c:if test="${logged}">
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">Profile</a>
                                    </li>
                        </c:if>
                        <c:if test="${not logged}">
                            <li class="nav-item"> <!--bottone user, deve anche aprire la finestra modale-->
                                <a class="nav-link" href="#" data-toggle="modal" data-target="#loginModal">Login</a>
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


                <!--Welcome della page-->                                            
        <c:if test="${logged}">
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
        </c:if>
        <c:if test="${!logged}">
            <div class="container-fluid padding-bottom">
                <div class="row welcome text-center" id="titoloPrincipale">
                    <div class="col-12 padding-top">
                        <img src="/ROOT/logos/logo.png" height="60" width="60">
                        <p class="logo-text-xxl">GEOLIST</p>
                    </div>
                    <div class="col-12">
                        <h1 class="display-4">Organize your shop experience.</h1>
                    </div>
                    <hr class="my-2">
                    <div class="col-12">
                        <p class="lead">Organize better your shopping lists with your friends and family.
                            <br> You will always know what and where buy stuff for your needs.
                            </h1>
                    </div>
                </div>
            </div>
            <hr class="my-4">
        </c:if>

        <!--liste-->
        <div class="container padding-top">

            <c:forEach var="list" items="${listOfPL}">
                <div class="row padding-bottom" style="width: 100%">

                    <!--titolo-->
                    <div class="col-xs-4 col-sm-6 col-md-7 col-lg-8" id="listaTitle">
                        <c:if test="${!logged}">
                            <div class="display-4 font-25 my-2">
                                This is your try list:
                            </div>
                        </c:if>
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
                        <c:if test="${logged}">
                            <a href="<c:url value="/chat">
                                   <c:param name="listID" value="${list.getId()}"/>
                               </c:url>"><button class="d-inline btn btn-outline-warning btn-md my-2" data-toggle="tooltip"
                                      data-placement="bottom" title="Chat">
                                    <i class="far fa-comments"></i></button>
                            </a>
                        </c:if>
                        <c:if test="${hasPermissionInThisList.get(list.getId())}">      
                            <a href="<c:url value="/List">
                                   <c:param name="listID" value="${list.getId()}"/>
                                   <c:param name="action" value="view"/>
                               </c:url>"><button class="d-inline btn btn-outline-info btn-md my-2" data-toggle="tooltip" data-placement="bottom" title="Modify">
                                    <i class="fas fa-pencil-alt"></i></button>
                            </a>
                        </c:if>
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
                                    <img class="mr-3" src="<c:url value="/List">
                                             <c:param name="action" value="retrieveImage" />
                                             <c:param name="listID" value="${list.getId()}" />
                                         </c:url>" alt="image">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-9">
                                <h5 class="mt-0">Description</h5>
                                <p>${list.getDescription()}</p>

                                <c:if test="${not mapWhoHaveAccess.get(list.getId()).isEmpty()}">
                                    <p class="d-inline padding-top mb-0">
                                        You share this list with :</p>
                                    <div class="d-inline font-italic font-weight-bold">
                                        <c:forEach var="username" items="${mapWhoHaveAccess.get(list.getId())}">
                                            ${username},
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>

                <!--lista di carte-->
                <div class="container testimonial-group">
                    <div class="row flex-nowrap flex-row card-deck">

                        <!--elenco di carte-->
                        <c:forEach var="item" items="${itemsOfList.get(list.getId())}">
                            <c:set var="keyItem" value="${item.getId()}${list.getId()}" /><!--chiave per ricavare il compose dalla mappa-->
                            <div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
                                <div class="card m-0">
                                    <a href="<c:url value="/ItemServlet">
                                           <c:param value="${item.getId()}" name="itemID"/>
                                           <c:param value="viewItem" name="action"/>
                                       </c:url>" id="card-trash">
                                        <button class="btn btn-success btn-md my-2 mr-2"><i class="far fa-eye"></i></button>
                                    </a>
                                    <div class="custom-control custom-checkbox" id="card-trash-2">
                                        <input type="checkbox" class="custom-control-input" id="customCheck${item.getId()}${list.getId()}" name="example1"
                                               onClick="writeMessage('k' + ' ' + '${item.getId()}' + ' ' + '${list.getId()}', '${item.getId()}', '${list.getId()}')" <c:if test="${mapCompose.get(keyItem).isTake()}">checked</c:if>>

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
                                        <button type="submit" class="d-inline btn btn-outline-danger btn-sm my-3" value="-" onClick="writeMessage('-' + ' ' + '${item.getId()}' + ' ' + '${list.getId()}', '${item.getId()}', '${list.getId()}')">
                                            <i class="fas fa-minus"></i>
                                        </button>
                                        Quantity: 
                                        <div class="d-inline" id="counter${item.getId()} ${list.getId()}">
                                            ${mapCompose.get(keyItem).getQuantity()}
                                        </div>
                                        <button type="submit" class="d-inline btn btn-outline-success btn-sm my-3" value="+" onClick="writeMessage('+' + ' ' + '${item.getId()}' + ' ' + '${list.getId()}', '${item.getId()}', '${list.getId()}')">
                                            <i class="fas fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>        
                <hr class="padding-bottom">
            </c:forEach>
        </div>


        <!--resto pagina per non loggato-->
        <c:if test="${!logged}">

            <!--3 colonne spiegazione-->
            <div class="container-fluid padding-bottom">
                <div class="row welcome text-center">
                    <div class="col-12 padding-bottom">
                        <h1 class="display-4">Never thought of having an account?</h1>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <i class="fas fa-list-ol"></i>
                        <h3>CREATE INFINITE LISTS</h3>
                        <p class="width-80">use lists for all your different needings instead of only one</p>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <i class="fas fa-user-friends"></i>
                        <h3>SHARE LISTS WITH YOUR FRIENDS</h3>
                        <p class="width-80">manage lists shared with who of interest</p>
                    </div>
                    <div class="col-sm-12 col-md-4">
                        <i class="fas fa-map-marked-alt"></i>
                        <h3>NEVER MISS A SHOP</h3>
                        <p class="width-80">receive notifications when you are near a shop where you can buy stuff on your lists</p>
                    </div>
                </div>
                <hr class="my-4">
            </div>

            <!--titolo liste prestabilite-->
            <div class="container-fluid padding-bottom">
                <div class="row welcome text-center">
                    <div class="col-12">
                        <h1 class="display-4">Use premade lists!</h1>
                    </div>
                </div>
            </div>

            <!--card per le liste prestabilite-->
            <div class="container-fluid padding-bottom">
                <div class="row padding-bottom">
                    <div class="card-deck">
                        <div class="card">
                            <img class="card-img-top" src="images/cards/carne.jpg">
                            <div class="card-body" style="height:160px">
                                <h4 class="card-title">Meat lover</h4>
                                <p class="card-text">Perfect for someone who loves cooking meat</p>
                                <a href="" class="btn btn-outline-danger">see list</a>
                            </div>
                        </div>
                        <div class="card">
                            <img class="card-img-top" src="images/cards/farmacia.jpg">
                            <div class="card-body" style="height:160px">
                                <h4 class="card-title">All for your Health</h4>
                                <p class="card-text">Never remain without medications</p>
                                <a href="" class="btn btn-outline-danger">see list</a>
                            </div>
                        </div>
                        <div class="card">
                            <img class="card-img-top" src="images/cards/vegetariano.jpg">
                            <div class="card-body" style="height:160px">
                                <h4 class="card-title">Veggie time</h4>
                                <p class="card-text">Just remain healty for today</p>
                                <a href="" class="btn btn-outline-danger">see list</a>
                            </div>
                        </div>
                        <div class="card">
                            <div class="mx-auto text-center padding-top" id="addList" style="width:100%">
                                <h4 class="card-title">Try out your own</h4>
                                <p class="card-text">create a simple list without an account and test the experience</p>
                                <button data-toggle="modal" data-target="#listModal" class="btn btn-outline-danger">Create one</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>


        <!--bottone fissato in basso a destra con finestra modale-->
        <button class="button-fixed-list btn btn-danger" data-toggle="modal" data-target="#listModal">
            New list <i class="fas fa-plus"></i>
        </button>
        <c:if test="${logged}"> 
            <button class="button-fixed-item btn btn-info" data-toggle="modal" data-target="#itemModal">
                New item <i class="fas fa-plus"></i>
            </button>
        </c:if>

        <!--finestra modale item-->
        <div class="modal fade show" id="itemModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">

                        <div class="center-absolute">
                            <span class="display-4 font-15">Create a new item</span>
                        </div>

                        <form method="POST" action="<c:url value="/ItemRegistration">
                                  <c:param name="action" value="addItem"/>
                              </c:url>" class="form-spacer needs-validation padding-top" enctype="multipart/form-data">

                            <!--input "base"-->
                            <div class="form-group">
                                <label for="Name">Name</label>
                                <input type="text" name="Name" id="Name" class="form-control" required autofocus>
                            </div>
                            <div class="form-group">
                                <label for="Note">Description</label>
                                <textarea name="Note" id="Note" class="form-control" style="height:150px; overflow-y: scroll;" required></textarea>
                            </div>
                            <div class="form-group">
                                <label for="File">Image</label><br>
                                <input name="File" id="File" type="file" accept="image/png, image/jpeg, image/jpg" required>
                            </div>
                            <div class="form-group">
                                <label for="category">Category</label>
                                <select class="form-control mr-sm-2" id="category" name="category" required>
                                    <c:forEach var="category" items="${listOfCat}">
                                        <option value="${category.getIdCatItem()}">${category.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!--bottoni-->
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-6 text-center">
                                        <button type="button" class="btn btn-outline-secondary btn-md" data-dismiss="modal">Cancel</button>
                                    </div>
                                    <div class="col-6 text-center">
                                        <!--div da cambiare in button-->
                                        <a href="create-lists.html">
                                            <button value="Submit" type="submit" class="btn btn-outline-danger btn-md">Create</button>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>

                </div>
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


        <!--finestra modale lista-->
        <div class="modal fade show" id="listModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">

                        <c:if test="${!hasAlreadyList}"> 
                            <!-- se è l'utente anonimo ha già una lista
                                 se l'utente è loggato questo valore è sempre false -->

                            <div class="center-absolute">
                                <span class="display-4 font-15">Create a new list</span>
                            </div>

                            <form method="POST" action="<c:url value="/ListRegistration">
                                      <c:param name="action" value="addList"/>
                                  </c:url>" class="form-spacer needs-validation padding-top" enctype="multipart/form-data">

                                <!--input "base"-->
                                <div class="form-group">
                                    <label for="name">Name</label>
                                    <input type="text" name="name" id="name" class="form-control" required autofocus>
                                </div>
                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea name="description" id="description" class="form-control" style="height:150px; overflow-y: scroll;" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="image">Image</label><br>
                                    <input name="image" id="image" type="file" accept="image/png, image/jpeg, image/jpg" required>
                                </div>
                                <div class="form-group">
                                    <label for="category">Category</label>
                                    <select class="form-control mr-sm-2" id="category" name="category" required>
                                        <c:forEach var="category" items="${mapCatOfLists}">
                                            <option value="${category.key}">${category.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <!--bottoni-->
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-6 text-center">
                                            <button type="button" class="btn btn-outline-secondary btn-md" data-dismiss="modal">Cancel</button>
                                        </div>
                                        <div class="col-6 text-center">
                                            <!--div da cambiare in button-->
                                            <a href="create-lists.html">
                                                <button value="Submit" type="submit" class="btn btn-outline-danger btn-md">Create</button>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </c:if>
                        <c:if test="${hasAlreadyList}">
                            <div class="text-center">
                                <span class="display-4 font-15">You can't make more than one list as an anonymous user.<br>
                                    Register to create more of them.</span>
                            </div>
                            <hr>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-6 text-center">
                                        <button type="button" class="btn btn-outline-secondary btn-md" data-dismiss="modal">Cancel</button>
                                    </div>
                                    <div class="col-6 text-center">
                                        <!--div da cambiare in button-->
                                        <a href="create-lists.html">
                                            <a href="<c:url value="/form-actions/register"><c:param name="action" value="view"></c:param></c:url>" >
                                                        <button class="btn btn-outline-danger btn-md">Register</button>
                                                    </a>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>

