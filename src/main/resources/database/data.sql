-- Insertion de données dans la table activite
INSERT INTO activite (nom) VALUES
('Excursion en montagne'),
('Croisiere cotiere'),
('Visite de monuments historiques'),
('Safari en Afrique'),
--Donnees de test
('Randonnée dans la nature sauvage'),
('Exploration de villes europeennes'),
('Plongee sous-marine');

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

-- Insertion de données dans la table bouquet_activite
INSERT INTO bouquet_activite (id_activite, id_bouquet) VALUES
(1, 1),  -- Excursion en montagne avec Bouquet Aventure
(2, 2),  -- Croisière côtière avec Bouquet Découverte
(3, 3),  -- Visite de monuments historiques avec Bouquet Historique
(4, 4),  -- Safari en Afrique avec Bouquet Safari
(5, 5),  -- Randonnée dans la nature sauvage avec Bouquet Nature
(6, 6),  -- Exploration de villes européennes avec Bouquet Culture
(7, 7);  -- Plongée sous-marine avec Bouquet Océan

select * from vue_activite_bouquet;