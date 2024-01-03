<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voyage 🏞️</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/fontawesome/css/all.min.css"> <!-- https://fontawesome.com/ -->
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap" rel="stylesheet"> <!-- https://fonts.google.com/ -->
    <link href="<%= request.getContextPath() %>/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/templatemo-xtra-blog.css" rel="stylesheet">
</head>
<body>

<jsp:include page="inc/header.jsp"/>

<div class="container-fluid">
    <main class="tm-main">
        <div class="row tm-row">
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="" class="effect-lily tm-post-link tm-pt-60">
                    <div class="tm-post-link-inner">
                        <img src="<%= request.getContextPath() %>/assets/img/img-01.jpg" alt="Image" class="img-fluid">
                    </div>
                    <span class="position-absolute tm-new-badge">New</span>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title"></h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="" class="effect-lily tm-post-link tm-pt-60">
                    <div class=" tm-post-link-inner">
                        <img src="<%= request.getContextPath() %>/assets/img/img-02.jpg" alt="Image" class="img-fluid">
                    </div>
                    <span class="position-absolute tm-new-badge">New</span>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">Voyages et bouquets</h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="" class="effect-lily tm-post-link tm-pt-20">
                    <div class="tm-post-link-inner">
                        <img src="<%= request.getContextPath() %>/assets/img/img-03.jpg" alt="Image" class="img-fluid">
                    </div>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">En toute confiance</h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="" class="effect-lily tm-post-link tm-pt-20">
                    <div class="tm-post-link-inner">
                        <img src="<%= request.getContextPath() %>/assets/img/img-04.jpg" alt="Image" class="img-fluid">
                    </div>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">Voyages</h2>
                </a>
                <article class="col-12 col-md-6 tm-post">
                    <hr class="tm-hr-primary">
                    <a href="" class="effect-lily tm-post-link tm-pt-20">
                        <div class="tm-post-link-inner">
                            <img src="<%= request.getContextPath() %>/assets/img/img-05.jpg" alt="Image" class="img-fluid">
                        </div>
                        <h2 class="tm-pt-30 tm-color-primary tm-post-title"></h2>
                    </a>
                </article>
            </article>
        </div>
    </main>
</div>

<script src="<%= request.getContextPath() %>/assets/js/jquery.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/js/templatemo-script.js"></script>

</body>
</html>