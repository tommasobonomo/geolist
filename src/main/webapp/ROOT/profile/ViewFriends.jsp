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
                            <a class="nav-link" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a href="" class="nav-link" data-toggle="modal" data-target="#geoModal">Geo</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="<c:url value="/ViewAccount"><c:param name="action" value="viewAccount"></c:param></c:url>">Profile</a>
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
                    <a class="menu-link-active"><p class="menu-link-active" >Friends</p></a> 
                    <c:if test="${isAdmin}"><a href="admin-profile.html" class="menu-link"><p class="menu-link">Administration area</p></a></c:if>
                    <a href="/signOut" class="menu-link"><p class="menu-link">Log out</p></a> 
                    <hr>
                </div>


                <div class="col-md-1"></div>

                <!--body a destra-->
                <div class="col-md-8 padding-top2 padding-bottom" id="remove-padding-phone">

                    <!--change premade lists-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#friendsWindow">
                        Friends <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to manage your friends</div>
                    <c:if test="${friends.size()==0}"><small style="color:red">you currently don't have friends</small></c:if>
                    <div class="collapse" id="friendsWindow">
                        <div class="container">
                            <ul class="list-group list-group-flush">
                                <c:forEach var="friend" items="${friends}">
                                    <li class="list-group-item">${friend.getUsername()}
                                        <a style="display: inline-block;" href="<c:url value="/friend/profile">
                                                    <c:param name="friendId" value="${friend.getId()}"/>
                                                    <c:param name="action" value="viewFriend"/>
                                                 </c:url>">
                                            <button class="btn btn-outline-info btn-md ml-2 my-2 mr-2"><i class="far fa-eye"></i></button>
                                        </a>
                                        <form style="display: inline-block;" class="form-inline" method="POST" action="<c:url value="/ManageFriends">
                                                    <c:param name="friendId" value="${friend.getId()}"/>
                                                    <c:param name="action" value="remove"/>
                                                </c:url>">
                                            <button type="submit" class="btn btn-outline-danger btn-md my-2"><i class="fas fa-user-times"></i></button>
                                        </form>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <hr class="padding-bottom">

                    <!--change premade lists-->
                    <button class="button-collapse display-4 font-15" data-toggle="collapse" data-target="#requestsWindow">
                        Requests <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to manage your friends requests</div>
                    <c:if test="${requestFriends.size()>0}"><small style="color:#5cb85c">you have some friend requests</small></c:if>
                    <c:if test="${requestFriends.size()==0}"><small style="color:red">you currently don't have friend requests</small></c:if>
                    <div class="collapse" id="requestsWindow">
                        <div class="container">
                            <ul class="list-group list-group-flush">
                            <c:forEach var="friend" items="${requestFriends}">
                                <li class="list-group-item">${friend.getUsername()}
                                    <form class="form-inline" style="display: inline-block;" method="POST" action="<c:url value="/ManageFriends">
                                                <c:param name="friendId" value="${friend.getId()}"/>
                                                <c:param name="action" value="accept"/>
                                            </c:url>">
                                        <button type="submit" class="btn btn-outline-success btn-md ml-2 my-2"><i class="fas fa-check"></i></button>
                                    </form>
                                    <form class="form-inline" style="display: inline-block;" method="POST" action="<c:url value="/ManageFriends">
                                                <c:param name="friendId" value="${friend.getId()}"/>
                                                <c:param name="action" value="refuse"/>
                                            </c:url>">
                                        <button type="submit" class="btn btn-outline-danger btn-md my-2"><i class="fas fa-times"></i></button>
                                    </form>
                                </li>
                            </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <hr class="padding-bottom">
                    
                    <button class="btn btn-outline-success btn-md my-2" data-toggle="modal" data-target="#inviteModal">
                        Invite <i class="fas fa-plus"></i>
                    </button>
                    <c:if test="${error}"><small style="color:red">no user found with that username</small></c:if>
                </div>
            </div>
        </div>
                    
        <!--finestra modale -->
        <div class="modal fade show" id="inviteModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">

                        <div class="text-center">
                            <span class="display-4 font-15">Insert username of a friends, you will send him a friend
                                request</span>
                        </div>

                        <form method="POST" action="<c:url value="/ManageFriends"><c:param name="action" value="search&add"/></c:url>" 
                                                    class="form-spacer needs-validation padding-top" novalidate>

                            <!--input "base"-->
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" placeholder="Username" name="username" id="username" class="form-control" required autofocus>
                                <div class="invalid-feedback">Username required</div>
                            </div>

                            <!--bottoni-->
                            <div class="form-group padding-top">
                                <div class="row">
                                    <div class="col-6 text-center">
                                        <button type="button" class="btn btn-outline-secondary btn-md" data-dismiss="modal">Cancel</button>
                                    </div>
                                    <div class="col-6 text-center">
                                        <!--div da cambiare in button-->
                                        <button type="submit" class="btn btn-outline-danger btn-md">Invite</button>
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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>