<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voyage</title>
    <link rel="stylesheet" href="fontawesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap" rel="stylesheet">
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
                <a href="post.html" class="effect-lily tm-post-link tm-pt-60">
<%--                    <div class="tm-post-link-inner">--%>
<%--                        <img src="img/img-01.jpg" alt="Image" class="img-fluid">--%>
<%--                    </div>--%>
                    <span class="position-absolute tm-new-badge">New</span>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title"></h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="post.html" class="effect-lily tm-post-link tm-pt-60">
<%--                    <div class=" tm-post-link-inner">--%>
<%--                        <img src="img/img-02.jpg" alt="Image" class="img-fluid">--%>
<%--                    </div>--%>
                    <span class="position-absolute tm-new-badge">New</span>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">Multi-purpose blog template</h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="post.html" class="effect-lily tm-post-link tm-pt-20">
<%--                    <div class="tm-post-link-inner">--%>
<%--                        <img src="img/img-03.jpg" alt="Image" class="img-fluid">--%>
<%--                    </div>--%>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">How can you apply Xtra Blog</h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="post.html" class="effect-lily tm-post-link tm-pt-20">
<%--                    <div class="tm-post-link-inner">--%>
<%--                        <img src="img/img-04.jpg" alt="Image" class="img-fluid">--%>
<%--                    </div>--%>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">A little restriction to apply</h2>
                </a>
            </article>
            <article class="col-12 col-md-6 tm-post">
                <hr class="tm-hr-primary">
                <a href="post.html" class="effect-lily tm-post-link tm-pt-20">
<%--                    <div class="tm-post-link-inner">--%>
<%--                        <img src="img/img-05.jpg" alt="Image" class="img-fluid">--%>
<%--                    </div>--%>
                    <h2 class="tm-pt-30 tm-color-primary tm-post-title">/h2>
                </a>
            </article>
        </div>
    </main>
</div>
<jsp:include page="inc/footer.jsp"/>
</body>
</html>