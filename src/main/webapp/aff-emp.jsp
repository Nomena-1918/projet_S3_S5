<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="inc/header.jsp"/>

<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.models.Emp" %>

<%
    List<Emp> emp = (List<Emp>)request.getAttribute("list-emp");
%>

<div class="container d-flex flex-column justify-content-center align-items-center py-4">

    <p><%= emp %></p>

</div>

<jsp:include page="inc/footer.jsp"/>

</body>
</html>

