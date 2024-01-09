-- create database voyage_db;

--create extension if not exists btree_gist;

create table activite(
    id serial primary key,
    nom varchar(100) unique not null,
    prix_unitaire decimal check ( prix_unitaire > 0 ) not null
);

create table bouquet(
    id serial primary key,
    nom varchar(100) unique not null
);

CREATE TABLE categorie_lieu (
   id serial PRIMARY KEY,
   nom varchar(100) unique not null
);

CREATE TABLE type_duree (
    id serial PRIMARY KEY,
    nom varchar(100) UNIQUE NOT NULL,
    intervaljour int4range,
    EXCLUDE USING gist (intervaljour WITH &&)
);


CREATE TABLE bouquet_activite (
     id serial PRIMARY KEY,
     id_categorie_lieu integer,
     id_duree integer,
     id_activite integer,
     id_bouquet integer,
     nombre integer NOT NULL CHECK (nombre > 0),
     CONSTRAINT bouquet_activite_id_activite_id_bouquet_key UNIQUE (id_activite, id_bouquet),
     CONSTRAINT bouquet_activite_id_activite_fkey FOREIGN KEY (id_activite) REFERENCES activite(id),
     CONSTRAINT bouquet_activite_id_bouquet_fkey FOREIGN KEY (id_bouquet) REFERENCES bouquet(id),
     CONSTRAINT bouquet_activite_id_categorie_lieu_fkey FOREIGN KEY (id_categorie_lieu) REFERENCES categorie_lieu(id),
     CONSTRAINT bouquet_activite_id_duree_fkey FOREIGN KEY (id_duree) REFERENCES type_duree(id)
);


CREATE VIEW vue_activite_bouquet AS
SELECT
    ba.id AS id,
    a.id AS id_activite,
    a.nom AS nom_activite,
    b.id AS id_bouquet,
    b.nom AS nom_bouquet
FROM bouquet_activite ba
JOIN activite a ON ba.id_activite = a.id
JOIN bouquet b ON ba.id_bouquet = b.id;


CREATE VIEW vue_activite_bouquet_nombre AS
SELECT
    ba.id AS id,
    a.id AS id_activite,
    a.nom AS nom_activite,
    b.id AS id_bouquet,
    b.nom AS nom_bouquet,
    ba.id_categorie_lieu AS id_categorie_lieu,
    ba.nombre AS nombre,
    cl.nom AS nom_categorie_lieu,
    td.id AS id_type_duree,
    td.nom AS nom_type_duree,
    td.intervaljour AS intervaljour,
    lower(td.intervaljour) AS debutjour,
    upper(td.intervaljour) AS finjour
FROM bouquet_activite ba
JOIN activite a ON ba.id_activite = a.id
JOIN bouquet b ON ba.id_bouquet = b.id
JOIN categorie_lieu cl ON ba.id_categorie_lieu = cl.id
JOIN type_duree td ON ba.id_duree = td.id;




CREATE VIEW vue_activite_bouquet_nombre_prix AS
SELECT
    vabn.id_bouquet,
    vabn.nom_bouquet,
    vabn.id_categorie_lieu,
    vabn.nom_categorie_lieu,
    vabn.id_type_duree,
    vabn.nom_type_duree,
    vabn.intervaljour,
    vabn.debutjour,
    vabn.finjour,
    sum(a.prix_unitaire*vabn.nombre) as prix_total
FROM vue_activite_bouquet_nombre vabn
JOIN activite a ON a.id=vabn.id_activite
group by
    vabn.id_bouquet,
    vabn.nom_bouquet,
    vabn.id_categorie_lieu,
    vabn.nom_categorie_lieu,
    vabn.id_type_duree,
    vabn.nom_type_duree,
    vabn.intervaljour,
    vabn.debutjour,
    vabn.finjour
;
Select * from vue_activite_bouquet_nombre_prix where prix_total between 80000 and 500000;