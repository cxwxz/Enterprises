<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<spring:url value='/resources/submit.css'/>"/>
        <link rel="stylesheet" type="text/css" href="<spring:url value='/resources/table.css'/>"/>
    </head>
    <body>

        <span style="float: right">
            <a href="?lang=en"><img alt="en" title="English" src="<spring:url value='/resources/images/en.jpg'/>"></a>
            | 
            <a href="?lang=ru"><img alt="en" title="Russia" src="<spring:url value='/resources/images/ru.jpg'/>"></a>
        </span>


        <div id="tab">

            <table style="color: lawngreen" class="simple-little-table" cellspacing='0'>
                <tr>
                    <td><h2><spring:message code="label.firstname"/></h2></td>
                    <th >${employee.getFirstName()}</th>
                    <td><h2><spring:message code="label.lastname"/></h2></td>
                    <th>${employee.getLastName()}</th>
                </tr>
                <tr>
                    <td><h2><spring:message code="label.birthday"/></h2></td>
                    <th >${employee.getBirthday()}</th>
                    <td><h2><spring:message code="label.position"/></h2></td>
                    <th>${employee.getType().getName()}</th>
                </tr>

            </table>


            <table style="color: lawngreen" class="simple-little-table" cellspacing='0' >

                <c:forEach  items="${employee.getDepartments()}" var="department">
                    <tr>
                        <td>${department.getId()} </td>
                        <td>${department.getName()}</td>
                        <td>${department.getType().getName()}</td>
                        <td><a href="${pageContext.request.contextPath}/departmentPage/${department.getId()}">link</a>
                    </tr>
                </c:forEach>

            </table>


        </div>   

        <a href="${pageContext.request.contextPath}/editEmployee?id=${employee.getId()}"><spring:message code="label.editEmployee"/></a>
        <a href="${pageContext.request.contextPath}/deleteEmployee/${employee.getId()}"><spring:message code="label.deleteEmployee"/></a>




        <form  method="POST" action="${pageContext.request.contextPath}/addToDepartment">
            <label >
                <input name="departmentName" type="text">
                <spring:message code="label.inputDepartmentName"/> 
            </label>

            <input type="submit" value="<spring:message code="label.submit"/>">
        </form>

    </body>
</html>
