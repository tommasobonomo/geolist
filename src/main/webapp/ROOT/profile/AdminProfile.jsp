<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - profile</title>
        <link rel="icon" href="/ROOT/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="../css/main.css" %></style>
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
                        <li class="nav-item active">
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
                            <p class="display-4 padding-top menu-title">Profile</p>
                            <hr>
                            <a href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>" class="menu-link"><p class="menu-link">General Info</p></a>
                    <a href="/ModifyServlet" class="menu-link"><p class="menu-link" >Edit profile</p></a> 
                    <a href="/ManageFriends" class="menu-link"><p class="menu-link" >Friends</p></a> 
                    <a class="menu-link-active"><p class="menu-link-active">Administration area</p></a>
                    <a href="/signOut" class="menu-link"><p class="menu-link">Log out</p></a> 
                    <hr>
                </div>


                <div class="col-md-1"></div>

                <!--body a destra-->
                <div class="col-md-8 padding-top2 padding-bottom" id="remove-padding-phone">
             
                    <!--view items-->
                    <button class="button-collapse display-4 font-15" data-toggle="collapse" data-target="#listsWindow">
                        Edit items <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to edit, add or delete items</div>
                    <div class="container">
                        <ul class="list-group list-group-flush border" style="height:250px; overflow-y: scroll">
                            <c:forEach var="item" items="${mapItems}">
                                <li class="list-group-item">${item.value}
                                    <a href="<c:url value="/ItemServlet">
                                       <c:param value="${item.key}" name="itemID"/>
                                       <c:param value="viewItem" name="action"/>
                                   </c:url>"><button class="btn btn-outline-warning btn-md ml-2 my-2"><i class="far fa-eye"></i></button></a>
                                    <a href="<c:url value="/ModifyItem" > 
                                        <c:param name="items" value="${item.key}"/>   
                                    </c:url>"><button class="btn btn-outline-info btn-md ml-2 my-2"><i class="fas fa-pencil-alt"></i></button></a>
                                    <form style="display: inline-block;" class="form-inline" method="POST" action="<c:url value="/AdminServlet">
                                                <c:param name="itemID" value="${item.key}"/>
                                       </c:url>"><button type="submit" name="modify" value="deleteItem" class="btn btn-outline-danger btn-md ml-2 my-2"><i class="far fa-trash-alt"></i></button>
                                    </form>
                                </li>
                            </c:forEach>
                        </ul>
                        <button class="btn btn-outline-success btn-md my-2" data-toggle="modal" data-target="#itemModal">
                            Create a new one <i class="fas fa-plus"></i>
                        </button>
                    </div>
                    <hr class="padding-bottom">
                    
                    <!--view items-->
                    <button class="button-collapse display-4 font-15" data-toggle="collapse" data-target="#usersWindow">
                        Users <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to upgrade users to admin</div>

                    <div class="container">
                        <ul class="list-group list-group-flush border" id="listItems">
                            <c:forEach var="user" items="${mapUsers}">
                                <li class="list-group-item">${user.value}
                                    <a style="display: inline-block;" href="<c:url value="/friend/profile">
                                                <c:param name="friendId" value="${user.key}"/>
                                                <c:param name="action" value="viewFriend"/>
                                             </c:url>">
                                        <button class="btn btn-outline-info btn-md ml-2 my-2"><i class="far fa-eye"></i></button>
                                    </a>
                                    <form style="display: inline-block;" class="form-inline" method="POST" action="<c:url value="/AdminServlet">
                                                <c:param name="userID" value="${user.key}"/>
                                             </c:url>"><button type="submit" name="modify" value="admin" class="btn btn-outline-success btn-md my-2"><i class="fas fa-level-up-alt"></i></button>
                                    </form>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    <hr class="padding-bottom">

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
                                        <button value="Submit" type="submit" class="btn btn-outline-danger btn-md">Create</button>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>

                </div>
            </div>
        </div>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>