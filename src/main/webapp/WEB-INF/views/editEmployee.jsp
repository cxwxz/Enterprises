<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <span style="float: right">
            <a href="?lang=en"><img alt="en" title="English" src="<spring:url value='/resources/images/en.jpg'/>"></a>
            | 
            <a href="?lang=ru"><img alt="en" title="Russia" src="<spring:url value='/resources/images/ru.jpg'/>"></a>
        </span>
        <form  method="POST" action="${pageContext.request.contextPath}/editEmployee?id=${employee.getId()}">

            <label>
                <input name="firstName" type="text" value="${employee.getFirstName()}">
                <spring:message code="label.inputEmployeeFirstName"/>
            </label>

            <label >
                <input name="lastName" type="text" value="${employee.getLastName()}">
                <spring:message code="label.inputEmployeeLastName"/>
            </label>
            <label>
                <input name="birthday" type="text" value="${String.valueOf(employee.getBirthday())}">
                <spring:message code="label.inputEmployeeBirthday"/>
            </label>

            <label>
                <input name="employeeType" type="text" value="${employee.getType().getName()}">
                <spring:message code="label.inputEmployeeTypeName"/>
            </label>

            <input type="submit" value="<spring:message code="label.save"/>">
        </form>
    </body>
</html>
