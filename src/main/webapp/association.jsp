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
    <link href="<%= request.getContextPath() %>/assets/css/bootstrap-icons.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/templatemo-xtra-blog.css" rel="stylesheet">
</head>
<body>
<jsp:include page="inc/header.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.models.Activite" %>
<%@ page import="org.example.demo.models.Bouquet" %>
<%@ page import="org.example.demo.models.CategorieLieu" %>
<%@ page import="org.example.demo.models.TypeDuree" %>
<%@ page import="static org.example.demo.utils.Cast.castToString" %>
<%@ page import="static org.example.demo.utils.Cast.castToList" %>

<%
    List<Activite> activite = castToList(request.getAttribute("list-activite"), Activite.class);
    List<Bouquet> bouquet = castToList(request.getAttribute("list-bouquet"), Bouquet.class);
    List<CategorieLieu> categorieLieu = castToList(request.getAttribute("list-categorieLieu"), CategorieLieu.class);
    List<TypeDuree> typeDuree = castToList(request.getAttribute("list-typeDuree"), TypeDuree.class);
    String messageError = castToString(request.getAttribute("messageError"));
%>
<div class="container-fluid">
    <main class="tm-main">
        <div class="row tm-row">
            <%if (messageError != null) {%>
            <div class="card col-12">
                <div class="card-body">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-octagon me-1"></i>
                        <%=messageError%>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Association activite bouquet</h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <form method="POST" action="activitebouquet-servlet" class="mb-5 ml-auto mr-0 tm-contact-form">
                    <div class="form-group row mb-4">
                        <label for="bouquet" class="col-sm-3 col-form-label text-right tm-color-primary">Bouquet</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idBouquet" id="bouquet" required>
                                <% for (Bouquet value : bouquet) {%>
                                <option value="<%=value.getId()%>"><%=value.getNom()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="categorieLieu" class="col-sm-3 col-form-label text-right tm-color-primary">Catégorie Lieu</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idCategorieLieu" id="CategorieLieu" required>
                                <% for (CategorieLieu item : categorieLieu) {%>
                                <option value="<%=item.getId()%>"><%=item.getNom()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="typeDuree" class="col-sm-3 col-form-label text-right tm-color-primary">Type Durée</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idTypeDuree" id="typeDuree" required>
                                <% for (TypeDuree item : typeDuree) {%>
                                <option value="<%=item.getId()%>"><%=item.getNom()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="activite" class="col-sm-3 col-form-label text-right tm-color-primary">Activité</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idActivite" id="activite"  required>
                                <% for (Activite item : activite) {%>
                                <option value="<%=item.getId()%>"><%=item.getNom()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="nombre" class="col-sm-3 col-form-label text-right tm-color-primary">Nombre</label>
                        <diV class="col-sm-9">
                            <input class="form-control mr-0 ml-auto" name="nombre" id="nombre" type="number" required>
                        </diV>
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