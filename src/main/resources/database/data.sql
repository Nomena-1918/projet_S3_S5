-- Insertion de données dans la table activite
INSERT INTO activite (nom,prix_Unitaire) VALUES
('Excursion en montagne',900),
('Croisiere cotiere',10000),
('Visite de monuments historiques',20000),
('Safari en Afrique',30000),
--Donnees de test
('Randonnée dans la nature sauvage',40000),
('Exploration de villes europeennes',50000),
('Plongee sous-marine',60000);

-- Insertion de données dans la table bouquet
INSERT INTO bouquet (nom) VALUES
('Bouquet Aventure'),
('Bouquet Decouverte'),
('Bouquet Historique'),
('Bouquet Safari'),
--Donnees de test
('Bouquet Nature'),
('Bouquet Culture'),
('Bouquet Océan');

INSERT INTO categorie_lieu (nom) VALUES
('regional'),
('national'),
('provincial');

INSERT INTO type_duree (nom, intervaljour) VALUES
('court', int4range(2, 4)),
('moyen', int4range(5, 7)),
('long', int4range(8, 10));

-- Insertion de données dans la table bouquet_activite
INSERT INTO bouquet_activite (id_activite, id_bouquet,id_categorie_lieu,id_duree,nombre) VALUES
(1,1,1,1,4),  -- Excursion en montagne avec Bouquet Aventure
(2,2,1,2,5),  -- Croisière côtière avec Bouquet Découverte
(3,3,1,3,2),  -- Visite de monuments historiques avec Bouquet Historique
(4,4,2,1,1),  -- Safari en Afrique avec Bouquet Safari
(5,5,2,2,6),  -- Randonnée dans la nature sauvage avec Bouquet Nature
(6,6,3,3,7),  -- Exploration de villes européennes avec Bouquet Culture
(7,7,3,1,8);  -- Plongée sous-marine avec Bouquet Océan

INSERT INTO bouquet_activite (id_activite, id_bouquet,id_categorie_lieu,id_duree,nombre) VALUES
(2,1,1,1,3);

INSERT INTO entree_activite (id_activite,quantite) values
(1,20),  -- Excursion en montagne avec Bouquet Aventure
(2,20),  -- Croisière côtière avec Bouquet Découverte
(3,20),  -- Visite de monuments historiques avec Bouquet Historique
(4,20),  -- Safari en Afrique avec Bouquet Safari
(5,20),  -- Randonnée dans la nature sauvage avec Bouquet Nature
(6,20),  -- Exploration de villes européennes avec Bouquet Culture
(7,20);  -- Plongée sous-marine avec Bouquet Océan

INSERT INTO reservation_voyage (id_categorie_lieu, id_duree, id_bouquet, nombre_billet) values
(1,1,1,1);

SELECT id_activite,nom_activite,nombre_billet_restant FROM reste_activite_voyage
    and id_activite=?