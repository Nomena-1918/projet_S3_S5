-- create database voyage_db;
set timezone = 'Indian/Antananarivo';
create extension if not exists btree_gist;
create table activite(
    id serial primary key,
    nom varchar(100) unique not null
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


create table voyage(
    id serial PRIMARY KEY,
    id_bouquet int references bouquet(id),
    id_duree int references type_duree(id),
    id_categorie_lieu int references categorie_lieu(id)
);


CREATE TABLE voyage_activite (
    id serial PRIMARY KEY,
    id_voyage int references voyage(id),
    id_activite integer,
    nombre integer NOT NULL CHECK (nombre > 0),
    CONSTRAINT bouquet_activite_id_activite_fkey FOREIGN KEY (id_activite) REFERENCES activite(id),
    CONSTRAINT bouquet_activite_id_bouquet_fkey FOREIGN KEY (id_voyage) REFERENCES bouquet(id)
);


CREATE TABLE entree_activite (
    id serial PRIMARY KEY,
    id_activite integer REFERENCES activite(id),
    prix_unitaire decimal check ( prix_unitaire>0 ) not null,
    quantite integer check ( quantite > 0 ) not null,
    date_heure_entree timestamp default now()
);


CREATE TABLE reservation_voyage (
    id serial PRIMARY KEY,
    id_voyage int references voyage(id),
    nombre_billet integer NOT NULL CHECK (nombre_billet > 0)
);


-- Toutes les activités d'un bouquet
CREATE VIEW vue_activite_bouquet AS
select
    va.id AS id,
    a.id AS id_activite,
    a.nom AS nom_activite,
    b.id AS id_bouquet,
    b.nom AS nom_bouquet,
    v.id as id_voyage
from voyage_activite va
join activite a on va.id_activite = a.id
join voyage v on va.id_voyage = v.id
join bouquet b on v.id_bouquet = b.id;



-- Toutes les activités d'un bouquet avec le nombre des activités
CREATE VIEW vue_activite_bouquet_nombre AS
SELECT
    va.id AS id,
    a.id AS id_activite,
    a.nom AS nom_activite,
    b.id AS id_bouquet,
    b.nom AS nom_bouquet,
    v.id_categorie_lieu AS id_categorie_lieu,
    va.nombre AS nombre,
    cl.nom AS nom_categorie_lieu,
    td.id AS id_type_duree,
    td.nom AS nom_type_duree,
    td.intervaljour AS intervaljour,
    lower(td.intervaljour) AS debutjour,
    upper(td.intervaljour) AS finjour,
    v.id as id_voyage
FROM voyage_activite va
JOIN activite a ON va.id_activite = a.id
JOIN voyage v on v.id = va.id_voyage
JOIN bouquet b ON v.id_bouquet = b.id
JOIN categorie_lieu cl ON v.id_categorie_lieu = cl.id
JOIN type_duree td ON v.id_duree = td.id;


-- Somme des entrées des billets activités
CREATE VIEW vue_quantite_entree_activite as
SELECT
    id_activite,
    sum(quantite) as quantite_total
FROM entree_activite ea
group by id_activite
order by id_activite;



-- Somme des sorties totales des billets activités
create or replace view vue_quantite_sortie_activite as
select vabn.id_activite, sum(coalesce(vabn.nombre*rv.nombre_billet, 0)) as nombre_billet_reserves
from vue_activite_bouquet_nombre vabn
    join reservation_voyage rv on vabn.id_voyage = rv.id_voyage
group by vabn.id_activite
order by id_activite;



-- Reste des billets pour chaque activité
CREATE VIEW vue_reste_activite_voyage as
select
    vqea.id_activite,
    (vqea.quantite_total - coalesce(vqsa.nombre_billet_reserves,0)) as quantite_reste
    from vue_quantite_entree_activite vqea
    left join vue_quantite_sortie_activite vqsa on vqea.id_activite = vqsa.id_activite;



-- Infos complètes voyage
create view vue_voyage_complet as
select v.id, id_bouquet, b.nom as nom_bouquet, id_duree, td.nom, id_categorie_lieu, cl.nom as nom_categorie_lieu
    from voyage v
join categorie_lieu cl on cl.id = v.id_categorie_lieu
join bouquet b on b.id = v.id
join type_duree td on td.id = v.id_duree;


create view vue_prix_unitaire_activite as
WITH RankedEntries AS (
    SELECT
        id,
        id_activite,
        prix_unitaire,
        quantite,
        date_heure_entree,
        ROW_NUMBER() OVER (PARTITION BY id_activite ORDER BY date_heure_entree DESC) AS RowNum
    FROM
        entree_activite
)
SELECT
    id,
    id_activite,
    prix_unitaire,
    quantite,
    date_heure_entree
FROM
    RankedEntries
WHERE
    RowNum = 1;



-- Coût total des activités pour chaque voyage
CREATE VIEW vue_activite_bouquet_nombre_prix AS
SELECT
    vabn.id_voyage,
    vabn.id_bouquet,
    vabn.nom_bouquet,
    vabn.id_categorie_lieu,
    vabn.nom_categorie_lieu,
    vabn.id_type_duree,
    vabn.nom_type_duree,
    vabn.intervaljour,
    vabn.debutjour,
    vabn.finjour,
    vabn.id_activite,
    sum(vpua.prix_unitaire*vabn.nombre) as prix_total
FROM vue_activite_bouquet_nombre vabn
JOIN activite a ON a.id=vabn.id_activite
join entree_activite ea on a.id = ea.id_activite
join vue_prix_unitaire_activite vpua on ea.id_activite = vpua.id_activite
group by
    vabn.id_bouquet,
    vabn.nom_bouquet,
    vabn.id_categorie_lieu,
    vabn.nom_categorie_lieu,
    vabn.id_type_duree,
    vabn.nom_type_duree,
    vabn.intervaljour,
    vabn.debutjour,
    vabn.finjour, vabn.id_activite, vabn.id_voyage
order by id_voyage;


Select * from vue_activite_bouquet_nombre_prix where prix_total between 80000 and 500000;



create table sexe(
    id serial primary key,
    nom varchar(100) not null
);


create table employe(
    id serial primary key,
    nom varchar(100) not null,
    prenom varchar(100) not null,
    id_sexe int not null references sexe(id),
    dtn date not null check ( dtn < now() )
);




create table prix_vente_activite(
    id serial primary key,
    id_activite int references activite(id),
    prix_vente decimal not null check ( prix_vente>0 )
);


create table voyage_employe(
    id serial primary key,
    id_voyage int references voyage(id),
    id_emp int references employe(id),
    heures_travail int not null check ( heures_travail > 0 )
);

-- Vue Prix de vente par activité
create view vue_prix_vente_activite as
select pva.id, pva.id_activite, a.nom as nom_activite, prix_vente
    from prix_vente_activite pva
join activite a on pva.id_activite = a.id;


-- Vue Bénéfice activite
create view vue_benefice_activite as
select vpua.id_activite, (vpva.prix_vente - vpua.prix_unitaire) as benefice_activite
    from vue_prix_unitaire_activite vpua
join vue_prix_vente_activite vpva on vpva.id_activite = vpua.id_activite;


-- Vue Bénéfice total des activités par voyage
create view vue_benefice_total_activite_voyage as
select vabn.id_voyage, sum(vabn.nombre * vba.benefice_activite) as benefice_activite_voyage
FROM vue_activite_bouquet_nombre vabn
join vue_benefice_activite vba on vabn.id_activite = vba.id_activite
group by vabn.id_voyage
order by vabn.id_voyage;


create table fonction_employe(
    id serial primary key,
    nom varchar(100) not null,
    salaire_horaire decimal not null check ( salaire_horaire>0 )
);




create table grade_fonction(
    id serial primary key,
    nom varchar(100) unique not null,
    coeff_taux_horaire decimal not null check ( coeff_taux_horaire > 0 ),
    plage_anciennete int4range,
    EXCLUDE USING gist (plage_anciennete WITH &&)
);

create table embauche_employe(
    id serial primary key,
    id_emp int not null references employe(id),
    id_fonction int not null references fonction_employe(id),
    date_embauche date not null check ( date_embauche <= now() )
);



-- Vue salaire total employé par voyage
create view vue_salaire_employe_voyage as
select ve.id_voyage,  sum(fe.salaire_horaire*heures_travail) as salaire_total
from voyage_employe ve
join employe e on ve.id_emp = e.id
join embauche_employe ee on e.id = ee.id_emp and ee.date_embauche = (select max(date_embauche) from embauche_employe where embauche_employe.id_emp = e.id)
join fonction_employe fe on ee.id_fonction = fe.id
group by ve.id_voyage
order by id_voyage;


-- Vue Bénéfice total par voyage
create view vue_benefice_total_voyage as
select vbtav.id_voyage, vbtav.benefice_activite_voyage, vsev.salaire_total, (vbtav.benefice_activite_voyage - vsev.salaire_total) as benefice_voyage
    from vue_benefice_total_activite_voyage vbtav
join vue_salaire_employe_voyage vsev on vsev.id_voyage = vbtav.id_voyage
order by id_voyage;


select * from vue_benefice_total_voyage where benefice_voyage between 2000000 and 5000000;



create view vue_reste_activite_complet_voyage as
    select a.id as id_activite, a.nom as nom_activite, vrav.quantite_reste as quantite_reste
from vue_reste_activite_voyage as vrav
join activite a on a.id = vrav.id_activite;

-------------
create view vue_voyage_complet_benefice_total as
select vvc.id as id_voyage, vvc.id_bouquet, vvc.nom_bouquet as nom_bouquet, vvc.id_duree, vvc.nom, vvc.id_categorie_lieu, vvc.nom_categorie_lieu as nom_categorie_lieu,
       vbtv.benefice_voyage as benefice_voyage
from vue_voyage_complet as vvc
join vue_benefice_total_voyage as vbtv on vvc.id=vbtv.id_voyage;
-------------


create view employe_complet as
WITH derniere_date AS (
    SELECT id_emp, MAX(date_embauche) AS derniere_date_embauche
    FROM embauche_employe
    GROUP BY id_emp
)
SELECT e.id, e.nom, ee.id_fonction, fe.nom as nom_fonction, fe.salaire_horaire, dd.derniere_date_embauche
FROM employe e
         JOIN embauche_employe ee ON e.id = ee.id_emp
         JOIN fonction_employe fe ON ee.id_fonction = fe.id
         JOIN derniere_date dd ON e.id = dd.id_emp AND ee.date_embauche = dd.derniere_date_embauche
ORDER BY dd.derniere_date_embauche DESC;


SELECT EXTRACT(YEAR FROM AGE('2024-01-23 15:27:34'::timestamp, '2004-01-22 15:27:34'::timestamp));


create view vue_liste_personnel as
select
    ec.id, ec.nom, ec.id_fonction, ec.nom_fonction, ec.salaire_horaire, ec.derniere_date_embauche,
    EXTRACT(YEAR FROM AGE(now(), ec.derniere_date_embauche))  as annees_service,
    gf.id as id_grade, gf.nom as nom_grade, gf.plage_anciennete, gf.coeff_taux_horaire, ec.salaire_horaire as salaire_de_base,
    (gf.coeff_taux_horaire * ec.salaire_horaire) as salaire_horaire_actuel
from employe_complet ec
join grade_fonction gf on gf.plage_anciennete @> EXTRACT(YEAR FROM AGE(now(), ec.derniere_date_embauche))::int



