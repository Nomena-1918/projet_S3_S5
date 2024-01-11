<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="tm-header" id="tm-header">
    <div class="tm-header-wrapper">
        <button class="navbar-toggler" type="button" aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>
        <div class="tm-site-header">
            <div class="mb-3 mx-auto tm-site-logo"><i class="fas fa-times fa-2x"></i></div>
            <h1 class="text-center">Holiday activity</h1>
        </div>
        <nav class="tm-nav" id="tm-nav">
            <ul>
                <li class="tm-nav-item"><a href="<%= request.getContextPath() %>/index.jsp" class="tm-nav-link">
                    <i class="fas fa-home"></i>
                    Accueil
                </a></li>
                <li class="tm-nav-item"><a href="activite-servlet" class="tm-nav-link">
                    <i class="fas fa-pen"></i>
                    Ajout activite
                </a></li>
                <li class="tm-nav-item"><a href="typeduree-servlet" class="tm-nav-link">
                    <i class="fas fa-pen"></i>
                    Ajout type duree
                </a></li>
                <li class="tm-nav-item"><a href="categorielieu-servlet" class="tm-nav-link">
                    <i class="fas fa-pen"></i>
                    Ajout catégorie lieu
                </a></li>
                <li class="tm-nav-item"><a href="bouquet-servlet" class="tm-nav-link">
                    <i class="fas fa-users"></i>
                    Ajout bouquet voyage
                </a></li>
                <li class="tm-nav-item"><a href="activitebouquet-servlet" class="tm-nav-link">
                    <i class="far fa-comments"></i>
                    Association activite bouquet
                </a></li>
                <li class="tm-nav-item"><a href="recherche-servlet" class="tm-nav-link">
                    <i class="far fa-comments"></i>
                    Recherche
                </a></li>
                <li class="tm-nav-item"><a href="nombreactivitevoyage-servlet" class="tm-nav-link">
                    <i class="far fa-comments"></i>
                    Nombre Activite Voyage
                </a></li>
                <li class="tm-nav-item"><a href="activitebouquetprix-servlet" class="tm-nav-link">
                    <i class="far fa-comments"></i>
                    Voyage fourchette de prix
                </a></li>
                <li class="tm-nav-item"><a href="entreeactivite-servlet" class="tm-nav-link">
                    <i class="fas fa-pen"></i>
                    Achat billet activité
                </a></li>
                <li class="tm-nav-item"><a href="reservationResteBilletActivite-servlet" class="tm-nav-link">
                    <i class="fas fa-pen"></i>
                    Reservation voyage
                </a></li>
                <li class="tm-nav-item"><a href="listeResteBilletActivite-servlet" class="tm-nav-link">
                    <i class="fas fa-pen"></i>
                    Liste reste billet activité
                </a></li>
            </ul>
        </nav>
        <div class="tm-mb-65">
            <a rel="nofollow" href="https://fb.com/templatemo" class="tm-social-link">
                <i class="fab fa-facebook tm-social-icon"></i>
            </a>
            <a href="https://twitter.com" class="tm-social-link">
                <i class="fab fa-twitter tm-social-icon"></i>
            </a>
            <a href="https://instagram.com" class="tm-social-link">
                <i class="fab fa-instagram tm-social-icon"></i>
            </a>
            <a href="https://linkedin.com" class="tm-social-link">
                <i class="fab fa-linkedin tm-social-icon"></i>
            </a>
        </div>
    </div>
</header>