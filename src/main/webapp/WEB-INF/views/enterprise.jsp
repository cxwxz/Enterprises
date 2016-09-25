<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="resources/submit.css"/>
        <link rel="stylesheet" type="text/css" href="resources/table.css"/>

    </head>
    <body>

        <span style="float: right">
            <a href="?lang=en"><img alt="en" title="English" src="<spring:url value='/resources/images/en.jpg'/>"></a>
            | 
            <a href="?lang=ru"><img alt="ru" title="Russia" src="<c:url value="resources/images/ru.jpg"/>"></a>
        </span>


        <div id="tab">
            <table class="simple-little-table" cellspacing='0'>
                <tr class='even'>
                    <td><spring:message code="label.enterpriseId"/></td>
                    <td><spring:message code="label.enterpriseName"/></td>
                    <td><spring:message code="label.foundationDate"/></td>
                    <td><spring:message code="label.enterpriseType"/></td>
                </tr>



                <c:forEach  items="${enterpriseList}" var="enterprise">
                    <tr>
                        <td>${enterprise.getId()} </td>
                        <td>${enterprise.getName()}</td>
                        <td>${enterprise.getFoundationDate()}</td>
                        <td>${enterprise.getType().getName()}</td>
                        <td><a href="${pageContext.request.contextPath}/enterprisePage/${enterprise.getId()}">link</a>
                    </tr>
                </c:forEach>



            </table>
        </div>

        <form  method="POST" action="${pageContext.request.contextPath}/createEnterprise">
            
            <table cellspacing='0'>
                <tr>
 
                    <td>
                        <input  name="enterpriseName" type="text">
                        <spring:message code="label.inputEnterpriseName"/>
                    </td>

                </tr>
                <tr>

                    <td>
                        <input name="foundationDate" type="text">
                        <spring:message code="label.inputEnterpriseFoundationDate"/>
                    </td>

                </tr>
                <tr>

                    <td>
                        <input name="enterpriseType" type="text">
                        <spring:message code="label.inputEnterpriseTypeName"/>
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