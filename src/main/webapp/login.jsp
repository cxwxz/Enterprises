<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Login</title>
        <link rel="stylesheet" href="resources/login.css">
    </head>
    <body>

     

        <c:if test="${not empty param.error}">
            <font color="red"> Еrror
                : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
            </c:if>
    <section class="container">


        <div class="login">
            <h1>Login to Web App</h1>
            <form method="POST" action="<c:url value="/j_spring_security_check" />">

                
                <p><input type="text" name="j_username" placeholder="Username" /></p>


               
                <p><input type="password" name="j_password" placeholder="Password" /></p>


                <p class="remember_me">
                    <label>
                        <input id="remember_me" type="checkbox" name="_spring_security_remember_me" />
                        Remember me on this computer
                    </label>
                </p>

                <p class="submit"><input type="submit" value="Login" /></p>


            </form>

        </div>
    </section>
</body>
</html>