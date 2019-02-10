<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - profile</title>
        <link rel="icon" href="../images/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="../css/main.css" %></style>
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
                    <a href="#" class="menu-link-active"><p class="menu-link-active" >Edit profile</p></a> 
                    <a href="/ManageFriends" class="menu-link"><p class="menu-link" >Friends</p></a>
                    <c:if test="${isAdmin}"><a href="admin-profile.html" class="menu-link"><p class="menu-link">Administration area</p></a></c:if>
                    <a href="/signOut" class="menu-link"><p class="menu-link">Log out</p></a> 
                    <hr>
                </div>
                <div class="col-md-1"></div>

                <!--body a destra-->
                <div class="col-md-8 padding-top2 padding-bottom" id="remove-padding-phone">

                    
                    <!--change avatar-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#avatarWindow"> 
                        Edit avatar <i class="fas fa-arrow-circle-right"></i> 
                    </button>
                    <div>use this option to change your avatar with a new one</div> 
                    <c:if test="${avatarError}"><small style="color:red">some errors accured</small></c:if>
                    <div class="collapse" id="avatarWindow">
                        <div class="container">
                            <div class="row padding-top">
                                <form method="POST" action="/ModifyServlet" enctype="multipart/form-data" class="form-spacer" id="changeAvatar" novalidate>
                                    <div class="form-group">
                                        <label for="avatar">choose an image for your new avatar </label>
                                        <input type="file" id="avatar" name="newAvatar" accept="image/png, image/jpeg, image/jpg">
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="avatar" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr class="padding-bottom">

                    
                    <!--change name-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#nameWindow"> 
                        Edit name <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to change your name with a new one</div>
                    <c:if test="${nameError}"><small style="color:red">some errors accured</small></c:if>
                    <div class="collapse" id="nameWindow">
                        <div class="container">
                            <div class="row padding-top">
                                <form method="POST" action="/ModifyServlet" class="form-spacer" id="changeName">

                                    <div class="form-group">
                                        <label for="newName">Write your new name</label>
                                        <input type="text" name="newName" id="newName" class="form-control">
                                    </div>
                                    
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="name" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr class="padding-bottom">
                    
                    
                    <!--change surname-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#surnameWindow"> 
                        Edit surname <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to change your surname with a new one</div>
                    <c:if test="${surnameError}"><small style="color:red">some errors accured</small></c:if>
                    <div class="collapse" id="surnameWindow">
                        <div class="container">
                            <div class="row padding-top">
                                <form method="POST" action="/ModifyServlet" class="form-spacer" id="changeName">

                                    <div class="form-group">
                                        <label for="newSurname">Write your new surname</label>
                                        <input type="text" name="newSurname" id="newSurname" class="form-control">
                                    </div>
                                    
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="surname" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr class="padding-bottom">

                    
                    <!--change username-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#usernameWindow"> 
                        Edit username <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to change your username with a new one</div>
                    <c:if test="${usernameError}"><small style="color:red">some errors accured</small></c:if>
                    <div class="collapse" id="usernameWindow">
                        <div class="container">
                            <div class="row padding-top">
                                <form method="POST" action="/ModifyServlet" class="form-spacer" id="changeName">

                                    <div class="form-group">
                                        <label for="newUsername">Write your new username</label>
                                        <input type="text" name="newUsername" id="newUsername" class="form-control">
                                    </div>
                                    
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="username" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr class="padding-bottom">
                    
                    
                    <!--change email-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#emailWindow"> 
                        Edit email <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to change your email with a new one</div>
                    <c:if test="${emailError}"><small style="color:red">some errors accured</small></c:if>
                    <div class="collapse" id="emailWindow">
                        <div class="container">
                            <div class="row padding-top">
                                <form method="POST" action="/ModifyServlet" class="form-spacer" id="changeName">

                                    <div class="form-group">
                                        <label for="newEmail">Write your new Email</label>
                                        <input type="text" name="newEmail" id="newEmail" class="form-control">
                                    </div>
                                    
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="email" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr class="padding-bottom">
                    
                    
                    <!--change password-->
                    <button class="button-collapse display-4 font-15" data-toggle="collapse" data-target="#passwordWindow"> 
                        Edit password <i class="fas fa-arrow-circle-right"></i> 
                    </button>
                    <div>use this option to change your password with a new one</div>
                    <c:if test="${passwordError}"><small style="color:red">some errors accured</small></c:if>
                    <div class="collapse" id="passwordWindow">
                        <div class="container">
                            <div class="row padding-top">
                                <form method="POST" action="/ModifyServlet" class="form-spacer" id="changePassword">
                                    <div class="form-group">
                                        <label for="oldPassword">Write your old password</label>
                                        <input type="password" name="oldPassword" id="oldPassword" class="form-control">
                                        <div class="invalid-feedback">password required, also remember the @</div>
                                    </div>
                                    <div class="form-group">
                                        <label for="newPassword">Write your new password</label>
                                        <input type="password" name="newPassword" id="newPassword" class="form-control">
                                        <div class="invalid-feedback">password required, also remember the @</div>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="password" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr>
                    
                    
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>