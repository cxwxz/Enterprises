<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
            <a href="?lang=ru"><img alt="ru" title="Russia" src="<spring:url value='/resources/images/ru.jpg'/>"></a>
        </span>


        <div id="tab">

            <h1 style="color: #7dc9e2">${enterprise.getName()}</h1>

            <table style="color: #7dc9e2" class="simple-little-table" cellspacing='0'>
                <tr class='even'>
                    <td><h2><spring:message code="label.foundationDate"/></h2></td>
                    <th>${enterprise.getFoundationDate()}</th>
                    <td><h2><spring:message code="label.enterpriseType"/></h2></td>
                    <th>${enterprise.getType().getName()}</th>
                </tr>

            </table>


            <!-- <table class="simple-little-table" cellspacing='0'>-->
            <table  style="color: #7dc9e2" class="simple-little-table" cellspacing='0'>    
                <c:forEach  items="${enterprise.getDepartments()}" var="department">
                    <tr>
                        <td>${department.getId()} </td>
                        <td>${department.getName()}</td>
                        <td>${department.getType().getName()}</td>
                        <td><a href="${pageContext.request.contextPath}/departmentPage/${department.getId()}">link</a>
                    </tr>
                </c:forEach>
            </table>
            <!--   </table>-->

        </div> 
        <a href="${pageContext.request.contextPath}/editEnterprise?id=${enterprise.getId()}"><spring:message code="label.editEnterprise"/></a>
        <a href="${pageContext.request.contextPath}/deleteEnterprise?id=${enterprise.getId()}"><spring:message code="label.deleteEnterprise"/></a>




        <form  method="POST" action="${pageContext.request.contextPath}/createDepartment?id=${enterprise.getId()}">
            <table cellspacing='2'>
                <tr>

                    <td>
                        <input name="departmentName" type="text">
                        <spring:message code="label.inputDepartmentName"/>
                    </td>

                </tr>
                <tr>

                    <td>
                        <input name="departmentType" type="text">
                        <spring:message code="label.inputDepartmentTypeName"/>
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
