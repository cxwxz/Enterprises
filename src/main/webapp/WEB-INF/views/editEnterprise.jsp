<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


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

        <form  method="POST" action="${pageContext.request.contextPath}/editEnterprise?id=${enterprise.getId()}">
            <label >
                <input name="enterpriseName" type="text" value="${enterprise.getName()}">
                <spring:message code="label.inputEnterpriseName"/>
            </label>
            <label>
                <input name="foundationDate" type="text" value="${String.valueOf(enterprise.getFoundationDate())}">
                <spring:message code="label.inputEnterpriseFoundationDate"/>
            </label>
            <label>
                <input name="enterpriseType" type="text" value="${enterprise.getType().getName()}">
                <spring:message code="label.inputEnterpriseTypeName"/>
            </label>

            <input type="submit" value="<spring:message code="label.save"/>">
        </form>
    </body>
</html>
