INSERT INTO activite (nom) VALUES
('Excursion en montagne'),
('Croisiere cotiere'),
('Visite de monuments historiques'),
('Safari en Afrique'),
('Randonnée dans la nature sauvage'),
('Exploration de villes europeennes'),
('Plongee sous-marine');

INSERT INTO bouquet (nom) VALUES
('Bouquet Aventure'),
('Bouquet Decouverte'),
('Bouquet Historique'),
('Bouquet Safari'),
('Bouquet Nature'),
('Bouquet Culture'),
('Bouquet Océan');

INSERT INTO categorie_lieu (nom) VALUES
('regional'),
('national'),
('provincial');

INSERT INTO type_duree (nom, intervaljour) VALUES
('Court', int4range(2, 4)),
('Moyen', int4range(5, 7)),
('Long', int4range(8, 10));

INSERT INTO voyage (id_bouquet,id_categorie_lieu,id_duree) VALUES
(1,1,1),
(2,2,2),
(3,3,3),
(4,1,1),
(5,2,2),
(6,3,3),
(7,1,1);

INSERT INTO voyage_activite (id_voyage, id_activite,nombre) VALUES
(1,1,2),
(1,2,2),
(1,3,2),

(2,4,2),
(2,5,2),
(2,6,2),

(3,7,2),
(3,1,2),
(3,2,2),

(4,3,2),
(4,4,2),
(4,5,2),

(5,6,2),
(5,7,2),
(5,1,2),

(6,2,2),
(6,3,2),
(6,4,2),

(7,5,2),
(7,6,2),
(7,7,2);

INSERT INTO entree_activite (id_activite,prix_unitaire,quantite) values
(1,20000,20),
(2,30000,20),
(3,40000,20),
(4,50000,20),
(5,60000,20),
(6,70000,20),
(7,80000,20);

INSERT INTO reservation_voyage (id_voyage, nombre_billet) values
(1,5);
INSERT INTO reservation_voyage (id_voyage, nombre_billet) values
(2,1);
INSERT INTO reservation_voyage (id_voyage, nombre_billet) values
(3,2);

-- Données pour la table fonction_employe
INSERT INTO fonction_employe (nom, salaire_horaire) VALUES
('Guide', 5000.00),
('Cuisinier', 4500.00),
('Chauffeur', 4000.00);

insert into sexe(nom) values
('Masculin'),
('Féminin');

-- Données pour la table employe
INSERT INTO employe (id_sexe, nom, prenom, dtn) VALUES
(1, 'Jean', 'Dupont', '2004-01-23'),
(2, 'Marie', 'Martin', '2004-01-23'),
(1, 'Pierre','Dubois', '2004-01-23'),
(2, 'Sophie', 'Tremblay', '2004-01-23'),
(1, 'Michel', 'Lavoie', '2004-01-23');

insert into embauche_employe(id_emp, id_fonction, date_embauche) values
(1, 1, '2024-01-23 00:01'),
(2, 2, '2023-01-23 00:02'),
(3, 3, '2022-01-23 00:03'),
(4, 1, '2021-01-23 00:04'),
(5, 2, '2019-01-23 00:05');

insert into grade_fonction(nom, coeff_taux_horaire, plage_anciennete) values
('Simple', 1, int4range(0, 2)),
('Senior', 2, int4range(2, 5)),
('Expert', 3, int4range(5, 80));

-- Données pour la table prix_vente_activite
INSERT INTO prix_vente_activite (id_activite, prix_vente) VALUES
(1,200000),
(2, 60000),
(3, 80000),
(4,1000000),
(5,1200000),
(6,1400000),
(7,160000);

-- Données pour la table voyage_employe
INSERT INTO voyage_employe (id_voyage, id_emp, heures_travail) VALUES
(1, 1, 20),
(1, 2, 30),
(2, 3, 20),
(2, 4, 25),
(3, 5, 20),
(4, 1, 15),
(5, 2, 20),
(6, 3, 10),
(7, 4, 30);
select * from vue_activite_bouquet_nombre where id_activite=1


set timezone  = 'Indian/Antananarivo';

