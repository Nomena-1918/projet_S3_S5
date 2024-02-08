-- create database voyage_db;
set timezone = 'Indian/Antananarivo';
create extension if not exists btree_gist;
create table activite(
    id serial primary key,
    nom varchar(100) unique not null
);
alter table activite add constraint unique_activite unique (nom);


create table bouquet(
    id serial primary key,
    nom varchar(100) unique not null
);
alter table bouquet add constraint unique_bouquet unique (nom);


CREATE TABLE categorie_lieu (
   id serial PRIMARY KEY,
   nom varchar(100) unique not null
);
alter table categorie_lieu add constraint unique_categorie_lieu unique (nom);


CREATE TABLE type_duree (
    id serial PRIMARY KEY,
    nom varchar(100) UNIQUE NOT NULL,
    intervaljour int4range,
    EXCLUDE USING gist (intervaljour WITH &&)
);
alter table type_duree add constraint unique_type_duree unique (nom);
alter table type_duree add constraint interval_jour_lower_positif check (lower(intervaljour) > 0);
alter table type_duree add constraint interval_jour_upper_positif check (upper(intervaljour) > 0);


create table voyage(
    id serial PRIMARY KEY,
    id_bouquet int references bouquet(id),
    id_duree int references type_duree(id),
    id_categorie_lieu int references categorie_lieu(id)
);
alter table voyage add constraint
    unique_bouquet_duree_categ_lieu unique (id_bouquet, id_duree, id_categorie_lieu);


CREATE TABLE voyage_activite (
    id serial PRIMARY KEY,
    id_voyage int references voyage(id),
    id_activite integer,
    nombre integer NOT NULL CHECK (nombre > 0),
    CONSTRAINT bouquet_activite_id_activite_fkey FOREIGN KEY (id_activite) REFERENCES activite(id),
    CONSTRAINT bouquet_activite_id_bouquet_fkey FOREIGN KEY (id_voyage) REFERENCES bouquet(id)
);
alter table voyage_activite add constraint unique_voyage_activite unique (id_voyage, id_activite);
alter table voyage_activite alter column id_voyage set not null;
alter table voyage_activite alter column id_activite set not null;


CREATE TABLE entree_activite (
    id serial PRIMARY KEY,
    id_activite integer REFERENCES activite(id),
    prix_unitaire decimal check ( prix_unitaire>0 ) not null,
    quantite integer check ( quantite > 0 ) not null,
    date_heure_entree timestamp default now()
);

alter table entree_activite
    add constraint unique_entree_activite
        unique (id_activite, date_heure_entree);


create table genre(
    id serial primary key,
    nom varchar(100) not null
);
 alter table genre add constraint unique_sexe unique (nom);



create table client(
   id serial primary key,
   nom varchar(100) not null,
   id_sexe int references genre(id)
);
alter table client add constraint unique_client unique (nom);



CREATE TABLE reservation_voyage (
    id serial PRIMARY KEY,
    id_voyage int references voyage(id),
    nombre_billet integer NOT NULL CHECK (nombre_billet > 0),
    id_client int not null references client(id)
);
alter table reservation_voyage add column date_reservation timestamp default now();


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
CREATE or replace VIEW vue_reste_activite_voyage as
select
    vqea.id_activite, --a.nom
    (vqea.quantite_total - coalesce(vqsa.nombre_billet_reserves,0)) as quantite_reste
    from vue_quantite_entree_activite vqea
             --join activite a on a.id = vqea.id_activite
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


create table candidat(
    id serial primary key,
    nom varchar(100) not null,
    prenom varchar(100) not null,
    id_sexe int not null references genre(id),
    dtn date not null check ( dtn < now() )
);
alter table candidat add constraint unique_employe unique (nom, prenom, id_sexe, dtn);


create table prix_vente_activite(
    id serial primary key,
    id_activite int references activite(id),
    prix_vente decimal not null check ( prix_vente>0 ),
    date_heure timestamp default now()
);

alter table prix_vente_activite
    add constraint unique_prix_vente_activite
        unique (id_activite, prix_vente, date_heure);


create table voyage_employe(
    id serial primary key,
    id_voyage int references voyage(id),
    id_emp int references candidat(id),
    heures_travail int not null check ( heures_travail > 0 )
);
alter table voyage_employe add constraint unique_voyage_emp unique (id_emp, id_voyage);

-- Vue Prix de vente par activité
create or replace view vue_prix_vente_activite as
WITH prix_recents AS (
    SELECT id_activite, MAX(date_heure) AS date_heure_max
    FROM prix_vente_activite
    GROUP BY id_activite
)
SELECT
        pv.id,
        pv.id_activite,
        a.nom as nom_activite,
        pv.prix_vente,
        pv.date_heure
FROM prix_vente_activite pv
         JOIN activite a ON a.id = pv.id_activite
         JOIN prix_recents pr ON pr.id_activite = pv.id_activite AND pr.date_heure_max = pv.date_heure
ORDER BY a.nom;





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
alter table fonction_employe add constraint unique_fonction_employe unique (nom, salaire_horaire);



create table grade_fonction(
    id serial primary key,
    nom varchar(100) unique not null,
    coeff_taux_horaire decimal not null check ( coeff_taux_horaire > 0 ),
    plage_anciennete int4range,
    EXCLUDE USING gist (plage_anciennete WITH &&)
);
alter table grade_fonction add constraint unique_grade_fonction unique (nom, coeff_taux_horaire, plage_anciennete);

create table embauche_employe(
    id serial primary key,
    id_emp int not null references candidat(id),
    id_fonction int not null references fonction_employe(id),
    date_embauche date not null check ( date_embauche <= now() )
);
alter table embauche_employe add constraint unique_embauche_employe unique (id_emp, id_fonction, date_embauche);


-- Vue salaire total employé par voyage
create view vue_salaire_employe_voyage as
select ve.id_voyage,  sum(fe.salaire_horaire*heures_travail) as salaire_total
from voyage_employe ve
join candidat e on ve.id_emp = e.id
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
select row_number() over () as id, vvc.id as id_voyage, vvc.id_bouquet, vvc.nom_bouquet as nom_bouquet, vvc.id_duree, vvc.nom, vvc.id_categorie_lieu, vvc.nom_categorie_lieu as nom_categorie_lieu,
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
FROM candidat e
         JOIN embauche_employe ee ON e.id = ee.id_emp
         JOIN fonction_employe fe ON ee.id_fonction = fe.id
         JOIN derniere_date dd ON e.id = dd.id_emp AND ee.date_embauche = dd.derniere_date_embauche
ORDER BY dd.derniere_date_embauche DESC;


SELECT EXTRACT(YEAR FROM AGE('2024-01-23 15:27:34'::timestamp, '2004-01-22 15:27:34'::timestamp));


create view vue_grade_fonction as
    select gf.*, lower(gf.plage_anciennete) as debut_ancien, upper(gf.plage_anciennete) as fin_ancien
        from grade_fonction gf;


create view vue_liste_personnel as
select
    row_number() over () as id_row, ec.id, ec.nom, ec.id_fonction, ec.nom_fonction, ec.salaire_horaire, ec.derniere_date_embauche,
    EXTRACT(YEAR FROM AGE(now(), ec.derniere_date_embauche))  as annees_service,
    gf.id as id_grade, gf.nom as nom_grade, gf.plage_anciennete, gf.coeff_taux_horaire, ec.salaire_horaire as salaire_de_base,
    (gf.coeff_taux_horaire * ec.salaire_horaire) as salaire_horaire_actuel
from employe_complet ec
join vue_grade_fonction gf on gf.plage_anciennete @> EXTRACT(YEAR FROM AGE(now(), ec.derniere_date_embauche))::int;


-- Stats vente d'activités par genre
create view vue_resume_reservation_client_genre as
select row_number() over () as id,
       rv.id as id_reservation, rv.id_client, c.nom as nom_client,
       s.id as id_sexe, s.nom as nom_sexe,
       rv.id_voyage, vabn.id_activite,
       vabn.nom_activite, vabn.nombre as nombre_activite,
       vpva.prix_vente, (vabn.nombre*vpva.prix_vente) as prix_vente_total
    from reservation_voyage rv
join client c on rv.id_client = c.id
join vue_activite_bouquet_nombre vabn on rv.id_voyage = vabn.id_voyage
join vue_prix_vente_activite vpva on vpva.id_activite = vabn.id_activite
join genre s on c.id_sexe = s.id;

select * from vue_resume_reservation_client_genre where id_sexe = 1;
select * from vue_resume_reservation_client_genre where id_sexe = 2;


create view vue_stats_genre as
select id_sexe, id_activite, nom_activite, sum(nombre_activite) as nombre, sum(prix_vente_total) as prix_total
    from vue_resume_reservation_client_genre vrscg
group by id_sexe, id_activite, nom_activite
order by id_sexe;
