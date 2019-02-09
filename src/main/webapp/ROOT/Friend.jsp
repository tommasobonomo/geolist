<%-- 
    Document   : friend
    Created on : 09-Feb-2019, 11:28:38
    Author     : Giorgio
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Scrivi l'username della persona di cui vuoi diventare amico
        <c:if test="${error}">
            <div>
                <font color="red">username non esistente<font>
            </div> 
        </c:if>
        
        <div>
            <br>
            <form method="POST" action="<c:url value="/friend">
                      <c:param name="action" value="search&add"/>
                  </c:url>">
                <input type="text" placeholder="username" name="username">
                <button type="submit"> submit </button>
            </form>
        </div>

        <!-- lista di amici-->
        FRIENDS
        <div >
            <c:forEach var="friend" items="${friends}">
                <a href="<c:url value="/ViewAccount">
                       <c:param name="userId" value="${friend.getId()}"/>
                   </c:url>">
                    <c:out value="${friend.getUsername()}"/>
                </a>
                <form method="POST" action="<c:url value="/friend">
                          <c:param name="friendId" value="${friend.getId()}"/>
                          <c:param name="action" value="remove"/>
                      </c:url>">
                    <input type="submit" value="remove"/>
                </form>
            </c:forEach>
        </div> 
        
        REQUEST FRIENDS (RECEIVED)
        <div >
            <c:forEach var="friend" items="${requestFriends}">
                <a href="<c:url value="/ViewAccount">
                       <c:param name="userId" value="${friend.getId()}"/>
                   </c:url>">
                    <c:out value="${friend.getUsername()}"/>
                </a>
                <form method="POST" action="<c:url value="/friend">
                          <c:param name="friendId" value="${friend.getId()}"/>
                          <c:param name="action" value="accept"/>
                      </c:url>">
                    <input type="submit" value="accept"/>
                </form>
                <form method="POST" action="<c:url value="/friend">
                          <c:param name="friendId" value="${friend.getId()}"/>
                          <c:param name="action" value="refuse"/>
                      </c:url>">
                    <input type="submit" value="refuse"/>
                </form>
            </c:forEach>
        </div> 
        
        RE
        
    </body>
</html>
