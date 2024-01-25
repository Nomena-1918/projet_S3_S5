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
<jsp:include page="../inc/header.jsp"/>
<%@ page import="org.example.demo.models.Activite" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.utils.Cast" %>
<%@ page import="static org.example.demo.utils.Cast.castToString" %>
<%@ page import="org.example.demo.models.client.StatistiqueSexe" %>
<%
    List<Activite> activite = Cast.castToList(request.getAttribute("list_activite"), Activite.class);
    List<StatistiqueSexe> statistiqueSexeHomme=Cast.castToList(request.getAttribute("list_statHomme"), StatistiqueSexe.class);
    List<StatistiqueSexe> statistiqueSexeFemme=Cast.castToList(request.getAttribute("list_statFemme"), StatistiqueSexe.class);
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
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Statistiques par genre
                </h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <form method="POST" action="" class="mb-5 ml-auto mr-0 tm-contact-form">
                    <div class="form-group row mb-4">
                        <label for="idActivite" class="col-sm-3 col-form-label text-right tm-color-primary">Activité</label>
                        <div class="col-sm-9">
                            <select class="form-control mr-0 ml-auto" name="idActivite" id="idActivite"  required>
                                <% for (Activite item : activite) {%>
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
        <%if (statistiqueSexeHomme != null) {%>
        <div class="row tm-row">
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Statistiques masculin à l'activité <%=statistiqueSexeFemme.get(0).getActivite().getNom()%></h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <table class="table table-striped tm-table">
                    <tr>
                        <th>nombre</th>
                        <th>prix_total</th>
                    </tr>
                    <%for(StatistiqueSexe item: statistiqueSexeHomme){%>
                    <tr>
                        <td><%=item.getNombre()%></td>
                        <td><%=item.getPrixTotal()%></td>
                    </tr>
                    <%}%>
                </table>
            </div>
        </div>
        <%}%>

        <%if (statistiqueSexeFemme != null) {%>
        <div class="row tm-row">
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Statistiques féminin à l'activité <%=statistiqueSexeFemme.get(0).getActivite().getNom()%></h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <table class="table table-striped tm-table">
                    <tr>
                        <th>nombre</th>
                        <th>prix_total</th>
                    </tr>
                    <%for(StatistiqueSexe item: statistiqueSexeFemme){%>
                    <tr>
                        <td><%=item.getNombre()%></td>
                        <td><%=item.getPrixTotal()%></td>
                    </tr>
                    <%}%>
                </table>
            </div>
        </div>
        <%}%>

    </main>
</div>

<jsp:include page="../inc/footer.jsp"/>
</body>
</html>