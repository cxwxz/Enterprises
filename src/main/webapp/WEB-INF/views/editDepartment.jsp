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
        <form  method="POST" action="${pageContext.request.contextPath}/editDepartment?id=${department.getId()}">
            <label >
                <input name="departmentName" type="text" value="${department.getName()}">
                <spring:message code="label.inputDepartmentName"/>
            </label>

            <label>
                <input name="departmentType" type="text" value="${department.getType().getName()}">
                <spring:message code="label.inputDepartmentTypeName"/>
            </label>

            <input type="submit" value="<spring:message code="label.save"/>">
        </form>
    </body>
</html>
