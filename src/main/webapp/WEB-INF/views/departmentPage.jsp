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

            <h1 style="color: tomato">${department.getName()}</h1>

            <table style="color: tomato" class="simple-little-table" cellspacing='0'>
                <tr>
                    <td><h2><spring:message code="label.departmentType"/></h2></td>
                    <th>${department.getType().getName()}</th>
                </tr>

            </table>


            <table style="color: tomato" class="simple-little-table" cellspacing='0'>

                <c:forEach  items="${department.getEmployees()}" var="employee">
                    <tr>
                        <td>${employee.getId()}</td>
                        <td>${employee.getFirstName()}</td>
                        <td>${employee.getLastName()}</td>
                        <td><a href="${pageContext.request.contextPath}/employeePage/${employee.getId()}">link</a>
                    </tr>
                </c:forEach>

            </table>


        </div>   

        <a href="${pageContext.request.contextPath}/editDepartment?id=${department.getId()}"><spring:message code="label.editDepartment"/></a>
        <a href="${pageContext.request.contextPath}/deleteDepartment/${department.getId()}"><spring:message code="label.deleteDepartment"/></a>

        <form  method="POST" action="${pageContext.request.contextPath}/createEmployee?id=${department.getId()}">
            <table cellspacing='2'>
                <tr>
                    <td>
                        <input name="firstName" type="text">
                        <spring:message code="label.inputEmployeeFirstName"/> 
                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="lastName" type="text">
                        <spring:message code="label.inputEmployeeLastName"/> 
                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="birthday" type="text">
                        <spring:message code="label.inputEmployeeBirthday"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="employeeType" type="text">
                        <spring:message code="label.inputEmployeeTypeName"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="<spring:message code="label.submit"/>">
                    </td>
                </tr>
            </table>
        </form>

    </body>
</html>
