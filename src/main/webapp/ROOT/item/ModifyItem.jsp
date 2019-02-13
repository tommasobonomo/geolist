<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - ${item.getName()}</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                            <a href="" class="nav-link" data-toggle="modal" data-target="#geoModal">Geo</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">Profile</a>
                                </li>
                            </ul>

                        </div>
                    </div>
                </nav>

        <div class="container padding-top2 center-phone">

            <!--titolo-->
            <div class="row padding-top">
                <div class="col-12">
                    <div class="display-4 font-25 my-2">
                        <form method="POST" action="<c:url value="/ModifyItem">
                                      <c:param value="${itemID}" name="items"/>
                                  </c:url>" class="form-spacer" id="changeName">
                            <input class="width-phone" name="newName" id="newName" type="text" value="${item.getName()}" required/><br>
                                <small class="font-15 mr-sm-2">${mapIdCat.get(item.getIdCat())}</small><br>
                                <button type="submit" name="modify" value="name" class="btn btn-outline-danger btn-large my-2">Submit name</button>
                        </form>
                    </div>
                </div>
            </div>
            <hr>


            <!--aggiungi descrizione-->
            <div class="row center-phone">
                <div class="col-12 text-center">
                    <div class="display-4 font-15 my-2">
                        modify the description: 
                    </div><br>
                </div>
                <div class="col-12 text-center">
                    <form method="POST" action="<c:url value="/ModifyItem">
                              <c:param value="${item.getId()}" name="items"/>
                          </c:url>">
                        <textarea name="newNote" id="description-box" style="height:150px" required>${item.getNote()}</textarea>
                        <div class="form-group my-2">
                            <button type="submit" name="modify" value="note" class="btn btn-outline-danger btn-large">Submit description</button>
                        </div>
                    </form>
                </div>
            </div>
            <hr>
            
            
            <!--modifica immagine-->
            <form method="POST" action="<c:url value="/ModifyItem">
                      <c:param value="${item.getId()}" name="items"/>
                  </c:url>" enctype="multipart/form-data" id="changeLogo">
                <div class="row center-phone">
                    <div class="col-xs-12 col-md-12 text-center">
                        <div class="display-4 font-15 my-2">
                            modify the logo: 
                        </div><br>
                    </div>
                    <div class="col-xs-12 col-md-6 m-auto" >
                        <div class="dislpay-4 font-10 my-2">
                            current logo: 
                            <div class="box-image border">
                                <img src="<c:url value="/ItemServlet">
                                         <c:param value="${item.getId()}" name="itemID"/>
                                         <c:param value="retrieveImage" name="action"/>
                                     </c:url>" alt="logo image" id="logoEdit">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6  m-auto">
                        <div class="dislpay-4 font-10 my-2">
                            <div class="form-group">
                                <label for="logo">Change with:</label>
                                <input type="file" id="logo" name="newLogo" accept="image/png, image/jpeg, image/jpg" required>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-12">
                        <div class="form-group text-center">
                            <button type="submit" name="modify" value="logo" class="btn btn-outline-danger btn-large">Submit logo</button>
                        </div>
                    </div>

                </div>
            </form>
            <hr>

            
            <!--pulsante di go back-->
            <div class="row padding-bottom">
                <div class="col-12 text-center">
                    <a href="<c:url value="/ItemServlet">
                           <c:param value="${item.getId()}" name="itemID"/>
                           <c:param value="viewItem" name="action"/>
                       </c:url>" class="btn btn-md btn-outline-danger m-2" ><i class="fas fa-chevron-left"></i> Back to item</a>
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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>