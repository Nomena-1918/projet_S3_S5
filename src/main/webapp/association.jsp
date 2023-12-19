<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="org.example.demo.models.Activite" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.demo.models.Bouquet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Holiday activity</title>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/templatemo-xtra-blog.css" rel="stylesheet">

    <%
        //List<Activite> activites = (List<Activite>) request.getAttribute("activites");
        List<Activite> activites = new ArrayList<>() {
            {
                add(new Activite(1L, "Activite 1"));
                add(new Activite(2L, "Activite 2"));
                add(new Activite(3L, "Activite 3"));
            }
        };
        request.setAttribute("activites", activites);

        //List<Bouquet> bouquets = (List<Bouquet>) request.getAttribute("bouquets");
        List<Bouquet> bouquets = new ArrayList<>() {
            {
                add(new Bouquet(1L, "Bouquet 1"));
                add(new Bouquet(2L, "Bouquet 2"));
                add(new Bouquet(3L, "Bouquet 3"));
            }
        };
        request.setAttribute("bouquets", bouquets);
    %>

</head>

<body>
<jsp:include page="inc/header.jsp"/>
<div class="container-fluid">
    <main class="tm-main">
        <div class="row tm-row tm-mb-120">
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Association activite bouquet</h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <form method="POST" action="" class="mb-5 ml-auto mr-0 tm-contact-form">
                    <div class="form-group row mb-4">
                        <label for="bouquet" class="col-sm-3 col-form-label text-right tm-color-primary">Bouquet</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="bouquet" id="bouquet" required>
                                <%--<jsp:useBean id="bouquets" scope="request" type="java.util.List"/>--%>
                                <c:forEach items="${bouquets}" var="activite">
                                    <option value="${bouquets.id}">${bouquets.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="activite" class="col-sm-3 col-form-label text-right tm-color-primary">Activité</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="activite" id="activite" required>
                                <%--<jsp:useBean id="activites" scope="request" type="java.util.List"/>--%>
                                <c:forEach items="${activites}" var="activite">
                                    <option value="${activite.id}">${activite.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row text-right">
                        <div class="col-12">
                            <button class="tm-btn tm-btn-primary tm-btn-small">Valider</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="inc/footer.jsp"/>
</body>
</html>