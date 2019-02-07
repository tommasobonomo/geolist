<%-- 
    Document   : List
    Created on : 14-Nov-2018, 18:50:00
    Author     : tommaso
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style><%@include file="./css/LandingPage.css" %></style>
        <title><c:out value="${name}"/></title>
        <script type="text/javascript">
            var listid = '${listID}';
            var userCookie = '${userCookie}';
            var url = '${url}';
            var ws = new WebSocket(url + listid + "/" + userCookie);

            ws.onopen = function (evt) {
                console.log("open");
            };

            ws.onmessage = function (evt) {
                console.log(JSON.parse(evt.data));
            };

            ws.onclose = function (evt) {
                console.log("close");
            };

            ws.onerror = function (evt) {
                console.log("big error");
            };

            function writeMessage(txt,itemid) {
                ws.send(txt);
                console.log(txt[0]);
                if (txt[0] === '-')
                    decrementValue('counter'+itemid);
                else if (txt[0] === '+')
                    incrementValue('counter'+itemid);
                
            }

            window.addEventListener('beforeunload', function (e) {
                ws.close();
            });

            function incrementValue(id)
            {
                var value = parseInt(document.getElementById(id).value, 10);
                value = isNaN(value) ? 0 : value;
                value++;
                document.getElementById(id).value = value;
            }
            
            function decrementValue(id)
            {
                var value = parseInt(document.getElementById(id).value, 10);
                value = isNaN(value) ? 0 : value;
                if(value>1)
                    value--;
                document.getElementById(id).value = value;
            }
        </script>

    </head>
    <body>


        <div class="header">Geolist</div>
        <h1>${name}</h1>
        <p>${description}</p>

        <c:if test="${logged}">
            <div class="chatoflist">
                <a href="<c:url value="/chat">
                       <c:param name="listID" value="${listID}"/>
                   </c:url>">
                    <c:out value="chat"/>
                </a>
            </div> 
        </c:if>

        <div class="list-items-in-list">
            <c:forEach var="item" items="${listItems}">
                <div class="name">
                    <a href="<c:url value="/ItemServlet">
                           <c:param name="itemID" value="${item.getId()}"/>
                           <c:param name="listID" value="${listID}"/>
                           <c:param name="action" value="viewItem"/>
                       </c:url>">
                        <c:out value="${item.getName()}" />
                    </a>

                    <input type="submit" value="-" onClick="writeMessage('-' + ' ' + '${item.getId()}','${item.getId()}')"/>
                    
                    <input type="text" id="counter${item.getId()}" value="${mapQuantityItem.get(item.getId())}"/>
                    
                    <input type="submit" value="+" onClick="writeMessage('+' + ' ' + '${item.getId()}','${item.getId()}')"/>
                    
                    <c:if test="${not mapIsTakeItem.get(item.getId())}">
                        <input type="checkbox" name="take${item.getId()}" onClick="writeMessage('k' + ' ' + '${item.getId()}','${item.getId()}')">
                    </c:if>
                    <c:if test="${mapIsTakeItem.get(item.getId())}">
                        <input type="checkbox" name="take${item.getId()}" onClick="writeMessage('k' + ' ' + '${item.getId()}','${item.getId()}')" checked>
                    </c:if>
                    
                    <form method="POST" action="<c:url value="/List">
                              <c:param name="listID" value="${listID}"/>
                              <c:param name="itemID" value="${item.getId()}"/>
                              <c:param name="action" value="removeItem"/>
                          </c:url>"
                          >
                        <input type="submit" value="Remove"/>
                    </form>
                </div>
            </c:forEach>
        </div>

        <div class="list-items-disponibili">
            <c:forEach var="item" items="${allItems}">
                <div>
                    <a href="<c:url value="/ItemServlet">
                           <c:param name="itemID" value="${item.getId()}"/>
                           <c:param name="action" value="viewItem"/>
                       </c:url>">
                        <c:out value="${item.getName()}"/>
                    </a>
                    <form method="POST" action="<c:url value="/List">
                              <c:param name="listID" value="${listID}"/>
                              <c:param name="itemID" value="${item.getId()}"/>
                              <c:param name="action" value="addItem"/>
                          </c:url>">
                        <input type="submit" value="Add"/>
                    </form>

                </div>
            </c:forEach>
        </div> 



        <a href="/">Back to Landing</a>
    </body>
</html>
