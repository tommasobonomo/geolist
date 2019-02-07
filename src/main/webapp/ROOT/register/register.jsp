<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Geolist - register</title>
        <link rel="icon" href="../images/logos/logo.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" crossorigin="anonymous">
        <style><%@include file="../css/main.css" %></style>
    </head>

    <body>
        
        <div class="container">
            <!--titolo-->
            <div class="row text-center padding-bottom padding-top">
                <div class="col-12">
                    <img src="<c:url value="/ROOT/logos/logo-orizzontale.png"></c:url>" alt="logo orizzontale" height="60" width="180">
                    <p class="lead">Registration form</p>
                    <hr class="my-4">
                    <small>items with " * " are required</small>
                </div>
            </div>

            <!--form-->
            <form method="POST" class="form-spacer needs-validation" action="<c:url value="/form-actions/register">
                                        <c:param name="action" value="send"></c:param>
                                    </c:url>" enctype="multipart/form-data">

                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="FirstName">Name *</label>
                            <input type="text" name="FirstName" id="FirstName" class="form-control" autofocus>
                            <c:if test="${nameError}"><small style="color:red">Name not correct (can't be empty)</small></c:if>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="LastName">Surname *</label>
                            <input type="text" name="LastName" id="LastName" class="form-control" >
                            <c:if test="${surnameError}"><small style="color:red">Surname not correct (can't be empty)</small></c:if>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="UserName">Username *</label>
                    <input type="text" name="UserName" id="UserName" class="form-control">
                    <c:if test="${usernameError}"><small style="color:red">User already present or not correct (can't have spaces or be empty)</small></c:if>
                </div>

                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="Email">Email *</label>
                            <input type="email" name="Email" id="Email" class="form-control">
                            <c:if test="${emailError}"><small style="color:red">Email already present or not correct</small></c:if>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="Password">Password *</label>
                            <input type="password" name="Password" id="Password" class="form-control">
                            <c:if test="${passwordError}"><small style="color:red">Password not correct (needs a number, a special character and a numeric one)</small></c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group ">
                    <label for="avatar">choose an image for your avatar</label>
                    <input type="file" id="avatar" name="avatar" accept="image/png, image/jpeg, image/jpg">
                </div>

                <div class="form-group">
                    <!--checkbox terms-->
                    <div class="form-check">
                        <input type="checkbox" name="terms" class="form-check-input">
                        <label for="terms" class="form-check-label">  I've read all the terms and conditions *</label>
                        <c:if test="${termsError}"><small style="color:red">You need to check this box</small></c:if>
                    </div>
                </div>

                <hr class=" my-4">

                <!--bottoni-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-6 text-center">
                            <a href="/"><button type="button" class="btn btn-outline-secondary btn-lg">Cancel</button></a>
                        </div>
                        <div class="col-6 text-center">
                            <button type="submit" value="Submit" class="btn btn-outline-danger btn-lg">Register</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
                
        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>