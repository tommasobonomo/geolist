x<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/clientFriendsPermission.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js"></script>
        <script type="text/javascript">
            connect('${url}', '${listID}', '${userCookie}');
        </script>
        <!-- Geolocation scripts in JS -->
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/geolocation.js"></script>

        
        <script type="text/javascript">
            listId='${listID}';
            <c:forEach var="item" items="${allItems}">
                items.push('${item.getName()}');
                mapNameId.set('${item.getName()}','${item.getId()}');
            </c:forEach>
        </script>
        
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

        <div class="container padding-top2">

            <!--titolo-->
            <div class="row padding-top center-phone">
                <div class="col-12">
                    <div class="display-4 font-25 my-2">
                        <form method="POST" action="<c:url value="/List">
                                      <c:param value="view" name="action"/>
                                      <c:param name="listID" value="${listID}" />
                                  </c:url>" class="form-spacer" id="changeName">
                            <input class="width-phone" name="newName" id="newName" type="text" value="${name}" required/><br>
                                <small class="font-15 mr-sm-2">${category}</small><br>
                                <button type="submit" name="modify" value="name" class="btn btn-outline-danger btn-large my-2">Submit name</button>
                        </form>
                    </div>
                </div>
            </div>
            <hr class="padding-bottom">

            <!--container per gli elementi che hai-->
            <c:if test="${listItems.size() != 0}">
                <div class="display-4 font-15 my-2 padding-bottom text-center">
                    Elements already in your list:
                </div>
                <div class="container testimonial-group">
                    <div class="row flex-row flex-nowrap card-deck">

                        <c:forEach var="item" items="${listItems}">
                            <!--elenco di carte-->
                            <div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
                                <div class="card m-0">
                                    <form method="POST" action="<c:url value="/List">
                                              <c:param name="listID" value="${listID}"/>
                                              <c:param name="itemID" value="${item.getId()}"/>
                                              <c:param name="action" value="removeItem"/>
                                          </c:url>" id="card-trash-solo">
                                        <button type="submit" value="Remove" class="btn btn-danger btn-md my-2"><i class="far fa-trash-alt"></i></button>
                                    </form>
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
                <hr>
            </c:if>

            <!--container per gli elementi che puoi aggiungere nella lista-->
            <div class="display-4 font-15 my-2 padding-bottom padding-top text-center">
                Elements you can add in your list:
            </div>
            <div class="input-group mb-2">
                <input type="text" class="form-control" placeholder="Search item.." id="searchBar">
                <div class="input-group-append">
                    <button class="btn btn-success" type="submit" onClick="clickButton()" id="buttonSearch">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
            <script type="text/javascript">
                var input = document.getElementById("searchBar");
            
                input.addEventListener("keyup", function (event) {
                    // Cancel the default action, if needed
                    event.preventDefault();
                    // Number 13 is the "Enter" key on the keyboard
                    if (event.keyCode === 13) {
                    // Trigger the button element with a click
                    document.getElementById("buttonSearch").click();
                    document.getElementById("searchBar").value='';
                    }
                });
            </script>
            <div class="container testimonial-group padding-bottom">
                <ul class="list-group list-group-flush border" id="listItems">

                    <c:forEach var="item" items="${allItems}">
                            <li class="list-group-item" id="${item.getName()}${item.getId()}">${item.getName()}
                                <a style="display: inline-block;" href="<c:url value="/ItemServlet">
                                       <c:param name="itemID" value="${item.getId()}"/>
                                       <c:param name="action" value="viewItem"/>
                                       <c:param name="listID" value="${listID}"/>
                                   </c:url>">
                                    <button class="btn btn-outline-info btn-md ml-2 my-2 mr-2"><i class="far fa-eye"></i></button>
                                </a>
                                <form style="display: inline-block;" class="form-inline" method="POST" action="<c:url value="/List">
                                          <c:param name="listID" value="${listID}"/>
                                          <c:param name="itemID" value="${item.getId()}"/>
                                          <c:param name="action" value="addItem"/>
                                      </c:url>">
                                    <button type="submit" class="btn btn-outline-success btn-md my-2"><i class="fas fa-plus"></i></button>
                                </form>
                            </li>
                    </c:forEach>

                </ul>
            </div>
            <hr>

            <!--aggiungi descrizione-->
            <div class="row padding-bottom">
                <div class="col-12 text-center">
                    <div class="display-4 font-15 my-2">
                        add a/modify the description:
                    </div><br>
                </div>
                <div class="col-12 text-center">
                    <form method="POST" action="<c:url value="/List">
                              <c:param value="view" name="action"/>
                              <c:param name="listID" value="${listID}" />
                          </c:url>">
                        <textarea name="newNote" id="description-box" style="height:150px" required>${desc}</textarea>
                        <div class="form-group my-2">
                            <button type="submit" name="modify" value="note" class="btn btn-outline-danger btn-large">Submit description</button>
                        </div>
                    </form>
                </div>
            </div>
            <hr>


            <!--cambio del logo-->
            <form method="POST" action="<c:url value="/List">
                      <c:param value="view" name="action"/>
                      <c:param name="listID" value="${listID}" />
                      <c:param name="modify" value="logo" />
                  </c:url>" enctype="multipart/form-data" id="changeImage">
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
                                <img src="<c:url value="/List">
                                               <c:param name="action" value="retrieveImage" />
                                               <c:param name="listID" value="${listID}" />
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
                            <button type="submit" class="btn btn-outline-danger btn-large">Submit logo</button>
                        </div>
                    </div>

                </div>
            </form>
            <hr>


            <c:if test="${friends.size() != 0}">
                <!--container con amici-->
                <div class="display-4 font-15 my-2 padding-bottom padding-top text-center">
                    Your Friends:
                </div>
                <div class="container testimonial-group padding-bottom">
                    <div class="row flex-row flex-nowrap card-deck">

                        <c:forEach var="friend" items="${friends}">
                            <!--elenco di carte-->
                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                <div class="card m-0">
                                    <img class="card-img-top border" src="<c:url value="/friend/profile">
                                             <c:param name="action" value="retrieveImage"/>
                                             <c:param name="friendId" value="${friend.getId()}"/>
                                         </c:url>">
                                    <h4 class="card-title text-center">${friend.getName()}</h4>
                                    <div class="card-footer">
                                        <div class="custom-control custom-checkbox ml-3">
                                            <input type="checkbox" class="custom-control-input" id="shared${friend.getId()}" 
                                                   name="example1" onCLick="updateSharing('${friend.getId()}')" 
                                                   <c:if test="${mapSharing.get(friend.getId())}">checked</c:if> >
                                            <label class="custom-control-label" for="shared${friend.getId()}"> &nbsp; shared</label>
                                        </div><br>

                                        <div class="custom-control custom-checkbox ml-3">
                                            <input type="checkbox" class="custom-control-input" id="permission${friend.getId()}"
                                                   onCLick="updatePermission('${friend.getId()}')" name="example1"
                                                   <c:if test="${mapPermission.get(friend.getId())}">checked</c:if>  >
                                            <label class="custom-control-label" for="permission${friend.getId()}"> &nbsp; can modify</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
        <hr>
        
        <!--pulsante di go back-->
        <div class="row padding-bottom">
            <div class="col-12 text-center">
                <a href="/" class="btn btn-md btn-outline-danger m-2" ><i class="fas fa-chevron-left"></i> Back to Home</a>
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
