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
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.utils.Cast" %>
<%@ page import="static org.example.demo.utils.Cast.castToString" %>
<%@ page import="org.example.demo.models.promotionPoste.SituationProPersonne" %>

<%
  List<SituationProPersonne> personnel = Cast.castToList(request.getAttribute("list-personnel"), SituationProPersonne.class);
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
        <h2 class="tm-color-primary tm-post-title tm-mb-60">Liste personnel
        </h2>
      </div>
    <%if (personnel != null) {%>
    <div class="row tm-row">
      <div class="col-12">
        <h2 class="tm-color-primary tm-post-title tm-mb-60">Liste de reste de billets d'activité</h2>
      </div>
      <div class="col-lg-7 tm-contact-left">
        <table class="table table-striped tm-table">
          <tr>
            <th>Matricule</th>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Dtn</th>
            <th>Sexe</th>
            <th>Fonction</th>
            <th>Date embauche</th>
            <th>Grade</th>
            <th>Taux horaire</th>
          </tr>
<%--          <%for(ResteActivite item: resteActivites){%>--%>
<%--          <tr>--%>
<%--            <td><%=item.getActivite().getNom()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--            <td><%=item.getResteBillet()%></td>--%>
<%--          </tr>--%>
<%--          <%}%>--%>
          <tr>
            <td>26</td>
            <td>Antema</td>
            <td>Rakotoarivelo</td>
            <td>19-02-2005</td>
            <td>Homme</td>
            <td>Informaticien</td>
            <td>10-02-2024</td>
            <td>Senior</td>
            <td>10000000</td>
          </tr>
        </table>
      </div>
    </div>
    <%}%>
  </main>
</div>

<jsp:include page="../inc/footer.jsp"/>
</body>
</html>