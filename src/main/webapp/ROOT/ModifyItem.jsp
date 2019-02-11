<%-- 
    Document   : ModifyItem
    Created on : 11-feb-2019, 12.28.40
    Author     : Lorenzo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - item</title>
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


                <!--tutto quello che voglio fare collassare lo metto all'interno di questo div-->
                <div class="collapse navbar-collapse" id="collapse-target">

                    <!--lista degli elementi cliccabili-->
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="/">Home</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        ${itemID}

        <!--vero body della pagina-->
        <div class="container padding-top padding-bottom">
            <div class="row">
                <!--body a destra-->
                <div class="col-md-8 padding-top2 padding-bottom" id="remove-padding-phone">



                    <!--change avatar-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#logoWindow"> 
                        Edit logo <i class="fas fa-arrow-circle-right"></i> 
                    </button>
                    <div>use this option to change your item logo with a new one</div> 
                    <c:if test="${logoError}"><small style="color:red">some errors accured</small></c:if>
                        <div class="collapse" id="logoWindow">
                            <div class="container">
                                <div class="row padding-top">
                                    <form method="POST" action="<c:url value="/ModifyItem">
                                          <c:param value="${itemID}" name="items"/>
                                      </c:url>" enctype="multipart/form-data" class="form-spacer" id="changeLogo" novalidate>
                                    <div class="form-group">
                                        <label for="logo">choose an image for your new logo </label>
                                        <input type="file" id="logo" name="newLogo" accept="image/png, image/jpeg, image/jpg">
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" name="modify" value="logo" class="btn btn-outline-danger btn-large">Submit changes</button>
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
                                    <form method="POST" action="<c:url value="/ModifyItem">
                                          <c:param value="${itemID}" name="items"/>
                                      </c:url>" class="form-spacer" id="changeName">
                                    <div class="form-group">
                                        <label for="newName">Write your new item name</label>
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


                    <!--change description-->
                    <button class="button-collapse display-4 font-15 " data-toggle="collapse" data-target="#descriptionWindow"> 
                        Edit item Description <i class="fas fa-arrow-circle-right"></i>
                    </button>
                    <div>use this option to change your Description with a new one</div>
                    <c:if test="${noteError}"><small style="color:red">some errors accured</small></c:if>
                        <div class="collapse" id="descriptionWindow">
                            <div class="container">
                                <div class="row padding-top">
                                    <form method="POST" action="<c:url value="/ModifyItem">
                                          <c:param value="${itemID}" name="items"/>
                                      </c:url>" class="form-spacer" id="changeDescription">
                                    <div class="form-group">
                                        <label for="newNote">Write your new description</label>
                                        <input type="text" name="newNote" id="newNote" class="form-control">
                                    </div>

                                    <div class="form-group">
                                        <button type="submit" name="modify" value="note" class="btn btn-outline-danger btn-large">Submit changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <hr class="padding-bottom">
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>

