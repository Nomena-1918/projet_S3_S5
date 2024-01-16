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

INSERT INTO entree_activite (id_activite,quantite) values
(1,20),
(2,20),
(3,20),
(4,20),
(5,20),
(6,20),
(7,20);

INSERT INTO reservation_voyage (id_voyage, nombre_billet) values
(1,5);
