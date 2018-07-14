<%@ page import="ru.misterparser.mlsbackendtest.PartServlet" %><%--
  Created by IntelliJ IDEA.
  User: stasa
  Date: 13.07.2018
  Time: 9:51
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Parts</title>
    <style>
        .table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid #d8d8d8;
        }

        .table td, .table th {
            border: 1px solid #d8d8d8;
            padding: 5px;
        }

        * {
            font-family: Arial, sans-serif;
        }

        form.filter div.filterSubmit {
            justify-content: center;
            display: flex;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}" method="get" class="filter">
    <input type="hidden" name="sort" value="${sort}">
    <input type="hidden" name="order" value="${order}">
    <input type="hidden" name="form" value="form">
    <table class="table">
        <thead>
        <tr>
            <th colspan="2">Filter</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>PN</td>
            <td><input name="number" title="PN" value="${number}"></td>
        </tr>
        <tr>
            <td>Part Name</td>
            <td><input name="name" title="Part Name" value="${name}"></td>
        </tr>
        <tr>
            <td>Vendor</td>
            <td><input name="vendor" title="Vendor" value="${vendor}"></td>
        </tr>
        <tr>
            <td>Qty</td>
            <td><input name="qty" title="Qty" value="${qty}"></td>
        </tr>
        <tr>
            <td>Shipped</td>
            <td>
                <span>after</span>
                <input name="afterShippedText" title="After shipped" value="${afterShippedText}">
                <span>before</span>
                <input name="beforeShippedText" title="Before shipped" value="${beforeShippedText}">
            </td>
        </tr>
        <tr>
            <td>Received</td>
            <td>
                <span>after</span>
                <input name="afterReceivedText" title="After received" value="${afterReceivedText}">
                <span>before</span>
                <input name="beforeReceivedText" title="Before received" value="${beforeReceivedText}">
            </td>
        </tr>
        </tbody>
    </table>
    <div class="filterSubmit">
        <input type="submit" value="Filter"/>
    </div>
</form>

<fmt:setLocale value="en_US" scope="request"/>

<table class="table">
    <thead>
    <tr>
        <th><a href="<%=PartServlet.buildHeaderUrl(pageContext, "number")%>">PN</a></th>
        <th><a href="<%=PartServlet.buildHeaderUrl(pageContext, "name")%>">Part Name</a></th>
        <th><a href="<%=PartServlet.buildHeaderUrl(pageContext, "vendor")%>">Vendor</a></th>
        <th><a href="<%=PartServlet.buildHeaderUrl(pageContext, "qty")%>">Qty</a></th>
        <th><a href="<%=PartServlet.buildHeaderUrl(pageContext, "shipped")%>">Shipped</a></th>
        <th><a href="<%=PartServlet.buildHeaderUrl(pageContext, "received")%>">Received</a></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${parts}" var="part">
        <tr>
            <td><c:out value="${part.number}"/></td>
            <td><c:out value="${part.name}"/></td>
            <td><c:out value="${part.vendor}"/></td>
            <td><c:out value="${part.qty}"/></td>
            <td><fmt:formatDate value="${part.shipped}" pattern="<%= PartServlet.DATE_PATTERN%>" /></td>
            <td><fmt:formatDate value="${part.received}" pattern="<%= PartServlet.DATE_PATTERN%>" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
