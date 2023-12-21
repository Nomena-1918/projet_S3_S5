-- create database voyage_db;

create table activite(
    id serial primary key,
    nom varchar(100) unique not null
);

create table bouquet(
    id serial primary key,
    nom varchar(100) unique not null
);

create table bouquet_activite(
    id serial primary key,
    id_activite integer references activite (id),
    id_bouquet  integer references bouquet (id),
    unique(id_activite,id_bouquet)
);

CREATE VIEW vue_activite_bouquet AS
SELECT ba.id AS id,
    a.id AS id_activite,
    a.nom AS nom_activite,
    b.id AS id_bouquet,
    b.nom AS nom_bouquet
FROM bouquet_activite ba
    JOIN activite a ON ba.id_activite = a.id
    JOIN bouquet b ON ba.id_bouquet = b.id;
