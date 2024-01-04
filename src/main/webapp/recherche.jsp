<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Activite</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/fontawesome/css/all.min.css"> <!-- https://fontawesome.com/ -->
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap" rel="stylesheet"> <!-- https://fonts.google.com/ -->
    <link href="<%= request.getContextPath() %>/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/templatemo-xtra-blog.css" rel="stylesheet">
</head>
<body>
<jsp:include page="inc/header.jsp"/>
<%@ page import="org.example.demo.models.Bouquet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.utils.Cast" %>

<%
    List<Bouquet> bouquet = Cast.castToList(request.getAttribute("list-bouquet"), Bouquet.class);
%>
<div class="container-fluid">
    <main class="tm-main">
        <div class="row tm-row tm-mb-120">
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Recherhe activite pour bouquet</h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <form method="POST" action="" class="mb-5 ml-auto mr-0 tm-contact-form">
                    <div class="form-group row mb-4">
                        <label for="activite" class="col-sm-3 col-form-label text-right tm-color-primary">Bouquet</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="bouquet" id="activite" required>
                                <% for (Bouquet item : bouquet) {%>
                                <option value="<%=item.getId()%>"><%=item.getNom()%>
                                </option>
                                <% }%>
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
<script src="js/jquery.min.js"></script>
<script src="js/templatemo-script.js"></script>
</body>
</html>