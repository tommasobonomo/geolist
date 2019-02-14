<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - search</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="/ROOT/css/main.css" %></style>
        <style><%@include file="/ROOT/css/chat.css" %></style>
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
                        <li class="nav-item">
                            <a class="nav-link" href="/">
                                <i class="fas fa-home"></i>
                                Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">
                                        <i class="fas fa-user"></i>
                                        Profile
                                    </a>
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
                            <p class="display-4 padding-top menu-title">Order by</p>
                            <hr>
                            <a href="<c:url value="/form-action/search">
                           <c:param name="orderBy" value="alfabetico"></c:param>
                           <c:param name="page" value="1"></c:param>
                       </c:url>" 
                       class="menu-link"><p class="menu-link">Order by name</p>
                    </a>
                    <a href="<c:url value="/form-action/search">
                           <c:param name="orderBy" value="categoria"></c:param>
                           <c:param name="page" value="1"></c:param>
                       </c:url>" 
                       class="menu-link"><p class="menu-link">Order by category</p>
                    </a>
                    <hr>

                    <c:if test="${pageTot!=0}">
                        <nav aria-label="Page navigation example">
                            <ul class="pagination justify-content-center">

                                <c:if test="${page != 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="/form-action/search?wordSearched=${wordSearched}&categorySearched=${categorySearched}&page=1&orderBy=${orderBy}" tabindex="-1">&laquo;</a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="/form-action/search?wordSearched=${wordSearched}&categorySearched=${categorySearched}&page=${Integer.parseInt(page)-1}&orderBy=${orderBy}">
                                            ${Integer.parseInt(page)-1}</a>
                                    </li>
                                </c:if>
                                <li class="page-item active"><span class="page-link">${page}</span></li>
                                <c:if test="${page != pageTot}">
                                    <li class="page-item">
                                        <a class="page-link" href="/form-action/search?wordSearched=${wordSearched}&categorySearched=${categorySearched}&page=${Integer.parseInt(page)+1}&orderBy=${orderBy}">
                                            ${Integer.parseInt(page)+1}</a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="/form-action/search?wordSearched=${wordSearched}&categorySearched=${categorySearched}&page=${pageTot}&orderBy=${orderBy}">&raquo;</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>
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
                            
                            <c:if test="${pageTot!=0}">
                                <c:forEach var="item" items="${items}">
                                    <!--carta-->
                                    <div class="col-xs-6 col-sm-6 col-md-6 col-lg-4">
                                        <div class="card">
                                            <div id="card-trash-solo"><span class="badge badge-pill badge-info my-2">${mapIdCat.get(item.getIdCat())}</span></div>
                                            <img class="card-img-top border-bottom" src="<c:url value="/ItemServlet">
                                                     <c:param value="${item.getId()}" name="itemID"/>
                                                     <c:param value="retrieveImage" name="action"/>
                                                 </c:url>">
                                            <%--<img class="card-img-top" src="<c:url value="/ROOT/logos/durango.png"></c:url>">--%>
                                            <div class="card-body">
                                                <h4 class="card-title font-12">${item.getName()}</h4>
                                                <p id="p-card-body-search" class="card-text font-10">${item.getNote()}</p>
                                            </div>
                                            <div class="card-footer">
                                                <a href="<c:url value="/ItemServlet">
                                                       <c:param value="${item.getId()}" name="itemID"/>
                                                       <c:param value="viewItem" name="action"/>
                                                   </c:url>
                                                   " class="btn btn-md btn-danger fluid m-2" >see</a><br>

                                                <div class="btn-group fluid ml-2" role="group">
                                                    <button id="btnGroupDrop1" type="button" class="btn btn-md btn-outline-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        Add to list
                                                    </button>
                                                    <div class="dropdown-menu mr-sm-2" aria-labelledby="btnGroupDrop1">

                                                        <c:forEach var="idlist" items="${mapListAddPermissionByItem.get(item.getId())}">
                                                            <a class="dropdown-item" 
                                                               href="../List?listID=${listOfUser.get(idlist).getId()}&itemID=${item.getId()}&action=addItem">
                                                                ${listOfUser.get(idlist).getName()}</a>
                                                            </c:forEach>


                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${pageTot==0}">   
                                <div class="col-12 text-center">
                                    <div class="display-4 font-15" style="color:grey">No results found</div>
                                </div>
                            </c:if>    
                        </div>


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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>