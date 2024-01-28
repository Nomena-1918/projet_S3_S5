<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
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
<%@ page import="org.example.demo.models.VoyageActivite" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.utils.Cast" %>

<%
  List<VoyageActivite> voyageActivite = Cast.castToList(request.getAttribute("list-voyageActivite"), VoyageActivite.class);
%>
      <div class="container-fluid">
        <main class="tm-main">
          <div class="row tm-row tm-mb-120">
            <div class="row tm-row">
              <div class="col-12">
                <h2 class="tm-color-primary tm-post-title">Liste des activités</h2>
                <table class="table table-striped tm-table">
                  <thead>
                  <tr>
                    <th>Les activites</th>
                  </tr>
                  </thead>
                  <tbody id="listeTableBody">
                    <%for(VoyageActivite item: voyageActivite){%>
                      <tr>
                        <td><%=item.getActivite().getNom()%></td>
                      </tr>
                    <%}%>
                  </tbody>
                </table>
              </div>
              <a href="recherche-servlet">Redirection vers la page de recherche</a>
            </div>
          </div>
        </main>
      </div>
<jsp:include page="inc/footer.jsp"/>
</body>
</html>