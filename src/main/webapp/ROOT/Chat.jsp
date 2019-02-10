<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - chat</title>
        <link rel="icon" href="../images/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
        <style><%@include file="./css/main.css" %></style>
        <style><%@include file="./css/chat.css" %></style>
        <script type="text/javascript">
            var listid = '${listID}';
            var userCookie = '${userCookie}';
            var url = '${url}';
            var myUsername = '${myUsername}';
            var ws = new WebSocket(url + listid + "/" + userCookie);

            ws.onopen = function (evt) {

            };

            ws.onmessage = function (evt) {
                var json = JSON.parse(evt.data);
                console.log("|"+json.authorName+"|");
                console.log("|"+myUsername+"|");
                if(json.authorName === myUsername){
                    createOutMsg(json.authorName, json.text, json.sendTime);
                }else{
                    createInMsg(json.authorName, json.text, json.sendTime);
                }
            };

            ws.onclose = function (evt) {
                console.log("close");
            };

            ws.onerror = function (evt) {
                console.log("big error");
            };

            function writeMessage(txt) {
                ws.send(txt);
            }

            window.addEventListener('beforeunload', function (e) {
                ws.close()
            });

            function createInMsg(authorName, text, sentTime) {
                var div = document.createElement("div");
                div.classList.add("incoming_msg");
                div.innerHTML = "<div class=\"received_msg\"> <div class=\"received_withd_msg\"><div class=\"authorName\">"+authorName+":</div><p>"+ text +"</p> <span class=\"time_date\"> "+ sentTime +" </span> </div> </div>";

                document.getElementById("main").appendChild(div);
            }
            
            function createOutMsg(authorName, text, sentTime) {
                var div = document.createElement("div");
                div.classList.add("outgoing_msg");
                div.innerHTML = "<div class=\"outgoing_msg_img\"> <div class=\"sent_msg\"> <p>"+ text +"</p> <span class=\"time_date\"> "+ sentTime +" </span> </div> </div>";

                document.getElementById("main").appendChild(div);
            }
        </script>
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
                        <li class="nav-item">
                            <a class="nav-link" href="/ViewAccount">Profile</a>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>



        <div class="container">
            <!--titolo-->
            <div class="row text-center padding-top2">
                <div class="col-12">
                    <p class="display-4 font-15 padding-top">Chat of ${listName}</p>
                </div>
            </div>

            <div class="messaging">
                <div class="inbox_msg">                   

                    <!-- messaggi aperti sulla destra-->
                    <div class="mesgs">
                        <div class="msg_history" id="main">
                            <!--messaggi-->
                        </div>
                        <div class="type_msg">
                            <div class="input_msg_write">
                                <input type="text" class="write_msg" placeholder="Type a message" name="text" id="textMessage"/>
                                <button class="msg_send_btn" onclick="writeMessage(document.getElementById('textMessage').value)" type="button"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
        <script>
            function addText(event) {
                //document.getElementById("chatbox").value += event;
            }
        </script>
    </body>
</html>