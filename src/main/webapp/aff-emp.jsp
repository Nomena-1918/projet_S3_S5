<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

    List<Emp> emp = (List<Emp>)request.getAttribute("list-emp");

%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>

<%= emp %>

</body>
</html>