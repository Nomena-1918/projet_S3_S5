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
<%@ page import="org.example.demo.models.composition_voyage.ActiviteBouquetPrix" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.utils.Cast" %>
<%@ page import="org.example.demo.models.gestion_reservation.Benefice" %>
<%
    List<Benefice> benefices = Cast.castToList(request.getAttribute("list-benefices"), Benefice.class);
    String messageError = (String) request.getAttribute("messageError");
%>
<div class="container-fluid">
    <main class="tm-main">
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
        <div class="row tm-row">
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Voyage avec bénéfice dans une fourchette de prix</h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <form method="POST" action="" class="mb-5 ml-auto mr-0 tm-contact-form">
                    <div class="form-group row mb-4">
                        <label for="min" class="col-sm-3 col-form-label text-right tm-color-primary">Prix Min</label>
                        <div class="col-sm-9">
                            <input class="form-control mr-0 ml-auto" name="prixMin" id="min" type="number" required>
                        </div>
                    </div>
                    <div class="form-group row mb-4">
                        <label for="max" class="col-sm-3 col-form-label text-right tm-color-primary">Prix Max</label>
                        <div class="col-sm-9">
                            <input class="form-control mr-0 ml-auto" name="prixMax" id="max" type="number" required>
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

        <%if (benefices != null && !benefices.isEmpty()) {%>
        <div class="row tm-row">
            <div class="col-12">
                <h2 class="tm-color-primary tm-post-title tm-mb-60">Liste des voyages correspondant</h2>
            </div>
            <div class="col-lg-7 tm-contact-left">
                <table class="table table-striped tm-table">
                    <tr>
                        <th>Bouquet</th>
                        <th>Durée</th>
                        <th>Catégorie lieu</th>
                        <th>Benefices</th>
                    </tr>

                    <%for(Benefice item: benefices){%>
                    <tr>
                        <td><%=item.getVoyage().getBouquet().getNom()%></td>
                        <td><%=item.getVoyage().getTypeDuree().getNom()%></td>
                        <td><%=item.getVoyage().getCategorieLieu().getNom()%></td>
                        <td><%=item.getBenefice()%></td>
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