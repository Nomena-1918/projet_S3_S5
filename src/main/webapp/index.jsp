<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="../assets/css/bootstrap.min.css" type="text/css">
</head>
<body>

<jsp:include page="inc/header.jsp"/>

<div class="container d-flex flex-column justify-content-center align-items-center py-4">

    <h1><%= "Hello World!" %>
    </h1>
    <br/>

    <a href="hello-servlet">Hello Servlet</a>
    <a href="emp-servlet?debut=2&&nbr_ligne=4">emp-servlet</a>

</div>

<jsp:include page="inc/footer.jsp"/>

</body>
</html>