<%-- 
    Document   : AddList
    Created on : 23-Nov-2018, 11:00:16
    Author     : tommaso
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add List</title>
    </head>
    <body>
        <h1>Add List</h1>
        <p> For
            <c:if test="${isAnon}"> anonymous user </c:if>
            <c:if test="${!isAnon}"> user with ID = <c:out value="${id}"/> </c:if>
            </p>
            <form method="POST" action="<c:url value="/ListRegistration">
                  <c:param name="action" value="addList"/>
              </c:url>" enctype="multipart/form-data">


            <label>Name: </label>   
            <input name="name" id="name" type="text"/><br/>
            <label>Description: </label>
            <input name="description" id="description" type="text"/><br/>
            <label>Image: </label>
            <input name="image" id="image" type="file"/><br/>

            <c:if test="${not isAnon}">
                <select name="friends" multiple>     

                    <c:forEach items="${friends}" var="f">
                        <option value="${f.getId()}"> ${f.getName()} </option>
                    </c:forEach>
                </select>
            </c:if>

            <select name="category">
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat.getIdCategory()}">${cat.getName()}</option>
                </c:forEach>
            </select>

            <input value="Sumbit" type="submit"/>
        </form>
            <p><c:out value="${formError}"/></p>
    </body>
</html>
