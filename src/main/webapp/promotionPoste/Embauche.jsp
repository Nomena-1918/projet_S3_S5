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
<jsp:include page="../inc/header.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="static org.voyage.demo.utils.Cast.castToList" %>
<%@ page import="org.voyage.demo.models.gestion_personnel.Candidat" %>
<%@ page import="org.voyage.demo.models.gestion_personnel.Fonction" %>

<%
    List<Candidat> candidat = castToList(request.getAttribute("list-candidat"));
    List<Fonction> fonctions = castToList(request.getAttribute("list_fonction"));
    String messageError = (String)(request.getAttribute("messageError"));
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
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Embauche</h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <form method="POST" action="" class="mb-5 ml-auto mr-0 tm-contact-form">
                    <div class="form-group row mb-4">
                        <label for="idEmploye" class="col-sm-3 col-form-label text-right tm-color-primary">Candidat</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idEmploye" id="idEmploye" required>
                                <% for (Candidat item : candidat) {%>
                                <option value="<%=item.getId()%>"><%=item.getNom()%> <%=item.getPrenom()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="fonction" class="col-sm-3 col-form-label text-right tm-color-primary">Fonction</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idFonction" id="fonction" required>
                                <% for (Fonction item : fonctions) {%>
                                <option
                                        value="<%=item.getId()%>">
                                    <%=item.getNom()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="date" class="col-sm-3 col-form-label text-right tm-color-primary">Date </label>
                        <div class="col-sm-9">
                            <input class="form-control mr-0 ml-auto" name="date" id="date" type="date" required>
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

<jsp:include page="../inc/footer.jsp"/>

</body>
</html>